package edu.esprit.crud;

import edu.esprit.entities.Event;
import edu.esprit.entities.Participation;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.*;


public class CrudEvent implements ICrud<Event> {
Connection cnx = DataSource.getInstance().getCnx();
    private ServiceUser serviceUser = new ServiceUser();
    private Participation Participation ;
    //private CrudParticipations crudParticipations = new CrudParticipations();

    public CrudEvent() {
        this.serviceUser = new ServiceUser();
    }


    @Override
    public void ajouter(Event event) {
        String req = "INSERT INTO `evenements`(`titre_evenement`, `d_debut_evenement`, `d_fin_evenement`,  `description_evenement`,`lieu_evenement`, `id_user`,`image`) VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, event.getTitre_evenement());
            ps.setTimestamp(2, Timestamp.valueOf(event.getD_debut_evenement().toLocalDateTime()));
            ps.setTimestamp(3, Timestamp.valueOf(event.getD_fin_evenement().toLocalDateTime()));
            ps.setString(4, event.getDescription_evenement());
            ps.setString(5, event.getLieu_evenement());
            ps.setInt(6, event.getUser().getId_user());
            ps.setString(7, event.getImage());
            ps.executeUpdate();
            System.out.println("Event added!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }




    @Override
    public void supprimer(Event event) {
        String req = "DELETE FROM `evenements` WHERE `id_evenement` = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, event.getId_event());  // Mettez à jour l'appel à getId_event()
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Event with ID " + event.getId_event() + " deleted successfully!");  // Mettez à jour l'appel à getId_event()
            } else {
                System.out.println("Event with ID " + event.getId_event() + " not found.");  // Mettez à jour l'appel à getId_event()
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    @Override
    public void modifier(Event event) {
        String req = "UPDATE `evenements` SET `titre_evenement`=?, `d_debut_evenement`=?, `d_fin_evenement`=?, `description_evenement`=?, `lieu_evenement`=?, `id_user`=?, `image`=? WHERE `id_evenement`=?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, event.getTitre_evenement());  // Mettez à jour l'appel à getTitre_event()
            ps.setTimestamp(2, event.getD_debut_evenement());
            ps.setTimestamp(3, event.getD_fin_evenement());
            ps.setString(4, event.getDescription_evenement());
            ps.setString(5, event.getLieu_evenement());
            ps.setInt(6, event.getUser().getId_user());
            ps.setString(7, event.getImage());
            ps.setInt(8, event.getId_event());  // Mettez à jour l'appel à getId_event()


            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Event with ID " + event.getId_event() + " updated successfully!");  // Mettez à jour l'appel à getId_event()
            } else {
                System.out.println("Event with ID " + event.getId_event() + " not found.");  // Mettez à jour l'appel à getId_event()
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Event> getAll() {
        List<Event> events = new ArrayList<>();
        String req = "SELECT * FROM evenements";
        try (PreparedStatement ps = cnx.prepareStatement(req); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id_evenement");
                String titre = rs.getString("titre_evenement");
                Timestamp dateDebut = rs.getTimestamp("d_debut_evenement");
                Timestamp dateFin = rs.getTimestamp("d_fin_evenement");
                String description = rs.getString("description_evenement");
                String lieu = rs.getString("lieu_evenement");
                int idUser = rs.getInt("id_user");
                String image = rs.getString("image");

                Event event = new Event(id, titre, dateDebut, dateFin, description, lieu,  serviceUser.getOneByID(idUser), image);



                // Ajouter les participations à l'événement

                events.add(event);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return events;
    }

    public Set<Participation> getParticipationsForEvent(int eventId) {
        Set<Participation> participations = new HashSet<>();
        String req = "SELECT * FROM `participation` WHERE id_evenement = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, eventId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int idParticipant = rs.getInt("id_participant");
                    //String participantName = rs.getString("nom_user");
                    int idParticipation = rs.getInt("id_participation");
                    participations.add(new Participation(idParticipation, idParticipant,eventId));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return participations;
    }


    public Map<Event, List<Participation>> getParticipantsForAllEvents() {
        Map<Event, List<Participation>> participantsMap = new HashMap<>();

        String req = "SELECT * FROM `participation`";
        try (PreparedStatement ps = cnx.prepareStatement(req); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int idEvent = rs.getInt("id_evenement");
                Event event = getOneByID(idEvent);

                if (!participantsMap.containsKey(event)) {
                    participantsMap.put(event, new ArrayList<>());
                }

                int idParticipant = rs.getInt("id_participant");
                int idParticipation = rs.getInt("id_participation");

                Participation participation = new Participation(idParticipation, idParticipant, idEvent);
                participantsMap.get(event).add(participation);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return participantsMap;
    }




    @Override
    public Event getOneByID(int id) {
        String req = "SELECT * FROM `evenements` WHERE `id_evenement`=?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String titre = rs.getString("titre_evenement");
                    Timestamp dateDebut = rs.getTimestamp("d_debut_evenement");
                    Timestamp dateFin = rs.getTimestamp("d_fin_evenement");
                    String description = rs.getString("description_evenement");
                    String lieu = rs.getString("lieu_evenement");
                    int idUser = rs.getInt("id_user");
                    String image = rs.getString("image");
                    String participantsString = rs.getString("id_user");
                    Set<Participation> participants = getParticipationsForEvent(id);
                    System.out.println("Fetched participations: " + participants);
                    return new Event(id, titre, dateDebut, dateFin, description, lieu, serviceUser.getOneByID(idUser), image , participants);
                } else {
                    System.out.println("Event with ID " + id + " not found.");
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    @Override
    public Event getOneByName(String name) {
        String req = "SELECT * FROM `evenements` WHERE `titre_evenement`=?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id_evenement");
                    String titre = rs.getString("titre_evenement");
                    Timestamp dateDebut = rs.getTimestamp("d_debut_evenement");
                    Timestamp dateFin = rs.getTimestamp("d_fin_evenement");
                    String description = rs.getString("description_evenement");
                    String lieu = rs.getString("lieu_evenement");
                    int idUser = rs.getInt("id_user");
                    String image = rs.getString("image");
                    //Set<String> participants = participations.getParticipantsForEvent(id);
                    return new Event(titre,dateDebut, dateFin, description, lieu,serviceUser.getOneByID(idUser), image);
                } else {
                    System.out.println("Event with name " + name + " not found.");
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    public List<Event> getEventsForUser(int userId) {
        List<Event> eventsForUser = new ArrayList<>();
        // Écrivez la requête SQL pour récupérer les événements associés à l'utilisateur par l'ID
        String query = "SELECT * FROM evenements WHERE id_user = ?";

        try {
            Connection cnx = DataSource.getInstance().getCnx();

            PreparedStatement ps = cnx.prepareStatement(query);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Lire les données de l'événement depuis le résultat de la requête
                int eventId = rs.getInt("id_evenement");
                String titre = rs.getString("titre_evenement");
                Timestamp dateDebut = rs.getTimestamp("d_debut_evenement");
                Timestamp dateFin = rs.getTimestamp("d_fin_evenement");
                String description = rs.getString("description_evenement");
                String lieu = rs.getString("lieu_evenement");
                String image = rs.getString("image");

                // Récupérer le nom de l'utilisateur associé à l'événement
                int idUser = rs.getInt("id_user");
                String userName = serviceUser.getOneByID(idUser).getNom_user();

                // Créer une instance d'Event avec les données lues
                Event event = new Event(eventId, titre, dateDebut, dateFin, description, lieu, serviceUser.getOneByID(userId), image);

                // Ajouter l'événement à la liste
                eventsForUser.add(event);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return eventsForUser;
    }

    public int getNumberOfParticipantsForEvent(int eventId) {
        String req = "SELECT COUNT(*) as totalParticipants FROM `participation` WHERE id_evenement = ?";
        int numberOfParticipants = 0;

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, eventId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    numberOfParticipants = rs.getInt("totalParticipants");
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du nombre de participants pour l'événement : " + e.getMessage());
        }

        return numberOfParticipants;
    }

    public void participer(int eventId, int participantId) {
        String req = "INSERT INTO `participation`(`id_participant`, `id_evenement`) VALUES (?,?)";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, participantId);
            ps.setInt(2, eventId);
            ps.executeUpdate();
            System.out.println("Participant added to event!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
