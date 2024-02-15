package edu.esprit.entities;

import java.util.Objects;

public class Groupe {
    private int id_group;
    private String nom_group;
    private String description_group;
    private int id_user;


public Groupe(){

}

public Groupe (String nom_group, String description_group, int id_user){
    this.nom_group=nom_group;
    this.description_group=description_group;
    this.id_user=id_user;
}

    public Groupe(int id_group, String nom_group, String description_group, int id_user) {
        this.id_group = id_group;
        this.nom_group = nom_group;
        this.description_group = description_group;
        this.id_user = id_user;
    }

    public int getId_group() {
        return id_group;
    }

    public void setId_group(int id_group) {
        this.id_group = id_group;
    }

    public String getNom_group() {
        return nom_group;
    }

    public void setNom_group(String nom_group) {
        this.nom_group = nom_group;
    }

    public String getDescription_group() {
        return description_group;
    }

    public void setDescription_group(String description_group) {
        this.description_group = description_group;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    @Override
    public String toString() {
        return "\nGroupe \n" +
                "{" +
                "id_group=" + id_group +
                ", \n nom_group='" + nom_group + '\'' +
                ", \n description_group='" + description_group + '\'' +
                ", \n id_user=" + id_user +
                "\n } \n ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Groupe groupe = (Groupe) o;
        return getId_group() == groupe.getId_group() && getId_user() == groupe.getId_user();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_group);
    }
}