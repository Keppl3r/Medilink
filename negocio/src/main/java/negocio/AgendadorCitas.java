/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import com.mycompany.persistencia.IAccesoDatos;
import com.mycompany.persistencia.entidades.Cita;
import com.mycompany.persistencia.entidades.Especialista;
import com.mycompany.persistencia.entidades.EstadoCita;
import com.mycompany.persistencia.entidades.Paciente;
import dto.DTO_Cita;
import excepciones.NegocioException;
import infraestructura.ICorreos;
import infraestructura.IPagos;

/**
 * Implementacion del caso de uso principal de agendamiento de citas. Orquesta
 * validaciones, pago, persistencia y notificacion.
 *
 * @author Adrian Mendoza
 */
public class AgendadorCitas implements IAgendadorCita {

    private final IAccesoDatos accesoDatos;
    private final IPagos pagos;
    private final ICorreos correos;
    private static int contadorCitas = 0;

    /**
     * Constructor con inyeccion de dependencias.
     *
     * @param accesoDatos acceso a la capa de persistencia
     * @param pagos servicio externo de procesamiento de pagos
     * @param correos servicio externo de notificaciones por correo
     */
    public AgendadorCitas(IAccesoDatos accesoDatos, IPagos pagos, ICorreos correos) {
        this.accesoDatos = accesoDatos;
        this.pagos = pagos;
        this.correos = correos;
    }

     @Override
    public Cita agendarCita(DTO_Cita dto) throws NegocioException {
     if (dto == null
           || dto.getEspecialista() == null
           || dto.getPaciente() == null
           || dto.getMotivo() == null
           || dto.getMotivo().trim().isEmpty()
           || dto.getFechaHora() == null
           || dto.getCostoConsulta() <= 0) {
             throw new NegocioException("Los datos de la cita estan incompletos.");
        }
     Especialista especialista = accesoDatos.obtenerEspecialistaPorId(dto.getEspecialista().getId());
     Paciente paciente = accesoDatos.obtenerPaciente(dto.getPaciente().getId());

        if (especialista == null || paciente == null) {
            throw new NegocioException("Especialista o paciente no encontrado en el sistema.");
        }
        for (Cita c : accesoDatos.obtenerCitasPorEspecialista(especialista.getId())) {
            if (c.getFechaHora().equals(dto.getFechaHora())) {
                throw new NegocioException("El especialista no tiene disponibilidad.");
            }
        }
        
        if (!pagos.procesarPago(dto.getCostoConsulta(), String.valueOf(paciente.getId()))) {
            throw new NegocioException("Error al procesar el pago.");
        }
        contadorCitas++;
        Cita cita = new Cita(contadorCitas, especialista, paciente, dto.getFechaHora(), dto.getMotivo(), dto.getCostoConsulta());
        cita.setEstado(EstadoCita.CONFIRMADA);
        accesoDatos.guardarCita(cita);

        correos.enviarConfirmacion(
                paciente.getCorreo(),
                "Su cita con " + especialista.getNombre() + " " + especialista.getApellido()
                + " (" + especialista.getEspecialidad() + ") ha sido confirmada para el "
                + dto.getFechaHora() + ". Motivo: " + dto.getMotivo()
        );

        return cita;
    }
}
