package by.vchernukha.demo.controller;

import by.vchernukha.demo.dto.client.ClientDTO;
import by.vchernukha.demo.dto.client.CreateClientDTO;
import by.vchernukha.demo.exception.NotFoundEntityException;
import by.vchernukha.demo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "client", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/create")
    public ClientDTO createClient(@Valid @RequestBody CreateClientDTO dto) {
        return clientService.createClient(dto);
    }

    @GetMapping("/{id}")
    public ClientDTO getClientById(@PathVariable Long id) throws NotFoundEntityException {
        return clientService.getClientById(id);
    }

    @GetMapping("/all")
    public List<ClientDTO> getAllClients() {
        return clientService.getAllClients();
    }

}
