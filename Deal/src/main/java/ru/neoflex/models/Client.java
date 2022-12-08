package ru.neoflex.models;

import jakarta.persistence.*;
import ru.neoflex.openapi.model.ScoringDataDTO;

import java.time.LocalDate;

@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "client_id", nullable = false)
    private long Id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "email")
    private String email;
    @Column(name = "birth_date")
    private LocalDate birthDate;
    @Enumerated(EnumType.STRING)
    private ScoringDataDTO.GenderEnum genderEnum;
    @Enumerated(EnumType.STRING)
    private  ScoringDataDTO.MaritalStatusEnum maritalStatusEnum;
    @Column(name = "dependent_amount")
    private int dependentAmount;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passport_id", nullable = false)
    private Passport passport;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employment_id", nullable = true)
    private Employment employment;
    @Column(name = "account")
    private String account;

    public Client(long id, String firstName, String lastName, String middleName, String email, LocalDate birthDate, ScoringDataDTO.GenderEnum genderEnum, ScoringDataDTO.MaritalStatusEnum maritalStatusEnum, int dependentAmount, Passport passport, Employment employment, String account) {
        Id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.email = email;
        this.birthDate = birthDate;
        this.genderEnum = genderEnum;
        this.maritalStatusEnum = maritalStatusEnum;
        this.dependentAmount = dependentAmount;
        this.passport = passport;
        this.employment = employment;
        this.account = account;
    }

    public Client() {
    }

    public long getId() {
        return Id;
    }

    public void setId(long clientIdLong) {
        this.Id = clientIdLong;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public ScoringDataDTO.GenderEnum getGenderEnum() {
        return genderEnum;
    }

    public void setGenderEnum(ScoringDataDTO.GenderEnum genderEnum) {
        this.genderEnum = genderEnum;
    }

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }

    public Employment getEmployment() {
        return employment;
    }

    public void setEmployment(Employment employment) {
        this.employment = employment;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
    public ScoringDataDTO.MaritalStatusEnum getMaritalStatusEnum() {
        return maritalStatusEnum;
    }

    public void setMaritalStatusEnum(ScoringDataDTO.MaritalStatusEnum maritalStatusEnum) {
        this.maritalStatusEnum = maritalStatusEnum;
    }

    public int getDependentAmount() {
        return dependentAmount;
    }

    public void setDependentAmount(int dependentAmount) {
        this.dependentAmount = dependentAmount;
    }
}
