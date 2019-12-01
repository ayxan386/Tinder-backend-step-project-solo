import filters.LoggedFilter;
import filters.MessagesGetFilter;
import filters.MessagesPostFilter;
import filters.PeoplesFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import services.implementations.SQLAuth;
import services.implementations.SQLLikedDao;
import services.implementations.SQLMessageDao;
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
    Server server = new Server(5000);
    ServletContextHandler handler = new ServletContextHandler();
    try {
      SQLAuth sqlAuth = new SQLAuth();
      SQLUserDao sqlUserDao = new SQLUserDao();
      SQLLikedDao sqlLikedDao = new SQLLikedDao();
      SQLMessageDao sqlMessageDao = new SQLMessageDao();
      TemplateEngine freemarker = new TemplateEngine("./static/templates");

      handler.addServlet(new ServletHolder(new LoginServlet(sqlAuth, freemarker)), "/login/*");
      handler.addServlet(new ServletHolder(new LogoutServlet()), "/logout/*");
      handler.addServlet(new ServletHolder(new LikedServlet(freemarker, sqlUserDao, sqlLikedDao)), "/liked/*");
      handler.addServlet(new ServletHolder(new UsersServlets(freemarker, sqlUserDao, sqlLikedDao)), "/users/*");
      handler.addServlet(new ServletHolder(new MessagesServlet(freemarker, sqlMessageDao, sqlUserDao)), "/messages/*");
      handler.addServlet(new ServletHolder(new StaticLoader()), "/static/*");


      handler.addFilter(LoggedFilter.class, "/liked/*", EnumSet.of(DispatcherType.INCLUDE, DispatcherType.REQUEST));
      handler.addFilter(LoggedFilter.class, "/users/*", EnumSet.of(DispatcherType.INCLUDE, DispatcherType.REQUEST));
      handler.addFilter(LoggedFilter.class, "/messages", EnumSet.of(DispatcherType.INCLUDE, DispatcherType.REQUEST));
      handler.addFilter(MessagesGetFilter.class, "/messages", EnumSet.of(DispatcherType.INCLUDE, DispatcherType.REQUEST));
      handler.addFilter(MessagesPostFilter.class, "/messages", EnumSet.of(DispatcherType.INCLUDE, DispatcherType.REQUEST));
      handler.addFilter(PeoplesFilter.class, "/users/*", EnumSet.of(DispatcherType.INCLUDE, DispatcherType.REQUEST));

      server.setHandler(handler);
      server.start();
      server.join();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
