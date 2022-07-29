package socialLogin.socialLoginTest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import socialLogin.socialLoginTest.domain.User;


public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUsername(String username);
}
