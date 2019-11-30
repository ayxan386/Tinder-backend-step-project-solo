package services.interfaces;

import entities.User;

import java.util.Optional;

public interface Auth {
  Optional<User> login(String name, String pass);

  boolean register(String name, String pass);
}
