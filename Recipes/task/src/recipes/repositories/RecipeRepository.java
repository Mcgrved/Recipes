package recipes.repositories;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.entities.Recipe;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    List<Recipe> findAllByCategoryIgnoreCaseOrderByDateDesc(String category);
    List<Recipe> findAllByNameContainsIgnoreCaseOrderByDateDesc(String name);

}
