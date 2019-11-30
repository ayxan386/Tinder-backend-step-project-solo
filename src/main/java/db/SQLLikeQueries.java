package db;

import entities.LikedUser;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SQLLikeQueries {
  private final String INSERT = "INSERT  INTO public.liked (who,whom) values(?,?)";
  private final String SELECT_ID = "SELECT id,who,whom FROM liked WHERE (who = ?)";

  public void add(LikedUser likedUser) {
    try (PreparedStatement statement = DataBaseConnection.getConnection().prepareStatement(INSERT)) {
      statement.setInt(1, likedUser.getWho());
      statement.setInt(2, likedUser.getWhom());
      statement.execute();
    } catch (Exception e) {
      throw new IllegalArgumentException("SQLLikeQueries add", e);
    }
  }

  public Optional<LikedUser> getById(int who) {
    try (PreparedStatement statement = DataBaseConnection.getConnection().prepareStatement(SELECT_ID)) {
      statement.setInt(1, who);
      ResultSet res = statement.executeQuery();
      while (res.next()) {
        if (res.getInt("who") != -1) {
          LikedUser likedUser = new LikedUser(res.getInt("id"), res.getInt("who"), res.getInt("whom"));
          return Optional.of(likedUser);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }

  public Optional<List<LikedUser>> getTheseById(int who) {
    try (PreparedStatement statement = DataBaseConnection.getConnection().prepareStatement(SELECT_ID)) {
      statement.setInt(1, who);
      ResultSet res = statement.executeQuery();
      ArrayList<LikedUser> res_list = new ArrayList<>();
      while (res.next()) {
        res_list.add(
            new LikedUser(res.getInt("id"), res.getInt("who"), res.getInt("whom"))
        );
      }
      return Optional.of(res_list);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }

}
