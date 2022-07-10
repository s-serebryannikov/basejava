package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Storage storage = Config.get().getStorage();
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("ContentType", "text/html; charset=UTF-8");
        PrintWriter pw = response.getWriter();
        pw.println("<html>");

        pw.println("<style>");
        pw.println("table, th, td {");
        pw.println("border: 1px solid black;");
        pw.println("}");
        pw.println("</style>");

        pw.println("<table>");
        pw.println("<tr>");
        pw.println("<td style=\"font-size: 15pt; font-family: monospace; font-weight: 600\">UUID</td>");
        pw.println("<td style=\"font-size: 15pt; font-family: monospace; font-weight: 600\">FULL_NAME</td>");
        pw.println("</tr>");
        List<Resume> resumes = storage.getAllSorted();
        for (Resume resume: resumes){
            pw.println("<tr>");
            pw.println("<td>" +resume.getUuid() + "</td>");
            pw.println("<td>"+resume.getFullName()+"</td>");
            pw.println("</tr>");
        }
        pw.println("</table>");
        pw.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
