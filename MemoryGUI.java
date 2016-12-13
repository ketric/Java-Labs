package assignment.product;


import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;

import static java.lang.System.*;


/*
 * Memory Game
 *
 * This is the view and control parts. Starting JavaFX, creating the model (Memory)
 * and respond to user input.
 *
 * No game logic here
 */
public class MemoryGUI extends Application {


    private ParallelTransition transition;  // This is the animation effect for cards
    private Memory memory;     // The logic, created at newGame()
    private int size = 16;     // Default size
    private List<Card> tempList = new ArrayList<>();

    // ------ Handle click on cards (i.e. buttons) -----------------
    private void handleButtons(Event event) {

        // No action during the animation
        if (transition != null && transition.getStatus() == Animation.Status.RUNNING) {
            return;
        }


        Button button = (Button) event.getTarget();
        Card selectedCard = reverseLookup(button);
        showCard(selectedCard);
        tempList.add(selectedCard);
        System.out.println(tempList);
        memory.addToSelectedList(tempList);

        if (memory.getSelectedList().size() == 2) {
            if (memory.matchedChecker()) {
                showAndRemoveCards(tempList);
                tempList.clear();
                System.out.println(tempList + " Equal");
            }else{
                memory.updateActual();
                showAndHideCardsUpdatePlayers(tempList, memory.getActual());
                tempList.clear();
                System.out.println(tempList + " Not Equal");
            }

        }

/*
        if (tempList.size() == 2) {
            if (tempList.get(0).hasSameNameTo(tempList.get(1))) {
                memory.addToSelectedList(tempList);
                showAndRemoveCards(tempList);
                tempList.clear();
                System.out.println(tempList + "Equal");
            }else{
                memory.addToSelectedList(tempList);
                showAndHideCardsUpdatePlayers(tempList, memory.getActual());
                tempList.clear();
                System.out.println(tempList + "Not Equal");
            }

        }
        */
    }

    // ----- Menu handlers (not fully implemented)   ---------------------------

    private void newGame(Event e) {
        out.println("New game");

        // TODO Create and connect objects

        // Wipe
        //memory.wipe();

        //. pick an player to begin the game

        // 1. Create an Array of 16 cardNames
        String[] cardNames = FileService.getCardNames(16);

        // 2. List of Cards
        List<Card> cardList = new ArrayList<>();
        for (String s : cardNames) {
            cardList.add(new Card(s));
        }

        // 3. List of Players, pick a player to begin
        List<Player> players = new ArrayList<>();
        players.add(new Player("p1"));
        players.add(new Player("p2"));


        // 4. board
        Board  board= new Board(cardList);

        // 5, memory
        memory = new Memory(board, players);
        memory.pickActual();

        System.out.print(memory.getActual());

        // 6. MUST used methods
        initCardView(cardList);
        initPlayerView(players);

    }

    private void saveGame(Event e) {
        out.println("Save Game");
        // Not implemented
    }

    private void setSize(Event e) {
        out.println("Size ");
        String[] n = ((MenuItem) e.getTarget()).getText().split(" ");
        size = Integer.valueOf(n[0]);
        out.println(size);
    }

    private void exit(Event e) {
        /*Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Do you really want to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }*/
    }

    // --------------   Below methods possible to *USE* (nothing to do) --------------

    // To connect logical cards in model to buttons in view
    // I.e. given a card find the button representing it.
    private Map<Card, Button> cardButton = new HashMap<>();

    // Will show the image of the cart (i.e. flip the cart)
    private void showCard(Card card) {
        cardButton.get(card).getGraphic().setVisible(true);
    }

    // Flip two cards to show the images and then remove them
    private void showAndRemoveCards(List<Card> cards) {
        Button b1 = cardButton.get(cards.get(0));
        Button b2 = cardButton.get(cards.get(1));
        b1.getGraphic().setVisible(true);
        b2.getGraphic().setVisible(true);
        transition = getTransition(b1, b2);
        transition.setOnFinished(evt -> {
            b1.setDisable(true);
            b2.setDisable(true);
        });
        transition.play();
    }

    // Flip two cards to show the images and then flip them back.
    private void showAndHideCardsUpdatePlayers(List<Card> cards, Player actual) {
        Button b1 = cardButton.get(cards.get(0));
        Button b2 = cardButton.get(cards.get(1));
        b1.getGraphic().setVisible(true);
        b2.getGraphic().setVisible(true);
        Node i1 = b1.getGraphic();
        Node i2 = b2.getGraphic();
        transition = getTransition(i1, i2);
        transition.setOnFinished(evt -> {
            i1.setVisible(false);
            i2.setVisible(false);
            i1.setOpacity(1);
            i2.setOpacity(1);
            updatePlayerView(actual);
        });
        transition.play();
    }


    // When removing last matching pair
    void showFinalResult(Player player) {
        /*Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Winner is");
        alert.setContentText(player.getName());
        alert.show();*/
    }

    // Given a button find which card it represents
    // (the inverse of cardButton.get(card) -> button)
    private Card reverseLookup(Button b) {
        for (Card c : cardButton.keySet()) {
            if (cardButton.get(c) == b) {
                return c;
            }
        }
        return null;
    }

