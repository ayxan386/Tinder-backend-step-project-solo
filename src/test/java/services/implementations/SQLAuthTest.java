package services.implementations;

import org.junit.Before;
import org.junit.Test;
import user.User;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SQLAuthTest {
  private SQLAuth sqlAuth;

  @Before
  public void setUp() throws Exception {
    sqlAuth = new SQLAuth();
  }

  @Test
  public void login() {
    String name = "test@tester.com";
    String password = "123456";

    Optional<User> userO = sqlAuth.login(name, password);
    assertTrue(userO.isPresent());
    User user = userO.get();
    assertEquals(name, user.getEmail());
    assertEquals(password, user.getPass());
  }
}