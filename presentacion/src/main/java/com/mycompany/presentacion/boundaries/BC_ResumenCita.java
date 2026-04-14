/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.presentacion.boundaries;

import com.mycompany.persistencia.entidades.Especialista;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Pantalla de resumen de cita con formulario de pago.
 * Muestra datos del doctor a la izquierda y formulario de tarjeta a la derecha.
 *
 * @author devor
 */
public class BC_ResumenCita {

    private final Especialista especialista;
    private final String motivo;
    private final LocalDateTime fechaHora;
    private final double costo;
    private final Runnable onConfirmar;
    private final Runnable onCancelar;

    /**
     * Constructor con datos del resumen y callbacks.
     *
     * @param especialista especialista seleccionado
     * @param motivo motivo de la cita
     * @param fechaHora fecha y hora seleccionada
     * @param costo costo de la consulta
     * @param onConfirmar accion al confirmar pago
     * @param onCancelar accion al cancelar
     */
    public BC_ResumenCita(Especialista especialista, String motivo,
                           LocalDateTime fechaHora, double costo,
                           Runnable onConfirmar, Runnable onCancelar) {
        this.especialista = especialista;
        this.motivo = motivo;
        this.fechaHora = fechaHora;
        this.costo = costo;
        this.onConfirmar = onConfirmar;
        this.onCancelar = onCancelar;
    }

    /**
     * Crea y retorna la escena de resumen de cita con pago.
     *
     * @return escena con resumen y formulario de pago
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
        btnAtras.setOnAction(e -> onCancelar.run());

        HBox atrasBar = new HBox();
        atrasBar.setPadding(new Insets(15, 30, 0, 30));
        atrasBar.getChildren().add(btnAtras);

        // Titulo
        Label titulo = new Label("Resumen de Cita");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        Label subtitulo = new Label("Revisa los detalles antes de completar su pago");
        subtitulo.setStyle("-fx-font-size: 13px; -fx-text-fill: #777;");

        VBox tituloBox = new VBox(3);
        tituloBox.setPadding(new Insets(5, 30, 10, 30));
        tituloBox.getChildren().addAll(titulo, subtitulo);

        // === Card izquierda: info del doctor ===
        VBox cardDoctor = crearCardDoctor();

        // === Card derecha: formulario de pago ===
        VBox cardPago = crearCardPago();

        // Layout horizontal de cards
        HBox cardsRow = new HBox(20);
        cardsRow.setPadding(new Insets(0, 30, 30, 30));
        cardsRow.setAlignment(Pos.TOP_CENTER);
        cardsRow.getChildren().addAll(cardDoctor, cardPago);
        HBox.setHgrow(cardPago, Priority.ALWAYS);

        VBox mainLayout = new VBox();
        mainLayout.getChildren().addAll(atrasBar, tituloBox, cardsRow);

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
     * Crea la card con informacion del doctor y detalles de la cita.
     */
    private VBox crearCardDoctor() {
        // Estado disponible
        Label lblDisponible = new Label("\u2022 Disponible");
        lblDisponible.setStyle("-fx-font-size: 11px; -fx-text-fill: #4CAF50;");

        // Info doctor con avatar
        Circle avatar = new Circle(22);
        avatar.setFill(Color.web("#78909C"));

        Label iniciales = new Label(
                String.valueOf(especialista.getNombre().charAt(0))
                + String.valueOf(especialista.getApellido().charAt(0)));
        iniciales.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: white;");

        javafx.scene.layout.StackPane avatarPane = new javafx.scene.layout.StackPane();
        avatarPane.getChildren().addAll(avatar, iniciales);

        Label nombreDoc = new Label("Dr. " + especialista.getNombre());
        nombreDoc.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #333;");

        Label espLabel = new Label("Especialidad " + especialista.getEspecialidad());
        espLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #777;");

        VBox infoDoc = new VBox(2);
        infoDoc.getChildren().addAll(nombreDoc, espLabel);

        HBox docRow = new HBox(10);
        docRow.setAlignment(Pos.CENTER_LEFT);
        docRow.getChildren().addAll(infoDoc, avatarPane);

        // Detalles de cita
        DateTimeFormatter dayFmt = DateTimeFormatter.ofPattern("EEEE dd 'de' MMMM",
                new Locale("es"));
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("hh:mm a");

        String fechaStr = fechaHora.format(dayFmt);
        fechaStr = fechaStr.substring(0, 1).toUpperCase() + fechaStr.substring(1);

        String horaInicio = fechaHora.format(timeFmt);
        String horaFin = fechaHora.plusMinutes(45).format(timeFmt);

        Label lblFecha = new Label("\uD83D\uDCC5  " + fechaStr);
        lblFecha.setStyle("-fx-font-size: 12px; -fx-text-fill: #555;");

        Label lblHora = new Label("\uD83D\uDD52  " + horaInicio + " - " + horaFin);
        lblHora.setStyle("-fx-font-size: 12px; -fx-text-fill: #555;");

        Label lblLugar = new Label("\uD83D\uDCCD  Centro medico Sanjose");
        lblLugar.setStyle("-fx-font-size: 12px; -fx-text-fill: #555;");

        // Total
        HBox totalBox = new HBox(10);
        totalBox.setAlignment(Pos.CENTER_LEFT);
        totalBox.setPadding(new Insets(10, 0, 0, 0));

        Label lblTotal = new Label("TOTAL A PAGAR :");
        lblTotal.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #333;");

        Label lblMonto = new Label("$" + String.format("%.2f", costo));
        lblMonto.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #00BCD4;");

        totalBox.getChildren().addAll(lblTotal, lblMonto);

        VBox card = new VBox(8);
        card.setPadding(new Insets(18));
        card.setPrefWidth(230);
        card.setStyle(
                "-fx-background-color: white; -fx-background-radius: 10; "
                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 8, 0, 0, 2); "
                + "-fx-border-color: #e0e0e0; -fx-border-radius: 10;");
        card.getChildren().addAll(lblDisponible, docRow, lblFecha, lblHora, lblLugar, totalBox);

