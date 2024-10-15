package com.example.lavaturopa.servicios;


import com.example.lavaturopa.dto.ClienteDTO;
import com.example.lavaturopa.dto.ClienteEditDTO;
import com.example.lavaturopa.dto.ClienteNewDTO;
import com.example.lavaturopa.mappers.ClienteMapper;
import com.example.lavaturopa.modelos.Cliente;
import com.example.lavaturopa.repositorios.ClienteRepositorio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ClienteService {


    private ClienteRepositorio clienteRepositorio;
    private ClienteMapper clienteMapper;

    /**
     * Este metdo busca a todos los clientes con un DNI en concreto
     * @param dni
     * @return
     */
    public List<Cliente> getClientepoDNI(String dni) {
        List<Cliente> clientes = clienteRepositorio.findAllByDniEquals(dni);
        return clientes;
    }

    /**
     * Este metodo obtiene todos los clientes
     * @return
     */
    public List<ClienteDTO> getall(){
        List<Cliente> clientes = clienteRepositorio.findAll();
        List<ClienteDTO> clienteDTOS = new ArrayList<>();
        for (Cliente c : clientes){
//            ClienteDTO dto = new ClienteDTO();
//            dto.setNombre(c.getNombre());
//            dto.setApellidos(c.getApellidos());
//            dto.setTelefono(c.getTelefono());
//            clienteDTOS.add(dto);
            clienteDTOS.add(clienteMapper.toDTO(c));
        }
        return clienteDTOS;
    }

    /**
     * Este metodo busca un cliente por su id
     * @param id
     * @return
     */
    public ClienteDTO clienteById(Integer id){
        Cliente cliente = clienteRepositorio.findById(id).orElse(null);
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNombre(cliente.getNombre());
        clienteDTO.setApellidos(cliente.getApellidos());
        clienteDTO.setTelefono(cliente.getTelefono());
        return clienteDTO;
    }

    /**
     * Este metedo guarda un cliente nuevo
     * @param clienteNewDTO
     * @return
     */
    public Cliente saveCliente(ClienteNewDTO clienteNewDTO){
        Cliente cliente = new Cliente();
        cliente.setNombre(clienteNewDTO.getNombre());
        cliente.setApellidos(clienteNewDTO.getApellidos());
        cliente.setTelefono(clienteNewDTO.getTelefono());
        cliente.setDni(clienteNewDTO.getDni());
        cliente.setDireccion(clienteNewDTO.getDireccion());
        return clienteRepositorio.save(cliente);
    }

    /**
     * Edita un cliente existente
     * @param cliente
     * @param id
     * @return
     */
    public Cliente editCliente(ClienteEditDTO cliente, Integer id){
        Cliente clientedit = clienteRepositorio.getReferenceById(id);
        clientedit.setNombre(cliente.getNombre());
        clientedit.setApellidos(cliente.getApellidos());
        clientedit.setDireccion(cliente.getDireccion());
        clientedit.setTelefono(cliente.getTelefono());
        return clienteRepositorio.save(clientedit);
    }

    /**
     * Este metodo elimina a un cliente por su id
     * @param id
     */
    public String eliminar(Integer id){
        String mensaje = "";
        Cliente cliente = clienteRepositorio.findById(id).orElse(null);
        if (cliente == null) {
            mensaje = "El cliente con el id indicado no existe";
        }
        try {
            clienteRepositorio.deleteById(id);
            cliente = clienteRepositorio.findById(id).orElse(null);
            if (cliente != null){
                mensaje = "No se ha podido eliminar el clinete";
            }
            mensaje = "Cliente eliminado con exito";
        } catch (Exception e){
            mensaje = "No se ha podido eliminar el clinete";
        }
        return mensaje;
    }
}
