package ru.contextservlet.listeners;

import ru.mycontext.ApplicationContext;
import ru.mycontext.ApplicationContextReflectionBased;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class PrimitiveContextServletListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        ApplicationContext applicationContext = new ApplicationContextReflectionBased();
        servletContext.setAttribute("componentsContext", applicationContext);
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
