package aykhan.entities;

public class UserBuilder {
  private String name;
  private String pass;
  private int id;
  private String link;
  private String email;

  public UserBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public UserBuilder withEmail(String email) {
    this.email = email;
    return this;
  }

  public UserBuilder withLink(String link) {
    this.link = link;
    return this;
  }

  public UserBuilder withPassword(String password) {
    this.pass = password;
    return this;
  }

  public UserBuilder withId(int id) {
    this.id = id;
    return this;
  }

  public User init() {
    return new User(
        this.email, this.pass, link, name, this.id);
  }

}