    private TilePane boardView;   // Layout for the cards (buttons). Created in start()
    private HBox playersView;     // Layout for the player names (buttons). Created in start()

    // Create the view of players, i.e. buttons with the names on it
    private void initPlayerView(List<Player> players){
        playersView.getChildren().clear();
        for (Player p : players) {
            Button b = new Button(p.getName());
            b.setId(p.getName());
            if (p.equals(memory.getActual())) {
                b.setStyle(activeButton);
            } else {
                b.setStyle(inActiveButton);
            }
            b.setPrefSize(100, 20);
            playersView.getChildren().add(b);
        }
    }

    // Will create the view of the board, i.e. buttons with an (possibly hidden) image
    private void initCardView(List<Card> cards){
        boardView.getChildren().clear();
        List<Button> buttons = new ArrayList<>();
        for (Card c : cards) {
            Button b = new Button();
            b.setMinSize(100, 100);
            b.setOnAction(this::handleButtons);   // When click button call handleButton method
            Image img = FileService.getImage(c.getName(), 80, 80);
            ImageView imgView = new ImageView(img);
            imgView.setVisible(false);              //Just during development, (false);
            b.setGraphic(imgView);
            cardButton.put(c, b);     // Save so that we can lookup button later
            buttons.add(b);
        }
        boardView.getChildren().addAll(buttons);
    }


    // ******** Nothing to do or use below it's JavaFX (GUI stuff and more) *********

    private static final int width = 500;   // Window size
    private static final int height = 500;

    // CSS styles for buttons
    private static final String activeButton = "-fx-background-color: #3eba22; -fx-text-fill: #ffffff; ";
    private static final String inActiveButton = "-fx-background-color: #ba2222; -fx-text-fill: #acb2aa;";
    private int transitionTime = 1500; // ms for animations


    // Will set active player button to green and highlight, others set to red
    private void updatePlayerView(Player actual) {
        for (Node n : playersView.getChildren()) {
            if (n.getId().equals(actual.getName())) {
                n.setStyle(activeButton);
            } else {
                n.setStyle(inActiveButton);
            }
        }
    }

    private void playerInputDialog(Event e) {
        // Not implemented
    }

    // Animate hiding and or removing of cards
    private ParallelTransition getTransition(Node n1, Node n2) {
        FadeTransition ft1 = new FadeTransition(Duration.millis(transitionTime), n1);
        ft1.setFromValue(1.0);
        ft1.setToValue(0);
        FadeTransition ft2 = new FadeTransition(Duration.millis(transitionTime), n2);
        ft2.setFromValue(1.0);
        ft2.setToValue(0);
        return new ParallelTransition(ft1, ft2);
    }

    // Create the menu
    private MenuBar getMenu() {

        // --- Menu File
        Menu menuFile = new Menu("File");
        MenuItem[] menuFileItems = {
                new MenuItemExt("New Game", this::newGame, KeyCode.N, KeyCombination.CONTROL_DOWN),
                new MenuItemExt("Save Game", this::saveGame),
                //new MenuItemExt("Quit Game", this::saveGame),
                new MenuItemExt("Exit", this::exit)
        };
        menuFile.getItems().addAll(menuFileItems);

        // --- Menu Options
        Menu menuOptions = new Menu("Options");
        Menu menuOptionsSize = new Menu("Size");   // Sub menu
        MenuItem menuOptionSizeItems[] = {
                new MenuItemExt("4 x 4", this::setSize),
                new MenuItemExt("5 x 5", this::setSize)
        };
        menuOptionsSize.getItems().addAll(menuOptionSizeItems);

        MenuItem[] menuOptionsItems = {
                new MenuItemExt("Players", this::playerInputDialog),
                menuOptionsSize
        };
        menuOptions.getItems().addAll(menuOptionsItems);

        // --- Menu About
        Menu menuAbout = new Menu("About");
        MenuItem[] menuAboutItems = {new MenuItemExt("About", e -> {
        })};
        menuAbout.getItems().addAll(menuAboutItems);

        // -- The bar for all menus
        MenuBar menuBar = new MenuBar();
        menuBar.setId("menuBar");
        menuBar.getMenus().addAll(menuFile, menuOptions, menuAbout);
        return menuBar;
    }

    //  Helper class for menu items with more useful constructors
    private static class MenuItemExt extends MenuItem {
        public MenuItemExt(String label, EventHandler<ActionEvent> e) {
            super(label);
            setOnAction(e);
        }

        public MenuItemExt(String label, EventHandler<ActionEvent> e, KeyCode keyCode,
                           KeyCombination.Modifier modifier) {
            this(label, e);
            setAccelerator(new KeyCodeCombination(keyCode, modifier));
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        // All the cards will be here
        boardView = new TilePane();
        boardView.setPadding(new Insets(10, 10, 10, 10));

        // Show names and ....
        playersView = new HBox();
        playersView.setPadding(new Insets(10, 10, 10, 10));
        playersView.setSpacing(10);

        MenuBar menuBar = getMenu();

        // Connect
        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(boardView);
        root.setBottom(playersView);

        // Add GUI to Scene
        Scene scene = new Scene(root, width, height);
        scene.setFill(Color.OLDLACE);

        // Put Scene on stage and show it
        primaryStage.setTitle("Memory");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
