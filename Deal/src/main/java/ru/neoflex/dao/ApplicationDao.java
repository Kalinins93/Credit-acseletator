package ru.neoflex.dao;

import ru.neoflex.models.Application;

import java.util.List;

public interface ApplicationDao {
    public Application findApplicationByID(int id) ;

    public void addApplication(Application application) ;

    public void updateApplication(Application application) ;

    public void removeApplication(int id) ;

    public List<Application> listApplications() ;
}
