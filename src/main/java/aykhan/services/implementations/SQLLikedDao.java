package aykhan.services.implementations;

import aykhan.db.SQLLikeQueries;
import aykhan.entities.LikedUser;
import aykhan.services.interfaces.DAO;

import java.util.List;
import java.util.Optional;

public class SQLLikedDao implements DAO<LikedUser> {

  private final SQLLikeQueries sqlLikeQueries = new SQLLikeQueries();

  @Override
  public void add(LikedUser data) {
    sqlLikeQueries.add(data);
  }

  @Override
  public Optional<LikedUser> get(int id) {
    return sqlLikeQueries.getById(id);
  }

  @Deprecated
  @Override
  public int update(LikedUser data) {
    throw new IllegalArgumentException("Not supported");
  }

  @Deprecated
  @Override
  public List<LikedUser> getThese(int min, int max) {
    throw new IllegalArgumentException("Not supported");
  }

  @Override
  public Optional<List<LikedUser>> getContaining(int id) {
    return sqlLikeQueries.getTheseById(id);
  }

}
