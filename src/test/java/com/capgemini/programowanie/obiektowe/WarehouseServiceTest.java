package com.capgemini.programowanie.obiektowe;

import static org.junit.jupiter.api.Assertions.*;

import com.capgemini.programowanie.obiektowe.client.ClientNotFoundException;
import com.capgemini.programowanie.obiektowe.client.ClientService;
import com.capgemini.programowanie.obiektowe.warehouse.SupportedMetalType;
import com.capgemini.programowanie.obiektowe.warehouse.WarehouseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;

class WarehouseServiceTest {

    private WarehouseService warehouseService;
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        // Stworzenie mocka dla ClientService
        clientService = Mockito.mock(ClientService.class);
        warehouseService = new WarehouseService(clientService);
    }

    @Test
    void testAddMetalIngotAndRetrieveData() {
        String clientId = "test-client-id";
        Mockito.when(clientService.clientExists(clientId)).thenReturn(true);

        assertDoesNotThrow(() -> warehouseService.addMetalIngot(clientId, SupportedMetalType.GOLD, 1000.0));

        Map<SupportedMetalType, Double> metals = warehouseService.getMetalTypesToMassStoredByClient(clientId);
        assertNotNull(metals);
        assertTrue(metals.containsKey(SupportedMetalType.GOLD));
        assertEquals(1000.0, metals.get(SupportedMetalType.GOLD));

        double volume = warehouseService.getTotalVolumeOccupiedByClient(clientId);
        assertTrue(volume > 0.0);
    }

    @Test
    void testAddMetalIngotClientNotFoundException() {
        String clientId = "non-existing-client-id";
        Mockito.when(clientService.clientExists(clientId)).thenReturn(false);

        assertThrows(ClientNotFoundException.class, () -> {
            warehouseService.addMetalIngot(clientId, SupportedMetalType.GOLD, 1000.0);
        });
    }
}