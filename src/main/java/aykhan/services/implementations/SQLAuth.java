package aykhan.services.implementations;

import aykhan.db.SQLUserQueries;
import aykhan.entities.User;
import aykhan.services.interfaces.Auth;

import java.util.Optional;

public class SQLAuth implements Auth {
  private final SQLUserQueries sqlUserQueries = new SQLUserQueries();

  @Override
  public Optional<User> login(String name, String pass) {
    User user = sqlUserQueries.login(name, pass);
    if (user.getId() == -1) return Optional.empty();
    return Optional.of(user);
  }

  @Override
  public boolean register(String name, String pass) {
    return false;
  }
}
