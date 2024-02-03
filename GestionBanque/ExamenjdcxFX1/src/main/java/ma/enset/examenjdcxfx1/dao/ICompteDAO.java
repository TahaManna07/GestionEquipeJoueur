package ma.enset.examenjdcxfx1.dao;

import ma.enset.examenjdcxfx1.dao.entities.Compte;

public interface ICompteDAO extends DAO<Compte , Integer> {
    void bloquerCompte(int idCompte);
    void debloquerCompte(int idCompte);
    void retirerMontant(int idCompte, float montant);
    void effectuerVersement(int idCompte, float montant);
}