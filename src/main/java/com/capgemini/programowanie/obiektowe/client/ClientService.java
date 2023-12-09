package com.capgemini.programowanie.obiektowe.client;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClientService implements Clients {

    private final Map<String, Client> clients = new HashMap<>();

    @Override
    public String createNewClient(String firstName, String lastName) {
        String clientId = UUID.randomUUID().toString();
        clients.put(clientId, new Client(firstName, lastName));
        return clientId;
    }

    @Override
    public String activatePremiumAccount(String clientId) throws ClientNotFoundException {
        Client client = clients.get(clientId);
        if (client == null) {
            throw new ClientNotFoundException("Client with ID: " + clientId + " not found.");
        }
        client.setPremium(true);
        return clientId;
    }

    @Override
    public String getClientFullName(String clientId) throws ClientNotFoundException {
        Client client = clients.get(clientId);
        if (client == null) {
            throw new ClientNotFoundException("Client with ID: " + clientId + " not found.");
        }
        return client.getFirstName() + " " + client.getLastName();
    }

    @Override
    public LocalDate getClientCreationDate(String clientId) throws ClientNotFoundException {
        Client client = clients.get(clientId);
        if (client == null) {
            throw new ClientNotFoundException("Client with ID: " + clientId + " not found.");
        }
        return client.getCreationDate();
    }

    @Override
    public boolean isPremiumClient(String clientId) {
        Client client = clients.get(clientId);
        return client != null && client.isPremium();
    }

    @Override
    public int getNumberOfClients() {
        return clients.size();
    }

    @Override
    public int getNumberOfPremiumClients() {
        return (int) clients.values().stream().filter(Client::isPremium).count();
    }

    public boolean clientExists(String clientId) {
        return clients.containsKey(clientId);
    }
}