package entities;

public class User {
  private final String email;
  private final String pass;
  private final String link;
  private final String name;
  private final int id;

  public User(String email, String pass, String link, String name, int id) {
    this.email = email;
    this.pass = pass;
    this.link = link;
    this.name = name;
    this.id = id;
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

  public boolean isDefault() {
    return getId() == -1;
  }

  @Override
  public String toString() {
    return String.format("User{email='%s', pass='%s', link='%s', name='%s', id=%d}", email, pass, link, name, id);
  }
}
