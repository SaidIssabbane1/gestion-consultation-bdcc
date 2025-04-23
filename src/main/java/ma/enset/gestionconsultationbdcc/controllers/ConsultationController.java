package ma.enset.gestionconsultationbdcc.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ma.enset.gestionconsultationbdcc.dao.ConsultationDao;
import ma.enset.gestionconsultationbdcc.dao.PatientDao;
import ma.enset.gestionconsultationbdcc.entities.Consultation;
import ma.enset.gestionconsultationbdcc.entities.Patient;
import ma.enset.gestionconsultationbdcc.service.CabinetService;
import ma.enset.gestionconsultationbdcc.service.ICabinetService;

import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ConsultationController implements Initializable {
    @FXML private DatePicker dateConsultation;
    @FXML private TextArea textFiledDescription;
    @FXML private ComboBox<Patient> comboPatient;
    @FXML private TableView<Consultation> tableConsultation;
    @FXML private TableColumn<Consultation,Long> columnId;
    @FXML private TableColumn<Consultation,Date> ColumnDate;
    @FXML private TableColumn<Consultation,Patient> ColumnPatient;
    @FXML private TableColumn<Consultation,String> columnDescription;

    private ICabinetService cabinetService;
    private ObservableList<Patient> patients = FXCollections.observableArrayList();
    private ObservableList<Consultation> consultations = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cabinetService = new CabinetService(new PatientDao(),new ConsultationDao());
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        ColumnDate.setCellValueFactory(new PropertyValueFactory<>("dateConsultation"));
        //ColumnPatient.setCellValueFactory(new PropertyValueFactory<>("patient"));
        ColumnPatient.setCellValueFactory(new PropertyValueFactory<>("patient"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        consultations.setAll(cabinetService.getAllConsultations());

        comboPatient.setItems(patients);
        patients.setAll(cabinetService.getAllPatients());
        tableConsultation.setItems(consultations);
    }

    public void addConsultation() {
        Consultation consultation = new Consultation();
        consultation.setPatient(comboPatient.getValue());
        consultation.setDescription(textFiledDescription.getText());
        consultation.setDateConsultation(Date.valueOf(dateConsultation.getValue()));
        cabinetService.addConsultation(consultation);
        consultations.setAll(cabinetService.getAllConsultations());
    }

    public void deleteConsultation() {
        cabinetService.deleteConsultation(tableConsultation.getSelectionModel().getSelectedItem());
        consultations.setAll(cabinetService.getAllConsultations());
    }

    public void editConsultation() {
        Consultation consultation = tableConsultation.getSelectionModel().getSelectedItem();
        consultation.setPatient(comboPatient.getValue());
        consultation.setDescription(textFiledDescription.getText());
        consultation.setDateConsultation(Date.valueOf(dateConsultation.getValue()));
        cabinetService.updateConsultation(consultation);
        consultations.setAll(cabinetService.getAllConsultations());
    }
}
