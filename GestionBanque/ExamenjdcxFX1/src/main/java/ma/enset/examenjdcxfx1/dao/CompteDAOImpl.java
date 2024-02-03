package ma.enset.examenjdcxfx1.dao;
import ma.enset.examenjdcxfx1.dao.entities.Client;
import ma.enset.examenjdcxfx1.dao.entities.Compte;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class CompteDAOImpl implements ICompteDAO {
    @Override
    public void ajouter(Compte compte) {
        Connection connection = DBSingleton.getConnection();
        PreparedStatement preparedStatement = null;

        try {
            String query = "INSERT INTO compte (num_compte, date_creation, solde, bloque, client_id) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, compte.getNumCompte());

            // Vérifier si la date de création n'est pas null avant d'appeler getTime()
            preparedStatement.setDate(2, new java.sql.Date(compte.getDateCreation().getTime()));

            preparedStatement.setFloat(3, compte.getSolde());
            preparedStatement.setBoolean(4, compte.isBloque());

            // Utiliser l'ID fourni dans le constructeur du compte pour le client
            preparedStatement.setLong(5, compte.getClient().getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestion des exceptions
        }
    }

    @Override
    public void supprimer(Integer idCompte) {
        Connection connection = DBSingleton.getConnection();
        PreparedStatement preparedStatement = null;

        try {
            String query = "DELETE FROM compte WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, idCompte);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestion des exceptions
        }
    }

    @Override
    public List<Compte> afficherListe() {
        List<Compte> comptes = new ArrayList<>();
        Connection connection = DBSingleton.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            String query = "SELECT * FROM compte";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String numCompte = resultSet.getString("num_compte");
                Date dateCreation = resultSet.getDate("date_creation");
                float solde = resultSet.getFloat("solde");
                boolean bloque = resultSet.getBoolean("bloque");
                long idClient = resultSet.getLong("client_id");

                Client client = getClientById((int) idClient);
                Compte compte = new Compte(id, numCompte, dateCreation, solde, bloque, client);
                comptes.add(compte);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestion des exceptions
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return comptes;
    }

    @Override
    public void update(Compte compte) {
        Connection connection = DBSingleton.getConnection();
        PreparedStatement preparedStatement = null;

        try {
            String query = "UPDATE compte SET num_compte = ?, date_creation = ?, solde = ?, bloque = ? WHERE  = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, compte.getNumCompte());
            preparedStatement.setDate(2, new java.sql.Date(compte.getDateCreation().getTime()));
            preparedStatement.setFloat(3, compte.getSolde());
            preparedStatement.setBoolean(4, compte.isBloque());
            preparedStatement.setLong(5, compte.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestion des exceptions
        }
    }

    @Override
    public Compte getByID(Integer id) {
        Connection connection = DBSingleton.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Compte compte = null;

        try {
            String query = "SELECT * FROM compte WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                long compteId = resultSet.getLong("id");
                String numCompte = resultSet.getString("num_compte");
                Date dateCreation = resultSet.getDate("date_creation");
                float solde = resultSet.getFloat("solde");
                boolean bloque = resultSet.getBoolean("bloque");
                long idClient = resultSet.getLong("client_id");

                Client client = getClientById((int) idClient);
                compte = new Compte(compteId, numCompte, dateCreation, solde, bloque, client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestion des exceptions
        }
        return compte;
    }

    @Override
    public void bloquerCompte(int idCompte) {

        updateBloqueStatus(idCompte, true);
    }

    @Override
    public void debloquerCompte(int idCompte) {
        updateBloqueStatus(idCompte, false);
    }

    @Override
    public void retirerMontant(int idCompte, float montant) {
        Connection connection = DBSingleton.getConnection();
        PreparedStatement preparedStatement = null;

        try {
            String query = "UPDATE compte SET solde = solde - ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setFloat(1, montant);
            preparedStatement.setLong(2, idCompte);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestion des exceptions
        }
    }

    @Override
    public void effectuerVersement(int idCompte, float montant) {
        Connection connection = DBSingleton.getConnection();
        PreparedStatement preparedStatement = null;

        try {
            String query = "UPDATE compte SET solde = solde + ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setFloat(1, montant);
            preparedStatement.setLong(2, idCompte);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestion des exceptions
        }
    }

    private void updateBloqueStatus(Integer idCompte, boolean bloque) {
        Connection connection = DBSingleton.getConnection();
        PreparedStatement preparedStatement = null;

        try {
            String query = "UPDATE compte SET bloque = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBoolean(1, bloque);
            preparedStatement.setLong(2, idCompte);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestion des exceptions
        }
    }

    private Client getClientById(int idClient) {
        Connection connection = DBSingleton.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Client client = null;

        try {
            String query = "SELECT * FROM client WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idClient);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Créer un objet Client à partir des données de la base de données
                client = new Client();
                client.setId(resultSet.getInt("id"));
                client.setNom(resultSet.getString("nom"));
                client.setPrenom(resultSet.getString("prenom"));
                client.setCin(resultSet.getString("cin"));
                client.setTelephone(resultSet.getString("telephone"));
                client.setEmail(resultSet.getString("email"));
                client.setDateNaissance(resultSet.getDate("date_naissance"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les exceptions
        }

        return client;
    }


}
