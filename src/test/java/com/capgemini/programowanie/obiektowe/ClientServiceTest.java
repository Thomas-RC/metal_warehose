package com.capgemini.programowanie.obiektowe;

import static org.junit.jupiter.api.Assertions.*;

import com.capgemini.programowanie.obiektowe.client.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class ClientServiceTest {

    private ClientService clientService;

    @BeforeEach
    void setUp() {
        clientService = new ClientService();
    }

    @Test
    void testCreateNewClientAndRetrieveData() {
        String clientId = clientService.createNewClient("Jan", "Kowalski");
        assertNotNull(clientId);

        assertDoesNotThrow(() -> {
            String fullName = clientService.getClientFullName(clientId);
            assertEquals("Jan Kowalski", fullName);

            LocalDate creationDate = clientService.getClientCreationDate(clientId);
            assertNotNull(creationDate);
        });
    }

    @Test
    void testActivatePremiumAccount() {
        String clientId = clientService.createNewClient("Jan", "Kowalski");
        assertDoesNotThrow(() -> clientService.activatePremiumAccount(clientId));
        assertTrue(clientService.isPremiumClient(clientId));
    }
}