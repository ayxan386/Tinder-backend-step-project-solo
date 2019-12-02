package aykhan.entities;

public class LikedUser {
  private final int id;
  private final int who;
  private final int whom;

  public LikedUser(int id, int who, int whom) {
    this.id = id;
    this.who = who;
    this.whom = whom;
  }

  public int getId() {
    return id;
  }

  public int getWho() {
    return who;
  }

  public int getWhom() {
    return whom;
  }
}
