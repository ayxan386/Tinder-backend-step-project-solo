package services.implementations;

import services.interfaces.DAO;
import user.User;

import java.util.List;
import java.util.Optional;

public class SQLDao implements DAO<User> {
  @Override
  public Optional<User> get(int id) {
    return null;
  }

  @Override
  public int update(User data) {
    return 0;
  }

  @Override
  public List<User> getThese(int min, int max) {
    return null;
  }
}
