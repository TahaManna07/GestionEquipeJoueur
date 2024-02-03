package ma.enset.examenjdcxfx1.dao.entities;

import java.io.Serializable;
import java.util.Date;

public class Compte implements Serializable {
    private long id;
    private String numCompte;
    private Date dateCreation;
    private float solde;
    private boolean bloque;
    private Client client;

    public Compte() {
        // Constructeur par défaut
    }

    public Compte(long id, String numCompte, Date dateCreation, float solde, boolean bloque, Client client) {
        this.id = id;
        this.numCompte = numCompte;
        this.dateCreation = dateCreation;
        this.solde = solde;
        this.bloque = bloque;
        this.client = client;
    }

    public Compte(long id, String numCompte, java.sql.Date dateCreation, float solde, boolean bloque, long idClient) {
    }

    public Compte(int idCompte, String numCompte, java.sql.Date dateCreation, float solde, boolean bloque) {
    }
    public Compte(String numCompte, Date dateCreation, float solde, boolean bloque, Client client) {
        this.numCompte = numCompte;
        this.dateCreation = dateCreation;
        this.solde = solde;
        this.bloque = bloque;
        this.client = client;
    }
    public Compte(String numCompte, java.sql.Date dateCreation, float solde, boolean bloque, long idClient) {
        this.numCompte = numCompte;
        this.dateCreation = dateCreation;
        this.solde = solde;
        this.bloque = bloque;
        this.client = new Client();
        this.client.setId(idClient);
    }

    // Setters
    public void setId(long id) {
        this.id = id;
    }

    public void setNumCompte(String numCompte) {
        this.numCompte = numCompte;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public void setSolde(float solde) {
        this.solde = solde;
    }

    public void setBloque(boolean bloque) {
        this.bloque = bloque;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    // Getters
    public long getId() {
        return id;
    }

    public String getNumCompte() {
        return numCompte;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public float getSolde() {
        return solde;
    }

    public boolean isBloque() {
        return bloque;
    }

    public Client getClient() {
        return client;
    }

    // Méthode toString
    @Override
    public String toString() {
        return "Compte{" +
                "id=" + id +
                ", numCompte='" + numCompte + '\'' +
                ", dateCreation=" + dateCreation +
                ", solde=" + solde +
                ", bloque=" + bloque +

                '}';
    }
}
