package edu.esprit.crud;

import edu.esprit.entities.Event;
import edu.esprit.entities.Participation;
import edu.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class CrudParticipations implements ICrudParticipations<Participation> {
    Connection cnx = DataSource.getInstance().getCnx();

    private ServiceUser serviceUser = new ServiceUser();
    private CrudEvent crudEvent = new CrudEvent();
    public CrudParticipations() {
        this.serviceUser = new ServiceUser();
    }
    @Override
    public void ajouter(Participation participations) {

        String req = "INSERT INTO `participation`(`id_participant`, `id_evenement`) VALUES (?,?)";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, participations.getId_participant());
            ps.setInt(2, participations.getId_evenement());
            ps.executeUpdate();
            System.out.println("Participation added!");
            Event event = crudEvent.getOneByID(participations.getId_evenement());
            if (event != null) {
                event.addParticipant();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void supprimer(Participation participations) {
        String req = "DELETE FROM `participation` WHERE id_participant = ? AND id_evenement = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, participations.getId_participant());
            ps.setInt(2, participations.getId_evenement());
            int rowCount = ps.executeUpdate();

            if (rowCount > 0) {
                System.out.println("Participation deleted!");
            } else {
                System.out.println("Participation not found or not deleted!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


        @Override
    public Participation getOneByNameParticipant(String name) {
        String req = "SELECT * FROM `participation` WHERE 1";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int idParticipant = rs.getInt("id_participant");
                    int idEvent = rs.getInt("id_evenement");
                    int idUser = rs.getInt("id_user");
                    String userName = serviceUser.getOneByID(idUser).getNom_user();
                    // Ajoutez les autres colonnes selon votre modèle de données

                    return new Participation(idParticipant, idEvent);
                } else {
                    System.out.println("Participation with user name " + name + " not found.");
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public Set<String> getParticipantsForEvent(int eventId) {
        Set<String> participants = new HashSet<>();
        String req = "SELECT id_participant FROM `participation` WHERE id_evenement = ?";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, eventId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int idUser = rs.getInt("id_participant");
                    String userName = serviceUser.getOneByID(idUser).getNom_user();
                    participants.add(userName);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return participants;
    }
    public boolean isParticipant(int participantId, int eventId) {
        String req = "SELECT * FROM `participation` WHERE id_participant = ? AND id_evenement = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, participantId);
            ps.setInt(2, eventId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();  // Retourne true si l'utilisateur participe, sinon false
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

}
