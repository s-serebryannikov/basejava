//package com.urise.webapp.storage.serializer;
//
//import com.urise.webapp.model.*;
//import com.urise.webapp.util.DateUtil;
//
//import java.io.*;
//import java.time.LocalDate;
//import java.time.Month;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//public class DataStreamSerializer implements StreamSerializer {
//
//    @Override
//    public void doWrite(Resume r, OutputStream os) throws IOException {
//        try (DataOutputStream dos = new DataOutputStream(os)) {
//            dos.writeUTF(r.getUuid());//write DataOutputStream uuid
//            dos.writeUTF(r.getFullName());//write DataOutputStream fullname
//            writerCollection(dos, r.getContacts().entrySet(), contact -> {//create Map contacts
//                dos.writeUTF(contact.getKey().name());
//                dos.writeUTF(contact.getValue());
//            });
//
//            writerCollection(dos, r.getSections().entrySet(), section -> {
//                SectionType sectionType = section.getKey();//write DataOutputStream section type
//                dos.writeUTF(String.valueOf(sectionType));//write DataOutputStream section value
//                AbstractSection sectionTypeValue = section.getValue();
//                switch (sectionType) {
//                    case OBJECTIVE, PERSONAL ->
//                            dos.writeUTF(((TextSection) sectionTypeValue).getContent());//write DataOutputStream section TextType - content
//                    case ACHIEVEMENT, QUALIFICATION ->
//                            writerCollection(dos, ((ListSection) sectionTypeValue).getContent(), dos::writeUTF);
//                    case EXPERIENCE, EDUCATION ->
//                            writerCollection(dos, ((OrganizationSection) sectionTypeValue).getOrganizations(), organization -> {
//                                dos.writeUTF(organization.getHomePage().getName());//write DataOutputStream organization link name
//                                String linkUrl = organization.getHomePage().getUrl() == null ? "" : organization.getHomePage().getUrl();
//                                dos.writeUTF(linkUrl);//write DataOutputStream organization link URL
//                                writerCollection(dos, organization.getPositions(), position -> {
//                                    writeLocalDate(dos, position.getStartDate());//write DataOutputStream position start Date
//                                    writeLocalDate(dos, position.getEndDate());//write DataOutputStream position end Date
//                                    dos.writeUTF(position.getTitle());//write DataOutputStream position title
//                                    String descripton = position.getDiscription() == null ? "" : position.getDiscription();//write DataOutputStream position discription
//                                    dos.writeUTF(descripton);
//                                });
//                            });
//                }
//            });
//        }
//    }
//
//    @Override
//    public Resume doRead(InputStream is) throws IOException {
//        Resume resume;
//        try (DataInputStream dis = new DataInputStream(is)) {
//            String uuid = dis.readUTF();//read uuid resume
//            String fullName = dis.readUTF();//read fullName resume
//            resume = new Resume(uuid, fullName);//initialization filds resume
//            readCollection(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));//read fields Contact Type and add in resume
//            readCollection(dis, () -> {
//                SectionType sectionType = SectionType.valueOf(dis.readUTF());//read type sections
//                switch (sectionType) {
//                    case OBJECTIVE, PERSONAL ->
//                            resume.addSection(sectionType, new TextSection(dis.readUTF()));//read Text Section
//                    case ACHIEVEMENT, QUALIFICATION ->
//                            resume.addSection(sectionType, new ListSection(readList(dis, dis::readUTF)));//read List Section
//                    case EXPERIENCE, EDUCATION -> {
//                        List<Organization> listOrg = new ArrayList<>();
//                        readCollection(dis, () -> listOrg.add(new Organization(new Link(dis.readUTF(), dis.readUTF()),
//                                readList(dis, () -> new Organization.Position(readLocalDate(dis), readLocalDate(dis), dis.readUTF(), dis.readUTF()))
//                        )));
//                        resume.addSection(sectionType, new OrganizationSection(listOrg));//add in resume sectionType
//                    }
//                }
//            });
//        }
//        return resume;
//    }
//
//    private void writeLocalDate(DataOutputStream dos, LocalDate ld) throws IOException {
//        dos.writeInt(ld.getYear());
//        dos.writeInt(ld.getMonth().getValue());
//    }
//
//    private LocalDate readLocalDate(DataInputStream dis) throws IOException {
//        return DateUtil.of(dis.readInt(), Month.of(dis.readInt()));
//    }
//
//    private interface WriterType<T> {
//        void writerItem(T sectionType) throws IOException;
//    }
//
//    private interface ReaderType {
//        void read() throws IOException;
//    }
//
//    private interface ReaderSectionType<T> {
//        T readItem() throws IOException;
//    }
//
//    private <T> void writerCollection(DataOutputStream dos, Collection<T> collection, WriterType<T> writer) throws IOException {
//        dos.writeInt(collection.size());
//        for (T item : collection) {
//            writer.writerItem(item);
//        }
//    }
//
//    private void readCollection(DataInputStream dis, ReaderType readerType) throws IOException {
//        int size = dis.readInt();
//        for (int i = 0; i < size; i++) {
//            readerType.read();
//        }
//    }
//
//    private <T> List<T> readList(DataInputStream dis, ReaderSectionType<T> reader) throws IOException {
//        List<T> list = new ArrayList<>();
//        int size = dis.readInt();
//        for (int i = 0; i < size; i++) {
//            list.add(reader.readItem());
//        }
//        return list;
//    }
//}
