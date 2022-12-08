package ru.neoflex.models;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.TypeDef;
import ru.neoflex.openapi.model.EmploymentDTO;

import java.math.BigDecimal;
import java.util.List;
@Data
@NoArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Entity
@Table(name = "employments")
public class Employment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "employment_id", nullable = false)
    private long employmentId;
    @Enumerated(EnumType.STRING)
    private EmploymentDTO.EmploymentStatusEnum status;
    @Column(name = "employer_inn")
    private String employerInn;
    @Column(name = "salary")
    private BigDecimal salary;
    @Enumerated(EnumType.STRING)
    private EmploymentDTO.PositionEnum position;
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "employment")
    private List<Client> clients;
}
