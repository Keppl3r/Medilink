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
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Pantalla de error que muestra mensajes cuando ocurre un fallo.
 * Permite al usuario regresar al dashboard.
 *
 * @author devor
 */
public class BC_Error {

    private final String mensaje;
    private final Runnable onVolver;

    /**
     * Constructor con mensaje de error y callback de navegacion.
     *
     * @param mensaje mensaje de error a mostrar
     * @param onVolver accion al presionar "Volver"
     */
    public BC_Error(String mensaje, Runnable onVolver) {
        this.mensaje = mensaje;
        this.onVolver = onVolver;
    }

    /**
     * Crea y retorna la escena de error.
     *
     * @return escena con mensaje de error
     */
    public Scene crearEscena() {
        HBox header = crearHeader();

        Label titulo = new Label("Error");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #E53935;");

        Label lblMensaje = new Label(mensaje);
        lblMensaje.setStyle("-fx-font-size: 14px; -fx-text-fill: #555;");
        lblMensaje.setWrapText(true);
        lblMensaje.setMaxWidth(400);

        VBox cardError = new VBox(15);
        cardError.setAlignment(Pos.CENTER);
        cardError.setPadding(new Insets(30));
        cardError.setMaxWidth(450);
        cardError.setStyle(
                "-fx-background-color: white; -fx-background-radius: 10; "
                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 8, 0, 0, 2);");
        cardError.getChildren().addAll(titulo, lblMensaje);

        Button btnVolver = new Button("Volver al Inicio");
        btnVolver.setStyle(
                "-fx-background-color: #00BCD4; -fx-text-fill: white; "
                + "-fx-font-size: 14px; -fx-font-weight: bold; "
                + "-fx-padding: 10 35; -fx-cursor: hand; -fx-background-radius: 5;");
        btnVolver.setOnAction(e -> onVolver.run());

        VBox contenido = new VBox(25);
        contenido.setAlignment(Pos.CENTER);
        contenido.setPadding(new Insets(50, 40, 40, 40));
        contenido.getChildren().addAll(cardError, btnVolver);

        VBox layout = new VBox();
        layout.setStyle("-fx-background-color: #F5F5F5;");
        layout.getChildren().addAll(header, contenido);

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

        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(10, 20, 10, 20));
        header.setStyle("-fx-background-color: #1A2332;");
        header.getChildren().addAll(logoBox, spacer, nav);
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
