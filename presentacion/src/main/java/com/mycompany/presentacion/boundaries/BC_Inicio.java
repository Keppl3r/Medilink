/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.presentacion.boundaries;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 * Pantalla de inicio estilo Medilink.
 * Muestra el logo y pregunta si el usuario es medico o paciente.
 *
 * @author devor
 */
public class BC_Inicio {

    private final Runnable onMedico;
    private final Runnable onPaciente;

    /**
     * Constructor con callbacks de navegacion.
     *
     * @param onMedico accion al seleccionar "Medico"
     * @param onPaciente accion al seleccionar "Paciente"
     */
    public BC_Inicio(Runnable onMedico, Runnable onPaciente) {
        this.onMedico = onMedico;
        this.onPaciente = onPaciente;
    }

    /**
     * Crea y retorna la escena de inicio con estilo Medilink.
     *
     * @return escena de bienvenida
     */
    public Scene crearEscena() {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: white;");

        // Triangulo diagonal de fondo (teal)
        Polygon triangulo = new Polygon();
        triangulo.getPoints().addAll(
                0.0, 0.0,
                700.0, 0.0,
                0.0, 550.0
        );
        triangulo.setFill(Color.web("#00BCD4"));

        // Contenido centrado
        VBox contenido = new VBox(15);
        contenido.setAlignment(Pos.CENTER);
        contenido.setPadding(new Insets(40));

        // Logo desde imagen
        ImageView logoView = null;
        try {
            Image logoImg = new Image(getClass().getResourceAsStream("/imagenes/logoblanco.png"));
            logoView = new ImageView(logoImg);
            logoView.setFitHeight(50);
            logoView.setPreserveRatio(true);
        } catch (Exception e) {
            // fallback a texto
        }

        Label logoFallback = new Label("medilink \u2764");
        logoFallback.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label tagline = new Label("el software para la salud");
        tagline.setStyle("-fx-font-size: 14px; -fx-text-fill: rgba(255,255,255,0.85);");

        // Pregunta
        Label pregunta = new Label("\u00bfEres medico o paciente?");
        pregunta.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white;");

        // Botones
        Button btnMedico = new Button("Login Medico");
        btnMedico.setStyle(
                "-fx-background-color: white; -fx-text-fill: #00BCD4; "
                + "-fx-font-size: 16px; -fx-font-weight: bold; "
                + "-fx-padding: 12 40; -fx-cursor: hand; "
                + "-fx-background-radius: 5;");
        btnMedico.setOnAction(e -> onMedico.run());

        Button btnPaciente = new Button("Login Paciente");
        btnPaciente.setStyle(
                "-fx-background-color: white; -fx-text-fill: #00BCD4; "
                + "-fx-font-size: 16px; -fx-font-weight: bold; "
                + "-fx-padding: 12 40; -fx-cursor: hand; "
                + "-fx-background-radius: 5;");
        btnPaciente.setOnAction(e -> onPaciente.run());

        HBox botones = new HBox(20);
        botones.setAlignment(Pos.CENTER);
        botones.getChildren().addAll(btnMedico, btnPaciente);

        if (logoView != null) {
            contenido.getChildren().addAll(logoView, tagline, pregunta, botones);
        } else {
            contenido.getChildren().addAll(logoFallback, tagline, pregunta, botones);
        }

        root.getChildren().addAll(triangulo, contenido);

        return new Scene(root, 700, 550);
    }
}
