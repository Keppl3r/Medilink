/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocio;
import com.mycompany.persistencia.entidades.Cita;
import dto.DTO_Cita;
import excepciones.NegocioException;
/**
 * Interfaz principal del caso de uso de agendamiento de citas. Define el
 * contrato que expone el subsistema de negocio hacia la capa de presentacion.
 *
 * @author Adrian Mendoza
 */
public interface IAgendadorCita {
     /**
     * Agenda una cita medica realizando todas las validaciones,
     * procesamiento de pago, persistencia y notificacion.
     *
     * @param dto datos de la cita a agendar
     * @return la cita agendada y confirmada
     * @throws Exception si ocurre algun error en el proceso
     */
    Cita agendarCita(DTO_Cita dto) throws NegocioException;
}
