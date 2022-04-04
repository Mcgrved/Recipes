package recipes.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import recipes.entities.Recipe;
import recipes.repositories.RecipeRepository;

import javax.validation.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {

    private final RecipeRepository repository;

    public RecipeController(RecipeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public Iterable<Recipe> getRecipe() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Recipe>> getRecipeId(@PathVariable Long id) {
        if (repository.existsById(id)) {
            return new ResponseEntity<>(repository.findById(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Object> gerSearchRecipe(@RequestParam(required = false) String category,
                                                  @RequestParam(required = false) String name) {
        List<Recipe> listOfFound;
        if (Objects.equals(null, category) && Objects.equals(null, name)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (Objects.equals(null, name)) {
            listOfFound = repository.findAllByCategoryIgnoreCaseOrderByDateDesc(category);
        } else {
            listOfFound = repository.findAllByNameContainsIgnoreCaseOrderByDateDesc(name);
        }
        return new ResponseEntity<>(listOfFound, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<Object> postRecipe(@Valid @RequestBody Recipe requestRecipe) {
        requestRecipe.setAuthorName(SecurityContextHolder.getContext().getAuthentication().getName());
        repository.save(requestRecipe);
        return new ResponseEntity<>(Map.of("id", requestRecipe.getRecipeId()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Optional<Recipe>> deleteRecipe(@PathVariable Long id) {
        if (repository.existsById(id) &&
                Objects.equals(SecurityContextHolder.getContext().getAuthentication().getName(),
                        repository.findById(id).get().getAuthorName())) {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else if (repository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> putRecipe(@PathVariable Long id, @Valid @RequestBody Recipe requestRecipe) {
        if (repository.existsById(id) &&
                Objects.equals(SecurityContextHolder.getContext().getAuthentication().getName(),
                        repository.findById(id).get().getAuthorName())) {
            Logger.getGlobal().info("ID PUT " + requestRecipe.getRecipeId());
            Logger.getGlobal().info("ID " + id);

            requestRecipe.setDate(LocalDateTime.now());
            requestRecipe.setAuthorName(repository.findById(id).get().getAuthorName());
            requestRecipe.setRecipeId(id);
            repository.save(requestRecipe);
            return new ResponseEntity<>(Map.of("id", requestRecipe.getRecipeId()), HttpStatus.NO_CONTENT);
        } else if (repository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}