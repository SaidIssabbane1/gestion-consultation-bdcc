package ma.enset.gestionconsultationbdcc.entities;

import java.sql.Date;
import java.util.List;

public class Consultation {
    private long id;
    private Date dateConsultation;
    private String description;
    private Patient patient;

    public Consultation() {
    }

    public Consultation(Date dateConsultation, String description, Patient patient) {
        this.dateConsultation = dateConsultation;
        this.description = description;
        this.patient = patient;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDateConsultation() {
        return dateConsultation;
    }

    public void setDateConsultation(Date dateConsultation) {
        this.dateConsultation = dateConsultation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public String toString() {
        return "Consultation{" +
                "id=" + id +
                ", dateConsultation=" + dateConsultation +
                ", description='" + description + '\'' +
                ", patient=" + patient +
                '}';
    }
}
