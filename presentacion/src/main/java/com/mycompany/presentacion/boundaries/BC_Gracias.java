/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.presentacion.boundaries;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Pantalla de confirmacion exitosa de la cita.
 * Muestra agradecimiento y permite ingresar correo para confirmacion.
 *
 * @author devor
 */
public class BC_Gracias {

    private final Runnable onVolverDashboard;
    private final Runnable onCerrarSesion;

    /**
     * Constructor con callbacks de navegacion.
     *
     * @param onVolverDashboard accion al volver al dashboard
     * @param onCerrarSesion accion al cerrar sesion
     */
    public BC_Gracias(Runnable onVolverDashboard, Runnable onCerrarSesion) {
        this.onVolverDashboard = onVolverDashboard;
        this.onCerrarSesion = onCerrarSesion;
    }

    /**
     * Crea y retorna la escena de agradecimiento.
     *
     * @return escena con mensaje de exito y campo de email
     */
    public Scene crearEscena() {
        // Header
        HBox header = crearHeader();

        // Boton atras
        Button btnAtras = new Button("Atras  <");
        btnAtras.setStyle(
                "-fx-background-color: white; -fx-text-fill: #333; "
                + "-fx-font-size: 12px; -fx-cursor: hand; "
                + "-fx-border-color: #ddd; -fx-border-radius: 3; -fx-padding: 5 12;");
        btnAtras.setOnAction(e -> onVolverDashboard.run());

        HBox atrasBar = new HBox();
        atrasBar.setPadding(new Insets(15, 30, 0, 30));
        atrasBar.getChildren().add(btnAtras);

        // Titulo
        Label titulo = new Label("Muchas gracias por tu preferencia");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        // Subtitulo
        Label subtitulo1 = new Label("Enviaremos los detalles de tu cita y la receta a tu correo electronico.");
        subtitulo1.setStyle("-fx-font-size: 13px; -fx-text-fill: #777;");
        subtitulo1.setWrapText(true);

        Label subtitulo2 = new Label("Si prefieres que sea enviado a uno en particular, puedes ingresarlo aqui");
        subtitulo2.setStyle("-fx-font-size: 13px; -fx-text-fill: #777;");
        subtitulo2.setWrapText(true);

        // Campo de email
        TextField txtEmail = new TextField();
        txtEmail.setPromptText("Fulanito@email.com");
        txtEmail.setMaxWidth(350);
        txtEmail.setStyle(
                "-fx-font-size: 14px; -fx-padding: 12; "
                + "-fx-border-color: #ddd; -fx-border-radius: 5; "
                + "-fx-background-radius: 5;");

        // Boton subir/enviar
        Button btnSubir = new Button("Subir");
        btnSubir.setStyle(
                "-fx-background-color: #00BCD4; -fx-text-fill: white; "
                + "-fx-font-size: 13px; -fx-font-weight: bold; "
                + "-fx-padding: 10 35; -fx-cursor: hand; -fx-background-radius: 5;");
        btnSubir.setOnAction(e -> {
            System.out.println("Correo de confirmacion enviado a: "
                    + (txtEmail.getText().isEmpty() ? "(correo predeterminado)" : txtEmail.getText()));
            onVolverDashboard.run();
        });

        VBox contenido = new VBox(18);
        contenido.setAlignment(Pos.CENTER);
        contenido.setPadding(new Insets(50, 40, 40, 40));
        contenido.getChildren().addAll(titulo, subtitulo1, subtitulo2, txtEmail, btnSubir);

        VBox mainLayout = new VBox();
        mainLayout.getChildren().addAll(atrasBar, contenido);

        VBox layout = new VBox();
        layout.setStyle("-fx-background-color: #F5F5F5;");
        layout.getChildren().addAll(header, mainLayout);
        VBox.setVgrow(mainLayout, Priority.ALWAYS);

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

        HBox header = new HBox(10);
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
