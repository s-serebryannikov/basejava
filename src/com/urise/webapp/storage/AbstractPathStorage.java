package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AbstractPathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private StorageStrategy storageStrategy;

    protected AbstractPathStorage(String dir,StorageStrategy storageStrategy) {
        directory = Paths.get(dir);
        this.storageStrategy = storageStrategy;
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    @Override
    protected List<Resume> doCopyAll() {

        try {
            return Files.list(directory).map(this::doGet).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Directory read error",  directory.getFileName().toString(), e);
        }

    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Directory read error",  directory.getFileName().toString(), e);
        }
    }

    @Override
    protected void doSave(Resume r, Path directory) {
        try {
            Files.createFile(directory);
        } catch (IOException e) {
            throw new StorageException("Couldn't create directory " + directory, directory.getFileName().toString(), e);
        }
        doUpdate(r, directory);
    }

    @Override
    protected Resume doGet(Path directory) {
        try {
            return storageStrategy.doRead(new BufferedInputStream(Files.newInputStream(directory)));
        } catch (IOException e) {
            throw new StorageException("Path reading error", directory.getFileName().toString(), e);
        }
    }

    @Override
    protected void doDelete(Path directory) {
        try {
            Files.delete(directory);
        } catch (IOException e) {
            throw new StorageException("File delete error", directory.getFileName().toString(), e);
        }
    }

    @Override
    protected void doUpdate(Resume r, Path directory) {
        try {
            storageStrategy.doWrite(r, new BufferedOutputStream(Files.newOutputStream(directory)));
        } catch (IOException e) {
            throw new StorageException("Path writing error",r.getUuid(), null);
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("Directory no files", null);
        }
    }

    @Override
    protected boolean isExist(Path directory) {
        return Files.isRegularFile(directory);
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

}
