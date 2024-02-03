package ma.enset.examenjdcxfx1.service;

import ma.enset.examenjdcxfx1.dao.CompteDAOImpl;
import ma.enset.examenjdcxfx1.dao.IClientDAO;
import ma.enset.examenjdcxfx1.dao.ICompteDAO;
import ma.enset.examenjdcxfx1.dao.entities.Client;
import ma.enset.examenjdcxfx1.dao.entities.Compte;

import java.util.List;

public class IBanqueServiceImpl implements IBanqueService{

    ICompteDAO compteDAO;
    IClientDAO clientDAO;

    public IBanqueServiceImpl(CompteDAOImpl compteDAO) {
        this.compteDAO = compteDAO;
    }

    @Override
    public void ajouterClient(Client client) {
        clientDAO.ajouter(client);

    }

    @Override
    public void supprimerClient(int idClient) {
        clientDAO.supprimer(idClient);
    }

    @Override
    public List<Client> listerClients() {
        return clientDAO.afficherListe();
    }

    @Override
    public void ajouterCompte(Compte compte) {
        compteDAO.ajouter(compte);
    }

    @Override
    public void supprimerCompte(int idCompte) {
        compteDAO.supprimer(idCompte);
    }

    @Override
    public List<Compte> listerComptes() {
        return compteDAO.afficherListe();
    }

    @Override
    public void bloquerCompte(int idCompte) {
        compteDAO.bloquerCompte(idCompte);
    }

    @Override
    public void debloquerCompte(int idCompte) {
        compteDAO.debloquerCompte(idCompte);
    }

    @Override
    public void retirerMontant(int idCompte, float montant) {
        compteDAO.retirerMontant(idCompte,montant);

    }

    @Override
    public void effectuerVersement(int idCompte, float montant) {
        compteDAO.effectuerVersement(idCompte,montant);

    }
}
