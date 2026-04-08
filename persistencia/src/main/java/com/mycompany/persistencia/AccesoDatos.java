/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.persistencia;

import com.mycompany.persistencia.entidades.Cita;
import com.mycompany.persistencia.entidades.Especialista;
import com.mycompany.persistencia.entidades.Paciente;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementacion en memoria del acceso a datos.
 * Simula almacenamiento usando listas con datos precargados.
 *
 * @author devor
 */
public class AccesoDatos implements IAccesoDatos {

    private final List<Especialista> especialistas;
    private final List<Paciente> pacientes;
    private final List<Cita> citas;

    public AccesoDatos() {
        this.especialistas = new ArrayList<>();
        this.pacientes = new ArrayList<>();
        this.citas = new ArrayList<>();
        cargarDatosIniciales();
    }

    /**
     * Precarga datos de ejemplo para la simulacion.
     * 4 especialistas y 2 pacientes con credenciales de acceso.
     */
    private void cargarDatosIniciales() {
        especialistas.add(new Especialista(1, "Husky", "Garcia", "Cardiologia",
                "drhusky", "1234"));
        especialistas.add(new Especialista(2, "Maria", "Lopez", "Dermatologia",
                "drmaria", "1234"));
        especialistas.add(new Especialista(3, "Carlos", "Martinez", "Pediatria",
                "drcarlos", "1234"));
        especialistas.add(new Especialista(4, "Ana", "Rodriguez", "Neurologia",
                "drana", "1234"));

        pacientes.add(new Paciente(1, "Alejandro", "Lopez", "5551234567",
                "alejandro@correo.com", "alejandro", "1234",
                "A+", "Masculino", 88, "Paracetamol"));
        pacientes.add(new Paciente(2, "Sofia", "Martinez", "5559876543",
                "sofia@correo.com", "sofia", "1234",
                "O+", "Femenino", 25, "Ninguna"));
    }

    @Override
    public List<Especialista> obtenerEspecialistas() {
        return new ArrayList<>(especialistas);
    }

    @Override
    public Especialista obtenerEspecialistaPorId(int id) {
        for (Especialista esp : especialistas) {
            if (esp.getId() == id) {
                return esp;
            }
        }
        return null;
    }

    @Override
    public List<Paciente> obtenerPacientes() {
        return new ArrayList<>(pacientes);
    }

    @Override
    public Paciente obtenerPaciente(int id) {
        for (Paciente pac : pacientes) {
            if (pac.getId() == id) {
                return pac;
            }
        }
        return null;
    }

    @Override
    public Especialista autenticarEspecialista(String usuario, String contrasena) {
        for (Especialista esp : especialistas) {
            if (esp.getUsuario().equals(usuario) && esp.getContrasena().equals(contrasena)) {
                return esp;
            }
        }
        return null;
    }

    @Override
    public Paciente autenticarPaciente(String usuario, String contrasena) {
        for (Paciente pac : pacientes) {
            if (pac.getUsuario().equals(usuario) && pac.getContrasena().equals(contrasena)) {
                return pac;
            }
        }
        return null;
    }

    @Override
    public void guardarCita(Cita cita) {
        citas.add(cita);
        System.out.println("Cita guardada en el sistema: " + cita);
    }

    @Override
    public List<Cita> obtenerCitasPorEspecialista(int idEspecialista) {
        List<Cita> resultado = new ArrayList<>();
        for (Cita cita : citas) {
            if (cita.getEspecialista().getId() == idEspecialista) {
                resultado.add(cita);
            }
        }
        return resultado;
    }
}
