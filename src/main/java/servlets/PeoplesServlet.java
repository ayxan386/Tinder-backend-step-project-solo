package servlets;

import services.implementations.SQLRandomUser;
import services.interfaces.DAO;
import templateEngine.TemplateEngine;
import user.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

public class PeoplesServlet extends HttpServlet {
  private final TemplateEngine marker;
  private final DAO<User> dao;

  public PeoplesServlet(TemplateEngine freemarker, DAO<User> dao) {
    marker = freemarker;
    this.dao = dao;
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
    if (liked) {
      int logged_user = Integer.parseInt(req.getCookies()[0].getValue());
      Optional<User> byId = dao.get(logged_user);
      if (byId.isPresent()) {
        User u = byId.get();
        if (!u.contains(id)) {
          u.addToList(id);
          int update = dao.update(u);
          resp.getWriter().println(update > 0 ? "done" : "error");
          return;
        }
      }
    }
    resp.sendRedirect("/users");
  }
}
