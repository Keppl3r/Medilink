/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.presentacion;

import com.mycompany.negocio.DTO_Negocios;
import com.mycompany.negocio.IAgendadorCita;
import com.mycompany.persistencia.IAccesoDatos;
import com.mycompany.persistencia.entidades.Especialista;
import com.mycompany.persistencia.entidades.Paciente;
import com.mycompany.presentacion.boundaries.BC_DashboardMedico;
import com.mycompany.presentacion.boundaries.BC_DashboardPaciente;
import com.mycompany.presentacion.boundaries.BC_DatosPaciente;
import com.mycompany.presentacion.boundaries.BC_Error;
import com.mycompany.presentacion.boundaries.BC_Gracias;
import com.mycompany.presentacion.boundaries.BC_Inicio;
import com.mycompany.presentacion.boundaries.BC_LoginMedico;
import com.mycompany.presentacion.boundaries.BC_LoginPaciente;
import com.mycompany.presentacion.boundaries.BC_MotivoCita;
import com.mycompany.presentacion.boundaries.BC_PacientesHoy;
import com.mycompany.presentacion.boundaries.BC_ResumenCita;
import com.mycompany.presentacion.boundaries.BC_SeleccionEspecialista;
import java.time.LocalDateTime;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Controlador principal del flujo de agendamiento de citas.
 * Orquesta la navegacion entre pantallas y delega la logica al negocio.
 * Soporta flujo de medico y paciente.
 *
 * @author devor
 */
public class ControlAgendarCita {

    private final IAgendadorCita agendador;
    private final IAccesoDatos datos;
    private final Stage stage;

    // Estado de sesion
    private Especialista medicoLogueado;
    private Paciente pacienteLogueado;

    // Estado acumulado durante el flujo de agendamiento
    private Especialista especialistaSeleccionado;
    private Paciente pacienteSeleccionado;
    private String motivo;
    private LocalDateTime fechaHora;
    private static final double COSTO_CONSULTA = 500.00;

    /**
     * Constructor con inyeccion de dependencias.
     *
     * @param agendador servicio de agendamiento de citas
     * @param datos acceso a datos para consultar especialistas y pacientes
     * @param stage ventana principal de JavaFX
     */
    public ControlAgendarCita(IAgendadorCita agendador, IAccesoDatos datos, Stage stage) {
        this.agendador = agendador;
        this.datos = datos;
        this.stage = stage;
    }

    // ===================== PANTALLA INICIAL =====================

    /**
     * Muestra la pantalla de inicio: seleccionar medico o paciente.
     */
    public void mostrarInicio() {
        limpiarSesion();
        BC_Inicio pantalla = new BC_Inicio(
                this::mostrarLoginMedico,
                this::mostrarLoginPaciente
        );
        cambiarEscena(pantalla.crearEscena(), "Medilink - Inicio");
    }

    // ===================== LOGIN =====================

    /**
     * Muestra la pantalla de login para medicos.
     */
    public void mostrarLoginMedico() {
        BC_LoginMedico pantalla = new BC_LoginMedico(
                (usuario, contrasena) -> {
                    Especialista medico = datos.autenticarEspecialista(usuario, contrasena);
                    if (medico != null) {
                        this.medicoLogueado = medico;
                        mostrarDashboardMedico();
                    } else {
                        mostrarError("Usuario o contrasena incorrectos.");
                    }
                },
                this::mostrarInicio
        );
        cambiarEscena(pantalla.crearEscena(), "Medilink - Login Medico");
    }

    /**
     * Muestra la pantalla de login para pacientes.
     */
    public void mostrarLoginPaciente() {
        BC_LoginPaciente pantalla = new BC_LoginPaciente(
                (usuario, contrasena) -> {
                    Paciente paciente = datos.autenticarPaciente(usuario, contrasena);
                    if (paciente != null) {
                        this.pacienteLogueado = paciente;
                        mostrarDashboardPaciente();
                    } else {
                        mostrarError("Usuario o contrasena incorrectos.");
                    }
                },
                this::mostrarInicio
        );
        cambiarEscena(pantalla.crearEscena(), "Medilink - Login Paciente");
    }

    // ===================== DASHBOARDS =====================

    /**
     * Muestra el dashboard del medico autenticado.
     */
    public void mostrarDashboardMedico() {
        BC_DashboardMedico pantalla = new BC_DashboardMedico(
                medicoLogueado,
                this::mostrarPacientesHoy,
                this::mostrarInicio
        );
        cambiarEscena(pantalla.crearEscena(), "Medilink - Dashboard Medico");
    }

    /**
     * Muestra el dashboard del paciente autenticado.
     */
    public void mostrarDashboardPaciente() {
        BC_DashboardPaciente pantalla = new BC_DashboardPaciente(
                pacienteLogueado,
                this::mostrarSeleccionEspecialista,
                this::mostrarInicio
        );
        cambiarEscena(pantalla.crearEscena(), "Medilink - Dashboard Paciente");
    }

    // ===================== FLUJO MEDICO =====================

    /**
     * Muestra la lista de pacientes de hoy (vista del medico).
     */
    public void mostrarPacientesHoy() {
        BC_PacientesHoy pantalla = new BC_PacientesHoy(
                datos.obtenerPacientes(),
                this::mostrarDatosPaciente,
                this::mostrarDashboardMedico,
                this::mostrarInicio
        );
        cambiarEscena(pantalla.crearEscena(), "Medilink - Pacientes de Hoy");
    }

