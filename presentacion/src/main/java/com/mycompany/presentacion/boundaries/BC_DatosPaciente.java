/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.presentacion.boundaries;

import com.mycompany.persistencia.entidades.Paciente;
import java.util.function.Consumer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Pantalla de datos del paciente para la vista del medico.
 * Muestra informacion personal, alergias y motivo de consulta.
 *
 * @author devor
 */
public class BC_DatosPaciente {

    private final Paciente paciente;
    private final Consumer<String> onAgendarCita;
    private final Runnable onVolver;
    private final Runnable onCerrarSesion;

    /**
     * Constructor con datos del paciente y callbacks.
     *
     * @param paciente paciente a visualizar
     * @param onAgendarCita accion al agendar cita con el motivo ingresado
     * @param onVolver accion al regresar
     * @param onCerrarSesion accion al cerrar sesion
     */
    public BC_DatosPaciente(Paciente paciente, Consumer<String> onAgendarCita,
                             Runnable onVolver, Runnable onCerrarSesion) {
        this.paciente = paciente;
        this.onAgendarCita = onAgendarCita;
        this.onVolver = onVolver;
        this.onCerrarSesion = onCerrarSesion;
    }

    /**
     * Crea y retorna la escena de datos del paciente.
     *
     * @return escena con datos del paciente
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
        btnAtras.setOnAction(e -> onVolver.run());

        HBox atrasBar = new HBox();
        atrasBar.setPadding(new Insets(15, 30, 0, 30));
        atrasBar.getChildren().add(btnAtras);

        // Titulo
        Label titulo = new Label("Datos del paciente");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        // Card principal
        VBox cardPrincipal = new VBox(20);
        cardPrincipal.setPadding(new Insets(25));
        cardPrincipal.setMaxWidth(600);
        cardPrincipal.setStyle(
                "-fx-background-color: white; -fx-background-radius: 10; "
                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 8, 0, 0, 2);");

        // Fila de datos basicos
        GridPane datosGrid = new GridPane();
        datosGrid.setHgap(20);
        datosGrid.setVgap(5);

        String[][] datos = {
            {"Nombre", paciente.getNombre()},
            {"Apellido\nPaterno", paciente.getApellido()},
            {"Tipo de\nsangre", paciente.getTipoSangre()},
            {"Sexo", paciente.getSexo()},
            {"Edad", String.valueOf(paciente.getEdad())}
        };

        for (int i = 0; i < datos.length; i++) {
            Label lbl = new Label(datos[i][0]);
            lbl.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #333;");
            Label val = new Label(datos[i][1]);
            val.setStyle("-fx-font-size: 12px; -fx-text-fill: #555;");
            datosGrid.add(lbl, i, 0);
            datosGrid.add(val, i, 1);
        }

        // Seccion alergias
        Label lblAlergias = new Label("\u24D8  Alergias");
        lblAlergias.setStyle(
                "-fx-font-size: 13px; -fx-text-fill: #333; "
                + "-fx-border-color: #ddd; -fx-border-radius: 12; "
                + "-fx-padding: 4 12; -fx-background-color: white;");

        TextArea txtAlergias = new TextArea(paciente.getAlergias());
        txtAlergias.setPrefRowCount(2);
        txtAlergias.setEditable(false);
        txtAlergias.setWrapText(true);
        txtAlergias.setStyle("-fx-font-size: 13px; -fx-opacity: 0.9;");

        // Seccion motivo de consulta
        Label lblMotivo = new Label("\u24D8  Motivo de consulta");
        lblMotivo.setStyle(
                "-fx-font-size: 13px; -fx-text-fill: #333; "
                + "-fx-border-color: #ddd; -fx-border-radius: 12; "
                + "-fx-padding: 4 12; -fx-background-color: white;");

        TextArea txtMotivo = new TextArea();
        txtMotivo.setPromptText("Ingrese el motivo de consulta del paciente...");
        txtMotivo.setPrefRowCount(3);
        txtMotivo.setWrapText(true);
        txtMotivo.setStyle("-fx-font-size: 13px;");

        // Botones de descarga (simulados)
        VBox descargas = new VBox(8);
        descargas.setAlignment(Pos.CENTER_RIGHT);

        Button btnDescAnalisis = new Button("Descargar analisis medico.   \u2193");
        btnDescAnalisis.setStyle(
                "-fx-background-color: white; -fx-text-fill: #777; "
                + "-fx-font-size: 12px; -fx-border-color: #ddd; "
                + "-fx-border-radius: 5; -fx-padding: 8 15; -fx-cursor: hand;");

        Button btnDescHistorial = new Button("Descargar tu historial clinico   \u2193");
        btnDescHistorial.setStyle(
                "-fx-background-color: white; -fx-text-fill: #777; "
                + "-fx-font-size: 12px; -fx-border-color: #ddd; "
                + "-fx-border-radius: 5; -fx-padding: 8 15; -fx-cursor: hand;");

        descargas.getChildren().addAll(btnDescAnalisis, btnDescHistorial);

        // Layout alergias + descargas en HBox
        HBox alergiasRow = new HBox(15);
        VBox alergiasCol = new VBox(5);
        alergiasCol.getChildren().addAll(lblAlergias, txtAlergias);
        HBox.setHgrow(alergiasCol, Priority.ALWAYS);
        alergiasRow.getChildren().addAll(alergiasCol, descargas);

        // Boton agendar
        Button btnAgendar = new Button("Agendar Cita");
        btnAgendar.setStyle(
                "-fx-background-color: #00BCD4; -fx-text-fill: white; "
                + "-fx-font-size: 14px; -fx-font-weight: bold; "
                + "-fx-padding: 10 35; -fx-cursor: hand; -fx-background-radius: 5;");
        btnAgendar.setOnAction(e -> {
            String motivo = txtMotivo.getText();
            if (motivo == null || motivo.trim().isEmpty()) {
                motivo = "Consulta general";
            }
            onAgendarCita.accept(motivo);
        });

        HBox btnBox = new HBox();
        btnBox.setAlignment(Pos.CENTER);
        btnBox.getChildren().add(btnAgendar);

        cardPrincipal.getChildren().addAll(datosGrid, alergiasRow, lblMotivo, txtMotivo, btnBox);

        VBox contenido = new VBox(15);
        contenido.setAlignment(Pos.TOP_CENTER);
        contenido.setPadding(new Insets(10, 30, 30, 30));
        contenido.getChildren().addAll(titulo, cardPrincipal);

        VBox mainLayout = new VBox();
        mainLayout.getChildren().addAll(atrasBar, contenido);

        ScrollPane scroll = new ScrollPane(mainLayout);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: #F5F5F5; -fx-background: #F5F5F5;");

        VBox layout = new VBox();
        layout.setStyle("-fx-background-color: #F5F5F5;");
        layout.getChildren().addAll(header, scroll);
        VBox.setVgrow(scroll, Priority.ALWAYS);

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
