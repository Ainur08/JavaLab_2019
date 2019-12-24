package ru.contextservlet.servlets;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import ru.contextservlet.services.ProfileService;
import ru.contextservlet.services.ProfileServiceImpl;
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
import java.util.List;
import java.util.Map;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    private ProfileService service;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        Object rawAttribute = servletContext.getAttribute("componentsContext");
        ApplicationContextReflectionBased applicationContext = (ApplicationContextReflectionBased) rawAttribute;
        this.service = applicationContext.getComponent(ProfileServiceImpl.class, "profileServiceImpl");
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

        HttpSession session = req.getSession();

        //товары пользователя
        List products = service.getProducts((String) session.getAttribute("user"));
        root.put("products", products);

        //отображение
        Template t = cfg.getTemplate("profile.ftl");
        try {
            t.process(root, resp.getWriter());
        } catch (TemplateException e) {
            throw new IllegalStateException(e);
        }
    }
}
