/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.presentacion;

import com.mycompany.infraestructura.ICorreos;
import com.mycompany.infraestructura.IPagos;
import com.mycompany.infraestructura.SS_Notificaciones;
import com.mycompany.infraestructura.SS_Pagos;
import com.mycompany.negocio.AgendadorCitas;
import com.mycompany.negocio.IAgendadorCita;
import com.mycompany.negocio.IValidadorCitas;
import com.mycompany.negocio.IValidadorPagos;
import com.mycompany.negocio.ValidadorCitas;
import com.mycompany.negocio.ValidadorPagos;
import com.mycompany.persistencia.AccesoDatos;
import com.mycompany.persistencia.IAccesoDatos;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Clase principal de la aplicacion. Punto de entrada del sistema.
 * Actua como Composition Root: instancia todas las implementaciones
 * concretas e inyecta las dependencias mediante constructores.
 *
 * @author devor
 */
public class Presentacion extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 1. Capa Infraestructura (servicios externos simulados)
        ICorreos correos = new SS_Notificaciones();
        IPagos pagos = new SS_Pagos();

        // 2. Capa Persistencia (acceso a datos en memoria)
        IAccesoDatos datos = new AccesoDatos();

        // 3. Capa Negocio (logica de validacion y agendamiento)
        IValidadorCitas validadorCitas = new ValidadorCitas(datos);
        IValidadorPagos validadorPagos = new ValidadorPagos(pagos);
        IAgendadorCita agendador = new AgendadorCitas(
                validadorCitas, validadorPagos, datos, correos
        );

        // 4. Capa Presentacion (controlador que maneja las pantallas)
        ControlAgendarCita controller = new ControlAgendarCita(
                agendador, datos, primaryStage
        );

        // 5. Mostrar pantalla de inicio
        controller.mostrarInicio();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
