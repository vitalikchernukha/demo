package by.vchernukha.demo.service.impl;

import by.vchernukha.demo.dto.client.ClientDTO;
import by.vchernukha.demo.dto.client.CreateClientDTO;
import by.vchernukha.demo.entity.ClientEntity;
import by.vchernukha.demo.exception.NotFoundEntityException;
import by.vchernukha.demo.mapper.ClientMapper;
import by.vchernukha.demo.repository.ClientRepository;
import by.vchernukha.demo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepo;

    @Autowired
    private ClientMapper mapper;

    @Override
    public ClientDTO createClient(CreateClientDTO createDTO) {
        return mapper.toClientDTO(clientRepo.save(mapper.toClientEntity(createDTO)));
    }

    @Override
    public ClientEntity getClientEntityById(Long clientId) throws NotFoundEntityException {
        return clientRepo.findById(clientId)
                .orElseThrow(() -> new NotFoundEntityException("ClientEntity with id=" + clientId + " not found"));
    }

    @Override
    public ClientDTO getClientById(Long clientId) throws NotFoundEntityException {
        return mapper.toClientDTO(getClientEntityById(clientId));
    }

    @Override
    public List<ClientDTO> getAllClients() {
        return mapper.toClientDTOList(clientRepo.findAll());
    }

}
