package com.capgemini.programowanie.obiektowe;

import com.capgemini.programowanie.obiektowe.client.ClientNotFoundException;
import com.capgemini.programowanie.obiektowe.client.ClientService;
import com.capgemini.programowanie.obiektowe.warehouse.FullWarehouseException;
import com.capgemini.programowanie.obiektowe.warehouse.ProhibitedMetalTypeException;
import com.capgemini.programowanie.obiektowe.warehouse.SupportedMetalType;
import com.capgemini.programowanie.obiektowe.warehouse.WarehouseService;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        // Tworzymy instancję ClientService, która zarządza naszymi klientami
        ClientService clientService = new ClientService();

        // Tworzymy WarehouseService i przekazujemy do niego ClientService
        WarehouseService warehouseService = new WarehouseService(clientService);

        try {
            // Tworzenie nowego klienta i zwrócenie jego ID klienta
            String clientId = clientService.createNewClient("Jane", "Doe");

            // Sprawdzenie, czy klient został poprawnie utworzony
            LocalDate creationDate = clientService.getClientCreationDate(clientId);
            System.out.println("Client created at: " + creationDate);

            // Aktywacja konta premium dla klienta
            clientService.activatePremiumAccount(clientId);
            System.out.println("Premium account activated for: " + clientService.getClientFullName(clientId));

            // Dodanie metalu do magazynu dla klienta.
            warehouseService.addMetalIngot(clientId, SupportedMetalType.GOLD, 1500.0);
            System.out.println("Added 1500.0 units of GOLD for client with ID: " + clientId);

            // Wyświetlanie metali przechowywanych przez klienta oraz ich objętości
            System.out.println("Metals stored by client " + clientId + ": " +
                    warehouseService.getMetalTypesToMassStoredByClient(clientId));
            System.out.println("Total volume occupied by client " + clientId + ": " +
                    warehouseService.getTotalVolumeOccupiedByClient(clientId));

            // Wyświetlanie liczby klientów i klientów premium
            System.out.println("Total number of clients: " + clientService.getNumberOfClients());
            System.out.println("Number of premium clients: " + clientService.getNumberOfPremiumClients());

            // Dodajemy kolejnego klienta i metal do magazynu
            String clientId2 = clientService.createNewClient("John", "Smith");
            warehouseService.addMetalIngot(clientId2, SupportedMetalType.SILVER, 2000.0);
            System.out.println("Added 2000.0 units of SILVER for client with ID: " + clientId2);

            // Próba dodania metalu do klienta, który nie istnieje, aby wywołać wyjątek
            try {
                warehouseService.addMetalIngot("non-existing-id", SupportedMetalType.COPPER, 1000.0);
            } catch (ClientNotFoundException e) {
                System.out.println("Expected exception: " + e.getMessage());
            }

        } catch (ClientNotFoundException | ProhibitedMetalTypeException | FullWarehouseException e) {
            e.printStackTrace();
        }
    }
}