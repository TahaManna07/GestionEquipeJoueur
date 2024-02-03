package ma.enset.examenjdcxfx1.dao;

import ma.enset.examenjdcxfx1.dao.entities.Client;
import ma.enset.examenjdcxfx1.dao.entities.Compte;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAOImpl implements IClientDAO {

    @Override
    public void ajouter(Client client) {
        Connection connection = DBSingleton.getConnection();
        PreparedStatement preparedStatement = null;

        try {
            String query = "INSERT INTO client (nom, prenom, cin, telephone, email, date_naissance) VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, client.getNom());
            preparedStatement.setString(2, client.getPrenom());
            preparedStatement.setString(3, client.getCin());
            preparedStatement.setString(4, client.getTelephone());
            preparedStatement.setString(5, client.getEmail());
            preparedStatement.setDate(6, new java.sql.Date(client.getDateNaissance().getTime()));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestion des exceptions
        }
    }

    @Override
    public void supprimer(Integer idClient) {
        Connection connection = DBSingleton.getConnection();
        PreparedStatement preparedStatement = null;

        try {
            // Commencez par supprimer les comptes associés au client
            String deleteComptesQuery = "DELETE FROM compte WHERE client_id = ?";
            preparedStatement = connection.prepareStatement(deleteComptesQuery);
            preparedStatement.setInt(1, idClient);
            preparedStatement.executeUpdate();

            // Ensuite, supprimez le client lui-même
            String deleteClientQuery = "DELETE FROM client WHERE id = ?";
            preparedStatement = connection.prepareStatement(deleteClientQuery);
            preparedStatement.setInt(1, idClient);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestion des exceptions
        }
    }


    @Override
    public List<Client> afficherListe() {
        List<Client> clients = new ArrayList<>();
        Connection connection = DBSingleton.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            String query = "SELECT * FROM client";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String cin = resultSet.getString("cin");
                String telephone = resultSet.getString("telephone");
                String email = resultSet.getString("email");
                Date dateNaissance = resultSet.getDate("date_naissance");

                Client client = new Client(id, nom, prenom, cin, telephone, email, dateNaissance);
                clients.add(client);
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

        return clients;
    }

    @Override
    public void update(Client client) {
        Connection connection = DBSingleton.getConnection();
        PreparedStatement preparedStatement = null;

        try {
            String query = "UPDATE client SET nom = ?, prenom = ?, cin = ?, telephone = ?, email = ?, date_naissance = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, client.getNom());
            preparedStatement.setString(2, client.getPrenom());
            preparedStatement.setString(3, client.getCin());
            preparedStatement.setString(4, client.getTelephone());
            preparedStatement.setString(5, client.getEmail());
            preparedStatement.setDate(6, new java.sql.Date(client.getDateNaissance().getTime()));
            preparedStatement.setLong(7, client.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestion des exceptions
        }
    }

    @Override
    public Client getByID(Integer id) {
        Connection connection = DBSingleton.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Client client = null;

        try {
            String query = "SELECT * FROM client WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
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

    @Override
    public List<Compte> getComptesClient(int idClient) {
        List<Compte> comptes = new ArrayList<>();
        Connection connection = DBSingleton.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Utilisation de INNER JOIN pour récupérer les comptes associés au client
            String query = "SELECT compte.* FROM compte " +
                    "INNER JOIN client ON compte.client_id = client.id " +
                    "WHERE client.id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idClient);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int idCompte = resultSet.getInt("id");
                String numCompte = resultSet.getString("num_compte");
                Date dateCreation = resultSet.getDate("date_creation");
                float solde = resultSet.getFloat("solde");
                boolean bloque = resultSet.getBoolean("bloque");

                // Notez que nous ne créons pas de nouveau Client ici
                // Nous devrions déjà avoir le client associé à partir de la jointure
                // Si nécessaire, vous pouvez également charger le client associé à ce moment-là
                Compte compte = new Compte(idCompte, numCompte, dateCreation, solde, bloque, null);
                comptes.add(compte);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestion des exceptions
        }
        return comptes;
    }

    @Override
    public List<Client> rechercherParMotCle(String motCle) {
        Connection connection = DBSingleton.getConnection();
        List<Client> resultats = new ArrayList<>();

        try {
            String query = "SELECT * FROM client WHERE nom LIKE ? OR prenom LIKE ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, "%" + motCle + "%");
                preparedStatement.setString(2, "%" + motCle + "%");

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String nom = resultSet.getString("nom");
                        String prenom = resultSet.getString("prenom");
                        String cin = resultSet.getString("cin");
                        String telephone = resultSet.getString("telephone");
                        String email = resultSet.getString("email");
                        Date dateNaissance = resultSet.getDate("date_naissance");

                        Client client = new Client(id, nom, prenom, cin, telephone, email, dateNaissance);
                        resultats.add(client);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les exceptions SQL
        }

        return resultats;
    }


}
