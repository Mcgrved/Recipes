package recipes.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "recipes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    @NotNull
    @JsonIgnore
    private long recipeId;
    @NotBlank
    @Column
    private String name;
    @NotBlank
    @Column
    private String category;
    @NotBlank
    @Column
    private String description;
    @NotEmpty
    @Column
    private String[] ingredients;
    @NotEmpty
    @Column
    private String[] directions;
    @Column
    private LocalDateTime date = LocalDateTime.now();
    @Column
    @JsonIgnore
    private String authorName;
//    @Column
//    @JsonIgnore
//    @CreatedBy
//    @ManyToOne
//    @JoinColumn(name = "USER_ID")
//    private User user;
}
