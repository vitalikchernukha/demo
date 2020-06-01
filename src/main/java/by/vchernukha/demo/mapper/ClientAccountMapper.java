package by.vchernukha.demo.mapper;

import by.vchernukha.demo.dto.account.ClientAccountDTO;
import by.vchernukha.demo.dto.account.CreateClientAccountDTO;
import by.vchernukha.demo.entity.ClientAccountEntity;
import by.vchernukha.demo.entity.ClientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ClientMapper.class})
public interface ClientAccountMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "balance", source = "dto.initialBalance")
    @Mapping(target = "client", source = "clientEntity")
    ClientAccountEntity toClientAccountEntity(CreateClientAccountDTO dto, ClientEntity clientEntity);

    ClientAccountDTO toClientAccountDto(ClientAccountEntity entity);

    List<ClientAccountDTO> toClientAccountDTOList(List<ClientAccountEntity> entities);

}
