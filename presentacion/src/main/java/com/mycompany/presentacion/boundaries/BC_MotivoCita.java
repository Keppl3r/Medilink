/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.presentacion.boundaries;

import com.mycompany.persistencia.entidades.Especialista;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Pantalla para ingresar el motivo de la cita.
 * Incluye chips de sintomas seleccionables y seleccion de fecha/hora.
 *
 * @author devor
 */
public class BC_MotivoCita {

    /**
     * Interfaz funcional para recibir motivo y fecha seleccionados.
     */
    public interface OnSiguienteListener {
        void onSiguiente(String motivo, LocalDateTime fechaHora);
    }

    private final Especialista especialista;
    private final OnSiguienteListener onSiguiente;
    private final Runnable onAtras;
    private final Runnable onCerrarSesion;

    private final List<String> sintomasSeleccionados = new ArrayList<>();

    /**
     * Constructor con datos y callbacks de navegacion.
     *
     * @param especialista especialista seleccionado previamente
     * @param onSiguiente accion al continuar con motivo y fecha
     * @param onAtras accion al regresar
     * @param onCerrarSesion accion al cerrar sesion
     */
    public BC_MotivoCita(Especialista especialista, OnSiguienteListener onSiguiente,
                          Runnable onAtras, Runnable onCerrarSesion) {
        this.especialista = especialista;
        this.onSiguiente = onSiguiente;
        this.onAtras = onAtras;
        this.onCerrarSesion = onCerrarSesion;
    }

