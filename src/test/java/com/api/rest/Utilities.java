package com.api.rest;

import com.api.rest.model.Empleado;

public class Utilities {
    public Utilities(){

    }
    public Empleado createEmpleado(String name, String lastName, String email){
        return Empleado.builder()
                       .nombre(name)
                       .apellido(lastName)
                       .email(email)
                       .build();

    }
    public Empleado createEmpleadoWithId(int id, String name, String lastName, String email){
        return Empleado.builder()
                .id((long) id)
                .nombre(name)
                .apellido(lastName)
                .email(email)
                .build();

    }
}
