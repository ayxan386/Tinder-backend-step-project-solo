import filters.LoggedFilter;
import filters.PeoplesFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import services.implementations.SQLAuth;
import services.implementations.SQLLikedDao;
import services.implementations.SQLUserDao;
import servlets.*;
import templateEngine.TemplateEngine;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

public class Application {
  public static void main(String[] args) {
    new Application().startServer();
  }

  private void startServer() {
    Server server = new Server(8880);
    ServletContextHandler handler = new ServletContextHandler();
    try {
      SQLAuth sqlAuth = new SQLAuth();
      SQLUserDao sqlDao = new SQLUserDao();
      SQLLikedDao sqlLikedDao = new SQLLikedDao();
      TemplateEngine freemarker = new TemplateEngine("./static/templates");

      handler.addServlet(new ServletHolder(new LoginServlet(sqlAuth, freemarker)), "/login/*");
      handler.addServlet(new ServletHolder(new LogoutServlet()), "/logout/*");
      handler.addServlet(new ServletHolder(new LikedServlet(freemarker)), "/liked/*");
      handler.addServlet(new ServletHolder(new UsersServlets(freemarker, sqlDao, sqlLikedDao)), "/users/*");
      handler.addServlet(new ServletHolder(new MessagesServlet(freemarker)), "/messages/*");
      handler.addServlet(new ServletHolder(new StaticLoader()), "/static/*");


      handler.addFilter(LoggedFilter.class, "/liked/*", EnumSet.of(DispatcherType.INCLUDE, DispatcherType.REQUEST));
      handler.addFilter(LoggedFilter.class, "/users/*", EnumSet.of(DispatcherType.INCLUDE, DispatcherType.REQUEST));
      handler.addFilter(LoggedFilter.class, "/messages", EnumSet.of(DispatcherType.INCLUDE, DispatcherType.REQUEST));
      handler.addFilter(PeoplesFilter.class, "/users/*", EnumSet.of(DispatcherType.INCLUDE, DispatcherType.REQUEST));
      server.setHandler(handler);
      server.start();
      server.join();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
