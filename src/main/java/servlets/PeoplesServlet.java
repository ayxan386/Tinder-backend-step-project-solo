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
import java.util.Map;

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
    Map<String, String[]> params = req.getParameterMap();
    int id = params.containsKey("id") ? Integer.parseInt(req.getParameter("id")) : -1;
    boolean liked = params.containsKey("liked") && Boolean.parseBoolean(req.getParameter("liked"));

    System.out.printf("id: %d %s", id, liked ? "liked" : "disliked");
    resp.sendRedirect("/users");

  }
}
