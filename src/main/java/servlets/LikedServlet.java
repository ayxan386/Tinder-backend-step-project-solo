package servlets;

import entities.LikedUser;
import entities.User;
import services.interfaces.DAO;
import templateEngine.TemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LikedServlet extends HttpServlet {
  private final TemplateEngine marker;
  private final DAO<User> dao;
  private final DAO<LikedUser> dao_liked;

  public LikedServlet(TemplateEngine freemarker, DAO<User> dao, DAO<LikedUser> dao_liked) {
    marker = freemarker;
    this.dao = dao;
    this.dao_liked = dao_liked;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    int myId = Integer.parseInt(req.getCookies()[0].getValue());
    Optional<List<LikedUser>> likedUserO = dao_liked.getContaining(myId);
    HashMap<String, Object> data = new HashMap<>();

    likedUserO.ifPresent(likedUsers -> {
      List<User> users = likedUsers.stream()
          .map(userLiked -> dao.get(userLiked.getWhom())
              .orElse(User.defaultUser()))
          .collect(Collectors.toList());
      data.put("users", users);
    });
    data.put("emptyList", Arrays.asList("You have not liked anybody yet:/"));
    data.put("def", -1);
    marker.render("/people-list.ftl", data, resp);
  }
}
