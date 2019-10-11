package ru.mywebapplication;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.util.Date;

public class Filter implements javax.servlet.Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        File file = new File("myfile.txt");
        PrintWriter pw = new PrintWriter(new FileWriter(file,true));
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        // bot for ip
        String ip = "";
        try {
            URL url = new URL("http://bot.whatismyipaddress.com/");
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            ip = br.readLine().trim();
        } catch (IOException e) {
            ip = "ip not found";
        }

        // logging
        pw.println(new Date().toString() + " " + req.getMethod() + " " + req.getParameter("urls") + " " + ip);
        pw.close();

        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {

    }
}


