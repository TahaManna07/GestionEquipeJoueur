package ma.enset.examenjdcxfx1.dao;


import ma.enset.examenjdcxfx1.dao.entities.Client;
import ma.enset.examenjdcxfx1.dao.entities.Compte;

import java.util.List;
public class Test1 {
    public static void main(String[] args) {

        IClientDAO clientDAO = new ClientDAOImpl();
        ICompteDAO compteDAO = new CompteDAOImpl();

        // Ajouter un client
        Client nouveauClient = new Client();
        nouveauClient.setNom("taha");
        nouveauClient.setPrenom("ahha");
        nouveauClient.setCin("BH1544");
        nouveauClient.setEmail("dsds@gmail.com");
        nouveauClient.setTelephone("0655870880");
        nouveauClient.setDateNaissance(java.sql.Date.valueOf("1990-01-01"));


        //clientDAO.ajouter(nouveauClient);
        //System.out.println("Client ajouté avec succès : " + nouveauClient);
        clientDAO.supprimer(2);
        List<Client> listeClients = clientDAO.afficherListe();

        System.out.println("Liste des clients :");
        for (Client client : listeClients) {
            System.out.println(client);
        }
        System.out.println("*******************************");

        // Rechercher des clients par mot-clé
        List<Client> clientsRecherches = clientDAO.rechercherParMotCle("taha");
        for (Client client : clientsRecherches) {
            System.out.println(client);
        }


        System.out.println("*******************************");
        // Test de la méthode getComptesClient
        List<Compte> comptes = clientDAO.getComptesClient(3);

            for (Compte compte : comptes) {
                System.out.println(compte);
            }
        System.out.println("************************");
        Client clientToUpdate = clientDAO.getByID(3)/* Récupérez un client de votre base de données */;
        clientToUpdate.setNom("NouveauNom");
        clientToUpdate.setPrenom("NouveauPrenom");
        clientToUpdate.setCin("NouveauCIN");
        clientToUpdate.setTelephone("065587");
        clientToUpdate.setEmail("nouveau.email@example.com");
        clientToUpdate.setDateNaissance(java.sql.Date.valueOf("1990-01-01"));

        clientDAO.update(clientToUpdate);
        }



    }





