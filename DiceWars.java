package assignment.product;

import java.util.Random;
import java.util.Scanner;

import static java.lang.Math.sqrt;
import static java.lang.System.in;
import static java.lang.System.out;

/*
 * Simplified text based (i.e. non graphical) version of the Dice Wars game
 * For a full graphical version, see http://www.gamedesign.jp/flash/dice/dice.html (or other)
 *
 * Some rule simplification
 * - No limit of dices in a country
 * - Distribution of earned dices may be "somewhat" random.
 * - Game over when a player lost all countries
 * - Player with most countries wins (or dices? or a combination? find a measure)
 *
 * Structure:
 * - As usual,separate IO and logic
 *
 * Process:
 * - The plotMap method (given) needs method hasBorders. Implement and test it.
 * - Use inside-out and functional decomposition to let attacker do one attack
 * - Add a command line (while+switch) and put the above under the attack selection.
 * - Implement the "next"-selection. More functional decomposition.
 * - Add loop for the command line and implement the quit-choice.
 */
public class DiceWars {

    public static void main(String[] args) {
        new DiceWars().program();
    }

    // The only allowed variables outside the methods
    static Scanner sc = new Scanner(in);
    static Random rand = new Random();

    void program() {
        // ------ Booleans------------------------------------
        boolean endTurn = false;
        boolean done = false;
        boolean q1 = false;
        boolean q2 = false;
        boolean q3 = false;
        boolean loopDone = false;

        // The players of the game (mostly referenced by index)
        String[] players = {"pelle", "fia", "lisa"};
        // A map with nine countries, named by their index (0-8)
        // Leading 1:s because can't have leading 0 (just skip the ones when processing)
        // 114 says: The country 0 has border to countries 1 and 4.
        // Number of countries is a multiple of players, they all get the same numbers of
        // countries
        int[] map = {114, 1024, 115, 146, 101357, 12487, 137, 14568, 157};
        // This is the owners of the countries. Country 0 is owned by player
        // pelle (players[0])
        int[] owners = {0, 1, 2, 1, 2, 0, 0, 1, 2};
        // This is the number of dices for a country. Country 1 has 3 dices.
        int[] dices = {2, 3, 2, 3, 1, 3, 1, 1, 3};

        /* Test area, should be possible to test at least some methods here */
        /*
        out.println(hasBorders(.. fill in data that results in true...));w
         */

        out.println("Welcome to Dice Wars \"lite!\"");
        out.println();

        int attacker = rand.nextInt(players.length);
        plotMap(map, owners, dices, players);


        // ******* Write code here *************
        while (!done) {
            System.out.println("the current attacker is: " + players[attacker]);
            while (!q1) {
                System.out.print("action((a)ttack/ (e)nd turn): ");
                // player choose to attack or to end his turn
                String ans1 = sc.nextLine();
                //sc.nextLine();
                System.out.println(" ");
                if (ans1.equals("a")) {
                    while (!q2) {
                        System.out.print("Attack from: ");
                        // player choose a land that he playerOwns()
                        // player will start to invade his neighbors from here.
                        int ans2 = sc.nextInt();
                        sc.nextLine();
                        System.out.println(" ");
                        if (playerOwns(attacker, ans2, owners)) {
                            while (!q3) {
                                System.out.print("Attack to: ");
                                // Player choose a neighbor to invade
                                // an attackable neighbor is the one player has border with
                                int ans3 = sc.nextInt();
                                //sc.nextLine();
                                if (hasBorder(ans2, ans3, map)) {

                                    // main game play:

                                    //1. player and the chosen neighbor castTheDice(). The one with more score wins
                                    // Method is done
                                    int[] result = castTheDice(ans2, ans3, dices);

                                    //2. diceCastWinner/ Looser:
                                    int  dicesWinner = result[0];
                                    int dicesLooser = result[1];

                                    //3. the winner takeOverLands()
                                    // Method is done
                                    takeOverLands(dicesWinner, dicesLooser, owners);

                                    //3.1 the winner moveDicePoints()
                                    moveDicePoints(dicesWinner, dicesLooser, dices);

                                    //4. show the new map and break back to loopQ1
                                    // Method was done by the teacher
                                    plotMap(map, owners, dices, players);

                                    break;
                                }else{ q3 = false;}
                            }
                            break;
                        }else{ q2 = false;}
                    }
                }else if (ans1.equals("e")){
                    endTurn = true;
                }
                else{q1 = false;}

                if (endTurn) {
                    //dices are randomly spreadOut() over the winner's lands
                    contributeDices(attacker, owners, dices);
                    plotMap(map, owners, dices, players);
                    attacker = (attacker + 1) % 3;
                    break;
                }else if (weHaveGotAWinner(owners)) {
                    loopDone = true;
                    break;
                } else {break;}
            }
            if (loopDone) {break;}
        }

        int indexWinner = owners[1];

        out.println("Game over");
        out.println("Winner is " + players[indexWinner]);
    }

