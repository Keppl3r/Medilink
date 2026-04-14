/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.presentacion.boundaries;

import com.mycompany.persistencia.entidades.Especialista;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
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
 * Pantalla de seleccion de especialista con tarjetas estilo Medilink.
 * Muestra especialistas en formato de cards con filtro por especialidad.
 *
 * @author devor
 */
public class BC_SeleccionEspecialista {

    private final List<Especialista> especialistas;
    private final Consumer<Especialista> onSeleccionar;
    private final Runnable onVolver;
    private final Runnable onCerrarSesion;

    /**
     * Constructor con datos y callbacks de navegacion.
     *
     * @param especialistas lista de especialistas disponibles
     * @param onSeleccionar accion al seleccionar un especialista
     * @param onVolver accion al regresar
     * @param onCerrarSesion accion al cerrar sesion
     */
    public BC_SeleccionEspecialista(List<Especialista> especialistas,
                                    Consumer<Especialista> onSeleccionar,
                                    Runnable onVolver,
                                    Runnable onCerrarSesion) {
        this.especialistas = especialistas;
        this.onSeleccionar = onSeleccionar;
        this.onVolver = onVolver;
        this.onCerrarSesion = onCerrarSesion;
    }

    private FlowPane gridEspecialistas;
    private final List<Button> botonesFiltro = new ArrayList<>();

    private static final String ESTILO_FILTRO_ACTIVO =
            "-fx-background-color: #00BCD4; -fx-text-fill: white; "
            + "-fx-font-size: 12px; -fx-padding: 6 15; "
            + "-fx-background-radius: 15; -fx-cursor: hand;";

    private static final String ESTILO_FILTRO_INACTIVO =
            "-fx-background-color: white; -fx-text-fill: #555; "
            + "-fx-font-size: 12px; -fx-padding: 6 15; "
            + "-fx-background-radius: 15; -fx-cursor: hand; "
            + "-fx-border-color: #ddd; -fx-border-radius: 15;";

    /**
     * Crea y retorna la escena de seleccion de especialista.
     *
     * @return escena con tarjetas de especialistas
     */
    public Scene crearEscena() {
        HBox header = crearHeader();

        Button btnVolver = new Button("Atras  <");
        btnVolver.setStyle(
                "-fx-background-color: white; -fx-text-fill: #333; "
                + "-fx-font-size: 12px; -fx-cursor: hand; "
                + "-fx-border-color: #ddd; -fx-border-radius: 3; -fx-padding: 5 12;");
        btnVolver.setOnAction(e -> onVolver.run());

        Label titulo = new Label("Selecciona un especialista");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        Label subtitulo = new Label("Encuentra el profesional adecuado para tu atencion medica");
        subtitulo.setStyle("-fx-font-size: 13px; -fx-text-fill: #777;");

        HBox filtros = crearBarraFiltros();

        VBox tituloSection = new VBox(5);
        tituloSection.setPadding(new Insets(15, 30, 5, 30));
        tituloSection.getChildren().addAll(btnVolver, titulo, subtitulo, filtros);

        gridEspecialistas = new FlowPane(20, 20);
        gridEspecialistas.setPadding(new Insets(10, 30, 30, 30));
        gridEspecialistas.setAlignment(Pos.CENTER);

        aplicarFiltro("Todos");

        ScrollPane scroll = new ScrollPane(gridEspecialistas);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: transparent; -fx-background: #F5F5F5;");

        VBox layout = new VBox();
        layout.setStyle("-fx-background-color: #F5F5F5;");
        layout.getChildren().addAll(header, tituloSection, scroll);
        VBox.setVgrow(scroll, Priority.ALWAYS);

        return new Scene(layout, 700, 550);
    }

    /**
     * Filtra las tarjetas de especialistas segun la especialidad seleccionada.
     */
    private void aplicarFiltro(String especialidad) {
        gridEspecialistas.getChildren().clear();
        for (Especialista esp : especialistas) {
            if ("Todos".equals(especialidad) || esp.getEspecialidad().equals(especialidad)) {
                gridEspecialistas.getChildren().add(crearTarjetaEspecialista(esp));
            }
        }
    }

    /**
     * Actualiza el estilo de los botones de filtro para resaltar el activo.
     */
    private void activarBotonFiltro(Button botonActivo) {
        for (Button btn : botonesFiltro) {
            btn.setStyle(ESTILO_FILTRO_INACTIVO);
        }
        botonActivo.setStyle(ESTILO_FILTRO_ACTIVO);
    }

    private HBox crearBarraFiltros() {
        HBox filtros = new HBox(8);
        filtros.setPadding(new Insets(10, 0, 5, 0));
        filtros.setAlignment(Pos.CENTER_LEFT);

        Button btnTodos = new Button("Todos");
        btnTodos.setStyle(ESTILO_FILTRO_ACTIVO);
        btnTodos.setOnAction(e -> {
            activarBotonFiltro(btnTodos);
            aplicarFiltro("Todos");
        });
        botonesFiltro.add(btnTodos);
        filtros.getChildren().add(btnTodos);

        List<String> especialidades = especialistas.stream()
                .map(Especialista::getEspecialidad)
                .distinct()
                .collect(Collectors.toList());

        for (String esp : especialidades) {
            Button btn = new Button(esp);
            btn.setStyle(ESTILO_FILTRO_INACTIVO);
            btn.setOnAction(e -> {
                activarBotonFiltro(btn);
                aplicarFiltro(esp);
            });
            botonesFiltro.add(btn);
            filtros.getChildren().add(btn);
        }

        return filtros;
    }

    private VBox crearTarjetaEspecialista(Especialista especialista) {
        Circle avatar = new Circle(30);
        avatar.setFill(Color.web("#78909C"));

        Label iniciales = new Label(
                String.valueOf(especialista.getNombre().charAt(0))
                + String.valueOf(especialista.getApellido().charAt(0)));
        iniciales.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");

        StackPane avatarPane = new StackPane();
        avatarPane.getChildren().addAll(avatar, iniciales);

        Label nombre = new Label("Dr. " + especialista.getNombre() + " " + especialista.getApellido());
        nombre.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #333;");

        Label especialidad = new Label(especialista.getEspecialidad());
        especialidad.setStyle("-fx-font-size: 11px; -fx-text-fill: #999;");

        Button btnAgendar = new Button("Agendar Cita");
        btnAgendar.setStyle(
                "-fx-background-color: #00BCD4; -fx-text-fill: white; "
                + "-fx-font-size: 12px; -fx-padding: 6 20; -fx-cursor: hand; "
                + "-fx-background-radius: 3;");
        btnAgendar.setOnAction(e -> onSeleccionar.accept(especialista));

        VBox card = new VBox(8);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(20, 15, 20, 15));
        card.setPrefWidth(150);
        card.setStyle(
                "-fx-background-color: white; -fx-background-radius: 10; "
                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 8, 0, 0, 2);");
        card.getChildren().addAll(avatarPane, nombre, especialidad, btnAgendar);

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
