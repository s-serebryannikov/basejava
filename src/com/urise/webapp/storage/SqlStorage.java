package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.util.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public void save(Resume r) {
        sqlHelper.execute("INSERT INTO resume(uuid, full_name) VALUES (?,?)",ps -> {
            try{
                ps.setString(1,r.getUuid());
                ps.setString(2,r.getFullName());
                ps.execute();
            } catch (SQLException e) {
                throw new ExistStorageException(r.getUuid());
            }
                return null;
        });
    }

    @Override
    public Resume get(String uuid) {
       return sqlHelper.execute("SELECT * FROM resume r WHERE r.uuid = ?", ps -> {
           ps.setString(1,uuid);
            ResultSet rs = ps.executeQuery();
            if(!rs.next()){
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid,rs.getString("full_name"));
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume r WHERE r.uuid = ?", ps -> {
            ps.setString(1,uuid);
            if(ps.executeUpdate()==0){
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public void update(Resume r) {
        sqlHelper.execute("UPDATE resume SET full_name = ? WHERE uuid = ?", ps -> {
            ps.setString(1,r.getFullName());
            ps.setString(2,r.getUuid());
            if(ps.executeUpdate()==0){
                throw new NotExistStorageException(r.getUuid());
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.execute("SELECT trim(uuid) AS uuid, full_name FROM resume r", ps -> {
            List<Resume> resumes = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                resumes.add(new Resume(rs.getString("uuid"),rs.getString("full_name")));
            }
            resumes.sort((r1, r2) -> r1.getFullName().compareTo(r2.getFullName()) != 0 ? r1.getFullName().compareTo(r2.getFullName()) : r1.getUuid().compareTo(r2.getUuid()));
            return resumes;
        });
    }

    @Override
    public int size() {
        return getAllSorted().size();
    }
}
