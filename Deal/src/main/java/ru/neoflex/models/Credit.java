package ru.neoflex.models;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "credits")
@NoArgsConstructor
public class Credit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "credit_id", nullable = false)
    private long id;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "term")
    private int term;
    @Column(name = "monthly_payment")
    private BigDecimal monthlyPayment;
    @Column(name = "rate")
    private BigDecimal rate;
    @Column(name = "psk")
    private BigDecimal psk;
    @Column(name = "payment_schedule")
    private BigDecimal paymentSchedule;
    @Column(name = "insurance_enable")
    private Boolean insuranceEnable;
    @Column(name = "salary_client")
    private Boolean salaryClient;
    @Enumerated(EnumType.STRING)
    private CreditStatus creditStatus;



}
