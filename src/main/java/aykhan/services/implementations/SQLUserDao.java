package aykhan.services.implementations;

import aykhan.db.SQLUserQueries;
import aykhan.entities.User;
import aykhan.services.interfaces.DAO;

import java.util.List;
import java.util.Optional;

public class SQLUserDao implements DAO<User> {
  private final SQLUserQueries sqlUserQueries = new SQLUserQueries();

  @Override
  public void add(User data) {
    sqlUserQueries.add(data);
  }

  @Override
  public Optional<User> get(int id) {
    return sqlUserQueries.getById(id);
  }

  @Override
  public int update(User data) {
    return sqlUserQueries.update(data);
  }

  @Override
  public List<User> getThese(int min, int max) {
    return sqlUserQueries.getThese(min, max);
  }

  @Deprecated
  @Override
  public Optional<List<User>> getContaining(int id) {
    throw new IllegalArgumentException("Not supported");
  }
}
