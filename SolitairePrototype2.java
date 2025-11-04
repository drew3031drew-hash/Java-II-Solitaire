package com.example.solitaireprototype2;//*********************************************
// Solitaire Prototype 1 -- GUI Components
// Version 2 -- Changes to exit button, event handlers, & menu bar made by Jenascia
//
// Author: Indy Hinton
// Course: CPT-237-W38 Java Programming II
// Semester: Fall 2025
// Date: 10/13/2025-10/21/2025
//
// Description:
// This sets up the shell of the Solitaire GUI:
// - BorderPane layout
// - Menu/Toolbar at the top
// - Status bar at the bottom
// - Swappable board in the center
//*********************************************

// JavaFX core classes
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.Scanner;

// GUI-only
// Class for the user interface layout (no game logic)
public class SolitairePrototype2 extends Application {

    // UI labels for the status bar (Score, Moves, Timer)
    private Label lblScore = new Label("Score: 0");
    private Label lblMoves = new Label("Moves: 0");
    private Label lblTime  = new Label("Time: 00:00");

    // Drop-down to select which Solitaire game to play
    private ComboBox<String> cboVariant = new ComboBox<>();

    // The center area that will display the chosen game
    private Pane board = new Pane();

    @Override
    public void start(Stage stage) {
        // Top section (menu + toolbar)
        MenuBar menuBar = buildMenuBar();
        ToolBar toolBar = buildToolBar();
        VBox top = new VBox(menuBar, toolBar);

        // Bottom section (status bar)
        HBox status = buildStatusBar();

        // Main layout
        BorderPane root = new BorderPane();
        root.setTop(top);
        root.setCenter(board); // insert pane
        root.setBottom(status);

        // Scene setup
        Scene scene = new Scene(root, 1000, 650);
        stage.setTitle("Solitaire – GUI Prototype");
        stage.setScene(scene);
        stage.show();

        // Initialize game choice
        cboVariant.getItems().addAll("Klondike", "Spider", "Pyramid");
        cboVariant.getSelectionModel().selectFirst();

        // When a game is selected, rebuild the board (visual only)
        cboVariant.valueProperty().addListener((obs, oldV, newV) -> rebuildBoard(newV, root));

        // Show the first board
        rebuildBoard(cboVariant.getValue(), root);
    }

    // Create the top menu bar
    private MenuBar buildMenuBar() {
        Menu mGame = new Menu("Game");
        MenuItem miNew   = new MenuItem("New Game");
        MenuItem miReset = new MenuItem("Reset");
        MenuItem miExit  = new MenuItem("Exit");

        // Event handler to be added for 'New Game' here
        // Event handler to be added Code for 'Reset' here

        // Menu Bar Exit Confirmation Dialog Box
        Alert alertExitMB = new Alert(Alert.AlertType.CONFIRMATION);
        alertExitMB.setTitle("Exit Confirmation");
        alertExitMB.setHeaderText("Exit Solitaire?");
        alertExitMB.setContentText("Are you sure you want to close Solitaire?");

        // File Not Found Dialog Box
        Alert alertFNF = new Alert(Alert.AlertType.ERROR);
        alertFNF.setTitle("FileNotFoundException");
        alertFNF.setHeaderText("File Not Found");
        alertFNF.setContentText("The Rules file could not be located");
        alertFNF.hide();

        // Event handler for menu bar 'Exit'
        miExit.setOnAction(e -> {
            Optional<ButtonType> result = alertExitMB.showAndWait();
            if (result.get() == ButtonType.OK) {
                System.exit(0);
            } else {

            }
        });

        mGame.getItems().addAll(miNew, miReset, new SeparatorMenuItem(), miExit);

        Menu mView = new Menu("View");
        MenuItem hintsItem = new MenuItem("Hints");
        mView.getItems().addAll(hintsItem);
        // mView.getItems().add(new CheckMenuItem("Show Hints"));

        Menu mHelp = new Menu("Help");
        MenuItem rulesItem = new MenuItem("Rules");
        mHelp.getItems().addAll(rulesItem);
        //mHelp.getItems().add(new MenuItem("Rules"));

        return new MenuBar(mGame, mView, mHelp);

        // 'Help - Rules' Event Handler
        rulesItem.setOnAction(e -> {
            // Imported Text for Rules
            File rulesFile = new File("Rules");

            try (Scanner ruleFileReader = new Scanner(rulesFile)) {
                while (ruleFileReader.hasNextLine()) {
                    String rules = ruleFileReader.nextLine();
                    System.out.println(rules);
                }
            } catch (FileNotFoundException s) {
                alertFNF.showAndWait();
            }
        });

        // 'View - Hints' Event Handler
        hintsItem.setOnAction(e -> {
            // Imported Text for Hints
            File rulesFile = new File("Hints");

            try (Scanner hintFileReader = new Scanner(rulesFile)) {
                while (hintFileReader.hasNextLine()) {
                    String hints = hintFileReader.nextLine();
                    System.out.println(hints);
                }
            } catch (FileNotFoundException e) {
                alertFNF.showAndWait();
            }
        });
    }