    /**
     * Crea y retorna la escena de motivo de cita.
     *
     * @return escena con campo de motivo, chips y seleccion de fecha
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
        btnAtras.setOnAction(e -> onAtras.run());

        HBox atrasBar = new HBox();
        atrasBar.setPadding(new Insets(15, 30, 0, 30));
        atrasBar.getChildren().add(btnAtras);

        // Titulo
        Label titulo = new Label("\u00bfCual es el motivo de tu visita?");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        // Card principal
        VBox cardContenido = new VBox(15);
        cardContenido.setPadding(new Insets(25));
        cardContenido.setMaxWidth(550);
        cardContenido.setStyle(
                "-fx-background-color: white; -fx-background-radius: 10; "
                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 8, 0, 0, 2);");

        // TextArea motivo
        TextArea txtMotivo = new TextArea();
        txtMotivo.setPromptText("Siento que ando muy acelerado ayudeme doctor");
        txtMotivo.setPrefRowCount(3);
        txtMotivo.setWrapText(true);
        txtMotivo.setStyle("-fx-font-size: 13px;");

        // Chips de sintomas
        FlowPane chipsPane = new FlowPane(8, 8);
        chipsPane.setAlignment(Pos.CENTER_LEFT);

        String[] sintomas = {"\uD83C\uDF21 Fiebre", "\uD83E\uDD15 Dolor de cabeza",
                "\u2764 Presion Alta", "\uD83E\uDE7A Nauseas", "\uD83D\uDCA4 Fatiga"};

        for (String sintoma : sintomas) {
            Button chip = crearChipSintoma(sintoma, txtMotivo);
            chipsPane.getChildren().add(chip);
        }

        // Fecha y hora
        Label lblFecha = new Label("Fecha y Hora de la cita:");
        lblFecha.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #555;");

        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Seleccione fecha");

        ComboBox<String> comboHora = new ComboBox<>();
        comboHora.getItems().addAll(
                "09:00", "10:00", "10:45", "11:00", "12:00",
                "13:00", "14:00", "15:00", "16:00", "17:00"
        );
        comboHora.setPromptText("Hora");

        HBox fechaHoraBox = new HBox(10);
        fechaHoraBox.setAlignment(Pos.CENTER_LEFT);
        fechaHoraBox.getChildren().addAll(datePicker, comboHora);

        // Botones de subida con FileChooser
        HBox uploads = new HBox(15);
        uploads.setAlignment(Pos.CENTER_LEFT);

        Button btnAnalisis = new Button("Sube tu analisis medico.   \u2191");
        btnAnalisis.setStyle(
                "-fx-background-color: white; -fx-text-fill: #777; "
                + "-fx-font-size: 12px; -fx-border-color: #ddd; "
                + "-fx-border-radius: 5; -fx-padding: 8 15; -fx-cursor: hand;");
        btnAnalisis.setOnAction(e -> {
            java.io.File archivo = crearFileChooser("Seleccionar analisis medico")
                    .showOpenDialog((Stage) btnAnalisis.getScene().getWindow());
            if (archivo != null) {
                btnAnalisis.setText("\u2705 " + archivo.getName());
                btnAnalisis.setStyle(
                        "-fx-background-color: #E8F5E9; -fx-text-fill: #2E7D32; "
                        + "-fx-font-size: 12px; -fx-border-color: #4CAF50; "
                        + "-fx-border-radius: 5; -fx-padding: 8 15; -fx-cursor: hand;");
                System.out.println("Analisis medico cargado: " + archivo.getAbsolutePath());
            }
        });

        Button btnHistorial = new Button("Sube tu historial clinico   \u2191");
        btnHistorial.setStyle(
                "-fx-background-color: white; -fx-text-fill: #777; "
                + "-fx-font-size: 12px; -fx-border-color: #ddd; "
                + "-fx-border-radius: 5; -fx-padding: 8 15; -fx-cursor: hand;");
        btnHistorial.setOnAction(e -> {
            java.io.File archivo = crearFileChooser("Seleccionar historial clinico")
                    .showOpenDialog((Stage) btnHistorial.getScene().getWindow());
            if (archivo != null) {
                btnHistorial.setText("\u2705 " + archivo.getName());
                btnHistorial.setStyle(
                        "-fx-background-color: #E8F5E9; -fx-text-fill: #2E7D32; "
                        + "-fx-font-size: 12px; -fx-border-color: #4CAF50; "
                        + "-fx-border-radius: 5; -fx-padding: 8 15; -fx-cursor: hand;");
                System.out.println("Historial clinico cargado: " + archivo.getAbsolutePath());
            }
        });

        uploads.getChildren().addAll(btnAnalisis, btnHistorial);

        Label lblError = new Label();
        lblError.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");

        // Boton enviar
        HBox enviarBox = new HBox();
        enviarBox.setAlignment(Pos.CENTER_RIGHT);

        Button btnEnviar = new Button("enviar");
        btnEnviar.setStyle(
                "-fx-background-color: #00BCD4; -fx-text-fill: white; "
                + "-fx-font-size: 13px; -fx-font-weight: bold; "
                + "-fx-padding: 8 30; -fx-cursor: hand; -fx-background-radius: 3;");
        btnEnviar.setOnAction(e -> {
            String motivo = txtMotivo.getText();
            if (!sintomasSeleccionados.isEmpty() && (motivo == null || motivo.trim().isEmpty())) {
                motivo = String.join(", ", sintomasSeleccionados);
            }
            LocalDate fecha = datePicker.getValue();
            String hora = comboHora.getValue();

            if (motivo == null || motivo.trim().isEmpty()) {
                lblError.setText("Debe ingresar el motivo o seleccionar sintomas.");
                return;
            }
            if (fecha == null) {
                lblError.setText("Debe seleccionar una fecha.");
                return;
            }
            if (hora == null) {
                lblError.setText("Debe seleccionar una hora.");
                return;
            }

            String[] partes = hora.split(":");
            LocalTime time = LocalTime.of(Integer.parseInt(partes[0]),
                                          Integer.parseInt(partes[1]));
            LocalDateTime fechaHoraFinal = LocalDateTime.of(fecha, time);
            onSiguiente.onSiguiente(motivo, fechaHoraFinal);
        });
        enviarBox.getChildren().add(btnEnviar);

        cardContenido.getChildren().addAll(txtMotivo, chipsPane, lblFecha,
                fechaHoraBox, uploads, lblError, enviarBox);

        VBox contenido = new VBox(15);
        contenido.setAlignment(Pos.TOP_CENTER);
        contenido.setPadding(new Insets(10, 30, 30, 30));
        contenido.getChildren().addAll(titulo, cardContenido);

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

    /**
     * Crea un chip de sintoma que se puede activar/desactivar.
     */
    private Button crearChipSintoma(String sintoma, TextArea txtMotivo) {
        Button chip = new Button(sintoma);
        final boolean[] activo = {false};
        chip.setStyle(
                "-fx-background-color: white; -fx-text-fill: #555; "
                + "-fx-font-size: 12px; -fx-border-color: #ddd; "
                + "-fx-border-radius: 15; -fx-background-radius: 15; "
                + "-fx-padding: 6 15; -fx-cursor: hand;");

        chip.setOnAction(e -> {
            activo[0] = !activo[0];
            if (activo[0]) {
                chip.setStyle(
                        "-fx-background-color: #00BCD4; -fx-text-fill: white; "
                        + "-fx-font-size: 12px; -fx-border-color: #00BCD4; "
                        + "-fx-border-radius: 15; -fx-background-radius: 15; "
                        + "-fx-padding: 6 15; -fx-cursor: hand;");
                sintomasSeleccionados.add(sintoma);
            } else {
                chip.setStyle(
                        "-fx-background-color: white; -fx-text-fill: #555; "
                        + "-fx-font-size: 12px; -fx-border-color: #ddd; "
                        + "-fx-border-radius: 15; -fx-background-radius: 15; "
                        + "-fx-padding: 6 15; -fx-cursor: hand;");
                sintomasSeleccionados.remove(sintoma);
            }
        });

        return chip;
    }

    /**
     * Crea un FileChooser configurado para documentos medicos.
     */
    private FileChooser crearFileChooser(String titulo) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(titulo);
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Documentos", "*.pdf", "*.doc", "*.docx"),
                new FileChooser.ExtensionFilter("Imagenes", "*.png", "*.jpg", "*.jpeg"),
                new FileChooser.ExtensionFilter("Todos los archivos", "*.*")
        );
        return fileChooser;
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

        // Navegacion
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
