package db;

import user.User;
import user.UserBuilder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SQLUserQueries {
  private final String LOGIN = "SELECT id,users.email,users.password,photo_link,liked,name FROM users WHERE email=? and password=?";
  private final String SELECT = "SELECT id,users.email,users.password,photo_link,liked, name FROM users WHERE (id >= ? and id <= ?)";

  public User login(String user, String pass) {
    try (PreparedStatement statement = DataBaseConnection.getConnection().prepareStatement(LOGIN)) {
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
}
