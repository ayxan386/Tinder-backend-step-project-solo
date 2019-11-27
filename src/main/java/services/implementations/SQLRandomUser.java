package services.implementations;

import db.SQLUserQueries;
import user.User;

import java.util.List;
import java.util.Random;

public class SQLRandomUser {
  private final SQLUserQueries sqlUserQueries = new SQLUserQueries();

  public User getRandom() {
    Random r = new Random();
    List<User> list = sqlUserQueries.getThese(
        1,
        r.nextInt(15) + 15);
    return list.get(r.nextInt(list.size()));
  }
}
