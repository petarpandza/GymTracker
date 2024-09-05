package hr.fer.gymtracker.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "PredlozakTreninga")
public class TrainingTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPredlozak")
    private Integer id;

    @Column(name = "imePredlozak", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "idKorisnik", nullable = false)
    private User user;

    @ManyToMany
    @JoinTable(
            name = "sastojiSeOd",
            joinColumns = @JoinColumn(name = "idPredlozak"),
            inverseJoinColumns = @JoinColumn(name = "idVjezba")
    )
    private List<Exercise> exercises;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
    }
}
