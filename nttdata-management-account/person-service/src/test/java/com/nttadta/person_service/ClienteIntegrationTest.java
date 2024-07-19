package com.nttadta.person_service;

import com.nttadta.person_service.constants.PathConstants;
import com.nttadta.person_service.dto.request.ClienteDto;
import com.nttadta.person_service.dto.response.ClienteResponse;
import com.nttadta.person_service.model.EstadoCliente;
import com.nttadta.person_service.model.Persona;
import com.nttadta.person_service.repository.EstadoClienteRepository;
import com.nttadta.person_service.repository.PersonaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClienteIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PersonaRepository personRepository;

    @Autowired
    private EstadoClienteRepository clientStateRepository;

    @Test
    public void testCrearYConsultarCliente() {
        // Create instances of related entities
        Persona person = new Persona();
        person.setNombre("David");
        person.setGenero("M");
        person.setEdad(30);
        person.setIdentificacion("1234567890");
        person.setDireccion("Calle Falsa 123");
        person.setTelefono("0987654321");
        person = personRepository.save(person);

        EstadoCliente clientState = new EstadoCliente();
        clientState.setNombre("Activo");
        clientState = clientStateRepository.save(clientState);

        // Create ClienteDto
        ClienteDto clientDto = new ClienteDto();
        clientDto.setPassword("password");
        clientDto.setNombre(person.getNombre());
        clientDto.setGenero(person.getGenero());
        clientDto.setEdad(person.getEdad());
        clientDto.setIdentificacion(person.getIdentificacion());
        clientDto.setDireccion(person.getDireccion());
        clientDto.setTelefono(person.getTelefono());
        clientDto.setIdEstadoCliente(clientState.getId());

        //
        //Make the POST request to create the client
        ResponseEntity<ClienteResponse> responseEntity = restTemplate.postForEntity(
                "http://localhost:" + port +"/"+ PathConstants.CLIENT,
                clientDto,
                ClienteResponse.class
        );

        //
        //Check the answer
        assertEquals(200, responseEntity.getStatusCodeValue());

        ClienteResponse clientResponse = responseEntity.getBody();
        assertNotNull(clientResponse);
        assertNotNull(clientResponse.getId());
        assertEquals("David", clientResponse.getNombre());

        // Make GET request to query the client by ID
        ResponseEntity<ClienteResponse> responseGetEntity = restTemplate.getForEntity(
                "http://localhost:" + port +"/"+ PathConstants.CLIENT +"/" + clientResponse.getId(),
                ClienteResponse.class
        );

        // Check the resp¿getStatusCodeValue());
        ClienteResponse clientGetResponse = responseGetEntity.getBody();
        assertNotNull(clientGetResponse);
        assertEquals(clientResponse.getId(), clientGetResponse.getId());
        assertEquals("David", clientGetResponse.getNombre());
    }
}
