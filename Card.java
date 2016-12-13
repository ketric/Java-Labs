package assignment.product;

/**
 *    A Card for a Memory Game
 */

/** Libraries **/
import javafx.scene.image.Image;


public class Card {
    /** State **/
    private String cardName;

    /** Constructor **/
    public Card(String name){
        this.cardName = name;
    }

    /** Behaviors **/
    public String getName() {
        return this.cardName;
    }

    public boolean hasSameNameTo(Card cardX) {
        return this.cardName.compareTo(cardX.getName()) == 0;
    }

    // Done
}
