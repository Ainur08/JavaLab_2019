package ru.mywebapplication;

import ru.downl.Threads;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Servlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] urls = req.getParameter("urls").split(" ");
        for (int i = 0; i < urls.length; i++) {
            Threads threads = new Threads(i, urls[i]);
            threads.start();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        writer.println("<html>" +
                "<body>" +
                "<form method=\"post\">" +
                "<label>Enter links with a space</label>" + " " +
                "<input name=\"urls\" type=\"text\">" + " " +
                "<button type=\"submit\">Download</button>" +
                "</form>" +
                "</body>" +
                "</html>");
        writer.close();
    }
}

