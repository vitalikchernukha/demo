package by.vchernukha.demo.controller;

import by.vchernukha.demo.dto.account.ClientAccountDTO;
import by.vchernukha.demo.dto.account.CreateClientAccountDTO;
import by.vchernukha.demo.dto.account.UpdateBalanceDTO;
import by.vchernukha.demo.exception.NegativeBalanceException;
import by.vchernukha.demo.exception.NotFoundEntityException;
import by.vchernukha.demo.service.ClientAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "client-account", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientAccountController {

    @Autowired
    private ClientAccountService clientAccountService;


    @PostMapping("/create")
    public ClientAccountDTO createClientAccount(@Valid @RequestBody CreateClientAccountDTO dto) throws NotFoundEntityException {
        return clientAccountService.createClientAccount(dto);
    }

    @GetMapping("/{id}")
    public ClientAccountDTO getClientAccountById(@PathVariable Long id) throws NotFoundEntityException {
        return clientAccountService.getClientAccountById(id);
    }

    @GetMapping("/all")
    public List<ClientAccountDTO> getAllClientAccounts() {
        return clientAccountService.getAllClientAccounts();
    }

    @PostMapping("/balance/top-up")
    public ClientAccountDTO topUpBalance(@Valid @RequestBody UpdateBalanceDTO dto) throws NotFoundEntityException {
        return clientAccountService.topUpBalance(dto);
    }

    @PostMapping("/balance/write-off")
    public ClientAccountDTO writeOffBalance(@Valid @RequestBody UpdateBalanceDTO dto) throws NotFoundEntityException, NegativeBalanceException {
        return clientAccountService.writeOffBalance(dto);
    }

}
