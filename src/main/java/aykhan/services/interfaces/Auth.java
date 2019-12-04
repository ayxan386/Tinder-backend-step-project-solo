package aykhan.services.interfaces;

import aykhan.entities.User;

import java.util.Optional;

public interface Auth {
  Optional<User> login(String name, String pass);

  boolean register(User user);
}
