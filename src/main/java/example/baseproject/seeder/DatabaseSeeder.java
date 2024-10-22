package example.baseproject.seeder;

import example.baseproject.user.model.User;
import example.baseproject.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class DatabaseSeeder implements CommandLineRunner {

    private final UserRepository userRepository;

    public DatabaseSeeder(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            User user = new User();
            user.setUsername("admin");
            user.setEmail("admin@example.com");
            user.setPassword("Aa@123456");

            userRepository.save(user);
        }
    }
}
