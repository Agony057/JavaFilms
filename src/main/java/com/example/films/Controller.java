package com.example.films;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private static final String COMMA_DELIMITER = ",";

    @FXML
    private TableView tableau;
    @FXML
    private TableColumn col_rang;
    @FXML
    private TableColumn col_id;
    @FXML
    private TableColumn col_nom;
    @FXML
    private TableColumn col_annee;
    @FXML
    private TableColumn col_acteur_principal;

    @FXML
    private Button btn_supprimer;
    @FXML
    private Button btn_ajouter;
    @FXML
    private Button btn_vider;

    @FXML
    private TextField text_rang;
    @FXML
    private TextField text_id;
    @FXML
    private TextField text_nom;
    @FXML
    private TextField text_annee;
    @FXML
    private TextField text_acteur_principal;

    @FXML
    protected void onSupprimerButtonClick(){
        tableau.getItems().removeAll(tableau.getSelectionModel().getSelectedItem());
    }
    @FXML
    protected void onAjouterButtonClick() {
        String rang = text_rang.getText().trim();
        String id = text_id.getText().trim();
        String nom = text_nom.getText().trim();
        String annee = text_annee.getText().trim();
        String acteurPrincipal = text_acteur_principal.getText().trim();

        ajouterFilmDansTableau(isInt(rang), id, nom, isInt(annee), acteurPrincipal);

        text_rang.clear();
        text_id.clear();
        text_nom.clear();
        text_annee.clear();
        text_acteur_principal.clear();

    }

    public int isInt(String valeur){
        int res = 0;
        if (valeur.matches("^\\p{Digit}+$")){
            res = Integer.parseInt(valeur);
        }
        return res;
    }

    @FXML
    protected void onViderButtonClick() {
        ObservableList<Film> films = tableau.getItems();
        films.clear();
        tableau.setItems(films);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        col_rang.setCellValueFactory(new PropertyValueFactory<Film,String>("rang"));
        col_id.setCellValueFactory(new PropertyValueFactory<Film,String>("id"));
        col_nom.setCellValueFactory(new PropertyValueFactory<Film,String>("nom"));
        col_annee.setCellValueFactory(new PropertyValueFactory<Film,String>("année"));
        col_acteur_principal.setCellValueFactory(new PropertyValueFactory<Film, String>("acteurPrincipal"));



        List<List<String>> csv = getCsv();
        for (int i = 1; i < csv.size(); i++) {
            Film film = new Film();
            film.setRang(isInt(csv.get(i).get(0)));
            film.setId(csv.get(i).get(1));
            film.setNom(csv.get(i).get(2));
            film.setAnnée(isInt(csv.get(i).get(3)));
            film.setActeurPrincipal(csv.get(i).get(4));
            tableau.getItems().add(film);
        }

//        ObservableList<Film> films = tableau.getItems();
//        films.add(film);
//        tableau.setItems(films);
    }

    public static List<List<String>> getCsv(){

        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("movies.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                records.add(Arrays.asList(values));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return records;
    }

    public void ajouterFilmDansTableau(int rang, String id, String nom, int année, String acteurPrincipal){
        Film film = new Film();

        film.setRang(rang);
        film.setId(id);
        film.setNom(nom);
        film.setAnnée(année);
        film.setActeurPrincipal(acteurPrincipal);

        ObservableList<Film> films = tableau.getItems();
        films.add(film);
        tableau.setItems(films);
    }

}