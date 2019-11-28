package servlets;

import services.implementations.SQLRandomUser;
import templateEngine.TemplateEngine;
import user.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class PeoplesServlet extends HttpServlet {
  private final TemplateEngine marker;

  public PeoplesServlet(TemplateEngine freemarker) {
    marker = freemarker;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    User u = new SQLRandomUser().getRandom();
    marker.render("/like-page.html", new HashMap<String, Object>() {{
      put("notfound", "not found");
      put("userName", u.getName());
      put("photo_link", u.getLink());
      put("user_id", u.getId());
    }}, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    int id = Integer.parseInt(req.getParameter("id"));
    boolean liked = Boolean.parseBoolean(req.getParameter("liked"));

    resp.setStatus(200);
    resp.getWriter().println("done");
  }
}
