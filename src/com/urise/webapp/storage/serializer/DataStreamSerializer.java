package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;
import com.urise.webapp.util.DateUtil;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            Map<SectionType, AbstractSection> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> section : sections.entrySet()) {
                SectionType sectionType = section.getKey();
                dos.writeUTF(String.valueOf(sectionType));
                AbstractSection sectionTypeValue = section.getValue();
                switch (sectionType) {
                    case OBJECTIVE, PERSONAL -> {
                        dos.writeUTF(((TextSection) sectionTypeValue).getContent());
                    }
                    case ACHIEVEMENT, QUALIFICATION -> {
                        List<String> itemList = ((ListSection) sectionTypeValue).getContent();
                        dos.writeInt(itemList.size());
                        for (String content : itemList) {
                            dos.writeUTF(content);
                        }
                    }
                    case EXPERIENCE, EDUCATION -> {
                        List<Organization> organizationsList = (((OrganizationSection) sectionTypeValue)).getOrganizations();
                        dos.writeInt(organizationsList.size());
                        for (Organization organization : organizationsList) {
                            Link titleLink = organization.getHomePage();
                            dos.writeUTF(titleLink.getName());
                            dos.writeUTF(titleLink.getUrl());
                            List<Organization.Position> positions = organization.getPositions();
                            dos.writeInt(positions.size());
                            for (Organization.Position position : positions) {
                                writeLocalDate(dos, position.getStartDate());
                                writeLocalDate(dos, position.getEndDate());
                                dos.writeUTF(position.getTitle());
                                dos.writeUTF(position.getDiscription());
                            }
                        }
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
                    case ACHIEVEMENT, QUALIFICATION -> {
                        List<String> listSection = new ArrayList<>(sectionSize);
                        int itemSectionSize = dis.readInt();
                        for (int j = 0; j < itemSectionSize; j++) {
                            listSection.add(dis.readUTF());
                        }
                        resume.addSection(sectionType, new ListSection(listSection));
                    }
                    case EXPERIENCE, EDUCATION -> {
                        int organizationsListSize = dis.readInt();
                        List<Organization> organizationsList = new ArrayList<>(organizationsListSize);
                        for (int j = 0; j < organizationsListSize; j++) {
                            Link link = new Link(dis.readUTF(), dis.readUTF());
                            int positionSize = dis.readInt();
                            List<Organization.Position> periodsList = new ArrayList<>();
                            for (int k = 0; k < positionSize; k++) {
                                LocalDate startLocalDate = readLocalDate(dis);
                                LocalDate endLocalDate = readLocalDate(dis);
                                String title = dis.readUTF();
                                String discription = dis.readUTF();
                                periodsList.add(new Organization.Position(startLocalDate, endLocalDate, title, discription));
                            }
                            organizationsList.add(new Organization(link, periodsList));
                        }
                        resume.addSection(sectionType, new OrganizationSection(organizationsList));
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
}
