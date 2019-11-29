package user;

import java.util.List;

public class User {
  private final String email;
  private final String pass;
  private final String link;
  private final String name;
  private final int id;
  private final List<Integer> liked;

  public User(String email, String pass, String link, String name, int id, List<Integer> liked) {
    this.email = email;
    this.pass = pass;
    this.link = link;
    this.name = name;
    this.id = id;
    this.liked = liked;
  }

  public static User defaultUser() {
    return new UserBuilder()
        .withId(-1)
        .withName("defaultUser")
        .withPassword("akdslaksd")
        .withLink("#")
        .init();
  }

  public String getEmail() {
    return email;
  }

  public String getPass() {
    return pass;
  }

  public int getId() {
    return id;
  }

  public String getLink() {
    return link;
  }

  public String getName() {
    return name;
  }

  public List getLiked() {
    return liked;
  }

  public void addToList(int other_id) {
    liked.add(other_id);
  }

  public boolean contains(int id) {
    return liked.contains(id);
  }
}
