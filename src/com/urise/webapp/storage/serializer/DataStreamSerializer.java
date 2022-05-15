package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;
import com.urise.webapp.util.DateUtil;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());//write DataOutputStream uuid
            dos.writeUTF(r.getFullName());//write DataOutputStream fullname
            Map<ContactType, String> contacts = r.getContacts();//create Map contacts
            dos.writeInt(contacts.size());//write DataOutputStream contacts size
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());//write DataOutputStream contact title
                dos.writeUTF(entry.getValue());//write DataOutputStream contact info
            }

            Map<SectionType, AbstractSection> sections = r.getSections();//create Map sections
            dos.writeInt(sections.size());//write DataOutputStream sections size
            for (Map.Entry<SectionType, AbstractSection> section : sections.entrySet()) {
                SectionType sectionType = section.getKey();//write DataOutputStream section type
                dos.writeUTF(String.valueOf(sectionType));//write DataOutputStream section value
                AbstractSection sectionTypeValue = section.getValue();
                switch (sectionType) {
                    case OBJECTIVE, PERSONAL ->
                            dos.writeUTF(((TextSection) sectionTypeValue).getContent());//write DataOutputStream section TextType - content

                    case ACHIEVEMENT, QUALIFICATION -> {
                        List<String> itemList = ((ListSection) sectionTypeValue).getContent();//create List of ListSection
//                        dos.writeInt(itemList.size());//write DataOutputStream sections size
//                        for (String content : itemList) {
//                            dos.writeUTF(content);//write DataOutputStream ListSection  content
//                        }
                        writerCollection(dos, ((ListSection) sectionTypeValue).getContent(), dos::writeUTF);
                    }
                    case EXPERIENCE, EDUCATION -> {
//                        List<Organization> organizationsList = ((OrganizationSection) sectionTypeValue).getOrganizations();//create List of OrganizationSection
//                        dos.writeInt(organizationsList.size());//write DataOutputStream OrganizationSection size
//                        for (Organization organization : organizationsList) {
//                            Link titleLink = organization.getHomePage();
//                            dos.writeUTF(titleLink.getName());//write DataOutputStream Link - name
//                            if (titleLink.getUrl() == null) {
//                                dos.writeUTF("");
//                            } else dos.writeUTF(titleLink.getUrl());//write DataOutputStream Link - URL
//                            List<Organization.Position> positions = organization.getPositions();//create List of Position
//                            dos.writeInt(positions.size());//write DataOutputStream OrganizationSection size
//                            for (Organization.Position position : positions) {
//                                writeLocalDate(dos, position.getStartDate());//write DataOutputStream position start Date
//                                writeLocalDate(dos, position.getEndDate());//write DataOutputStream position end Date
//                                dos.writeUTF(position.getTitle());//write DataOutputStream position title
//                                if (position.getDiscription() == null) {
//                                    dos.writeUTF("");
//                                } else
//                                    dos.writeUTF(position.getDiscription());//write DataOutputStream position discription
//                            }
//                        }
                        writerCollection(dos, ((OrganizationSection) sectionTypeValue).getOrganizations(), organization -> {
                            dos.writeUTF(organization.getHomePage().getName());
                            if (organization.getHomePage().getUrl() == null) {
                                dos.writeUTF("");
                            } else dos.writeUTF(organization.getHomePage().getUrl());
                            writerCollection(dos, organization.getPositions(), position -> {
                                writeLocalDate(dos, position.getStartDate());//write DataOutputStream position start Date
                                writeLocalDate(dos, position.getEndDate());//write DataOutputStream position end Date
                                dos.writeUTF(position.getTitle());//write DataOutputStream position title
                                if (position.getDiscription() == null) {
                                    dos.writeUTF("");
                                } else dos.writeUTF(position.getDiscription());//write DataOutputStream position discription
                            });
                        });
                    }
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        Resume resume;
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            int sectionSize = dis.readInt();
            for (int i = 0; i < sectionSize; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case OBJECTIVE, PERSONAL -> resume.addSection(sectionType, new TextSection(dis.readUTF()));
                    case ACHIEVEMENT, QUALIFICATION ->
                            resume.addSection(sectionType, new ListSection(readList(dis, dis::readUTF)));
                    case EXPERIENCE, EDUCATION -> {
//                        for (int j = 0; j < organizationsListSize; j++) {
//                            Link link = new Link(dis.readUTF(), dis.readUTF());
//                            int positionSize = dis.readInt();
//                            List<Organization.Position> periodsList = new ArrayList<>();
//                            for (int k = 0; k < positionSize; k++) {
//                                LocalDate startLocalDate = readLocalDate(dis);
//                                LocalDate endLocalDate = readLocalDate(dis);
//                                String title = dis.readUTF();
//                                String discription = dis.readUTF();
//                                periodsList.add(new Organization.Position(startLocalDate, endLocalDate, title, discription));
//                            }
//                            organizationsList.add(new Organization(link, periodsList));
//                        }
//                        resume.addSection(sectionType, new OrganizationSection(organizationsList));

                        List<Organization> listOrg = new ArrayList<>();
                        readCollection(dis, () -> listOrg.add(new Organization(new Link(dis.readUTF(), dis.readUTF()),
                                readList(dis, () -> new Organization.Position(readLocalDate(dis), readLocalDate(dis), dis.readUTF(), dis.readUTF()))
                        )));
                        resume.addSection(sectionType, new OrganizationSection(listOrg));

                    }
                }
            }
        }
        return resume;
    }

    private void writeLocalDate(DataOutputStream dos, LocalDate ld) throws IOException {
        dos.writeInt(ld.getYear());
        dos.writeInt(ld.getMonth().getValue());
    }

    private LocalDate readLocalDate(DataInputStream dis) throws IOException {
        return DateUtil.of(dis.readInt(), Month.of(dis.readInt()));
    }

    private interface WriterType<T> {
        void writerItem(T sectionType) throws IOException;
    }


    private interface ReaderType {
        void read() throws IOException;
    }

    private interface ReaderSectionType<T> {
        T readItem() throws IOException;
    }

    private <T> void writerCollection(DataOutputStream dos, Collection<T> collection, WriterType<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T item : collection) {
            writer.writerItem(item);
        }
    }

    private void readCollection(DataInputStream dis, ReaderType readerType) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            readerType.read();
        }
    }

    private <T> List<T> readList(DataInputStream dis, ReaderSectionType<T> reader) throws IOException {
        List<T> list = new ArrayList<>();
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            list.add(reader.readItem());
        }
        return list;
    }
}
