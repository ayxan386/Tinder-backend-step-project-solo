package services.interfaces;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {
  Optional<T> get(int id);

  int update(T data);

  List<T> getThese(int min, int max);
}
