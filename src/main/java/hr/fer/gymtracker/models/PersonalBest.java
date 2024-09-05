package hr.fer.gymtracker.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "najboljiRezultat")
public class PersonalBest {

    @EmbeddedId
    private PersonalBestId id;

    @Column(name = "najboljiBrKg", nullable = false)
    private Double bestWeight;

    @Column(name = "datumPostignuca", nullable = false)
    private String achievementDate;

    @ManyToOne
    @MapsId("exerciseId")
    @JoinColumn(name = "idVjezba")
    private Exercise exercise;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "idKorisnik")
    private User user;

    public PersonalBest() {
    }

    public PersonalBest(PersonalBestId id, Double bestWeight, String achievementDate, Exercise exercise, User user) {
        this.id = id;
        this.bestWeight = bestWeight;
        this.achievementDate = achievementDate;
        this.exercise = exercise;
        this.user = user;
    }

    public PersonalBestId getId() {
        return id;
    }

    public void setId(PersonalBestId id) {
        this.id = id;
    }

    public Double getBestWeight() {
        return bestWeight;
    }

    public void setBestWeight(Double bestWeight) {
        this.bestWeight = bestWeight;
    }

    public String getAchievementDate() {
        return achievementDate;
    }

    public void setAchievementDate(String achievementDate) {
        this.achievementDate = achievementDate;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Embeddable
    public static class PersonalBestId implements Serializable {

        @Column(name = "najboljiBrPon", nullable = false)
        private Integer bestReps;

        @Column(name = "idVjezba", nullable = false)
        private Integer exerciseId;

        @Column(name = "idKorisnik", nullable = false)
        private Integer userId;

        public PersonalBestId() {
        }

        public PersonalBestId(Integer bestReps, Integer exerciseId, Integer userId) {
            this.bestReps = bestReps;
            this.exerciseId = exerciseId;
            this.userId = userId;
        }

        public Integer getBestReps() {
            return bestReps;
        }

        public void setBestReps(Integer bestReps) {
            this.bestReps = bestReps;
        }

        public Integer getExerciseId() {
            return exerciseId;
        }

        public void setExerciseId(Integer exerciseId) {
            this.exerciseId = exerciseId;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PersonalBestId that = (PersonalBestId) o;
            return Objects.equals(bestReps, that.bestReps) &&
                    Objects.equals(exerciseId, that.exerciseId) &&
                    Objects.equals(userId, that.userId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(bestReps, exerciseId, userId);
        }
    }
}
