package by.vchernukha.demo.service;

import by.vchernukha.demo.dto.account.ClientAccountDTO;
import by.vchernukha.demo.dto.account.CreateClientAccountDTO;
import by.vchernukha.demo.dto.account.UpdateBalanceDTO;
import by.vchernukha.demo.dto.client.ClientDTO;
import by.vchernukha.demo.dto.client.CreateClientDTO;
import by.vchernukha.demo.exception.NegativeBalanceException;
import by.vchernukha.demo.exception.NotFoundEntityException;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@SpringBootTest
public class ClientAccountServiceTest {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientAccountService clientAccountService;

    @Test
    public void testCreateClientAccount() throws NotFoundEntityException {
        createClientAccount();
    }

    @Test
    @Transactional
    public void testGetClientAccountById() throws NotFoundEntityException {
        ClientAccountDTO expectedDTO = createClientAccount();
        ClientAccountDTO actualDTO = clientAccountService.getClientAccountById(expectedDTO.getId());
        assertTrue(equalsClientAccountDTO(expectedDTO, actualDTO));
    }

    @Test
    @Transactional
    public void testGetAllClientAccounts() throws NotFoundEntityException {
        List<ClientAccountDTO> expectedList = new ArrayList<>();
        expectedList.add(createClientAccount());
        expectedList.add(createClientAccount());
        expectedList.add(createClientAccount());

        List<ClientAccountDTO> actualList = clientAccountService.getAllClientAccounts();

        expectedList.forEach(expectedDTO ->
                assertTrue(actualList.stream()
                        .anyMatch(actualDTO -> equalsClientAccountDTO(actualDTO, expectedDTO))
                )
        );
    }

    @Test
    void testTopUpBalance() throws NotFoundEntityException {
        ClientAccountDTO initialClientAccountDTO = createClientAccount();
        BigDecimal transactionAmount = new BigDecimal(99.99);
        UpdateBalanceDTO updateBalanceDTO = new UpdateBalanceDTO(initialClientAccountDTO.getId(), transactionAmount);
        ClientAccountDTO actualClientAccountDTO = clientAccountService.topUpBalance(updateBalanceDTO);
        Assertions.assertEquals(initialClientAccountDTO.getBalance().add(transactionAmount), actualClientAccountDTO.getBalance());
    }

    @Test
    void testWriteOffBalance() throws NotFoundEntityException, NegativeBalanceException {
        ClientAccountDTO initialClientAccountDTO = createClientAccount();
        BigDecimal transactionAmount = new BigDecimal(Integer.MIN_VALUE);
        UpdateBalanceDTO updateBalanceDTO = new UpdateBalanceDTO(initialClientAccountDTO.getId(), transactionAmount);
        ClientAccountDTO actualClientAccountDTO = clientAccountService.writeOffBalance(updateBalanceDTO);
        Assertions.assertEquals(initialClientAccountDTO.getBalance().subtract(transactionAmount), actualClientAccountDTO.getBalance());
    }

    @Test
    void testNegativeBalanceWriteOffBalance() throws NotFoundEntityException {
        ClientAccountDTO initialClientAccountDTO = createClientAccount();
        BigDecimal transactionAmount = new BigDecimal(Integer.MAX_VALUE);
        UpdateBalanceDTO updateBalanceDTO = new UpdateBalanceDTO(initialClientAccountDTO.getId(), transactionAmount);
        Assertions.assertThrows(NegativeBalanceException.class, () -> clientAccountService.writeOffBalance(updateBalanceDTO));
    }

    private ClientAccountDTO createClientAccount() throws NotFoundEntityException {
        ClientDTO clientDTO = clientService.createClient(new CreateClientDTO("testClient"));
        CreateClientAccountDTO createClientAccountDTO = new CreateClientAccountDTO(clientDTO.getId(), new BigDecimal(0.00));
        ClientAccountDTO clientAccount = clientAccountService.createClientAccount(createClientAccountDTO);

        Assertions.assertTrue(EqualsBuilder.reflectionEquals(clientDTO, clientAccount.getClient()));
        Assertions.assertEquals(createClientAccountDTO.getInitialBalance(), clientAccount.getBalance());
        return clientAccount;
    }

    private boolean equalsClientAccountDTO(ClientAccountDTO expectedDTO, ClientAccountDTO actualDTO) {
        return EqualsBuilder.reflectionEquals(expectedDTO.getId(), actualDTO.getId())
                && EqualsBuilder.reflectionEquals(expectedDTO.getBalance(), actualDTO.getBalance())
                && EqualsBuilder.reflectionEquals(expectedDTO.getClient(), actualDTO.getClient());
    }

}

