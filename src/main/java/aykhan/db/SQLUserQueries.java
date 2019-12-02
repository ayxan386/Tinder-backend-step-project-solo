package aykhan.db;

import at.favre.lib.crypto.bcrypt.BCrypt;
import aykhan.entities.User;
import aykhan.entities.UserBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class SQLUserQueries {
  private final String LOGIN = "SELECT id,users.email,users.password,photo_link,name FROM users WHERE email=? and password=?";
  private final String SELECT = "SELECT id,users.email,users.password,photo_link, name FROM users WHERE (id >= ? and id <= ?)";
  private final String SELECT_ID = "SELECT id,users.email,users.password,photo_link, name FROM users WHERE (id = ?)";
  private final String UPDATE_USER = "UPDATE users SET email = ?,password=?,photo_link=?,name=? WHERE (id = ?)";
  private final String INSERT = "INSERT INTO users (email,password,photo_link, name) values (?,?,?,?)";

  public User login(String user, String pass) {
    try (PreparedStatement statement = DataBaseConnection.getConnection().prepareStatement(LOGIN)) {
      BCrypt.Hasher hasher = BCrypt.with(BCrypt.Version.VERSION_2Y);
      String pass2 = hasher.hashToString(12, pass.toCharArray());
      System.out.println(pass2);
      statement.setString(1, user);
      statement.setString(2, pass);
      ResultSet res = statement.executeQuery();
      while (res.next()) {
        if (res.getInt("id") != -1) {
          return new UserBuilder()
              .withId(res.getInt("id"))
              .withEmail(res.getString("email"))
              .withName(res.getString("name"))
              .withLink(res.getString("photo_link"))
              .withPassword(res.getString("password"))
              .init();
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return User.defaultUser();
  }

  public List<User> getThese(int min, int max) {
    try (PreparedStatement statement = DataBaseConnection.getConnection().prepareStatement(SELECT)) {
      statement.setInt(1, min);
      statement.setInt(2, max);
      ResultSet res = statement.executeQuery();
      List<User> resList = new ArrayList<>();
      while (res.next()) {
        if (res.getInt("id") != -1) {
          resList.add(new UserBuilder()
              .withId(res.getInt("id"))
              .withName(res.getString("name"))
              .withEmail(res.getString("email"))
              .withLink(res.getString("photo_link"))
              .withPassword(res.getString("password"))
              .init());
        }
      }
      return resList;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Arrays.asList(User.defaultUser());
  }

  public Optional<User> getById(int id) {
    try (PreparedStatement statement = DataBaseConnection.getConnection().prepareStatement(SELECT_ID)) {
      statement.setInt(1, id);
      ResultSet res = statement.executeQuery();
      while (res.next()) {
        if (res.getInt("id") != -1) {
          return Optional.of(new UserBuilder()
              .withId(res.getInt("id"))
              .withEmail(res.getString("email"))
              .withName(res.getString("name"))
              .withLink(res.getString("photo_link"))
              .withPassword(res.getString("password"))
              .init());
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }

  public int add(User u) {
    Connection conn = DataBaseConnection.getConnection();
    try (PreparedStatement statement = conn.prepareStatement(INSERT)) {
      statement.setString(1, u.getEmail());
      statement.setString(2, u.getPass());
      statement.setString(3, u.getLink());
      statement.setString(4, u.getName());
      return statement.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return 0;
  }

  public int update(User u) {
    Connection conn = DataBaseConnection.getConnection();
    try (PreparedStatement statement = conn.prepareStatement(UPDATE_USER)) {
      statement.setString(1, u.getEmail());
      statement.setString(2, u.getPass());
      statement.setString(3, u.getLink());
      statement.setString(4, u.getName());
      statement.setInt(5, u.getId());
      return statement.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return 0;
  }
}
