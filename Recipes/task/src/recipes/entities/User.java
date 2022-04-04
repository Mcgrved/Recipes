package recipes.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    @NotNull
    @JsonIgnore
    private long userId;
    @Column
    @Pattern(regexp = ".+@.+\\..+")
    private String email;
    @Column
    @NotBlank
    @Size(min = 8)
    private String password;
    @Column
    private String role;
    @Column
    @OneToMany
    private Recipe[] recipes;

}
