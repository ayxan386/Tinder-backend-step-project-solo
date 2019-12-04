package aykhan.servlets;

import aykhan.entities.Message;
import aykhan.entities.User;
import aykhan.services.interfaces.DAO;
import aykhan.templateEngine.TemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class MessagesServlet extends HttpServlet {
  private final TemplateEngine marker;
  private final DAO<Message> dao;
  private final DAO<User> daoUser;

  public MessagesServlet(TemplateEngine freemarker, DAO<Message> dao, DAO<User> daoUser) {
    marker = freemarker;
    this.dao = dao;
    this.daoUser = daoUser;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    int my_id = Integer.parseInt(req.getCookies()[0].getValue());

    String[] paths = req.getPathInfo().replace("/", "").split("/");
    int other_id = Integer.parseInt(paths[0]);

    HashMap<String, Object> data = new HashMap<>();
    Optional<User> other_user = daoUser.get(other_id);
    other_user.ifPresent(user -> {
      List<Message> these = dao.getThese(my_id, other_id);
      data.put("messages", these);
      data.put("other", user);
    });
    data.put("def", Arrays.asList(new Message("Start the conversation", my_id, other_id)));
    marker.render("/chat.ftl", data, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    int sender = Integer.parseInt(req.getCookies()[0].getValue());
    int receiver = Integer.parseInt(req.getParameter("receiver"));
    String text = req.getParameter("message");
    dao.add(new Message(text, sender, receiver));
    resp.sendRedirect("/messages/" + receiver);
  }
}
