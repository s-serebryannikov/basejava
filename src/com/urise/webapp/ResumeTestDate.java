package com.urise.webapp;

import com.urise.webapp.model.*;
import com.urise.webapp.util.DateUtil;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static com.urise.webapp.util.DateUtil.NOW;

public class ResumeTestDate {
//    public static void main(String[] args) {
//        Resume resume = createResume(new Resume("uuid1", "Григорий Кислин"));
//        System.out.println(resume.getFullName() + '\n');
//        Map<ContactType, String> contacts = resume.getContacts();
//        for (Map.Entry<ContactType, String> contact : contacts.entrySet()) {
//            System.out.println(contact.getKey().getTitle() + " - " + contact.getValue());
//        }
//        System.out.println();
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
//
//        Map<SectionType, AbstractSection> sections = resume.getSections();
//        for (Map.Entry<SectionType, AbstractSection> section : sections.entrySet()) {
//            SectionType sectionTypeKey = section.getKey();
//            AbstractSection sectionTypeValue = section.getValue();
//            switch (sectionTypeKey) {
//                case OBJECTIVE, PERSONAL -> {
//                    TextSection textSection = (TextSection) sectionTypeValue;
//                    System.out.println(section.getKey().getTitle() + '\n' + textSection.getContent() + '\n');
//                }
//                case ACHIEVEMENT, QUALIFICATION -> {
//                    ListSection listSectionText = (ListSection) sectionTypeValue;
//                    System.out.println(section.getKey().getTitle() + '\n');
//                    for (String str : listSectionText.getContent()) {
//                        System.out.println(str);
//                    }
//                    System.out.println();
//                }
//                case EXPERIENCE, EDUCATION -> {
//                    OrganizationSection organizationSection = (OrganizationSection) sectionTypeValue;
//                    System.out.println(section.getKey().getTitle() + '\n');
//                    for (Organization organization : organizationSection.getOrganizations()) {
//                        Link titleLink = organization.getHomePage();
//                        System.out.println(titleLink.getName());
//                        for (Organization.Position periods : organization.getPositions()) {
//                            System.out.println(formatter.format(periods.getStartDate()) + " - " + formatter.format(periods.getEndDate()));
//                            if (periods.getTitle() != null) System.out.println(periods.getTitle());
//                            System.out.println(periods.getDiscription());
//                        }
//                        System.out.println();
//                    }
//                }
//            }
//        }
//    }

    public static Resume createResume(String uuid,String fullName) {

        Resume resume = new Resume(uuid,fullName);
        resume.contacts.put(ContactType.PHONE_NUMBER, "+7(921) 855-0482");
        resume.contacts.put(ContactType.SKYPE, "grigory.kislin");
        resume.contacts.put(ContactType.MAIL, "gkislin@yandex.ru");
        resume.contacts.put(ContactType.LINKED_IN, "Профиль LinkedIn");
        resume.contacts.put(ContactType.GITHUB, "Профиль GitHub");
        resume.contacts.put(ContactType.STACKOVERFLOW, "Профиль Stackoverflow");

        TextSection personal = new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        TextSection objective = new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");

        List<String> achievementList = new ArrayList<>();
        achievementList.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        achievementList.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievementList.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        achievementList.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievementList.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        achievementList.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        ListSection achievement = new ListSection(achievementList);

        List<String> qualificationList = new ArrayList<>();
        qualificationList.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualificationList.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualificationList.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,");
        qualificationList.add("MySQL, SQLite, MS SQL, HSQLDB");
        qualificationList.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,");
        qualificationList.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,");
        ListSection qualification = new ListSection(qualificationList);

        List<Organization> organizationsList = new ArrayList<>();
        List<Organization.Position> periodsListOnlineProject = new ArrayList<>();
        periodsListOnlineProject.add(new Organization.Position(DateUtil.of(2013, Month.NOVEMBER), NOW, "Автор проекта", "Создание, организация и проведение Java онлайн проектов и стажировок."));
        periodsListOnlineProject.add(new Organization.Position(DateUtil.of(2015, Month.NOVEMBER), NOW, "Ментор", "Ревью кода, менторство."));
        organizationsList.add(new Organization(new Link("Java Online Projects", "https://javaops.ru/"), periodsListOnlineProject));

        List<Organization.Position> periodsListWrike = new ArrayList<>();
        periodsListWrike.add(new Organization.Position(DateUtil.of(2014, Month.NOVEMBER), DateUtil.of(2016, Month.JANUARY), "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike"));
        organizationsList.add(new Organization(new Link("Wrike", "https://www.wrike.com/vm/"), periodsListWrike));

        List<Organization.Position> periodsListRIT = new ArrayList<>();
        periodsListRIT.add(new Organization.Position(DateUtil.of(2012, Month.APRIL), DateUtil.of(2014, Month.NOVEMBER), "Java Архитектор", "Организация процесса разработки системы ERP для разных окружений"));
        organizationsList.add(new Organization(new Link("Wrike", "https://www.RIT.com/vm/"), periodsListRIT));

        OrganizationSection experience = new OrganizationSection(organizationsList);

        List<Organization> educationList = new ArrayList<>();
        List<Organization.Position> periodsListCousera = new ArrayList<>();
        periodsListCousera.add(new Organization.Position(DateUtil.of(2013, Month.MARCH), DateUtil.of(2013, Month.MAY), "", "\"Functional Programming Principles in Scala\" by Martin Odersky"));
        educationList.add(new Organization(new Link("Coursera", "https://www.coursera.org/learn/scala-functional-programming"), periodsListCousera));

        List<Organization.Position> periodsListLuxoft = new ArrayList<>();
        periodsListLuxoft.add(new Organization.Position(DateUtil.of(2011, Month.MARCH), DateUtil.of(2011, Month.APRIL), "", "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\""));
        educationList.add(new Organization(new Link("Luxoft", "https://www.luxoft-training.ru/kurs/obektno-orientirovannyy_analiz_i_proektirovanie_na_uml.html"), periodsListLuxoft));

        List<Organization.Position> periodsListSiemens = new ArrayList<>();
        periodsListSiemens.add(new Organization.Position(DateUtil.of(2005, Month.JANUARY), DateUtil.of(2005, Month.APRIL), "", "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\""));
        educationList.add(new Organization(new Link("Siemens AG", "https://new.siemens.com/ru/ru.html"), periodsListSiemens));
        OrganizationSection education = new OrganizationSection(educationList);

        resume.sections.put(SectionType.OBJECTIVE, objective);
        resume.sections.put(SectionType.PERSONAL, personal);
        resume.sections.put(SectionType.ACHIEVEMENT, achievement);
        resume.sections.put(SectionType.QUALIFICATION, qualification);
        resume.sections.put(SectionType.EXPERIENCE, experience);
        resume.sections.put(SectionType.EDUCATION, education);

        return resume;
    }
}
