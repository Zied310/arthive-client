package edu.esprit.entities;

import java.util.Objects;

public class Panier {

    private int id_Panier;
    private User user ;
    private Produit produit;

    public Panier() {

    }

    public Panier(User user, Produit produit) {
        this.user = user;
        this.produit = produit;
    }

    public Panier(int id_Panier, User user, Produit produit) {
        this.id_Panier = id_Panier;
        this.user = user;
        this.produit = produit;
    }

    public int getId_Panier() {
        return id_Panier;
    }

    public void setId_Panier(int id_Panier) {
        this.id_Panier = id_Panier;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    @Override
    public String toString() {
        return "Panier{" +
                "user=" + user +
                ", produit=" + produit +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Panier panier = (Panier) o;
        return id_Panier == panier.id_Panier;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_Panier);
    }


}
