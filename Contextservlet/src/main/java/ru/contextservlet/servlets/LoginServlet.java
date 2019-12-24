package ru.contextservlet.servlets;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import ru.contextservlet.dto.User;
import ru.contextservlet.services.UserService;
import ru.contextservlet.services.UserServiceImpl;
import ru.mycontext.ApplicationContextReflectionBased;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserService service;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        Object rawAttribute = servletContext.getAttribute("componentsContext");
        ApplicationContextReflectionBased applicationContext = (ApplicationContextReflectionBased) rawAttribute;
        this.service = applicationContext.getComponent(UserServiceImpl.class, "userServiceImpl");
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
        ServletContext servletContext = getServletContext();

        cfg.setServletContextForTemplateLoading(servletContext, "WEB-INF/templates/");
        cfg.setDefaultEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        // Сессия
        HttpSession session = req.getSession();
        String login = (String) session.getAttribute("user");
        if (login != null) {
            resp.sendRedirect("profile");
        } else {
            Map root = new HashMap();
            Template t = cfg.getTemplate("login.ftl");
            try {
                t.process(root, resp.getWriter());
            } catch (TemplateException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer userId = service.login(req);
        if (userId == null) {
            resp.sendRedirect("login");
        } else {
            resp.sendRedirect("profile");
        }
    }
}
