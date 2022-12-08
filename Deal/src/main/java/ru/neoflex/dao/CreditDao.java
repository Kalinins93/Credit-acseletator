package ru.neoflex.dao;

import ru.neoflex.models.Credit;

import java.util.List;

public interface CreditDao {
    public Credit findCreditByID(int id);

    public void addCredit(Credit credit);

    public void updateCredit(Credit credit);

    public void removeCredit(int id);

    public List<Credit> listCredits();
}
