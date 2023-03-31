package com.api.rest.base;

import com.api.rest.Utilities;
import com.api.rest.model.Empleado;
import org.junit.jupiter.api.BeforeEach;

public class BaseTest {
    public Empleado empleado;
    @BeforeEach
    void setUp(){
        Utilities utils = new Utilities();
        empleado = utils.createEmpleado("Christian","Sanchez","c22@gmail.com");

    }

}
