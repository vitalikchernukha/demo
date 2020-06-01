package by.vchernukha.demo.service;

import by.vchernukha.demo.dto.client.ClientDTO;
import by.vchernukha.demo.dto.client.CreateClientDTO;
import by.vchernukha.demo.entity.ClientEntity;
import by.vchernukha.demo.exception.NotFoundEntityException;

import java.util.List;

public interface ClientService {

    ClientDTO createClient(CreateClientDTO dto);

    ClientEntity getClientEntityById(Long clientId) throws NotFoundEntityException;

    ClientDTO getClientById(Long clientId) throws NotFoundEntityException;

    List<ClientDTO> getAllClients();

}
