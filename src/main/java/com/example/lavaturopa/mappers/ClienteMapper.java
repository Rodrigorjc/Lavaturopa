package com.example.lavaturopa.mappers;


import com.example.lavaturopa.dto.ClienteDTO;
import com.example.lavaturopa.modelos.Cliente;
import org.mapstruct.Mapper;

@Mapper
public interface ClienteMapper {

    /**
     * Pasa de un DTO a una Entity
     * @param dto
     * @return
     */
    Cliente toEntity(ClienteDTO dto);

    ClienteDTO toDTO(Cliente entity);


}
