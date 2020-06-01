package by.vchernukha.demo.mapper;

import by.vchernukha.demo.dto.client.ClientDTO;
import by.vchernukha.demo.dto.client.CreateClientDTO;
import by.vchernukha.demo.entity.ClientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(target = "id", ignore = true)
    ClientEntity toClientEntity(CreateClientDTO dto);

    ClientDTO toClientDTO(ClientEntity entity);

    List<ClientDTO> toClientDTOList(List<ClientEntity> entities);

}
