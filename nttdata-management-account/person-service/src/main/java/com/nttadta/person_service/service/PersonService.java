package com.nttadta.person_service.service;

import com.nttadta.person_service.dto.request.PersonaDto;
import com.nttadta.person_service.dto.response.IPersonaResponse;
import com.nttadta.person_service.model.Persona;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface PersonService {
    CompletableFuture<Persona> create(PersonaDto personDto);

    CompletableFuture<Persona> update(Long id, PersonaDto personDto);

    CompletableFuture<Object> searchPersonById(Long id, Class<?> type);

    CompletableFuture<Void> delete(Long id);

    CompletableFuture<List<IPersonaResponse>> searchPeople();
}