    // ------ Write methods below this -------------------
    //1. Done
    public static boolean playerOwns(int attacker, int ans1, int[] onwers){
        // write a method that defies if a land is owned by player
        if (attacker == onwers[ans1]) {
            return true;
        }else{
            return false;
        }
    }

    //2. Done
    public static boolean hasBorder(int from, int to, int[] map) {
        // Yes, if country from has border to country to
        int borders = map[from];
        while (borders > 1) {
            if (borders % 10 == to) { return true;
            }else{
                borders = borders / 10;
            }
        }
        return false;
    }

    //3. Done
    public static int[] castTheDice(int from, int to, int[] dices) {
        int attackerPoints;
        int neighborPoints;
        int[] result = {0,0};

        attackerPoints = (rand.nextInt(6) + 1) * (dices[from]);
        neighborPoints = (rand.nextInt(6) + 1) * (dices[to]);
        int winner = 0;
        int looser = 0;

        if (attackerPoints == neighborPoints || attackerPoints < neighborPoints) {
            winner = to;
            looser = from;
        }else if(attackerPoints > neighborPoints) {
            winner = from;
            looser = to;
        }
        result[0] = winner;
        result[1] = looser;
        return result;
    }

    //4. Done
    public static int[] takeOverLands(int diceCastWinner, int diceCastLooser, int[] owners) {
        // write a method that overwrites new owners onto old owners
        owners[diceCastLooser] = owners[diceCastWinner];
        int[] newMap = owners;
        return newMap;
    }

    //4.1 Done
    public static int[] moveDicePoints(int diceCastWinner, int diceCastLooser,int[] dices) {
        dices[diceCastLooser] = dices[diceCastWinner] - 1;
        dices[diceCastWinner] = 1;
        return dices;
    }

    //5. Done?
    public static int[] contributeDices(int winner, int[] owners, int[] dices) {
        // Write a method that spread out the dices over player's lands
        int i;
        for (i = 0; i < 9; i ++) {
            if (owners[i] == winner) {dices[i] = rand.nextInt(12) + dices[winner];}
            else {dices[i] = dices[i] + 0;}
        }
        return dices;

    }


    //7. Done
    public static boolean weHaveGotAWinner(int[] owners) {
        // when all the elements in owners[] have a same value, the current attacker is the winner
        //boolean something = true;
        for (int i = 0; i < owners.length - 1; i++) {
            if (owners[i] != owners[i + 1]) {
                //something = false;
                return false;
            }
        }
        return true;
    }

    // ----  Nothing to do below -----------------------

    // Plot map (as a graph) using ASCII chars
    void plotMap(int[] map, int[] owners, int[] dices, String[] players) {
        int n = (int) sqrt(map.length);
        for (int row = 0; row < 2 * n - 1; row += 2) {
            // One row with horizontal connections
            for (int col = 0; col < n; col++) {
                int i = 3 * row / 2 + col;
                out.print(players[owners[i]] + ":" + dices[i]);
                if (hasBorder(i, i + 1, map)) {
                    out.print("--");
                } else {
                    out.print("  ");
                }
            }
            out.println();
            // Another row with vertical connections
            for (int col = 0; col < n; col++) {
                int i = 3 * row / 2 + col;
                if (hasBorder(i, i + n, map)
                        && hasBorder(i, i + n - 1, map)
                        && hasBorder(i, i + n + 1, map)) {
                    out.print("  / | \\ ");
                } else if (hasBorder(i, i + n, map)
                        && hasBorder(i, i + n - 1, map)) {
                    out.print("/  |     ");
                } else if (hasBorder(i, i + n, map)
                        && hasBorder(i, i + n + 1, map)) {
                    out.print("   | \\  ");
                } else if (hasBorder(i, i + n - 1, map)) {
                    out.print(" /      ");
                } else if (hasBorder(i, i + n + 1, map)) {
                    out.print("     \\  ");
                } else if (hasBorder(i, i + n, map)) {
                    out.print("   |    ");
                } else {
                    out.print("         ");
                }
            }
            out.println();
        }
        out.println("-----------------------------------------");
    }
}

