package com.urise.webapp;

import com.urise.webapp.model.*;
import com.urise.webapp.util.DateUtil;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.urise.webapp.util.DateUtil.NOW;

public class TestData {
    public static final String UUID_1 = UUID.randomUUID().toString();
    public static final String UUID_2 = UUID.randomUUID().toString();
    public static final String UUID_3 = UUID.randomUUID().toString();
    public static final String UUID_4 = UUID.randomUUID().toString();

    public static final Resume R1;
    public static final Resume R2;
    public static final Resume R3;
    public static final Resume R4;

    static {
        R1 = createResume(UUID_1, "Name1");
        R2 = createResume(UUID_2, "Name2");
        R3 = createResume(UUID_3, "Name3");
        R4 = createResume(UUID_4, "Name4");


//        R1.addContact(ContactType.PHONE_NUMBER, "+7(921) 855-0482");
//        R1.addContact(ContactType.SKYPE, "grigory.kislin");
//        R1.addContact(ContactType.MAIL, "gkislin@yandex.ru");
//        R1.addContact(ContactType.LINKEDIN, "Профиль LinkedIn");
//        R1.addContact(ContactType.GITHUB, "Профиль GitHub");
//        R1.addContact(ContactType.STATCKOVERFLOW, "Профиль Stackoverflow");
//
//        R1.addSection(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
//        R1.addSection(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
//        R1.addSection(SectionType.ACHIEVEMENT, new ListSection("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira"
//                , "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке:Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера."
//                , "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга."));
//        R1.addSection(SectionType.QUALIFICATION, new ListSection("EE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2"
//                , "Version control: Subversion, Git, Mercury, ClearCase, Perforce"
//                , "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle"));
//        R1.addSection(SectionType.EXPERIENCE,
//                new OrganizationSection(
//                        new Organization("Organization11", "http://Organization11.ru",
//                                new Organization.Position(DateUtil.of(2013, Month.NOVEMBER), "position1", "content1"),
//                                new Organization.Position(DateUtil.of(2001, Month.MARCH), DateUtil.of(2005, Month.JANUARY), "position2", "content2"))));
//        R1.addSection(SectionType.EXPERIENCE,
//                new OrganizationSection(
//                        new Organization("Organization2", "http://Organization2.ru",
//                                new Organization.Position(DateUtil.of(2015, Month.JANUARY), "position1", "content1"))));
//        R1.addSection(SectionType.EDUCATION,
//                new OrganizationSection(
//                        new Organization("Institute", null,
//                                new Organization.Position(DateUtil.of(1996, Month.MARCH), DateUtil.of(2000, Month.DECEMBER), "aspirant", null),
//                                new Organization.Position(DateUtil.of(2001, Month.MARCH), DateUtil.of(2001, Month.MARCH), "student", "IT facultet")),
//                        new Organization("Organization12", "http://Organization12.ru")));

//        R2.addContact(ContactType.MAIL, "mail1@mail.ru");
//        R2.addContact(ContactType.SKYPE, "skype2");
//        R2.addContact(ContactType.PHONE_NUMBER, "22222");
////
//        R3.addContact(ContactType.MAIL, "mail1@gmail.com");

//        R4.addContact(ContactType.PHONE_NUMBER, "44444");
//        R4.addContact(ContactType.SKYPE, "Skype");
    }
        public static Resume createResume (String uuid, String fullName){
            Resume resume = new Resume(uuid, fullName);
            resume.addContact(ContactType.PHONE_NUMBER, "+7(921) 855-0482");
            resume.addContact(ContactType.SKYPE, "grigory.kislin");
            resume.addContact(ContactType.MAIL, "gkislin@yandex.ru");
            resume.addContact(ContactType.LINKEDIN, "Профиль LinkedIn");
            resume.addContact(ContactType.GITHUB, "Профиль GitHub");
            resume.addContact(ContactType.STATCKOVERFLOW, "Профиль Stackoverflow");

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
        organizationsList.add(new Organization(new Link("RIT", "https://www.RIT.com/vm/"), periodsListRIT));

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

            resume.addSection(SectionType.OBJECTIVE, objective);
            resume.addSection(SectionType.PERSONAL, personal);
            resume.addSection(SectionType.ACHIEVEMENT, achievement);
            resume.addSection(SectionType.QUALIFICATION, qualification);
            resume.addSection(SectionType.EXPERIENCE, experience);
            resume.addSection(SectionType.EDUCATION, education);

            return resume;
        }
    }
