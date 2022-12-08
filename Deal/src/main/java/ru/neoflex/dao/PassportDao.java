package ru.neoflex.dao;

import org.hibernate.HibernateException;
import ru.neoflex.models.Passport;

import java.util.List;

public interface PassportDao {
    public Passport findPassportByID(int id) ;

    public void addPassport(Passport passport) ;

    public void updatePassport(Passport passport) ;

    public void removePassport(int id) ;

    public List<Passport> listPassports() ;

}
