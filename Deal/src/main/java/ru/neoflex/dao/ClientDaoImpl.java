package ru.neoflex.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.neoflex.models.Client;

import java.util.List;

@Repository
@Transactional
public class ClientDaoImpl implements ClientDao {
    private static final Logger logger = LoggerFactory.getLogger(ClientDaoImpl.class);
    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public Client findClientByID(int id) {
        Integer findId=(Integer)id;
        Session session =this.sessionFactory.getCurrentSession();
        Client client = (Client) session.load(Client.class, findId);
        return client;
    }

    @Override
    public void addClient(Client client) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(client);
    }

    @Override
    public void updateClient(Client client) {
        Session session =this.sessionFactory.getCurrentSession();
        session.update(client);
    }

    @Override
    public void removeClient(int id) {
        Integer removeId=(Integer)id;
        Session session =this.sessionFactory.getCurrentSession();
        Client client = (Client) session.load(Client.class, removeId);
        if (client!=null) session.delete(client);
    }

    @Override
    public List<Client> listClients() {
        Session session =this.sessionFactory.getCurrentSession();
        List<Client> clientList= session.createSQLQuery("select * from clients").addEntity(Client.class).list();
        return clientList;
    }
}
