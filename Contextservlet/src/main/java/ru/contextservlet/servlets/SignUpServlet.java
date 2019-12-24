package ru.contextservlet.servlets;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import ru.contextservlet.dto.User;
import ru.contextservlet.services.SignUpService;
import ru.contextservlet.services.SignUpServiceImpl;
import ru.mycontext.ApplicationContextReflectionBased;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/signUp")
public class SignUpServlet extends HttpServlet {
    private SignUpService service;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        Object rawAttribute = servletContext.getAttribute("componentsContext");
        ApplicationContextReflectionBased applicationContext = (ApplicationContextReflectionBased) rawAttribute;
        this.service = applicationContext.getComponent(SignUpServiceImpl.class, "signUpServiceImpl");
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //конфигурация
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
        Map root = new HashMap();
        ServletContext servletContext = getServletContext();
        cfg.setServletContextForTemplateLoading(servletContext, "WEB-INF/templates/");

        //кодировка
        cfg.setDefaultEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        //отображение
        Template t = cfg.getTemplate("registration.ftl");
        try {
            t.process(root, resp.getWriter());
        } catch (TemplateException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = new User();
        user.setLogin(req.getParameter("login"));
        user.setPassword(req.getParameter("password"));
        service.signUp(user);

        req.getSession().setAttribute("user", req.getParameter("login"));
        resp.sendRedirect("login");
    }
}