    /**
     * Muestra los datos detallados del paciente seleccionado.
     *
     * @param paciente paciente a visualizar
     */
    private void mostrarDatosPaciente(Paciente paciente) {
        this.pacienteSeleccionado = paciente;
        this.especialistaSeleccionado = medicoLogueado;

        BC_DatosPaciente pantalla = new BC_DatosPaciente(
                paciente,
                (motivoIngresado) -> {
                    this.motivo = motivoIngresado;
                    mostrarMotivoCitaMedico();
                },
                this::mostrarPacientesHoy,
                this::mostrarInicio
        );
        cambiarEscena(pantalla.crearEscena(), "Medilink - Datos del Paciente");
    }

    /**
     * Muestra pantalla de motivo y fecha (flujo medico).
     */
    private void mostrarMotivoCitaMedico() {
        BC_MotivoCita pantalla = new BC_MotivoCita(
                especialistaSeleccionado,
                (motivoIngresado, fechaSeleccionada) -> {
                    this.motivo = motivoIngresado;
                    this.fechaHora = fechaSeleccionada;
                    mostrarResumen();
                },
                this::mostrarPacientesHoy,
                this::mostrarInicio
        );
        cambiarEscena(pantalla.crearEscena(), "Medilink - Motivo de Cita");
    }

    // ===================== FLUJO PACIENTE =====================

    /**
     * Muestra la seleccion de especialistas (vista del paciente).
     */
    public void mostrarSeleccionEspecialista() {
        this.pacienteSeleccionado = pacienteLogueado;
        BC_SeleccionEspecialista pantalla = new BC_SeleccionEspecialista(
                datos.obtenerEspecialistas(),
                this::seleccionarEspecialista,
                this::mostrarDashboardPaciente,
                this::mostrarInicio
        );
        cambiarEscena(pantalla.crearEscena(), "Medilink - Seleccionar Especialista");
    }

    /**
     * El paciente selecciona un especialista.
     *
     * @param especialista especialista seleccionado
     */
    private void seleccionarEspecialista(Especialista especialista) {
        this.especialistaSeleccionado = especialista;
        mostrarMotivoCitaPaciente();
    }

    /**
     * Muestra pantalla de motivo (flujo paciente).
     */
    private void mostrarMotivoCitaPaciente() {
        BC_MotivoCita pantalla = new BC_MotivoCita(
                especialistaSeleccionado,
                (motivoIngresado, fechaSeleccionada) -> {
                    this.motivo = motivoIngresado;
                    this.fechaHora = fechaSeleccionada;
                    mostrarResumen();
                },
                this::mostrarSeleccionEspecialista,
                this::mostrarInicio
        );
        cambiarEscena(pantalla.crearEscena(), "Medilink - Motivo de Cita");
    }

    // ===================== FLUJO COMUN =====================

    /**
     * Muestra la pantalla de resumen de la cita con formulario de pago.
     */
    private void mostrarResumen() {
        Runnable onCancelar = (medicoLogueado != null)
                ? this::mostrarDashboardMedico
                : this::mostrarDashboardPaciente;

        BC_ResumenCita pantalla = new BC_ResumenCita(
                especialistaSeleccionado,
                motivo,
                fechaHora,
                COSTO_CONSULTA,
                this::confirmarCita,
                onCancelar
        );
        cambiarEscena(pantalla.crearEscena(), "Medilink - Resumen de Cita");
    }

    /**
     * Ejecuta el proceso de confirmacion de cita.
     * Construye el DTO y llama al agendador de negocio.
     */
    public void confirmarCita() {
        try {
            DTO_Negocios dto = new DTO_Negocios(
                    especialistaSeleccionado.getId(),
                    pacienteSeleccionado.getId(),
                    motivo,
                    fechaHora,
                    COSTO_CONSULTA
            );

            agendador.agendarCita(dto);
            mostrarGracias();

        } catch (Exception ex) {
            mostrarError(ex.getMessage());
        }
    }

    /**
     * Muestra la pantalla de error.
     *
     * @param mensaje mensaje de error
     */
    public void mostrarError(String mensaje) {
        Runnable onVolver;
        if (medicoLogueado != null) {
            onVolver = this::mostrarDashboardMedico;
        } else if (pacienteLogueado != null) {
            onVolver = this::mostrarDashboardPaciente;
        } else {
            onVolver = this::mostrarInicio;
        }

        BC_Error pantalla = new BC_Error(mensaje, onVolver);
        cambiarEscena(pantalla.crearEscena(), "Medilink - Error");
    }

    /**
     * Muestra la pantalla de agradecimiento con campo de email.
     */
    public void mostrarGracias() {
        Runnable onVolverDashboard = (medicoLogueado != null)
                ? this::mostrarDashboardMedico
                : this::mostrarDashboardPaciente;

        BC_Gracias pantalla = new BC_Gracias(onVolverDashboard, this::mostrarInicio);
        cambiarEscena(pantalla.crearEscena(), "Medilink - Cita Confirmada");
    }

    // ===================== UTILIDADES =====================

    /**
     * Cambia la escena del stage principal.
     */
    private void cambiarEscena(Scene scene, String titulo) {
        stage.setScene(scene);
        stage.setTitle(titulo);
        stage.show();
    }

    /**
     * Limpia todo el estado de sesion y flujo.
     */
    private void limpiarSesion() {
        medicoLogueado = null;
        pacienteLogueado = null;
        especialistaSeleccionado = null;
        pacienteSeleccionado = null;
        motivo = null;
        fechaHora = null;
    }
}
