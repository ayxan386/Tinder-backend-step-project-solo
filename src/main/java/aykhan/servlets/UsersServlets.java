package aykhan.servlets;

import aykhan.entities.LikedUser;
import aykhan.entities.User;
import aykhan.services.implementations.RandomUsers;
import aykhan.services.interfaces.DAO;
import aykhan.templateEngine.TemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    int id = Integer.parseInt(req.getCookies()[0].getValue());
    RandomUsers randomUsers = new RandomUsers(dao);
    List<Integer> alreadyLiked = dao_liked.getContaining(id)
        .orElse(Collections.emptyList())
        .stream()
        .map(user -> user.getWhom())
        .collect(Collectors.toList());
    User u = randomUsers.getRandom(id, alreadyLiked);
    //Liked all users
    if (u.isDefault()) {
      resp.sendRedirect("/liked");
      return;
    }
    HashMap<String, Object> data = new HashMap<>();
    data.put("notfound", "not found");
    data.put("userName", u.getName());
    data.put("photo_link", u.getLink());
    data.put("user_id", u.getId());

    marker.render("/like-page.html", data, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    int id = Integer.parseInt(req.getParameter("id"));
    boolean liked = Boolean.parseBoolean(req.getParameter("liked"));
    if (liked) {
      int logged_user_id = Integer.parseInt(req.getCookies()[0].getValue());
      Optional<List<LikedUser>> list_of_liked = dao_liked.getContaining(logged_user_id);

      list_of_liked.ifPresent(list -> {
        List<Integer> intList = list.stream()
            .map(LikedUser::getWhom)
            .collect(Collectors.toList());
        if (!intList.contains(id)) {
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
