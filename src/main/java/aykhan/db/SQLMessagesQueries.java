package aykhan.db;

import aykhan.entities.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SQLMessagesQueries {
  private final String SELECT = "SELECT id,sender,receiver,message FROM messages WHERE ((sender = ? and receiver = ?) or (sender = ? and receiver = ?))";
  private final String INSERT = "INSERT INTO messages (message,sender,receiver) values (?,?,?)";

  public int add(Message u) {
    Connection conn = DataBaseConnection.getConnection();
    try (PreparedStatement statement = conn.prepareStatement(INSERT)) {
      statement.setString(1, u.getContent());
      statement.setInt(2, u.getSender());
      statement.setInt(3, u.getReceiver());
      return statement.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return 0;
  }
// just in case
//  public Optional<Message> getOne(int sender, int receiver) {
//    Connection conn = DataBaseConnection.getConnection();
//    try (PreparedStatement statement = conn.prepareStatement(SELECT)) {
//      statement.setInt(1, sender);
//      statement.setInt(2, receiver);
//      statement.setInt(3, sender);
//      statement.setInt(4, receiver);
//      ResultSet resultSet = statement.executeQuery();
//      while (resultSet.next()) {
//        return Optional.of(new Message(resultSet.getString("message"),
//            resultSet.getInt("sender"),
//            resultSet.getInt("receiver")));
//      }
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//    return Optional.empty();
//  }

  public Optional<List<Message>> getBetween(int sender, int receiver) {
    Connection conn = DataBaseConnection.getConnection();
    try (PreparedStatement statement = conn.prepareStatement(SELECT)) {
      statement.setInt(1, sender);
      statement.setInt(2, receiver);
      statement.setInt(3, receiver);
      statement.setInt(4, sender);
      ResultSet resultSet = statement.executeQuery();
      ArrayList<Message> res = new ArrayList<>();
      while (resultSet.next()) {
        res.add(new Message(resultSet.getString("message"),
            resultSet.getInt("sender"),
            resultSet.getInt("receiver")));
      }
      return Optional.of(res);

    } catch (Exception e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }
}
