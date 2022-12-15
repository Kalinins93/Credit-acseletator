package ru.neoflex.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Passport implements Serializable {
    private final static long serialVersionUID = 7702L;
    private long passportId;
    private String series;
    private String number;
    private String issueBranch;
    private Date issueDate;
    private Client client;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passport passport = (Passport) o;
        return passportId == passport.passportId && Objects.equals(series, passport.series) && Objects.equals(number, passport.number) && Objects.equals(issueBranch, passport.issueBranch) && Objects.equals(issueDate, passport.issueDate) && Objects.equals(client, passport.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(passportId, series, number, issueBranch, issueDate, client);
    }
}
