package assignment.product;

import samples.basics.state.Light;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.out;

/*
 * The application main model class
 *
 */
public class Memory {

    private final Random rand = new Random();
    private final List<Player> players;
    private final Board board;
    private Player actual = null;
    private List<Card> selectedList;

    public enum State {     // Game state
        // TODO

    }



    public Memory(Board board, List<Player> players) {
        this.board = board;
        this.players = players;
        actual = players.get(rand.nextInt(players.size()));
    }


    // TODO Methods here

    // 0. Generate actual:
    public void pickActual() {
        int p = rand.nextInt(100);
        if (p <= 50) {
            actual = players.get(1);
        }else{
            actual = players.get(0);
        }
        System.out.println(p);
    }

    // 1. matchedChecker
    // check if two cards match to each other
    public boolean matchedChecker(){
        boolean bool1 = selectedList.get(0) != selectedList.get(1);
        boolean bool2 = selectedList.get(0).hasSameNameTo(selectedList.get(1));
        return bool1 && bool2;
    }

    // 2. get actual player
    public Player getActual() {
        return actual;
    }

    // 3. add cards to the selected list
    public void addToSelectedList (List<Card> tempList) {
        selectedList = tempList;
    }

    // 4. get the selected list
    public List<Card> getSelectedList() {
        return this.selectedList;
    }

    // 5. getBoard
    public Board getBoard() {
        return this.board;
    }

    // 6. memoryClear
    public void wipe() {
        players.clear();
        this.board.wipe();
        selectedList.clear();
    }

    // 7. update actual
    public void updateActual() {
        if (actual.getName().compareTo(players.get(0).getName()) == 0) {
            actual = players.get(1);
        }else{
            actual = players.get(0);
        }
    }


    // -------- Getters for GUI ----------
    // TODO Need more???


}

