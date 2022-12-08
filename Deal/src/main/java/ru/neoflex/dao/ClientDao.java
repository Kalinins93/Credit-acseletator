package ru.neoflex.dao;

import ru.neoflex.models.Client;

import java.util.List;

public interface ClientDao {
    public Client findClientByID(int id);

    public void addClient(Client client);

    public void updateClient(Client client);

    public void removeClient(int id);

    public List<Client> listClients();
}
