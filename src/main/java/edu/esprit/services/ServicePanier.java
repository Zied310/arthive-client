package edu.esprit.services;

import edu.esprit.entities.Panier;
import edu.esprit.entities.Produit;
import edu.esprit.entities.User;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServicePanier implements IService<Panier> {

    private static ServicePanier instance;

    Connection cnx = DataSource.getInstance().getCnx();
    ServiceUser su = new ServiceUser();
    ServiceProduit sp = new ServiceProduit();


    @Override
    public void ajouter(Panier panier) {
        String req = "INSERT INTO panier (id_user, id_produit) VALUES (?, ?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, panier.getUser().getId_user());
            ps.setInt(2, panier.getProduit().getId_produit());
            ps.executeUpdate();
            System.out.println("Panier ajouté !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Set<Panier> getAll() {
        Set<Panier> paniers = new HashSet<>();
        String req = "SELECT * FROM panier";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                int id_panier = rs.getInt("id_panier");
                int id_user = rs.getInt("id_user");
                int id_produit = rs.getInt("id_produit");
                User user = su.getOneByID(id_user);
                Produit produit = sp.getOneByID(id_produit);
                Panier panier = new Panier();
                panier.setId_Panier(id_panier);
                panier.setUser(user);
                panier.setProduit(produit);
                paniers.add(panier);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return paniers;
    }

    @Override
    public Panier getOneByID(int id) {
        Panier panier = null;
        String req = "SELECT * FROM panier WHERE id_panier = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id_user = rs.getInt("id_user");
                int id_produit = rs.getInt("id_produit");
                User user = su.getOneByID(id_user);
                Produit produit = sp.getOneByID(id_produit);
                panier = new Panier();
                panier.setId_Panier(id);
                panier.setUser(user);
                panier.setProduit(produit);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return panier;
    }

    @Override
    public void modifier(Panier panier) {
        String req = "UPDATE panier SET id_user=?, id_produit=? WHERE id_panier=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, panier.getUser().getId_user());
            ps.setInt(2, panier.getProduit().getId_produit());
            ps.setInt(3, panier.getId_Panier());
            ps.executeUpdate();
            System.out.println("Panier modifié !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM panier WHERE id_panier = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Panier supprimé !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void ajouterAuPanier(User user, Produit produit) {
        String req = "INSERT INTO panier (id_user, id_produit) VALUES (?, ?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, user.getId_user());
            ps.setInt(2, produit.getId_produit());
            ps.executeUpdate();
            System.out.println("Produit ajouté au panier !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Set<Produit> getProduitsDansPanierUtilisateur(User user) {
        Set<Produit> produits = new HashSet<>();
        String req = "SELECT p.* FROM panier pa JOIN produit p ON pa.id_produit = p.id_produit WHERE pa.id_user = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, user.getId_user());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Produit produit = new Produit();
                produit.setId_produit(rs.getInt("id_produit"));
                produit.setNom_produit(rs.getString("nom_produit"));
                produit.setPrix_produit(rs.getDouble("prix_produit"));
                produit.setImage_produit(rs.getString("image_produit"));
                produit.setStock_produit(rs.getInt("stock_produit"));
                // Ajoutez d'autres attributs au besoin
                produits.add(produit);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return produits;
    }

    public void supprimerProduitDuPanier(User user, Produit produit) {
        String req = "DELETE FROM panier WHERE id_user = ? AND id_produit = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, user.getId_user());
            ps.setInt(2, produit.getId_produit());
            ps.executeUpdate();
            System.out.println("Produit supprimé du panier !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void supprimerPanierUtilisateur(User user) {
        String req = "DELETE FROM panier WHERE id_user = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, user.getId_user());
            ps.executeUpdate();
            System.out.println("Le panier de l'utilisateur a été supprimé avec succès !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
