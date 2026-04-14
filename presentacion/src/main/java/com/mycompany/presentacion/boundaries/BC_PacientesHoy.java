/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.presentacion.boundaries;

import com.mycompany.persistencia.entidades.Paciente;
import java.util.List;
import java.util.function.Consumer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Pantalla que muestra los pacientes de hoy para el medico.
 * Presenta tarjetas de pacientes con opcion de agendar cita.
 *
 * @author devor
 */
public class BC_PacientesHoy {

    private final List<Paciente> pacientes;
    private final Consumer<Paciente> onAgendarCita;
    private final Runnable onVolver;
    private final Runnable onCerrarSesion;

    /**
     * Constructor con datos y callbacks.
     *
     * @param pacientes lista de pacientes
     * @param onAgendarCita accion al seleccionar un paciente para agendar
     * @param onVolver accion al regresar al dashboard
     * @param onCerrarSesion accion al cerrar sesion
     */
    public BC_PacientesHoy(List<Paciente> pacientes, Consumer<Paciente> onAgendarCita,
                            Runnable onVolver, Runnable onCerrarSesion) {
        this.pacientes = pacientes;
        this.onAgendarCita = onAgendarCita;
        this.onVolver = onVolver;
        this.onCerrarSesion = onCerrarSesion;
    }

    /**
     * Crea y retorna la escena de pacientes de hoy.
     *
     * @return escena con tarjetas de pacientes
     */
    public Scene crearEscena() {
        HBox header = crearHeader();

        Button btnVolver = new Button("Atras  <");
        btnVolver.setStyle(
                "-fx-background-color: white; -fx-text-fill: #333; "
                + "-fx-font-size: 12px; -fx-cursor: hand; "
                + "-fx-border-color: #ddd; -fx-border-radius: 3; -fx-padding: 5 12;");
        btnVolver.setOnAction(e -> onVolver.run());

        Label titulo = new Label("Pacientes de hoy");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        HBox tituloBar = new HBox(15);
        tituloBar.setAlignment(Pos.CENTER_LEFT);
        tituloBar.setPadding(new Insets(15, 30, 10, 30));
        tituloBar.getChildren().addAll(btnVolver, titulo);

        FlowPane gridPacientes = new FlowPane(20, 20);
        gridPacientes.setPadding(new Insets(10, 30, 30, 30));
        gridPacientes.setAlignment(Pos.CENTER);

        for (Paciente pac : pacientes) {
            VBox card = crearTarjetaPaciente(pac);
            gridPacientes.getChildren().add(card);
        }

        ScrollPane scroll = new ScrollPane(gridPacientes);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: transparent; -fx-background: #F5F5F5;");

        VBox layout = new VBox();
        layout.setStyle("-fx-background-color: #F5F5F5;");
        layout.getChildren().addAll(header, tituloBar, scroll);
        VBox.setVgrow(scroll, Priority.ALWAYS);

        return new Scene(layout, 700, 550);
    }

    private VBox crearTarjetaPaciente(Paciente paciente) {
        Circle avatar = new Circle(30);
        avatar.setFill(Color.web("#B0BEC5"));

        Label iniciales = new Label(
                String.valueOf(paciente.getNombre().charAt(0))
                + String.valueOf(paciente.getApellido().charAt(0)));
        iniciales.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");

        StackPane avatarPane = new StackPane();
        avatarPane.getChildren().addAll(avatar, iniciales);

        Label nombre = new Label(paciente.getNombre() + " " + paciente.getApellido());
        nombre.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #333;");

        Label correo = new Label(paciente.getCorreo());
        correo.setStyle("-fx-font-size: 11px; -fx-text-fill: #999;");

        Button btnAgendar = new Button("Ver informacion clinica");
        btnAgendar.setStyle(
                "-fx-background-color: #00BCD4; -fx-text-fill: white; "
                + "-fx-font-size: 12px; -fx-padding: 6 20; -fx-cursor: hand; "
                + "-fx-background-radius: 3;");
        btnAgendar.setOnAction(e -> onAgendarCita.accept(paciente));

        VBox card = new VBox(10);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(20));
        card.setPrefWidth(180);
        card.setStyle(
                "-fx-background-color: white; -fx-background-radius: 10; "
                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 8, 0, 0, 2);");
        card.getChildren().addAll(avatarPane, nombre, correo, btnAgendar);

        return card;
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
