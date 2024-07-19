package com.nttadta.person_service.service.implement;

import com.nttadta.person_service.constants.PathConstants;
import com.nttadta.person_service.dto.request.ClienteDto;
import com.nttadta.person_service.dto.request.ClientePatchDto;
import com.nttadta.person_service.dto.request.PersonaDto;
import com.nttadta.person_service.dto.response.IClienteResponse;
import com.nttadta.person_service.exception.BadRequestException;
import com.nttadta.person_service.exception.ResourceNotFoundException;
import com.nttadta.person_service.model.Cliente;
import com.nttadta.person_service.model.EstadoCliente;
import com.nttadta.person_service.model.Persona;
import com.nttadta.person_service.repository.ClienteRepository;
import com.nttadta.person_service.repository.PersonaRepository;
import com.nttadta.person_service.service.ClienteService;
import com.nttadta.person_service.service.EstadoClienteService;
import com.nttadta.person_service.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Class for the service of client
 */
@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clientRepository;
    private final PersonService personService;
    private final EstadoClienteService clientStateService;
    private final PersonaRepository personRepository;

    /**
     * Method to create a client
     *
     * @param clientDto Object with the data of the client
     * @return Object with the data of the created client
     */
    @Override
    public CompletableFuture<IClienteResponse> create(ClienteDto clientDto) {
        return validateAndSaveClient(null, clientDto);
    }

    /**
     * Method to update a client
     *
     * @param id         Id of the client
     * @param clientDto Object with the data of the client
     * @return Object with the data of the created client
     */
    @Override
    public CompletableFuture<IClienteResponse> update(Long id, ClienteDto clientDto) {
        return validateAndSaveClient(id, clientDto);
    }

    /**
     * Method to update a client by fields
     *
     * @param id         Id of the client
     * @param clientDto Object with the data of the client
     */
    @Override
    public CompletableFuture<IClienteResponse> updateByFields(Long id, ClientePatchDto clientDto) {
        return CompletableFuture.supplyAsync(() -> {
            Cliente client = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(PathConstants.CLIENT, "id", id));
            Persona person = (Persona) personService.searchPersonById(client.getId(), Persona.class).join();
            if (clientDto.getNombre() != null)
                person.setNombre(clientDto.getNombre());
            if (clientDto.getGenero() != null)
                person.setGenero(clientDto.getGenero());
            if (clientDto.getEdad() != null)
                person.setEdad(clientDto.getEdad());
            if (clientDto.getIdentificacion() != null)
                person.setIdentificacion(clientDto.getIdentificacion());
            if (clientDto.getDireccion() != null)
                person.setDireccion(clientDto.getDireccion());
            if (clientDto.getTelefono() != null)
                person.setTelefono(clientDto.getTelefono());
            if (clientDto.getIdEstadoCliente() != null) {
                EstadoCliente clientState = clientStateService.searchClientStateById(clientDto.getIdEstadoCliente()).join();
                client.setEstadoCliente(clientState);
            }
            if (clientDto.getPassword() != null)
                client.setPassword(clientDto.getPassword());

            personRepository.save(person);
            client.setPerson(person);
            clientRepository.save(client);
            return clientRepository.findById(client.getId(), IClienteResponse.class);
        });
    }

    /**
     * Method to delete a client by id
     *
     * @param id Id of cliente
     */
    @Override
    public CompletableFuture<Void> deleteClientById(Long id) {
        return CompletableFuture.runAsync(() -> {
            Cliente cliente = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(PathConstants.CLIENT, "id", id));
            try {
                clientRepository.deleteById(id);
            } catch (DataIntegrityViolationException e) {
                throw new BadRequestException("Solo se puede eliminar el cliente si no tiene una cuenta asociada");
            }
            personService.delete(cliente.getPerson().getId());
        });
    }

    /**
     * Method to search client by id
     *
     * @param id Id of cliente
     * @return Object with the information of the client
     */
    @Override
    public CompletableFuture<IClienteResponse> searchClientById(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            var cliente = clientRepository.findById(id, IClienteResponse.class);
            if (cliente == null)
                throw new ResourceNotFoundException(PathConstants.CLIENT, "id", id);

            return clientRepository.findById(id, IClienteResponse.class);
        });
    }

    /**
     * Method to search all clients
     *
     * @return List with the data of the clients
     */
    @Override
    public CompletableFuture<List<IClienteResponse>> searchClients() {
        return CompletableFuture.supplyAsync(() ->
                clientRepository.findAll(IClienteResponse.class)
        );
    }

    /**
     * Method to validate and save a client
     *
     * @param id Id of cliente
     * @return clientDto Object with the information of the client
     * @return Object with the information of the client
     */
    private CompletableFuture<IClienteResponse> validateAndSaveClient(Long id, ClienteDto clientDto) {
        return CompletableFuture.supplyAsync(() -> {
            Cliente client = id != null ? clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(PathConstants.CLIENT, "id", id)) : new Cliente();
            Persona persona = id != null ? (Persona) personService.searchPersonById(client.getId(), Persona.class).join() : new Persona();
            PersonaDto personDto = new PersonaDto();
            personDto.setNombre(clientDto.getNombre());
            personDto.setGenero(clientDto.getGenero());
            personDto.setEdad(clientDto.getEdad());
            personDto.setIdentifiacion(clientDto.getIdentificacion());
            personDto.setDireccion(clientDto.getDireccion());
            personDto.setTelefono(clientDto.getTelefono());
            persona = id != null ? personService.update(persona.getId(), personDto).join() : personService.create(personDto).join();

            // Update the state of the client
            EstadoCliente clientState;
            try {
                clientState = clientStateService.searchClientStateById(clientDto.getIdEstadoCliente()).join();
            } catch (Exception e) {
                throw new ResourceNotFoundException(PathConstants.CLIENT_STATE, "id", clientDto.getIdEstadoCliente());
            }
            if (id != null) client.setId(id);
            client.setPerson(persona);
            client.setEstadoCliente(clientState);
            client.setPassword(clientDto.getPassword());
            clientRepository.save(client);
            return clientRepository.findById(client.getId(), IClienteResponse.class);

        });
    }
}
