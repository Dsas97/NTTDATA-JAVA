package com.nttadta.person_service.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClienteTest {

    /**
     * Test of Client Entity
     */
    @Test
    public void testCliente() {
        // Create instances of related entities
        Persona person = new Persona();
        EstadoCliente clientState = new EstadoCliente();

        // Create an instanceof client
        Cliente cliente = new Cliente();
        cliente.setPassword("secret");
        cliente.setPerson(person);
        cliente.setEstadoCliente(clientState);

        // Check the values
        assertEquals("secret", cliente.getPassword());
        assertEquals(person, cliente.getPerson());
        assertEquals(clientState, cliente.getEstadoCliente());
    }

    /**
     * Test fot the methods getter and setter of the entity Client
     */
    @Test
    public void testEquals() {
        Persona person1 = new Persona();
        EstadoCliente clientState1 = new EstadoCliente();
        Cliente client1 = new Cliente("secret", person1, clientState1);

        Persona person2 = new Persona();
        EstadoCliente clientState2 = new EstadoCliente();
        Cliente client2 = new Cliente("secret", person2, clientState2);

        //Verify that two Client instances are the same if they have the same values
        assertEquals(client1, client2);
    }

}
