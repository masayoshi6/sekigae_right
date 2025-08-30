package sekigae_right;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SekigaeRightApplication {

  public static void main(String[] args) {
    SpringApplication.run(SekigaeRightApplication.class, args);

    System.out.println("http://localhost:8080/students");
  }

}
