package by.vchernukha.demo.service.impl;

import by.vchernukha.demo.dto.account.ClientAccountDTO;
import by.vchernukha.demo.dto.account.CreateClientAccountDTO;
import by.vchernukha.demo.dto.account.UpdateBalanceDTO;
import by.vchernukha.demo.entity.ClientAccountEntity;
import by.vchernukha.demo.entity.ClientEntity;
import by.vchernukha.demo.exception.NegativeBalanceException;
import by.vchernukha.demo.exception.NotFoundEntityException;
import by.vchernukha.demo.mapper.ClientAccountMapper;
import by.vchernukha.demo.repository.ClientAccountRepository;
import by.vchernukha.demo.service.ClientAccountService;
import by.vchernukha.demo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClientAccountServiceImpl implements ClientAccountService {

    @Autowired
    private ClientAccountRepository clientAccountRepo;

    @Autowired
    private ClientAccountMapper mapper;

    @Autowired
    private ClientService clientService;

    @Override
    public ClientAccountDTO createClientAccount(CreateClientAccountDTO dto) throws NotFoundEntityException {
        ClientEntity clientEntity = clientService.getClientEntityById(dto.getClientId());
        return mapper.toClientAccountDto(clientAccountRepo.save(mapper.toClientAccountEntity(dto, clientEntity)));
    }

    @Override
    public ClientAccountDTO getClientAccountById(Long clientAccountId) throws NotFoundEntityException {
        return mapper.toClientAccountDto(clientAccountRepo.findById(clientAccountId)
                .orElseThrow(() -> new NotFoundEntityException("ClientAccountEntity with id=" + clientAccountId + " not found")));
    }

    @Override
    public List<ClientAccountDTO> getAllClientAccounts() {
        return mapper.toClientAccountDTOList(clientAccountRepo.findAll());
    }

    @Transactional
    @Override
    public ClientAccountDTO topUpBalance(UpdateBalanceDTO dto) throws NotFoundEntityException {
        ClientAccountEntity clientAccount = clientAccountRepo.findById(dto.getClientAccountId())
                .orElseThrow(() -> new NotFoundEntityException("ClientAccountEntity with id=" + dto.getClientAccountId() + " not found"));
        clientAccount.setBalance(clientAccount.getBalance().add(dto.getTransactionAmount()));
        return mapper.toClientAccountDto(clientAccountRepo.save(clientAccount));
    }

    @Transactional
    @Override
    public ClientAccountDTO writeOffBalance(UpdateBalanceDTO dto) throws NotFoundEntityException, NegativeBalanceException {
        ClientAccountEntity clientAccount = clientAccountRepo.findById(dto.getClientAccountId())
                .orElseThrow(() -> new NotFoundEntityException("ClientAccountEntity with id=" + dto.getClientAccountId() + " not found"));

        if (clientAccount.getBalance().compareTo(dto.getTransactionAmount()) > -1) {
            clientAccount.setBalance(clientAccount.getBalance().subtract(dto.getTransactionAmount()));
            clientAccountRepo.save(clientAccount);
        } else {
            throw new NegativeBalanceException("The account balance cannot be negative");
        }

        return mapper.toClientAccountDto(clientAccount);
    }

}
