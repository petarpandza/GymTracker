package hr.fer.gymtracker.models;

import jakarta.persistence.*;

import java.util.Objects;

/**
 * Class represents an exercise a user can do.
 * It has a name, description and type.
 * It is stored in the database under the name "Vjezba".
 */
@Entity
@Table(name = "Vjezba")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idVjezba")
    private Integer exerciseId;

    @Column(name = "imeVjezba", nullable = false)
    private String name;

    @Column(name = "opisVjezba", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "idVrstaVjezbe", nullable = false)
    private ExerciseType exerciseType;

    public Exercise() {
    }

    public Exercise(String name, String description, ExerciseType exerciseType) {
        this.name = name;
        this.description = description;
        this.exerciseType = exerciseType;
    }

    public Integer getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Integer id) {
        this.exerciseId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ExerciseType getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(ExerciseType exerciseType) {
        this.exerciseType = exerciseType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exercise exercise = (Exercise) o;
        return Objects.equals(exerciseId, exercise.exerciseId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(exerciseId);
    }
}