        return card;
    }

    /**
     * Crea la card con formulario de informacion de pago.
     */
    private VBox crearCardPago() {
        Label lblTitulo = new Label("Informacion del pago");
        lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #333;");

        // Campo titular
        Label lblTitular = new Label("Titular de la tarjeta");
        lblTitular.setStyle("-fx-font-size: 12px; -fx-text-fill: #555;");

        TextField txtTitular = new TextField();
        txtTitular.setPromptText("Nombre como aparece en la tarjeta");
        txtTitular.setStyle("-fx-font-size: 13px; -fx-padding: 10; -fx-border-color: #ddd; -fx-border-radius: 5;");

        // Campo numero tarjeta
        Label lblNumero = new Label("Numero de la tarjeta");
        lblNumero.setStyle("-fx-font-size: 12px; -fx-text-fill: #555;");

        TextField txtNumero = new TextField();
        txtNumero.setPromptText("0000 0000 0000 0000");
        txtNumero.setStyle("-fx-font-size: 13px; -fx-padding: 10; -fx-border-color: #ddd; -fx-border-radius: 5;");

        // Vencimiento y CVC
        Label lblVencimiento = new Label("Vencimiento");
        lblVencimiento.setStyle("-fx-font-size: 12px; -fx-text-fill: #555;");

        TextField txtVencimiento = new TextField();
        txtVencimiento.setPromptText("MM/AA");
        txtVencimiento.setStyle("-fx-font-size: 13px; -fx-padding: 10; -fx-border-color: #ddd; -fx-border-radius: 5;");
        txtVencimiento.setPrefWidth(130);

        Label lblCVC = new Label("CVC");
        lblCVC.setStyle("-fx-font-size: 12px; -fx-text-fill: #555;");

        TextField txtCVC = new TextField();
        txtCVC.setPromptText("123");
        txtCVC.setStyle("-fx-font-size: 13px; -fx-padding: 10; -fx-border-color: #ddd; -fx-border-radius: 5;");
        txtCVC.setPrefWidth(100);

        VBox vencCol = new VBox(3);
        vencCol.getChildren().addAll(lblVencimiento, txtVencimiento);

        VBox cvcCol = new VBox(3);
        cvcCol.getChildren().addAll(lblCVC, txtCVC);

        HBox vencCvcRow = new HBox(15);
        vencCvcRow.getChildren().addAll(vencCol, cvcCol);

        // Boton proceder
        Button btnProceder = new Button("Proceder al Pago   \u2192");
        btnProceder.setStyle(
                "-fx-background-color: #00BCD4; -fx-text-fill: white; "
                + "-fx-font-size: 14px; -fx-font-weight: bold; "
                + "-fx-padding: 12 35; -fx-cursor: hand; -fx-background-radius: 5;");
        btnProceder.setMaxWidth(Double.MAX_VALUE);
        btnProceder.setOnAction(e -> onConfirmar.run());

        VBox card = new VBox(10);
        card.setPadding(new Insets(20));
        card.setStyle(
                "-fx-background-color: white; -fx-background-radius: 10; "
                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 8, 0, 0, 2);");
        card.getChildren().addAll(lblTitulo, lblTitular, txtTitular, lblNumero, txtNumero,
                vencCvcRow, btnProceder);

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
