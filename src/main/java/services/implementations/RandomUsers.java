package services.implementations;

import entities.User;
import services.interfaces.DAO;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class RandomUsers {
  private final DAO<User> sqlUserDao;

  public RandomUsers(DAO<User> sqlUserDao) {
    this.sqlUserDao = sqlUserDao;
  }

  public User getRandom(int id, List<Integer> alreadyLiked) {
    Random r = new Random();
    List<User> list = sqlUserDao.getThese(
        1,
        10);

    list = list.stream()
        .filter(user ->
            user.getId() != id && !alreadyLiked.contains(user.getId()))
        .collect(Collectors.toList());

    if (list.isEmpty()) return User.defaultUser();
    return list.get(r.nextInt(list.size()));
  }
}
