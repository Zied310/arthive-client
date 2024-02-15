package edu.esprit.tests;

import edu.esprit.entities.Groupe;
import edu.esprit.services.ServiceGroupe;

public class Main {
    public static void main(String[] args) {
        ServiceGroupe SG = new ServiceGroupe();
        Groupe classe3A5 = new Groupe("3A5","ceci est un groupe de classe",2);
        //G.ajouter(classe3A5);
        System.out.println(SG.getAll());

    }
}
