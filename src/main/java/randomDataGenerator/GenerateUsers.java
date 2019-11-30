package randomDataGenerator;

import entities.User;
import entities.UserBuilder;
import services.implementations.SQLUserDao;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GenerateUsers {

  public static void main(String[] args) {
    SQLUserDao sqlDao = new SQLUserDao();
    int n = 155;
    for (int i = 0; i < n; i++) {
      String name = randomName();
      String password = randomPassword(name);
      String email = randomEmail(name);
      User user = new UserBuilder()
          .withLink("'https://picsum.photos/200/300'")
          .withName(name)
          .withPassword(password)
          .withEmail(email)
          .init();
      sqlDao.add(user);
    }
  }

  private static String randomEmail(String name) {
    List<String> providers = Arrays.asList("gmail.com", "yahoo.com", "mail.ru");
    return name.toLowerCase() + "@" +
        providers.get((int) (Math.random() * providers.size()));
  }

  private static String randomPassword(String name) {
    String key = "verySecrectKeyWord";
    StringBuilder res = new StringBuilder();
    for (int i = 0; i < name.length(); i++) {
      res.append(name.charAt(i) ^ key.charAt(i % key.length()));
    }
    return res.toString();
  }

  private static String randomName() {
    List<String> names = Arrays.asList("Albertha",
        "Tona",
        "Antionette",
        "Flo",
        "Mirella",
        "Piedad",
        "Josef",
        "Luci",
        "Cornelia",
        "Corrie",
        "Lashawnda",
        "Roxane",
        "Laureen",
        "Jeni",
        "Rutha",
        "Wen",
        "Toya",
        "Myrtis",
        "Dann",
        "Junko",
        "Juliet",
        "Marlon",
        "Kimberlie",
        "Billye",
        "Prudence",
        "Paul",
        "Linn",
        "Ivey",
        "Mariko",
        "Robt",
        "Raeann",
        "Josiah",
        "Nicholle",
        "Concha",
        "Fae",
        "Darby",
        "Qiana",
        "Don",
        "Laila",
        "Inge",
        "Rae",
        "Ruthanne",
        "Salvatore",
        "Ira",
        "Nicholas",
        "Mellissa",
        "Mark",
        "Marceline",
        "Leonarda",
        "Brandi");
    return names.get((int) (Math.random() * names.size())) + randomDigits();
  }

  private static String randomDigits() {
    Random r = new Random();
    return String.valueOf(r.nextInt(2020));
  }
}

