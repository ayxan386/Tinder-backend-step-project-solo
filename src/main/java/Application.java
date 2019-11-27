
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import services.implementations.SQLAuth;
import servlets.*;
import templateEngine.TemplateEngine;

public class Application {
  public static void main(String[] args) {
    new Application().startServer();
  }

  private void startServer() {
    Server server = new Server(8080);
    ServletContextHandler handler = new ServletContextHandler();
    try {
      SQLAuth sqlAuth = new SQLAuth();
      TemplateEngine freemarker = new TemplateEngine("./static/templates");

      handler.addServlet(new ServletHolder(new UsersServlet(sqlAuth, freemarker)), "/login/*");
      handler.addServlet(new ServletHolder(new LikedServlet(freemarker)), "/liked/*");
      handler.addServlet(new ServletHolder(new PeoplesServlet(freemarker)), "/users/*");
      handler.addServlet(new ServletHolder(new MessagesServlet(freemarker)), "/messages/*");
      handler.addServlet(new ServletHolder(new StaticLoader()), "/static/*");

      server.setHandler(handler);
      server.start();
      server.join();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
