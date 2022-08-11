package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;
import com.urise.webapp.util.DateUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private Storage storage = Config.get().getStorage();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
                r = storage.get(uuid);
                break;
            case "edit":
                if (uuid != null) {
                    r = storage.get(uuid);
                } else {
                    r = new Resume("", "");
                }
                for (SectionType type : SectionType.values()) {
                    AbstractSection section = r.getSection(type);
                    switch (type) {
                        case OBJECTIVE:
                        case PERSONAL:
                            if (section == null) {
                                r.addSection(type, new TextSection(""));
                            }
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATION:
                            if (section == null) {
                                r.addSection(type, new ListSection(""));
                            }
                            break;
                        case EDUCATION:
                        case EXPERIENCE:
                            OrganizationSection organizationSection = (OrganizationSection) section;
                            List<Organization> organizations = new ArrayList<>();
                            organizations.add(new Organization("", "", new Organization.Position()));
                            if (organizationSection != null) {
                                for (Organization org : organizationSection.getOrganizations()) {
                                    List<Organization.Position> positions = new ArrayList<>();
                                    positions.add(new Organization.Position());
                                    positions.addAll(org.getPositions());
                                    organizations.add(new Organization(org.getHomePage(), positions));
                                }
                                r.addSection(type, new OrganizationSection(organizations));
                            } else {
                                r.addSection(type, new OrganizationSection(new Organization("", "", new Organization.Position())));
                            }
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }

        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume r;
        if (uuid == null || uuid.length() == 0) {
            r = new Resume(fullName);
        } else {
            r = storage.get(uuid);
            r.setFullName(fullName);
        }
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }

        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            String[] values = request.getParameterValues(type.name());
            if (value == null || value.trim().length() == 0 && values.length < 2) {
                r.getSections().remove(type);
            } else {
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        r.addSection(type, new TextSection(value));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATION:
                        r.addSection(type, new ListSection(value.split("\\n")));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        String[] urls = request.getParameterValues(type.name() + "url");
                        List<Organization> organizations = new ArrayList<>();
                        for (int i = 0; i < values.length; i++) {
                            String titleLink = values[i];
                            if (titleLink != null && titleLink.trim().length() != 0) {
                                List<Organization.Position> positions = new ArrayList<>();
                                String[] titles = request.getParameterValues(type.name() + i + "title");
                                String[] startDates = request.getParameterValues(type.name() + i + "startDate");
                                String[] endDates = request.getParameterValues(type.name() + i + "endDate");
                                String[] descriptions = request.getParameterValues(type.name() + i + "description");
                                for (int j = 0; j < startDates.length; j++) {
                                    if (startDates[j] != null && startDates[j].trim().length() != 0) {
                                        positions.add(new Organization.Position(DateUtil.getLocalDate(startDates[j]),
                                                DateUtil.getLocalDate(endDates[j]), titles[j], descriptions[j]));
                                    }
                                }
                                organizations.add(new Organization(new Link(titleLink, urls[i]), positions));
                            }
                        }
                        r.addSection(type, new OrganizationSection(organizations));
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + type);
                }
            }
        }
        if (uuid == null || uuid.length() == 0) {
            storage.save(r);
        } else {
            storage.update(r);
        }
        response.sendRedirect("resume");
    }
}