    // Create the toolbar with buttons and game selector
    private ToolBar buildToolBar() {
        Button btnNew  = new Button("New");
        Button btnDeal = new Button("Deal/Draw");
        Button btnUndo = new Button("Undo");
        Button btnExit = new Button("Exit");

        //**** EVENT LISTENERS ****//

        // Event Listener for New button
        btnNew.setOnMouseReleased(e ->
        {
            // CODE FOR NEW BUTTON HERE
        });

        // Event Listener for Deal button
        btnDeal.setOnMouseReleased(e ->
        {
            // CODE FOR DEAL BUTTON HERE
        });

        // Event Listener for Undo button
        btnUndo.setOnMouseReleased(e ->
        {
            // CODE FOR UNDO BUTTON HERE
        });

        // Exit Confirmation Dialog Box
        Alert alertExitB = new Alert(Alert.AlertType.CONFIRMATION);
        alertExitB.setTitle("Exit Confirmation");
        alertExitB.setHeaderText("Exit Solitaire?");
        alertExitB.setContentText("Are you sure you want to close Solitaire?");

        // Event Listener for Exit button
        btnExit.setOnMouseReleased(e ->
        {
            Optional<ButtonType> result = alertExitB.showAndWait();
            if (result.get() == ButtonType.OK) {
                System.exit(0);
            } else {

            }
        });

        cboVariant.setPrefWidth(160);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        return new ToolBar(
                new Label("Variant:"), cboVariant,
                new Separator(),
                btnNew, btnDeal, btnUndo,
                spacer,
                btnExit
        );
    }

    // Create the bottom status bar
    private HBox buildStatusBar() {
        lblScore.setFont(Font.font(13));
        lblMoves.setFont(Font.font(13));
        lblTime.setFont(Font.font(13));

        HBox status = new HBox(20, lblScore, lblMoves, lblTime);
        status.setPadding(new Insets(8));
        status.setAlignment(Pos.CENTER_LEFT);
        status.setStyle("""
            -fx-background-color: linear-gradient(to right, #f4f4f4, #eaeaea);
            -fx-border-color: #d0d0d0;
            -fx-border-width: 1 0 0 0;
        """);
        return status;
    }

    // Game is selected and board is rebuilt
    private void rebuildBoard(String variant, BorderPane root) {
        Pane newBoard = switch (variant) {
            case "Spider"  -> SpiderBoardFactory.build();
            case "Pyramid" -> PyramidBoardFactory.build();
            default        -> KlondikeBoardFactory.build();
        };
        root.setCenter(newBoard);
        board = newBoard;
    }

    // Launch the app
    public static void main(String[] args) {
        launch();
    }
}

// Game variant classes for visual board layouts (logic added later)

// Klondike Board
class KlondikeBoardFactory {
    public static Pane build() {
        GridPane gp = Basics.grid(7);
        for (int col = 0; col < 7; col++)
            gp.add(Basics.pile("T" + (col + 1), 5 + col), col, 0);
        return Basics.wrap(gp);
    }
}

// Spider Board
class SpiderBoardFactory {
    public static Pane build() {
        GridPane gp = Basics.grid(10);
        for (int col = 0; col < 10; col++)
            gp.add(Basics.pile("S" + (col + 1), 6), col, 0);
        return Basics.wrap(gp);
    }
}

// Pyramid Board
class PyramidBoardFactory {
    public static Pane build() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(16));
        for (int r = 1; r <= 7; r++) {
            HBox row = new HBox(8);
            row.setAlignment(Pos.CENTER);
            for (int i = 0; i < r; i++)
                row.getChildren().add(Basics.card("■"));
            root.getChildren().add(row);
        }
        return root;
    }
}

// Methods to create the grid, card piles, and cards

// Grid
class Basics {
    static GridPane grid(int cols) {
        GridPane gp = new GridPane();
        gp.setHgap(8);
        gp.setVgap(8);
        gp.setPadding(new Insets(16));

        ColumnConstraints cc = new ColumnConstraints();
        cc.setPercentWidth(100.0 / cols);
        for (int i = 0; i < cols; i++)
            gp.getColumnConstraints().add(new ColumnConstraints(cc.getPercentWidth()));
        return gp;
    }

    // Card Pile
    static VBox pile(String label, int height) {
        VBox pile = new VBox(6);
        pile.setAlignment(Pos.TOP_CENTER);

        Label title = new Label(label);
        title.setStyle("-fx-text-fill: #444; -fx-font-size: 11px;");
        pile.getChildren().add(title);

        for (int i = 0; i < height; i++) {
            StackPane card = card(" ");
            card.setTranslateY(i * -18);
            pile.getChildren().add(card);
        }

        pile.setStyle("-fx-background-color: #f7f7f7; -fx-border-color: #ddd;");
        pile.setPadding(new Insets(8));
        return pile;
    }

    // Cards (current basic card visuals, can alter later when group decides)
    static StackPane card(String text) {
        StackPane c = new StackPane();
        c.setMinSize(64, 86);
        c.setMaxSize(64, 86);
        c.setStyle("""
            -fx-background-color: white;
            -fx-border-color: #bbb;
            -fx-background-radius: 6;
            -fx-border-radius: 6;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 6, 0, 0, 2);
        """);
        c.getChildren().add(new Label(text));
        return c;
    }

    // Padding
    static Pane wrap(Pane p) {
        StackPane w = new StackPane(p);
        w.setPadding(new Insets(8));
        return w;
    }
}
