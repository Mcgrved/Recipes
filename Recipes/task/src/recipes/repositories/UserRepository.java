package recipes.repositories;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.entities.User;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);

}
