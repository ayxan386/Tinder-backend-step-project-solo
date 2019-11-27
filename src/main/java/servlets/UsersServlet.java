package servlets;

import services.interfaces.Auth;
import templateEngine.TemplateEngine;
import user.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Optional;


public class UsersServlet extends HttpServlet {
  private final Auth auth;
  private final TemplateEngine marker;

  public UsersServlet(Auth sqlAuth, TemplateEngine marker) {
    auth = sqlAuth;
    this.marker = marker;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    Files.copy(Paths.get("./static/templates/login.html"), resp.getOutputStream());
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String name = req.getParameter("email");
    String pass = req.getParameter("password");
    Optional<User> oUser = auth.login(name, pass);

    if (oUser.isPresent()) {
      final User userLogged = oUser.get();

      marker.render("/test.html", new HashMap<String, Object>() {{
        put("email", userLogged.getEmail());
        put("password", userLogged.getPass());
        put("id", userLogged.getId());
      }}, resp);

    }
  }
}
