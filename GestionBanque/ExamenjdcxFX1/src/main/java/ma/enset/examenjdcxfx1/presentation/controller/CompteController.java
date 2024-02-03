package ma.enset.examenjdcxfx1.presentation.controller;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ma.enset.examenjdcxfx1.dao.CompteDAOImpl;
import ma.enset.examenjdcxfx1.dao.entities.Client;
import ma.enset.examenjdcxfx1.dao.entities.Compte;
import ma.enset.examenjdcxfx1.service.IBanqueService;
import ma.enset.examenjdcxfx1.service.IBanqueServiceImpl;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class CompteController implements Initializable {



    @FXML
    private MenuButton MenuButtonBloque;

    @FXML
    private TableColumn<Compte, Boolean> colonneBloque;

    @FXML
    private TableColumn<Compte, Integer> colonneClientID;

    @FXML
    private TableColumn<Compte, Date> colonneDateCreation;

    @FXML
    private TableColumn<Compte, Integer> colonneIdCompte;

    @FXML
    private TableColumn<Compte, String> colonneNumeroCompte;

    @FXML
    private TableColumn<Compte, Float> colonneSolde;

    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField labelBloque;


    @FXML
    private TextField labeLClientID;

    @FXML
    private TextField labeLNumCompte;

    @FXML
    private TextField labeLSolde;

    @FXML
    private Button bloquerButton;

    @FXML
    private Button debloquerButton;

    @FXML
    private TextField labeLVersementMontant;


    @FXML
    private TextField labelretirerMontant;

    @FXML
    private Button retierButton;

    @FXML
    private TableView<Compte> tableViewCompte;

    @FXML
    private Button verserButton;
    ObservableList<Compte> data = FXCollections.observableArrayList();
    IBanqueService metier = new IBanqueServiceImpl(new CompteDAOImpl());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colonneIdCompte.setCellValueFactory(new PropertyValueFactory<>("id"));
        colonneNumeroCompte.setCellValueFactory(new PropertyValueFactory<>("numCompte"));
        colonneDateCreation.setCellValueFactory(new PropertyValueFactory<>("dateCreation"));
        colonneSolde.setCellValueFactory(new PropertyValueFactory<>("solde"));
        colonneBloque.setCellValueFactory(new PropertyValueFactory<>("bloque"));
        colonneClientID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getClient().getId()).asObject());

        loadCompte();
        tableViewCompte.setItems(data);

    }
    private void loadCompte(){
        data.clear();
        data.addAll(metier.listerComptes());
    }
    @FXML
    void ajouterCompte(ActionEvent event) {
        String numCompte = labeLNumCompte.getText();
        Date dateCreation = Date.valueOf(datePicker.getValue());
        float solde = Float.parseFloat(labeLSolde.getText());
        Boolean bloque = Boolean.valueOf(labelBloque.getText());
        Client client = new Client();
        client.setId(Integer.parseInt(labeLClientID.getText()));

        //int idclient = Integer.parseInt(labeLClientID.getText());
        // You may need to retrieve the client ID from the appropriate field in your UI
        //int clientId = Integer.parseInt(labeLClientID.getText());

        // Create a new Compte object
        Compte nouveauCompte = new Compte();
        nouveauCompte.setNumCompte(numCompte);
        nouveauCompte.setDateCreation(dateCreation);
        nouveauCompte.setSolde(solde);
        nouveauCompte.setBloque(bloque);
        nouveauCompte.setClient(client);

        // You may need to set the client for the compte
        // nouveauCompte.setClient(client);

        // Call the method to add the new account
        metier.ajouterCompte(nouveauCompte);

        // Refresh the TableView
        loadCompte();
    }

    @FXML
    void retirerMontant(ActionEvent event) {
        Compte selectedCompte = tableViewCompte.getSelectionModel().getSelectedItem();

        if (selectedCompte != null) {
            try {
                float montantRetrait = Float.parseFloat(labelretirerMontant.getText());

                if (montantRetrait > 0 && montantRetrait <= selectedCompte.getSolde()) {
                    // Call the method to withdraw money
                    metier.retirerMontant((int)selectedCompte.getId(), montantRetrait);

                    // Refresh the TableView
                    loadCompte();
                } else {
                    showAlert("Montant invalide", "Le montant de retrait doit être positif et ne pas dépasser le solde actuel.", Alert.AlertType.WARNING);
                }
            } catch (NumberFormatException e) {
                showAlert("Montant invalide", "Veuillez entrer un montant valide.", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Sélectionnez un compte", "Veuillez sélectionner un compte pour retirer de l'argent.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void verserMontant(ActionEvent event) {
       // int compteId = Integer.parseInt(labeLVersementID.getText());
        float montant = Float.parseFloat(labeLVersementMontant.getText());
        Compte selectedCompte = tableViewCompte.getSelectionModel().getSelectedItem();


        // Call the method to deposit money
        metier.effectuerVersement((int) selectedCompte.getId() , montant);
        loadCompte();

        // Refresh the TableView
    }
    @FXML
    void supprimerCompte(ActionEvent event) {
            Compte compte = tableViewCompte.getSelectionModel().getSelectedItem();
            metier.supprimerCompte((int) compte.getId());
            loadCompte();
    }



    @FXML
    void bloquerCompte(ActionEvent event) {
        Compte selectedCompte = tableViewCompte.getSelectionModel().getSelectedItem();

        if (selectedCompte != null) {
            // Call the method to block the account
            metier.bloquerCompte((int) selectedCompte.getId());

            // Refresh the TableView
            loadCompte();
        } else {
            showAlert("Sélectionnez un compte", "Veuillez sélectionner un compte pour bloquer.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void debloquerCompte(ActionEvent event) {
        Compte selectedCompte = tableViewCompte.getSelectionModel().getSelectedItem();

        if (selectedCompte != null) {
            // Call the method to unblock the account
            metier.debloquerCompte((int) selectedCompte.getId());;

            // Refresh the TableView
            loadCompte();
        } else {
            showAlert("Sélectionnez un compte", "Veuillez sélectionner un compte pour débloquer.", Alert.AlertType.WARNING);
        }
    }
    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }




}

