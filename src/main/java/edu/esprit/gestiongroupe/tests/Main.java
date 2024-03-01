package edu.esprit.gestiongroupe.tests;

import edu.esprit.gestiongroupe.entities.Groupe;
import edu.esprit.gestiongroupe.entities.User;
import edu.esprit.gestiongroupe.services.ServiceGroupe;
import edu.esprit.gestiongroupe.services.ServiceUser;

import java.sql.Date;

public class Main {
    public static void main(String[] args) {
        ServiceGroupe SG = new ServiceGroupe();
        Date birthDate = new java.sql.Date(2002, 2, 15);
        User user=new User("skan","lrb","skander.laribi@esprit.tn","azerty",birthDate,"Tunis",29292929,"user");
        //ServiceUser SV = new ServiceUser();
        //SV.add(user);
        Groupe G = new Groupe("Kharja","groupe as7ab",19);
        SG.ajouter(G);
        System.out.println(SG.getAll());

    }
}
