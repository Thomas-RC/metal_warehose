package com.capgemini.programowanie.obiektowe.warehouse;

import com.capgemini.programowanie.obiektowe.client.ClientNotFoundException;
import com.capgemini.programowanie.obiektowe.client.ClientService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WarehouseService implements Warehouse {
    private static final double MAX_WAREHOUSE_VOLUME = 10000.0; // Przykładowa maksymalna pojemność
    private final Map<String, Map<SupportedMetalType, Double>> clientMetals = new HashMap<>();

    // Referencja do ClientService
    private final ClientService clientService;

    // Konstruktor przyjmujący ClientService
    public WarehouseService(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public void addMetalIngot(String clientId, SupportedMetalType metalType, double mass)
            throws ClientNotFoundException, ProhibitedMetalTypeException, FullWarehouseException {

        // Sprawdzenie, czy klient istnieje za pomocą ClientService
        if (!clientService.clientExists(clientId)) {
            throw new ClientNotFoundException("Client with ID: " + clientId + " does not exist.");
        }

        double volumeToAdd = mass / metalType.getDensity();
        double totalVolume = getTotalVolumeOccupiedByClient(clientId);
        if (totalVolume + volumeToAdd > MAX_WAREHOUSE_VOLUME) {
            throw new FullWarehouseException("No space left in warehouse to store the specified mass of metal.");
        }

        // Dodanie masy metalu do magazynu dla klienta
        clientMetals.computeIfAbsent(clientId, k -> new HashMap<>());
        Map<SupportedMetalType, Double> metals = clientMetals.get(clientId);
        metals.merge(metalType, mass, Double::sum); // Nowa masa jest sumą starej i dodanej
    }

    @Override
    public Map<SupportedMetalType, Double> getMetalTypesToMassStoredByClient(String clientId)
            throws ClientNotFoundException {
        if (!clientService.clientExists(clientId)) {
            throw new ClientNotFoundException("Client with ID: " + clientId + " does not exist.");
        }
        return clientMetals.getOrDefault(clientId, new HashMap<>());
    }

    @Override
    public double getTotalVolumeOccupiedByClient(String clientId) {
        if (!clientService.clientExists(clientId)) {
            return 0.0;
        }
        Map<SupportedMetalType, Double> metals = clientMetals.get(clientId);
        return metals != null ? metals.entrySet().stream()
                .mapToDouble(entry -> entry.getValue() / entry.getKey().getDensity())
                .sum() : 0.0;
    }

    @Override
    public List<SupportedMetalType> getStoredMetalTypesByClient(String clientId)
            throws ClientNotFoundException {
        if (!clientService.clientExists(clientId)) {
            throw new ClientNotFoundException("Client with ID: " + clientId + " does not exist.");
        }
        Map<SupportedMetalType, Double> metals = clientMetals.get(clientId);
        return metals != null ? new ArrayList<>(metals.keySet()) : List.of();
    }
}