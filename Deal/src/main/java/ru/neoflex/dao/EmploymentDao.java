package ru.neoflex.dao;

import org.hibernate.HibernateException;
import ru.neoflex.models.Employment;

import java.util.List;

public interface EmploymentDao {
    public Employment findEmploymentByID(int id) ;

    public void addEmployment(Employment employment) ;

    public void updateEmployment(Employment employment) ;

    public void removeEmployment(int id) ;

    public List<Employment> listEmployments() ;
}
