package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.AbstractSection;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.SectionType;
import com.urise.webapp.sql.SqlHelper;
import com.urise.webapp.util.JsonParser;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(conn -> {
            Resume r;
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume WHERE uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                r = new Resume(uuid, rs.getString("full_name"));
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact WHERE resume_uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addContact(rs, r);
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section WHERE resume_uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addSection(rs, r);
                }
            }

            return r;
        });
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                if (ps.executeUpdate() != 1) {
                    throw new NotExistStorageException(r.getUuid());
                }
            }
            deleteContacts(conn, r);
            deleteSections(conn, r);
            insertContacts(conn, r);
            insertSections(conn, r);
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                        ps.setString(1, r.getUuid());
                        ps.setString(2, r.getFullName());
                        ps.execute();
                    }
                    insertContacts(conn, r);
                    insertSections(conn, r);
                    return null;
                }
        );
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid=?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(conn -> {
            Map<String, Resume> resumes = new LinkedHashMap<>();

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    resumes.put(uuid, new Resume(uuid, rs.getString("full_name")));
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Resume r = resumes.get(rs.getString("resume_uuid"));
                    addContact(rs, r);
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Resume r = resumes.get(rs.getString("resume_uuid"));
                    addSection(rs, r);
                }
            }

            return new ArrayList<>(resumes.values());
        });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) FROM resume", st -> {
            ResultSet rs = st.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private void insertContacts(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertSections(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> e : r.getSections().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                AbstractSection section = e.getValue();
                ps.setString(3, JsonParser.write(section, AbstractSection.class));
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteContacts(Connection conn, Resume r) throws SQLException {
        deleteAttributes(conn, r, "DELETE  FROM contact WHERE resume_uuid=?");
    }

    private void deleteSections(Connection conn, Resume r) throws SQLException {
        deleteAttributes(conn, r, "DELETE  FROM section WHERE resume_uuid=?");
    }

    private void deleteAttributes(Connection conn, Resume r, String sql) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, r.getUuid());
            ps.execute();
        }
    }

    private void addContact(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            r.addContact(ContactType.valueOf(rs.getString("type")), value);
        }
    }

    private void addSection(ResultSet rs, Resume r) throws SQLException {
        String content = rs.getString("value");
        if (content != null) {
            SectionType type = SectionType.valueOf(rs.getString("type"));
            r.addSection(type, JsonParser.read(content, AbstractSection.class));
        }
    }
