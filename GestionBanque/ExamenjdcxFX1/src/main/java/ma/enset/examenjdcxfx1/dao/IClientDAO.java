package ma.enset.examenjdcxfx1.dao;

import ma.enset.examenjdcxfx1.dao.entities.Client;
import ma.enset.examenjdcxfx1.dao.entities.Compte;

import java.util.List;

public interface IClientDAO extends DAO<Client ,Integer > {
    List<Compte> getComptesClient(int idClient);
    List<Client> rechercherParMotCle(String motCle);
}