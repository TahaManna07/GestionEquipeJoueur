package ma.enset.examenjdcxfx1.service;

import ma.enset.examenjdcxfx1.dao.entities.Client;
import ma.enset.examenjdcxfx1.dao.entities.Compte;

import java.util.List;

public interface IBanqueService {

    void ajouterClient(Client client);

    void supprimerClient(int idClient);

    List<Client> listerClients();

    void ajouterCompte(Compte compte);

    void supprimerCompte(int idCompte);

    List<Compte> listerComptes();

    void bloquerCompte(int idCompte);

    void debloquerCompte(int idCompte);

    void retirerMontant(int idCompte, float montant);

    void effectuerVersement(int idCompte, float montant);
}