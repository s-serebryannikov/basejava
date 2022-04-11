package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class ResumeTestDate {
    public static void main(String[] args) {
        Resume resume = new Resume("uuid1","Григорий Кислин");

        resume.contacts.put(ContactType.PHONE_NUMBER,"+7(921) 855-0482");
        resume.contacts.put(ContactType.SKYPE,"grigory.kislin");
        resume.contacts.put(ContactType.MAIL,"gkislin@yandex.ru");
        resume.contacts.put(ContactType.LINKED_IN,"Профиль LinkedIn");
        resume.contacts.put(ContactType.GITHUB,"Профиль GitHub");
        resume.contacts.put(ContactType.STACKOVERFLOW,"Профиль Stackoverflow");

        List<String> achievement = new ArrayList<>();
        achievement.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        achievement.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievement.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        achievement.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievement.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        achievement.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");

        List<String> qualification = new ArrayList<>();
        qualification.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualification.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualification.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,");
        qualification.add("MySQL, SQLite, MS SQL, HSQLDB");
        qualification.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,");
        qualification.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,");

        List<Company> companies = new ArrayList<>();
        companies.add(new Company("Java Online Projects","Java Online Projects","https://javaops.ru/",LocalDate.of(2013, Month.NOVEMBER,1), LocalDate.now(),"Создание, организация и проведение Java онлайн проектов и стажировок."));
        companies.add(new Company("Wrike","Wrike","https://www.wrike.com/vm/",LocalDate.of(2014, Month.NOVEMBER,1), LocalDate.of(2016, Month.JANUARY,1),"Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."));
        companies.add(new Company("RIT Center","RIT Center",null,LocalDate.of(2012, Month.APRIL,1), LocalDate.of(2014, Month.NOVEMBER,1),"Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python"));

        List<Company> education = new ArrayList<>();
        education.add(new Company("Coursera","Coursera","https://www.coursera.org/learn/scala-functional-programming",LocalDate.of(2013, Month.MARCH,1), LocalDate.of(2013, Month.MAY,1),"\"Functional Programming Principles in Scala\" by Martin Odersky"));
        education.add(new Company("Luxoft","Luxoft","https://www.luxoft-training.ru/kurs/obektno-orientirovannyy_analiz_i_proektirovanie_na_uml.html",LocalDate.of(2011, Month.MARCH,1), LocalDate.of(2011, Month.APRIL,1),"Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\""));
        education.add(new Company("Siemens AG","Siemens AG","https://new.siemens.com/ru/ru.html",LocalDate.of(2005, Month.JANUARY,1), LocalDate.of(2005, Month.APRIL,1),"3 месяца обучения мобильным IN сетям (Берлин)"));


        resume.sections.put(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        resume.sections.put(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
        resume.sections.put(SectionType.ACHIEVEMENT,new ListSection(achievement));
        resume.sections.put(SectionType.QUALIFICATION,new ListSection(achievement));
        resume.sections.put(SectionType.EXPERIENCE, new CompanySection(companies));
        resume.sections.put(SectionType.EDUCATION, new CompanySection(education));


        System.out.println(resume);

    }
}
