package ru.neoflex.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.neoflex.openapi.model.EmploymentDTO;

import java.io.Serializable;
import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employment implements Serializable {
    private final static long serialVersionUID = 7704L;
    private long employmentId;
    private EmploymentDTO.EmploymentStatusEnum status;
    private String employerInn;
    private BigDecimal salary;
    private EmploymentDTO.PositionEnum position;
}
