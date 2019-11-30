package services.implementations;

import entities.User;
import services.interfaces.DAO;

import java.util.List;
import java.util.Random;

public class RandomUsers {
  private final DAO<User> sqlUserDao;

  public RandomUsers(DAO<User> sqlUserDao) {
    this.sqlUserDao = sqlUserDao;
  }

  public User getRandom() {
    Random r = new Random();
    List<User> list = sqlUserDao.getThese(
        1,
        r.nextInt(15) + 15);

    return list.get(r.nextInt(list.size()));
  }
}
