/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.presentacion.boundaries;

import java.util.function.BiConsumer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Pantalla de login para medicos.
 * Solicita usuario y contrasena para autenticar al especialista.
 *
 * @author devor
 */
public class BC_LoginMedico {

    private final BiConsumer<String, String> onLogin;
    private final Runnable onVolver;

    /**
     * Constructor con callbacks.
     *
     * @param onLogin accion al intentar login (usuario, contrasena)
     * @param onVolver accion al regresar
     */
    public BC_LoginMedico(BiConsumer<String, String> onLogin, Runnable onVolver) {
        this.onLogin = onLogin;
        this.onVolver = onVolver;
    }

    /**
     * Crea y retorna la escena de login medico.
     *
     * @return escena de login
     */
    public Scene crearEscena() {
        // Header
        HBox header = crearHeader();

        // Contenido del login
        Label titulo = new Label("Login Medico");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        Label lblUsuario = new Label("Usuario:");
        lblUsuario.setStyle("-fx-font-size: 14px; -fx-text-fill: #555;");

        TextField txtUsuario = new TextField();
        txtUsuario.setPromptText("Ingrese su usuario");
        txtUsuario.setMaxWidth(300);
        txtUsuario.setStyle("-fx-font-size: 14px; -fx-padding: 10;");

        Label lblContrasena = new Label("Contrasena:");
        lblContrasena.setStyle("-fx-font-size: 14px; -fx-text-fill: #555;");

        PasswordField txtContrasena = new PasswordField();
        txtContrasena.setPromptText("Ingrese su contrasena");
        txtContrasena.setMaxWidth(300);
        txtContrasena.setStyle("-fx-font-size: 14px; -fx-padding: 10;");

        Label lblError = new Label();
        lblError.setStyle("-fx-text-fill: red; -fx-font-size: 13px;");

        Button btnLogin = new Button("Ingresar");
        btnLogin.setStyle(
                "-fx-background-color: #00BCD4; -fx-text-fill: white; "
                + "-fx-font-size: 15px; -fx-font-weight: bold; "
                + "-fx-padding: 10 50; -fx-cursor: hand; -fx-background-radius: 5;");
        btnLogin.setOnAction(e -> {
            String usuario = txtUsuario.getText().trim();
            String contrasena = txtContrasena.getText().trim();
            if (usuario.isEmpty() || contrasena.isEmpty()) {
                lblError.setText("Ingrese usuario y contrasena.");
                return;
            }
            onLogin.accept(usuario, contrasena);
        });

        Button btnVolver = new Button("Volver");
        btnVolver.setStyle(
                "-fx-background-color: transparent; -fx-text-fill: #00BCD4; "
                + "-fx-font-size: 13px; -fx-cursor: hand; -fx-border-color: #00BCD4; "
                + "-fx-border-radius: 5; -fx-padding: 8 30;");
        btnVolver.setOnAction(e -> onVolver.run());

        VBox formulario = new VBox(12);
        formulario.setAlignment(Pos.CENTER);
        formulario.setPadding(new Insets(40));
        formulario.getChildren().addAll(titulo, lblUsuario, txtUsuario,
                lblContrasena, txtContrasena, lblError, btnLogin, btnVolver);

        VBox layout = new VBox();
        layout.setStyle("-fx-background-color: #F5F5F5;");
        layout.getChildren().addAll(header, formulario);

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

        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(10, 20, 10, 20));
        header.setStyle("-fx-background-color: #1A2332;");
        header.getChildren().addAll(logoBox, spacer);
        return header;
    }
}
