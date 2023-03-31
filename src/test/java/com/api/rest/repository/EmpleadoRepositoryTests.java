package com.api.rest.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.api.rest.Utilities;
import com.api.rest.base.BaseTest;
import com.api.rest.model.Empleado;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest //probar componentes de la capa de persistencia
public class EmpleadoRepositoryTests extends BaseTest {
   @Autowired
   private EmpleadoRepository empleadoRepository;

   @DisplayName("Test para guardar un empleado")
   @Test
   void testGuardarEmpleado(){
       Utilities utils = new Utilities();
       //given
       Empleado empleado1 = utils.createEmpleado("Pepe","Lopez","p12@gmail.com");
       //when
       Empleado empleadoGuardado = empleadoRepository.save(empleado1);
       //then
       assertThat(empleadoGuardado).isNotNull();
       assertThat(empleadoGuardado.getId()).isGreaterThan(0);

    }
    @DisplayName("Test para listar empleados")
    @Test
    void testListarEmpleados(){
        Utilities utils = new Utilities();
        //given
        Empleado empleado1 = utils.createEmpleado("Julen","Oliva","j2@gmail.com");
        empleadoRepository.save(empleado1);
        empleadoRepository.save(empleado);
        //when
        List<Empleado> listaEmpleados = empleadoRepository.findAll();
        //then
        assertThat(listaEmpleados).isNotNull();
        assertThat(listaEmpleados.size()).isEqualTo(2);


    }
    @DisplayName("Test para obtener empleado por ID")
    @Test
    void testListarEmpleadoPorId(){
        //given
        empleadoRepository.save(empleado);
        //when
        Empleado empleadoBD = empleadoRepository.findById(empleado.getId()).get();
        //then
        assertThat(empleadoBD).isNotNull();

    }
    @DisplayName("Test para actualizar un empleado")
    @Test
    void testActualizarEmpleado(){
        //given
        empleadoRepository.save(empleado);
        //when
        Empleado empleadoGuardado = empleadoRepository.findById(empleado.getId()).get();
        empleadoGuardado.setEmail("g21@gmail.com");
        empleadoGuardado.setNombre("Jorge");
        empleadoGuardado.setApellido("Ramirez");

        Empleado empleadoActualizado = empleadoRepository.save(empleadoGuardado);

        //then
        assertThat(empleadoActualizado.getEmail()).isEqualTo("g21@gmail.com");
        assertThat(empleadoActualizado.getNombre()).isEqualTo("Jorge");
        assertThat(empleadoActualizado.getApellido()).isEqualTo("Ramirez");

    }
    @DisplayName("Test para eliminar un empleado")
    @Test
    void testEliminarEmpleado(){
        //given
        empleadoRepository.save(empleado);
        //when
        empleadoRepository.deleteById(empleado.getId());
        Optional<Empleado> empleadoOptional = empleadoRepository.findById(empleado.getId());

        //then
        assertThat(empleadoOptional).isEmpty();

    }

}
