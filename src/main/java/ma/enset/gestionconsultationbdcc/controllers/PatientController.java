package ma.enset.gestionconsultationbdcc.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import ma.enset.gestionconsultationbdcc.dao.ConsultationDao;
import ma.enset.gestionconsultationbdcc.dao.PatientDao;
import ma.enset.gestionconsultationbdcc.entities.Patient;
import ma.enset.gestionconsultationbdcc.service.CabinetService;
import ma.enset.gestionconsultationbdcc.service.ICabinetService;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class PatientController implements Initializable {
    @FXML private TextField textFiledNom;
    @FXML private TextField textFieldPrenom;
    @FXML private TextField textFieldTel;
    @FXML private TextField textFieldSearch;
    @FXML private TableView<Patient> tablePatient;
    @FXML private TableColumn<Patient,Long> columnId;
    @FXML private TableColumn<Patient,String> columnNom;
    @FXML private TableColumn<Patient,String> columnTel;
    @FXML private TableColumn<Patient,String> columnPrenom;

    private ICabinetService cabinetService ;
    private ObservableList<Patient> patients =FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cabinetService = new CabinetService(new PatientDao(),new ConsultationDao());
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        columnTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        columnPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));

        patients.setAll(cabinetService.getAllPatients());
        tablePatient.setItems(patients);
        textFieldSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                //cabinetService.searchPatientByQuery(newValue);
                patients.setAll(cabinetService.searchPatientByQuery(newValue));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        tablePatient.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                textFieldPrenom.setText(newValue.getPrenom());
                textFieldTel.setText(newValue.getTel());
                textFiledNom.setText(newValue.getNom());
            }
        });
    }

    public void addPatient() {
        Patient patient = new Patient();
        patient.setNom(textFiledNom.getText());
        patient.setPrenom(textFieldPrenom.getText());
        patient.setTel(textFieldTel.getText());
        cabinetService.addPatient(patient);
        patients.setAll(cabinetService.getAllPatients());
    }

    public void delPatient() {
        tablePatient.getSelectionModel().getSelectedItem();
        cabinetService.deletePatient(tablePatient.getSelectionModel().getSelectedItem());
        patients.setAll(cabinetService.getAllPatients());
    }

    public void updatePatient() {
        Patient patient = tablePatient.getSelectionModel().getSelectedItem();
        patient.setNom(textFiledNom.getText());
        patient.setPrenom(textFieldPrenom.getText());
        patient.setTel(textFieldTel.getText());
        cabinetService.updatePatient(patient);
        patients.setAll(cabinetService.getAllPatients());
    }

}
