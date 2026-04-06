/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.negocio;

import com.mycompany.persistencia.entidades.Cita;
import com.mycompany.persistencia.entidades.Especialista;
import com.mycompany.persistencia.entidades.EstadoCita;
import com.mycompany.persistencia.entidades.Paciente;
import java.time.LocalDateTime;

/**
 * Clase fabrica para la creacion de objetos de negocio.
 * Centraliza la creacion de entidades del dominio.
 *
 * @author devor
 */
public class ObjetosNegocio {

    private static int contadorCitas = 0;

    /**
     * Crea un nuevo objeto Especialista.
     */
    public static Especialista crearEspecialista(int id, String nombre,
                                                  String apellido, String especialidad,
                                                  String usuario, String contrasena) {
        return new Especialista(id, nombre, apellido, especialidad, usuario, contrasena);
    }

    /**
     * Crea un nuevo objeto Paciente.
     */
    public static Paciente crearPaciente(int id, String nombre, String apellido,
                                          String telefono, String correo,
                                          String usuario, String contrasena,
                                          String tipoSangre, String sexo,
                                          int edad, String alergias) {
        return new Paciente(id, nombre, apellido, telefono, correo,
                usuario, contrasena, tipoSangre, sexo, edad, alergias);
    }

    /**
     * Crea un nuevo objeto Cita con estado PENDIENTE.
     */
    public static Cita crearCita(Especialista especialista, Paciente paciente,
                                  LocalDateTime fechaHora, String motivo,
                                  double costoConsulta) {
        contadorCitas++;
        return new Cita(contadorCitas, especialista, paciente, fechaHora,
                        motivo, costoConsulta);
    }

    /**
     * Crea una Cita y la marca como CONFIRMADA.
     */
    public static Cita crearCitaConfirmada(Especialista especialista, Paciente paciente,
                                            LocalDateTime fechaHora, String motivo,
                                            double costoConsulta) {
        Cita cita = crearCita(especialista, paciente, fechaHora, motivo, costoConsulta);
        cita.setEstado(EstadoCita.CONFIRMADA);
        return cita;
    }
}
