package webapp.listener;

import webapp.db.DBConnection;

import javax.servlet.annotation.WebListener;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;


@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        DataSource pool = DBConnection.getConnectionPool();
        servletContext.setAttribute("dbPool", pool);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
