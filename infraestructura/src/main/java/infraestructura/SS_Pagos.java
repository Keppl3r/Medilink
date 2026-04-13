/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package infraestructura;

/**
 * Servicio simulado de procesamiento de pagos. Implementa IPagos simulando que
 * el pago siempre es exitoso.
 *
 * @author Adrian Mendoza 
 */
public class SS_Pagos implements IPagos {

    @Override
    public boolean procesarPago(double monto, String referenciaPaciente) {
        System.out.println("========== PROCESAMIENTO DE PAGO ==========");
        System.out.println("Paciente: " + referenciaPaciente);
        System.out.println("Monto: $" + String.format("%.2f", monto));
        System.out.println("Estado: Pago procesado exitosamente (simulado)");
        System.out.println("============================================");
        return true;
    }
}
