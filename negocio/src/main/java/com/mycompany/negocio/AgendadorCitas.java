/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.negocio;

import com.mycompany.infraestructura.ICorreos;
import com.mycompany.persistencia.IAccesoDatos;
import com.mycompany.persistencia.entidades.Cita;
import com.mycompany.persistencia.entidades.Especialista;
import com.mycompany.persistencia.entidades.Paciente;

/**
 * Implementacion del caso de uso principal de agendamiento de citas.
 * Orquesta validaciones, pago, persistencia y notificacion.
 *
 * @author devor
 */
public class AgendadorCitas implements IAgendadorCita {

    private final IValidadorCitas validadorCitas;
    private final IValidadorPagos validadorPagos;
    private final IAccesoDatos accesoDatos;
    private final ICorreos correos;

    /**
     * Constructor con inyeccion de dependencias.
     *
     * @param validadorCitas validador de datos y disponibilidad
     * @param validadorPagos validador y procesador de pagos
     * @param accesoDatos acceso a la capa de persistencia
     * @param correos servicio de notificaciones por correo
     */
    public AgendadorCitas(IValidadorCitas validadorCitas, IValidadorPagos validadorPagos,
                          IAccesoDatos accesoDatos, ICorreos correos) {
        this.validadorCitas = validadorCitas;
        this.validadorPagos = validadorPagos;
        this.accesoDatos = accesoDatos;
        this.correos = correos;
    }

    @Override
    public Cita agendarCita(DTO_Negocios dto) throws Exception {
        // 1. Validar datos completos
        if (!validadorCitas.validarDatosCompletos(dto)) {
            throw new Exception("Los datos de la cita estan incompletos. "
                    + "Verifique que todos los campos esten llenos.");
        }

        // 2. Validar disponibilidad del especialista
        if (!validadorCitas.validarDisponibilidad(dto.getIdEspecialista(), dto.getFechaHora())) {
            throw new Exception("El especialista no tiene disponibilidad "
                    + "en la fecha y hora seleccionada.");
        }

        // 3. Procesar pago
        if (!validadorPagos.validarPago(dto.getCostoConsulta(),
                String.valueOf(dto.getIdPaciente()))) {
            throw new Exception("Error al procesar el pago. "
                    + "Intente de nuevo mas tarde.");
        }

        // 4. Obtener entidades y crear la cita
        Especialista especialista = accesoDatos.obtenerEspecialistaPorId(dto.getIdEspecialista());
        Paciente paciente = accesoDatos.obtenerPaciente(dto.getIdPaciente());

        Cita cita = ObjetosNegocio.crearCitaConfirmada(
                especialista, paciente, dto.getFechaHora(),
                dto.getMotivo(), dto.getCostoConsulta()
        );

        // 5. Guardar cita en persistencia
        accesoDatos.guardarCita(cita);

        // 6. Enviar notificacion por correo
        correos.enviarConfirmacion(
                paciente.getCorreo(),
                "Su cita con " + especialista.getNombre() + " " + especialista.getApellido()
                + " (" + especialista.getEspecialidad() + ") ha sido confirmada para el "
                + dto.getFechaHora() + ". Motivo: " + dto.getMotivo()
        );

        return cita;
    }
}
