package ma.enset.examenjdcxfx1.dao;

import ma.enset.examenjdcxfx1.dao.entities.Client;
import ma.enset.examenjdcxfx1.dao.entities.Compte;

import java.sql.Date;
import java.util.List;

public class Test2 {
    public static void main(String[] args) {

        CompteDAOImpl compteDAO = new CompteDAOImpl();
        ClientDAOImpl clientDAO = new ClientDAOImpl();

        Client  c = new Client();
        c.setId(3);

        // Ajouter ensuite le compte avec l'ID du client
        Compte compte = new Compte("16151917", new java.sql.Date(System.currentTimeMillis()), 9000, true, c);
        //compteDAO.ajouter(compte);
      //  System.out.println("Compte ajouté : " + compte);
        System.out.println("****************");
        // Tester la méthode afficherListe
        System.out.println("*************** Before *************");
        /*
        List<Compte> listeComptes = compteDAO.afficherListe();
        for (Compte cc : listeComptes) {
            System.out.println(cc);
        }
        compteDAO.debloquerCompte(2);
        System.out.println("*************** After *************");
        listeComptes = compteDAO.afficherListe();

        for (Compte cc : listeComptes) {
            System.out.println(cc);
        }*/
        /*
        for (Compte cc : listeComptes) {
            System.out.println(cc);
        }
        compteDAO.effectuerVersement(2, 5000);
        System.out.println("apres le reti");
        listeComptes = compteDAO.afficherListe();
        for (Compte cc : listeComptes) {
            System.out.println(cc);
        }*/
        //compteDAO.supprimer(18);
        //compteDAO.supprimer(19);

        Compte compteToUpdate = compteDAO.getByID(21)/* Récupérez un compte de votre base de données */;
        compteToUpdate.setNumCompte("NouveauNumCompte");
        compteToUpdate.setDateCreation(java.sql.Date.valueOf("1990-01-01"));
        compteToUpdate.setSolde(2000.0f);
        compteToUpdate.setBloque(true);

        //compteDAO.update(compteToUpdate);
        System.out.println(compteToUpdate);

        List<Compte> listeComptes = compteDAO.afficherListe();
        for (Compte cc : listeComptes) {
            System.out.println(cc);
        }

    }
}
