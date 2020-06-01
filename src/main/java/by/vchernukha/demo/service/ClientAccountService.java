package by.vchernukha.demo.service;

import by.vchernukha.demo.dto.account.ClientAccountDTO;
import by.vchernukha.demo.dto.account.CreateClientAccountDTO;
import by.vchernukha.demo.dto.account.UpdateBalanceDTO;
import by.vchernukha.demo.exception.NegativeBalanceException;
import by.vchernukha.demo.exception.NotFoundEntityException;

import java.util.List;

public interface ClientAccountService {

    ClientAccountDTO createClientAccount(CreateClientAccountDTO dto) throws NotFoundEntityException;

    ClientAccountDTO getClientAccountById(Long clientAccountId) throws NotFoundEntityException;

    List<ClientAccountDTO> getAllClientAccounts();

    ClientAccountDTO topUpBalance(UpdateBalanceDTO dto) throws NotFoundEntityException;

    ClientAccountDTO writeOffBalance(UpdateBalanceDTO dto) throws NotFoundEntityException, NegativeBalanceException;

}