//    SqlHelper sqlHelper;
//
//    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
//        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
//    }
//
//    @Override
//    public void clear() {
//        sqlHelper.execute("DELETE FROM resume", PreparedStatement::execute);
//    }
//
//    @Override
//    public void save(Resume r) {
//        sqlHelper.transactionalExecute(conn -> {
//                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
//                        ps.setString(1, r.getUuid());
//                        ps.setString(2, r.getFullName());
//                        ps.execute();
//                    }
//                    insertContact(r, conn);
//                    insertSection(r, conn);
//                    return null;
//                }
//        );
//    }
//
//    @Override
//    public Resume get(String uuid) {
////        return sqlHelper.transactionalExecute(conn -> {
////            try (PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM resume r  LEFT JOIN contact c ON r.uuid = c.resume_uuid WHERE r.uuid = ?");
////                 PreparedStatement ps2 = conn.prepareStatement("SELECT type, value FROM section WHERE resume_uuid = ?")) {
////                ps1.setString(1, uuid);
////                ResultSet rs1 = ps1.executeQuery();
////                if (!rs1.next()) {
////                    throw new NotExistStorageException(uuid);
////                }
////                Resume r = new Resume(uuid, rs1.getString("full_name"));
////                do {
////                    addContact(rs1, r);
////                } while (rs1.next());
////                ps2.setString(1, uuid);
////                ResultSet rs2 = ps2.executeQuery();
////                while (rs2.next()) {
////                    addSection(rs2, r);
////                }
////                return r;
////            }
////        });
//        return sqlHelper.transactionalExecute(conn -> {
//            Resume resume;
//            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume WHERE uuid = ?")) {
//                ps.setString(1, uuid);
//                ResultSet rs = ps.executeQuery();
//                if (!rs.next()) {
//                    throw new NotExistStorageException(uuid);
//                }
//                resume = new Resume(uuid, rs.getString("full_name"));
//            }
//            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact WHERE resume_uuid = ?")) {
//                ps.setString(1, uuid);
//                ResultSet rs = ps.executeQuery();
//                while (rs.next()) {
//                    addContact(rs, resume);
//                }
//            }
//            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section WHERE resume_uuid = ?")) {
//                ps.setString(1, uuid);
//                ResultSet rs = ps.executeQuery();
//                while (rs.next()) {
//                    addSection(rs, resume);
//                }
//                return resume;
//            }
//        });
//    }
//
//
//    @Override
//    public void delete(String uuid) {
//        sqlHelper.execute("DELETE FROM resume r WHERE r.uuid = ?", ps -> {
//            ps.setString(1, uuid);
//            if (ps.executeUpdate() == 0) {
//                throw new NotExistStorageException(uuid);
//            }
//            return null;
//        });
//    }
//
//    @Override
//    public void update(Resume r) {
//        sqlHelper.transactionalExecute(conn -> {
//                    try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
//                        ps.setString(1, r.getFullName());
//                        ps.setString(2, r.getUuid());
//                        ps.execute();
//                        if (ps.executeUpdate() == 0) {
//                            throw new NotExistStorageException("Resume " + r.getUuid() + " not exist");
//                        }
//                    }
//                    deleteContact(r);
//                    insertContact(r, conn);
//                    deleteSection(r);
//                    insertSection(r, conn);
//                    return null;
//                }
//        );
//    }
//
//    @Override
//    public List<Resume> getAllSorted() {
//        List<Resume> resumes = new ArrayList<>();
//        sqlHelper.transactionalExecute(connection -> {
//            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid")) {
//                ResultSet rs = ps.executeQuery();
//                while (rs.next()) {
//                    resumes.add(new Resume(rs.getString(1).trim(), rs.getString(2)));
//                }
//            }
//            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM contact WHERE resume_uuid = ?")) {
//                for (Resume resume : resumes) {
//                    ps.setString(1, resume.getUuid());
//                    ResultSet rs = ps.executeQuery();
//                    while (rs.next()) {
//                        addContact(rs, resume);
//                    }
//                }
//            }
//            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM section WHERE resume_uuid = ?")) {
//                for (Resume resume : resumes) {
//                    ps.setString(1, resume.getUuid());
//                    ResultSet rs = ps.executeQuery();
//                    while (rs.next()) {
//                        addSection(rs, resume);
//                    }
//                }
//            }
//            return null;
//        });
//        return resumes;
//    }
//
//    @Override
//    public int size() {
//        return sqlHelper.execute("SELECT COUNT(*) AS count FROM resume", ps -> {
//            ResultSet rs = ps.executeQuery();
//            return rs.next() ? rs.getInt(1) : 0;
//        });
//    }
//
//    private void insertContact(Resume r, Connection conn) throws SQLException {
//        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
//            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
//                ps.setString(1, r.getUuid());
//                ps.setString(2, e.getKey().name());
//                ps.setString(3, e.getValue());
//                ps.addBatch();
//            }
//            ps.executeBatch();
//        }
//    }
//
//    private void insertSection(Resume r, Connection conn) throws SQLException {
//        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
//            for (Map.Entry<SectionType, AbstractSection> e : r.getSections().entrySet()) {
//                ps.setString(1, r.getUuid());
//                ps.setString(2, e.getKey().name());
//
//                SectionType sectionTypeKey = e.getKey();
//                AbstractSection sectionTypeValue = e.getValue();
//                switch (sectionTypeKey) {
//                    case OBJECTIVE, PERSONAL -> {
//                        ps.setString(3, sectionTypeValue.toString());
//                    }
//                    case ACHIEVEMENT, QUALIFICATION -> {
//                        ps.setString(3, String.join("\n", ((ListSection) sectionTypeValue).getContent()));
//                    }
//                }
//                ps.addBatch();
//            }
//            ps.executeBatch();
//        }
//    }
//
//    private void addContact(ResultSet rs, Resume r) throws SQLException {
//        String value = rs.getString("value");
//        if (value != null) {
//            r.addContact(ContactType.valueOf(rs.getString("type")), value);
//        }
//    }
//
//    private void addSection(ResultSet rs, Resume r) throws SQLException {
//        String value = rs.getString("value");
//        if (value != null) {
//            SectionType sectionType = SectionType.valueOf(rs.getString("type"));
//            switch (sectionType) {
//                case PERSONAL, OBJECTIVE -> {
//                    r.addSection(sectionType, new TextSection(value));
//                }
//                case ACHIEVEMENT, QUALIFICATION -> {
//                    List<String> listValue = Arrays.asList(value.split("\n"));
//                    r.addSection(sectionType, new ListSection(listValue));
//                }
//            }
//        }
//    }
//
//    private void deleteContact(Resume r) {
//        sqlHelper.execute("DELETE FROM contact WHERE resume_uuid = ?", ps -> {
//            ps.setString(1, r.getUuid());
//            if (ps.executeUpdate() == 0) {
//                throw new NotExistStorageException(r.getUuid());
//            }
//            return null;
//        });
//    }
//
//    private void deleteSection(Resume r) throws SQLException {
//        sqlHelper.execute("DELETE FROM section WHERE resume_uuid = ?", ps -> {
//            ps.setString(1, r.getUuid());
//            if (ps.executeUpdate() == 0) {
//                throw new NotExistStorageException(r.getUuid());
//            }
//            return null;
//        });
//    }
}
