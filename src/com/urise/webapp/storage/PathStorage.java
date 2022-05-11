package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serializer.StreamSerializer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private StreamSerializer streamSerializer;

    protected PathStorage(String dir, StreamSerializer streamSerializer) {
        directory = Paths.get(dir);
        this.streamSerializer = streamSerializer;
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        return getFilesList().map(this::doGet).collect(Collectors.toList());
    }

    @Override
    public void clear() {
        getFilesList().forEach(this::doDelete);
    }

    @Override
    protected void doSave(Resume r, Path directory) {
        try {
            Files.createFile(directory);
        } catch (IOException e) {
            throw new StorageException("Couldn't create directory " + directory, getFileName(directory), e);
        }
        doUpdate(r, directory);
    }

    @Override
    protected Resume doGet(Path directory) {
        try {
            return streamSerializer.doRead(new BufferedInputStream(Files.newInputStream(directory)));
        } catch (IOException e) {
            throw new StorageException("Path reading error", getFileName(directory), e);
        }
    }

    @Override
    protected void doDelete(Path directory) {
        try {
            Files.delete(directory);
        } catch (IOException e) {
            throw new StorageException("File delete error", getFileName(directory), e);
        }
    }

    @Override
    protected void doUpdate(Resume r, Path directory) {
        try {
            streamSerializer.doWrite(r, new BufferedOutputStream(Files.newOutputStream(directory)));
        } catch (IOException e) {
            throw new StorageException("Path writing error", r.getUuid(), null);
        }
    }

    @Override
    public int size() {
        return (int) getFilesList().count();
    }

    @Override
    protected boolean isExist(Path directory) {
        return Files.isRegularFile(directory);
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    private String getFileName(Path path) {
        return path.getFileName().toString();
    }

    private Stream<Path> getFilesList() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Directory no files", e);
        }
    }
}
