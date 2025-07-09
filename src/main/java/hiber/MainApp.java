package hiber;

import hiber.config.AppConfig;
import hiber.model.*;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.List;

public class MainApp {
   public static void main(String[] args) {
      AnnotationConfigApplicationContext context =
              new AnnotationConfigApplicationContext(AppConfig.class);
      UserService userService = context.getBean(UserService.class);

      List<User> users = Arrays.asList(
              new User("User1", "Lastname1", "user1@mail.ru"),
              new User("User2", "Lastname2", "user2@mail.ru"),
              new User("User3", "Lastname3", "user3@mail.ru"),
              new User("User4", "Lastname4", "user4@mail.ru")
      );
      users.forEach(userService::add);

      List<Car> cars = Arrays.asList(
              new Car("Model1", 111),
              new Car("Model2", 222),
              new Car("Model3", 333),
              new Car("Model4", 444)
      );
      cars.forEach(car -> userService.addCar(car));

      List<User> dbUsers = userService.listUsers();
      List<Car> dbCars = userService.listCars();

      for (int i = 0; i < Math.min(dbUsers.size(), dbCars.size()); i++) {
         User user = dbUsers.get(i);
         Car car = dbCars.get(i);
         user.setCar(car);
         userService.update(user);
      }

      userService.listUsers().forEach(user -> {
         System.out.println(user);
         if (user.getCar() != null) {
            System.out.println("  Car: " + user.getCar());
         }
      });

      context.close();
   }
}
