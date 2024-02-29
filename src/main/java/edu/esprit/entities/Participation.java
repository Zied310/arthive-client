package edu.esprit.entities;

import java.util.Objects;

public class Participation {
    private int id_participation;
    private int id_participant;
    private int id_evenement;
private User user;

    Participation(){}
    public Participation(int id_participation, int id_participant, int id_evenement ) {
        this.id_participant = id_participant;
        this.id_evenement = id_evenement;
        this.id_participation=id_participation;

    }

    public Participation(int id_participant, int id_evenement) {
        this.id_participant = id_participant;
        this.id_evenement = id_evenement;
    }

    public int getId_participation() {
        return id_participation;
    }

    public void setId_participation(int id_participation) {
        this.id_participation = id_participation;
    }

    public int getId_evenement() {
        return id_evenement;
    }

    public void setId_evenement(int id_evenement) {
        this.id_evenement = id_evenement;
    }

    public int getId_participant() {
        return id_participant;
    }

    public void setId_participant(int id_participant) {
        this.id_participant = id_participant;
    }


    @Override
    public String toString() {
        return "Participation{" +
                "id_participant=" + id_participant +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Participation that)) return false;
        return id_participant == that.id_participant && id_evenement == that.id_evenement;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_participant);
    }
}
