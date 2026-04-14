/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.presentacion.boundaries;

import com.mycompany.persistencia.entidades.Paciente;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Dashboard principal para el paciente.
 * Muestra bienvenida y acceso a agendar nueva cita.
 *
 * @author devor
 */
public class BC_DashboardPaciente {

    private final Paciente paciente;
    private final Runnable onAgendarCita;
    private final Runnable onCerrarSesion;

    /**
     * Constructor con datos del paciente y callbacks.
     *
     * @param paciente paciente autenticado
     * @param onAgendarCita accion al presionar "Agendar Nueva Cita"
     * @param onCerrarSesion accion al cerrar sesion
     */
    public BC_DashboardPaciente(Paciente paciente, Runnable onAgendarCita,
                                 Runnable onCerrarSesion) {
        this.paciente = paciente;
        this.onAgendarCita = onAgendarCita;
        this.onCerrarSesion = onCerrarSesion;
    }

    /**
     * Crea y retorna la escena del dashboard paciente.
     *
     * @return escena del dashboard
     */
    public Scene crearEscena() {
        HBox header = crearHeader();

        Label saludo = new Label("Hola, " + paciente.getNombre());
        saludo.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #333;");

        Label subtitulo = new Label("\u00bfComo podemos ayudarte hoy?");
        subtitulo.setStyle("-fx-font-size: 15px; -fx-text-fill: #777;");

        Button btnAgendar = new Button("\uD83D\uDCC5  Agendar Nueva Cita");
        btnAgendar.setStyle(
                "-fx-background-color: #00BCD4; -fx-text-fill: white; "
                + "-fx-font-size: 15px; -fx-font-weight: bold; "
                + "-fx-padding: 12 35; -fx-cursor: hand; -fx-background-radius: 20;");
        btnAgendar.setOnAction(e -> onAgendarCita.run());

        VBox contenido = new VBox(15);
        contenido.setAlignment(Pos.CENTER);
        contenido.setPadding(new Insets(80, 40, 40, 40));
        contenido.getChildren().addAll(saludo, subtitulo, btnAgendar);

        VBox layout = new VBox();
        layout.setStyle("-fx-background-color: #F5F5F5;");
        layout.getChildren().addAll(header, contenido);
        VBox.setVgrow(contenido, Priority.ALWAYS);

        return new Scene(layout, 700, 550);
    }

    private HBox crearHeader() {
        ImageView logoView;
        try {
            Image logoImg = new Image(getClass().getResourceAsStream("/imagenes/logoblanco.png"));
            logoView = new ImageView(logoImg);
            logoView.setFitHeight(28);
            logoView.setPreserveRatio(true);
        } catch (Exception e) {
            logoView = null;
        }

        Label logoText = new Label("medilink");
        logoText.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #00BCD4;");

        HBox logoBox = new HBox(5);
        logoBox.setAlignment(Pos.CENTER_LEFT);
        if (logoView != null) {
            logoBox.getChildren().add(logoView);
        } else {
            logoBox.getChildren().add(logoText);
        }

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox nav = crearNavegacion();

        Button btnLogout = new Button("Cerrar Sesion");
        btnLogout.setStyle(
                "-fx-background-color: transparent; -fx-text-fill: white; "
                + "-fx-font-size: 11px; -fx-cursor: hand; "
                + "-fx-border-color: rgba(255,255,255,0.5); -fx-border-radius: 3; "
                + "-fx-padding: 5 12;");
        btnLogout.setOnAction(e -> onCerrarSesion.run());

        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(10, 20, 10, 20));
        header.setStyle("-fx-background-color: #1A2332;");
        header.getChildren().addAll(logoBox, spacer, nav, btnLogout);
        return header;
    }

    private HBox crearNavegacion() {
        String[][] items = {
            {"\u2302", "Inicio"}, {"\uD83D\uDCC5", "Agendar"},
            {"\u2764", "Sintomas"}, {"\uD83D\uDCCB", "Recetas"}
        };
        HBox nav = new HBox(18);
        nav.setAlignment(Pos.CENTER_RIGHT);
        for (String[] item : items) {
            VBox navItem = new VBox(2);
            navItem.setAlignment(Pos.CENTER);
            Label icono = new Label(item[0]);
            icono.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
            Label texto = new Label(item[1]);
            texto.setStyle("-fx-text-fill: white; -fx-font-size: 10px;");
            navItem.getChildren().addAll(icono, texto);
            nav.getChildren().add(navItem);
        }
        return nav;
    }
}
