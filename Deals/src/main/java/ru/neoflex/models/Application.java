package ru.neoflex.models;


import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
@Entity
@Table(name="applications")
@NoArgsConstructor
@Data
@AllArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Application {
    @NotNull
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
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
    @Type(type = "jsonb")
    @Column(name = "status_history", columnDefinition = "jsonb")
    private StatusHistory statusHistory;

}
