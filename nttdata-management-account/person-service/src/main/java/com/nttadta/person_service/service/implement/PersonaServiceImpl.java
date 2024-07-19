package com.nttadta.person_service.service.implement;

import com.nttadta.person_service.constants.PathConstants;
import com.nttadta.person_service.dto.request.PersonaDto;
import com.nttadta.person_service.dto.response.IPersonaResponse;
import com.nttadta.person_service.exception.ResourceNotFoundException;
import com.nttadta.person_service.model.Persona;
import com.nttadta.person_service.repository.PersonaRepository;
import com.nttadta.person_service.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Class fot the service Person
 */
@Service
@RequiredArgsConstructor
public class PersonaServiceImpl implements PersonService {

    private final PersonaRepository personRepository;


    /**
     * Method to create a person
     *
     * @param personDto Object with the person's information
     * @return Object with the information of the person created
     */
    @Override
    public CompletableFuture<Persona> create(PersonaDto personDto) {
        return validateAndSave(null, personDto);
    }

    /**
     * Method to update a person
     *
     * @param id Identifier of the person
     * @param personDto Object with the person's information
     * @return Object with the updated person information
     */
    @Override
    public CompletableFuture<Persona> update(Long id, PersonaDto personDto) {
        return validateAndSave(id, personDto);
    }

    /**
     * Method to update a person by fields
     *
     * @param id Identifier of the person
     * @param personDto Object with the person's information
     * @return Object with the updated person information
     */
    private CompletableFuture<Persona> validateAndSave(Long id, PersonaDto personDto){
        return CompletableFuture.supplyAsync(() -> {
            Persona person = id != null ? personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(PathConstants.PERSON, "id", id)) : new Persona();
            person.setNombre(personDto.getNombre());
            person.setGenero(personDto.getGenero());
            person.setEdad(personDto.getEdad());
            person.setIdentificacion(personDto.getIdentifiacion());
            person.setDireccion(personDto.getDireccion());
            person.setTelefono(personDto.getTelefono());
            return personRepository.save(person);
        });

    }

    /**
     * Method to search a person by id
     *
     * @param id Id of the person
     * @return Object with the information of the person
     */
    @Override
    public CompletableFuture<Object> searchPersonById(Long id, Class<?> type) {
        return CompletableFuture.supplyAsync(() -> {
            Object person = personRepository.findById(id, type);
            if (person == null) throw new ResourceNotFoundException(PathConstants.PERSON, "id", id);
            return person;
        });
    }

    /**
     * Method to delete a person
     *
     * @param id Id of the person
     */
    @Override
    public CompletableFuture<Void> delete(Long id) {
        return CompletableFuture.runAsync(() -> {
            Persona person = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(PathConstants.PERSON, "id", id));
            personRepository.delete(person);
        });
    }

    /**
     * Method to find all people
     *
     * @return List with the data of the people
     */
    @Override
    public CompletableFuture<List<IPersonaResponse>> searchPeople() {
        return CompletableFuture.supplyAsync(() -> personRepository.findAll(IPersonaResponse.class));
    }
}
