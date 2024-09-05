package hr.fer.gymtracker.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Korisnik", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@NamedQueries({
        @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email=:email"),
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idKorisnik")
    private int userId;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "jeAdmin", nullable = false)
    private int isAdmin;

    @Column(name = "korisnickoIme", nullable = false)
    private String username;

    @Column(name = "spol")
    private Integer gender;

    @Column(name = "hashLozinka", nullable = false)
    private String passwordHash;

    @Column(name = "saltLozinka", nullable = false)
    private String passwordSalt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("startTime DESC")
    private List<TrainingSession> trainingSessions;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrainingTemplate> trainingTemplates;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public List<TrainingSession> getTrainingSessions() {
        return trainingSessions;
    }

    public void setTrainingSessions(List<TrainingSession> trainingSessions) {
        this.trainingSessions = trainingSessions;
    }

    public List<TrainingTemplate> getTrainingTemplates() {
        return trainingTemplates;
    }

    public void setTrainingTemplates(List<TrainingTemplate> trainingTemplates) {
        this.trainingTemplates = trainingTemplates;
    }
}
