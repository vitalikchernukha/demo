package by.vchernukha.demo.service;

import by.vchernukha.demo.dto.client.ClientDTO;
import by.vchernukha.demo.dto.client.CreateClientDTO;
import by.vchernukha.demo.exception.NotFoundEntityException;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@SpringBootTest
public class ClientServiceTest {

    @Autowired
    private ClientService clientService;

    @Test
    public void createClient() {
        createClient("testClient");
    }

    @Test
    public void testGetClientById() throws NotFoundEntityException {
        ClientDTO expectedDTO = createClient("testClient");
        ClientDTO actualDTO = clientService.getClientById(expectedDTO.getId());
        assertTrue(EqualsBuilder.reflectionEquals(expectedDTO, actualDTO));
    }

    @Test
    public void testNotFoundGetClientById() {
        Assertions.assertThrows(NotFoundEntityException.class, () -> clientService.getClientById(-1L));
    }

    @Test
    public void testGetAllClients() {
        List<ClientDTO> expectedClientList = new ArrayList<>();
        expectedClientList.add(createClient("testClient1"));
        expectedClientList.add(createClient("testClient2"));
        expectedClientList.add(createClient("testClient3"));

        List<ClientDTO> actualClientList = clientService.getAllClients();

        expectedClientList.forEach(expectedDTO ->
                assertTrue(actualClientList.stream()
                        .anyMatch(actualDTO -> EqualsBuilder.reflectionEquals(actualDTO, expectedDTO))
                )
        );
    }

    private ClientDTO createClient(String clientName) {
        CreateClientDTO createClientDTO = new CreateClientDTO(clientName);
        ClientDTO clientDTO = clientService.createClient(createClientDTO);
        assertEquals(createClientDTO.getName(), clientDTO.getName());
        return clientDTO;
    }

}
