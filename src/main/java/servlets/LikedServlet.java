package servlets;

import templateEngine.TemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LikedServlet extends HttpServlet {
  private final TemplateEngine marker;

  public LikedServlet(TemplateEngine freemarker) {
    marker = freemarker;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    marker.render("/people-list.html", resp);
  }
}
