package com.api.rest.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import com.api.rest.Utilities;
import com.api.rest.exception.ResourceNotFoundException;
import com.api.rest.model.Empleado;
import com.api.rest.repository.EmpleadoRepository;
import com.api.rest.service.impl.EmpleadoServiceImpl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.api.rest.base.BaseTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmpleadoServiceTests extends BaseTest {
    @Mock //simulacro del repository
    private EmpleadoRepository empleadoRepository;
    
    @InjectMocks
    private EmpleadoServiceImpl empleadoService;
    
    @DisplayName("Test para guardar empleado")
    @Test
    void testGuardarEmpleado(){
        given(empleadoRepository.findByEmail(empleado.getEmail()))
                .willReturn(Optional.empty());

        given(empleadoRepository.save(empleado))
                .willReturn(empleado);

        //when
        Empleado empleadoGuardado = empleadoService.saveEmpleado(empleado);

        //then
        assertThat(empleadoGuardado).isNotNull();

    }
    @DisplayName("Test para guardar empleado con throwException")
    @Test
    void testGuardarEmpleadoConThrowException(){
        given(empleadoRepository.findByEmail(empleado.getEmail()))
                .willReturn(Optional.of(empleado));
        //when
        assertThrows(ResourceNotFoundException.class, () -> {
            empleadoService.saveEmpleado(empleado);

        } );
        //then
        verify(empleadoRepository, never()).save(any(Empleado.class));

    }
    @DisplayName("Test para listar empleados")
    @Test
    void testListarEmpleados(){
        Utilities utils = new Utilities();
        Empleado empleado1 = utils.createEmpleadoWithId(1, "Jorge", "Campos", "jorgecampos@gmail.com");

        given(empleadoRepository.findAll())
                .willReturn(List.of(empleado, empleado1));
        //when
        List<Empleado> empleados = empleadoService.getAllEmpleados();
        //then
        assertThat(empleados.size()).isEqualTo(2);

    }
    @DisplayName("Test para listar empleados de manera vacia")
    @Test
    void testListarEmpleadosVacia(){
        given(empleadoRepository.findAll())
                .willReturn(Collections.emptyList());
        //when
        List<Empleado> empleados = empleadoService.getAllEmpleados();
        //then
        assertThat(empleados.size()).isEqualTo(0);

    }
    @DisplayName("Test para obtener un empleado por id")
    @Test
    void testObtenerEmpleadoPorId(){
        Utilities utils = new Utilities();
        Empleado empleado1 = utils.createEmpleadoWithId(2, "Jorge", "Campos", "jorgecampos@gmail.com");

        given(empleadoRepository.findById(2L))
                .willReturn(Optional.of(empleado1));
        //when
        Empleado empleadoGuardado = empleadoService.getEmpleadoById(empleado1.getId()).get();
        //then
        assertThat(empleadoGuardado).isNotNull();
        assertThat(empleadoGuardado.getId()).isEqualTo(2);
        assertThat(empleadoGuardado.getNombre()).isEqualTo("Jorge");
        assertThat(empleadoGuardado.getApellido()).isEqualTo("Campos");
        assertThat(empleadoGuardado.getEmail()).isEqualTo("jorgecampos@gmail.com");

    }
    @DisplayName("Test para actualizar un empleado")
    @Test
    void testActualizarEmpleado(){
        given(empleadoRepository.save(empleado))
                .willReturn(empleado);
        empleado.setEmail("nameLastname@email.com");
        //when
        Empleado empleadoActualizado = empleadoService.updateEmpleado(empleado);
        //then
        assertThat(empleadoActualizado.getNombre()).isEqualTo("Christian");
        assertThat(empleadoActualizado.getApellido()).isEqualTo("Sanchez");
        assertThat(empleadoActualizado.getEmail()).isEqualTo("nameLastname@email.com");

    }
    @DisplayName("Test para eliminar un empleado")
    @Test
    void testEliminarEmpleado(){
        Utilities utils = new Utilities();
        Empleado empleado1 = utils.createEmpleadoWithId(2, "Jorge", "Campos", "jorgecampos@gmail.com");
        willDoNothing().given(empleadoRepository).deleteById(empleado1.getId());
        //when
        empleadoService.deleteEmpleado(empleado1.getId());
        //then
        verify(empleadoRepository, times(1)).deleteById(empleado1.getId());
    }

}
