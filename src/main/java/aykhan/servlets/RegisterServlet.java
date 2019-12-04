package aykhan.servlets;

import aykhan.entities.User;
import aykhan.entities.UserBuilder;
import aykhan.services.interfaces.Auth;
import aykhan.templateEngine.TemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class RegisterServlet extends HttpServlet {
  private final Auth auth;
  private final TemplateEngine marker;

  public RegisterServlet(Auth sqlAuth, TemplateEngine marker) {
    auth = sqlAuth;
    this.marker = marker;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    marker.render("/registerPage.html", resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String email = req.getParameter("email");
    String pass = req.getParameter("password");
    String name = req.getParameter("name");
    User newUser = new UserBuilder().withName(name).withEmail(email).withPassword(pass).init();
    boolean res = auth.register(newUser);
    if (res) {
      resp.sendRedirect("/login");
    } else {
      resp.sendRedirect("/register");
    }
  }
}
