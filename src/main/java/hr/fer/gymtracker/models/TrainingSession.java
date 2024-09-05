package hr.fer.gymtracker.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Trening")
public class TrainingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTrening")
    private int trainingSessionId;

    @Column(name = "vrijemePocetka", nullable = false)
    private String startTime;

    @Column(name = "trajanje", nullable = false)
    private int duration;

    @Column(name = "imeTrening", nullable = false)
    private String sessionName;

    @ManyToOne
    @JoinColumn(name = "idKorisnik", nullable = false)
    private User user;

    @OneToMany(mappedBy = "trainingSession", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("exercise.exerciseId, id.setNumber")
    private List<Set> sets;

    public int getTrainingSessionId() {
        return trainingSessionId;
    }

    public void setTrainingSessionId(int trainingSessionId) {
        this.trainingSessionId = trainingSessionId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Set> getSets() {
        return sets;
    }

    public void setSets(List<Set> sets) {
        this.sets = sets;
    }
}
