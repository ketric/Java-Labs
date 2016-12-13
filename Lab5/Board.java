package assignment.product;

/** Libraries **/
import com.sun.xml.internal.bind.v2.model.core.ID;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Collection;

//-----------------------------------------------------------
public class Board {

    /** State **/
    private final List<Card> cards;

    /** Constructor **/
    public Board(List<Card> cards) {
        this.cards = cards;
    }

    /** Behaviors **/
    // TODO methods here
    // getCardList
    public List<Card> getCardList() {
        return this.cards;
    }


    public void wipe() {
        cards.clear();
    }
    // no more methods

}
