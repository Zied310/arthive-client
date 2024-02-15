package edu.esprit.services;

import edu.esprit.entities.Groupe;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceGroupe implements IService<Groupe> {

    @Override
    public void ajouter (Groupe groupe) {
        String req = "INSERT INTO `groups`(`nom_group`, `description_group`, `id_user`) VALUES (?,?,?)";

        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, groupe.getNom_group());
            ps.setString(2, groupe.getDescription_group());
            ps.setInt(3, groupe.getId_user());
            ps.executeUpdate();
            System.out.println("Groupe "+groupe.getNom_group()+" added !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void modifier(Groupe groupe) {
        String req = "UPDATE `groups` SET `nom_group`=?, `description_group`=?, `id_user`=? WHERE `id_group`=?";

        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, groupe.getNom_group());
            ps.setString(2, groupe.getDescription_group());
            ps.setInt(3, groupe.getId_user());
            ps.setInt(4, groupe.getId_group());
            ps.executeUpdate();
            System.out.println("Groupe " + groupe.getNom_group() + " modifié !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM `groups` WHERE `id_group`=?";

        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Groupe avec l'ID " + id + " supprimé !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Set<Groupe> getAll() {
        Set<Groupe> G = new HashSet<>();

        String req = "SELECT * FROM `groups`";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next()){
                int idGroupe = rs.getInt(1);
                String nomGroup = rs.getString(2);
                String DescGroup = rs.getString(3);
                int idUser= rs.getInt(4);
                Groupe group = new Groupe(idGroupe,nomGroup,DescGroup,idUser);
                G.add(group);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return G;
    }

    @Override
    public Groupe getOneByID(int id) {
        String req = "SELECT * FROM `groups` WHERE `id_group` = ?";
        return null;
    }

    Connection cnx = DataSource.getInstance().getCnx();



}
