package ru.neoflex.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.neoflex.dao.ApplicationDaoImpl;
import ru.neoflex.dao.ClientDaoImpl;
import ru.neoflex.dao.CreditDaoImpl;
import ru.neoflex.dao.PassportDaoImpl;
import ru.neoflex.models.Application;
import ru.neoflex.models.Client;
import ru.neoflex.models.Credit;
import ru.neoflex.models.Passport;

import java.util.List;
@Service
public class DealServiceImpl implements DealService {
    @Autowired
    private ClientDaoImpl clientDao;
    @Autowired
    private ApplicationDaoImpl applicationDao;
    @Autowired
    private CreditDaoImpl creditDao;
    @Autowired
    private PassportDaoImpl passportDao;
    @Override
    public Client findClientByID(int id) {
        return clientDao.findClientByID(id);
    }

    @Override
    public void addClient(Client client) {
        clientDao.addClient(client);
    }

    @Override
    public void updateClient(Client client) {
        clientDao.updateClient(client);
    }

    @Override
    public void removeClient(int id) {
        clientDao.removeClient(id);
    }

    @Override
    public List<Client> listClients() {
        return clientDao.listClients();
    }

    @Override
    public Credit findCreditByID(int id) {
        return creditDao.findCreditByID(id);
    }

    @Override
    public void addCredit(Credit credit) {
        creditDao.addCredit(credit);
    }

    @Override
    public void updateCredit(Credit credit) {
        creditDao.updateCredit(credit);
    }

    @Override
    public void removeCredit(int id) {
        creditDao.removeCredit(id);
    }

    @Override
    public List<Credit> listCredits() {
        return creditDao.listCredits();
    }

    @Override
    public Passport findPassportByID(int id)  {
        return passportDao.findPassportByID(id);
    }

    @Override
    public void addPassport(Passport passport) {
        passportDao.addPassport(passport);
    }

    @Override
    public void updatePassport(Passport passport) {
        passportDao.updatePassport(passport);
    }

    @Override
    public void removePassport(int id) {
        passportDao.removePassport(id);
    }

    @Override
    public List<Passport> listPassports()  {
        return passportDao.listPassports();
    }

    @Override
    public Application findApplicationByID(int id)  {
        return applicationDao.findApplicationByID(id);
    }

    @Override
    public void addApplication(Application application)  {
        applicationDao.addApplication(application);
    }

    @Override
    public void updateApplication(Application application)  {
        applicationDao.updateApplication(application);
    }

    @Override
    public void removeApplication(int id)  {
        applicationDao.removeApplication(id);
    }

    @Override
    public List<Application> listApplications()  {
        return applicationDao.listApplications();
    }
}
