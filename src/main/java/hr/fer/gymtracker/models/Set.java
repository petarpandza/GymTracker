package hr.fer.gymtracker.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Serija")
public class Set implements Serializable {

    @EmbeddedId
    private SetId id;

    @Column(name = "brPonSerija", nullable = false)
    private Integer repetitions;

    @Column(name = "brkgSerija", nullable = false)
    private Double weight;

    @ManyToOne
    @MapsId("exerciseId")
    @JoinColumn(name = "idVjezba", referencedColumnName = "idVjezba")
    private Exercise exercise;

    @ManyToOne
    @MapsId("trainingSessionId")
    @JoinColumn(name = "idTrening", referencedColumnName = "idTrening")
    private TrainingSession trainingSession;

    @ManyToOne
    @JoinColumn(name = "idVrstaSerije", nullable = false)
    private SetType setType;

    public SetId getId() {
        return id;
    }

    public void setId(SetId id) {
        this.id = id;
    }

    public Integer getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(Integer repetitions) {
        this.repetitions = repetitions;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public TrainingSession getTrainingSession() {
        return trainingSession;
    }

    public void setTrainingSession(TrainingSession training) {
        this.trainingSession = training;
    }

    public SetType getSetType() {
        return setType;
    }

    public void setSetType(SetType setType) {
        this.setType = setType;
    }

    @Override
    public String toString() {
        return "Set{" +
                "id=" + id +
                ", repetitions=" + repetitions +
                ", weight=" + weight +
                ", exercise=" + exercise +
                ", trainingSession=" + trainingSession +
                ", setType=" + setType +
                '}';
    }

    @Embeddable
    public static class SetId implements Serializable {

        @Column(name = "redniBrojSerije", nullable = false)
        private Integer setNumber;

        @Column(name = "idVjezba", nullable = false)
        private Integer exerciseId;

        @Column(name = "idTrening", nullable = false)
        private Integer trainingId;

        public SetId() {
        }

        public SetId(Integer seriesNumber, Integer exerciseId, Integer trainingId) {
            this.setNumber = seriesNumber;
            this.exerciseId = exerciseId;
            this.trainingId = trainingId;
        }

        public Integer getSetNumber() {
            return setNumber;
        }

        public void setSetNumber(Integer setNumber) {
            this.setNumber = setNumber;
        }

        public Integer getExerciseId() {
            return exerciseId;
        }

        public void setExerciseId(Integer exerciseId) {
            this.exerciseId = exerciseId;
        }

        public Integer getTrainingId() {
            return trainingId;
        }

        public void setTrainingId(Integer trainingId) {
            this.trainingId = trainingId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SetId setId = (SetId) o;
            return Objects.equals(setNumber, setId.setNumber) &&
                    Objects.equals(exerciseId, setId.exerciseId) &&
                    Objects.equals(trainingId, setId.trainingId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(setNumber, exerciseId, trainingId);
        }
    }
}
