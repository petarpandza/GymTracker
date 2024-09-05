package hr.fer.gymtracker.models;

import jakarta.persistence.*;

import java.util.List;

/**
 * Class represents a type of exercise.
 * It has a name.
 * It is stored in the database under the name "VrstaVjezbe".
 */
@Entity
@Table(name = "VrstaVjezbe")
public class ExerciseType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idVrstaVjezbe")
    private Integer id;

    @Column(name = "imeVrstaVjezbe", nullable = false)
    private String name;

    @OneToMany(mappedBy = "exerciseType", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("name ASC")
    private List<Exercise> exercises;

    public ExerciseType() {
    }

    public ExerciseType(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }
}
