package ru.neoflex.models;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import ru.neoflex.openapi.model.ApplicationStatusHistoryDTO;

import java.time.LocalDateTime;
@Entity
@Table(name="applications")
@NoArgsConstructor
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "application_id", nullable = false)
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_id", nullable = false)
    private Credit credit;
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    @Column(name = "applied_offer")
    private String applied_offer;
    @Column(name = "sign_date")
    private LocalDateTime signDate;
    @Column(name = "ses_code")
    private int sesCode;
    @Enumerated(EnumType.STRING)
    private ApplicationStatusHistoryDTO.StatusEnum statusHistory;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getApplied_offer() {
        return applied_offer;
    }

    public void setApplied_offer(String applied_offer) {
        this.applied_offer = applied_offer;
    }

    public LocalDateTime getSignDate() {
        return signDate;
    }

    public void setSignDate(LocalDateTime signDate) {
        this.signDate = signDate;
    }

    public int getSesCode() {
        return sesCode;
    }

    public void setSesCode(int sesCode) {
        this.sesCode = sesCode;
    }

    public ApplicationStatusHistoryDTO.StatusEnum getStatusHistory() {
        return statusHistory;
    }

    public void setStatusHistory(ApplicationStatusHistoryDTO.StatusEnum statusHistory) {
        this.statusHistory = statusHistory;
    }
}
