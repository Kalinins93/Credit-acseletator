package ru.neoflex.services;

import ru.neoflex.models.Application;
import ru.neoflex.models.Client;
import ru.neoflex.models.Credit;
import ru.neoflex.models.Passport;

import java.util.List;

public interface DealService {
    public Client findClientByID(int id);

    public void addClient(Client client);

    public void updateClient(Client client);

    public void removeClient(int id);

    public List<Client> listClients();

    public Credit findCreditByID(int id);

    public void addCredit(Credit credit);

    public void updateCredit(Credit credit);

    public void removeCredit(int id);

    public List<Credit> listCredits();
    public Application findApplicationByID(int id) ;

    public void addApplication(Application application) ;

    public void updateApplication(Application application) ;

    public void removeApplication(int id) ;

    public List<Application> listApplications() ;
}
