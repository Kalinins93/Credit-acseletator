package ru.neoflex.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusHistory implements Serializable {
    private final static long serialVersionUID = 7703L;
    private String status;
    private LocalDate time;
    private Change_type change_type;

}
