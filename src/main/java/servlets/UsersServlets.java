package servlets;

import entities.LikedUser;
import entities.User;
import services.implementations.RandomUsers;
import services.interfaces.DAO;
import templateEngine.TemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class UsersServlets extends HttpServlet {
  private final TemplateEngine marker;
  private final DAO<User> dao;
  private final DAO<LikedUser> dao_liked;

  public UsersServlets(TemplateEngine freemarker, DAO<User> dao, DAO<LikedUser> dao_liked) {
    marker = freemarker;
    this.dao = dao;
    this.dao_liked = dao_liked;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    User u = new RandomUsers(dao).getRandom();
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
      int logged_user_id = Integer.parseInt(req.getCookies()[0].getValue());
      Optional<List<LikedUser>> list_of_liked = dao_liked.getContaining(logged_user_id);

      list_of_liked.ifPresent(list -> {
            System.out.println(list);
            if (!list.contains(id)) {
              dao_liked.add(new LikedUser(-1, logged_user_id, id));
            }
          }
      );
      resp.getWriter().println("done");
      return;
    }
    resp.sendRedirect("/users");
  }
}
