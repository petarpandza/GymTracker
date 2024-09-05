package hr.fer.gymtracker.models;

import jakarta.persistence.*;

@Entity
@Table(name = "VrstaSerije")
public class SetType {

    @Id
    @Column(name = "idVrstaSerije")
    private Integer setTypeId;

    @Column(name = "imeVrstaSerije", nullable = false)
    private String name;

    public SetType() {
    }

    public SetType(Integer id, String name) {
        this.setTypeId = id;
        this.name = name;
    }

    public Integer getSetTypeId() {
        return setTypeId;
    }

    public void setSetTypeId(Integer id) {
        this.setTypeId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
