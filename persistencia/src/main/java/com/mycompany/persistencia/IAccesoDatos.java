/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.persistencia;

import com.mycompany.persistencia.entidades.Cita;
import com.mycompany.persistencia.entidades.Especialista;
import com.mycompany.persistencia.entidades.Paciente;
import java.util.List;

/**
 * Interfaz que define las operaciones de acceso a datos.
 * Abstrae el almacenamiento para desacoplar la persistencia del negocio.
 *
 * @author dylan
 */
public interface IAccesoDatos {

    /**
     * Obtiene la lista de todos los especialistas disponibles.
     *
     * @return lista de especialistas
     */
    List<Especialista> obtenerEspecialistas();

    /**
     * Busca un especialista por su identificador.
     *
     * @param id identificador del especialista
     * @return el especialista encontrado o null si no existe
     */
    Especialista obtenerEspecialistaPorId(int id);

    /**
     * Obtiene la lista de todos los pacientes registrados.
     *
     * @return lista de pacientes
     */
    List<Paciente> obtenerPacientes();

    /**
     * Obtiene un paciente por su identificador.
     *
     * @param id identificador del paciente
     * @return el paciente encontrado o null si no existe
     */
    Paciente obtenerPaciente(int id);

    /**
     * Autentica un especialista por usuario y contrasena.
     *
     * @param usuario nombre de usuario
     * @param contrasena contrasena del usuario
     * @return el especialista autenticado o null si las credenciales son invalidas
     */
    Especialista autenticarEspecialista(String usuario, String contrasena);

    /**
     * Autentica un paciente por usuario y contrasena.
     *
     * @param usuario nombre de usuario
     * @param contrasena contrasena del usuario
     * @return el paciente autenticado o null si las credenciales son invalidas
     */
    Paciente autenticarPaciente(String usuario, String contrasena);

    /**
     * Guarda una cita en el almacenamiento.
     *
     * @param cita la cita a guardar
     */
    void guardarCita(Cita cita);

    /**
     * Obtiene todas las citas registradas de un especialista.
     *
     * @param idEspecialista identificador del especialista
     * @return lista de citas del especialista
     */
    List<Cita> obtenerCitasPorEspecialista(int idEspecialista);
}
