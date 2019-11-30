package servlets;

import entities.User;
import services.interfaces.Auth;
import templateEngine.TemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;


public class LoginServlet extends HttpServlet {
  private final Auth auth;
  private final TemplateEngine marker;

  public LoginServlet(Auth sqlAuth, TemplateEngine marker) {
    auth = sqlAuth;
    this.marker = marker;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    marker.render("/login.html", resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String name = req.getParameter("email");
    String pass = req.getParameter("password");
    Optional<User> oUser = auth.login(name, pass);

    if (oUser.isPresent()) {
      final User userLogged = oUser.get();

      resp.addCookie(new Cookie("%t.id%", String.valueOf(userLogged.getId())));
      resp.sendRedirect("/users");

    } else {
      resp.sendRedirect("/login");
    }
  }
}
