package assignment.product;

//------------------------------------------------------------
/** LIBRARIES **/
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Random;

import static java.lang.Math.round;
import static java.lang.Math.sqrt;
import static java.lang.System.*;

//------------------------------------------------------------

public class Simulation extends Application {
    /** JAVA FX **/
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Run tests if needed
        if (doTest) {
            test();
            exit(0);
        }

        // Create the world
        world = initWorld();

        // JavaFX stuff, don't bother
        Group root = new Group();
        Canvas canvas = new Canvas(width, height);
        root.getChildren().addAll(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Create a timer
        AnimationTimer timer = new AnimationTimer() {
            // This method called by FX supplying the current time
            public void handle(long currentNanoTime) {
                long elapsedNanos = currentNanoTime - previousTime;
                if (elapsedNanos > 25) {
                    updateWorld();
                    drawWorld(gc);
                    previousTime = currentNanoTime;
                }
            }
        };
        // Create a scene and connect to the stage
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation");
        primaryStage.show();

        timer.start();  // Start simulation
    }

    /** Paint the state of the world to the screen **/
    public void drawWorld(GraphicsContext g) {
        int size = world.length;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                int x = 10 * col + 50;
                int y = 10 * row + 50;

                if (world[row][col] == Actor.RED) {
                    g.setFill(Color.RED);
                } else if (world[row][col] == Actor.BLUE) {
                    g.setFill(Color.BLUE);
                } else {
                    g.setFill(Color.WHITE);
                }
                g.fillOval(x, y, 10, 10);

            }
        }
    }

    /** Test **/
    void test() {
        double th = 0.1;   // Simple threshold for testing
        // TODO add tests as needed
    }

//------------------------------------------------------------

    /** Final variables **/
    final boolean   doTest       = false;
    final int       width        = 400;   // Size for window
    final int       height       = 400;


    /** Variables for intializing: **/
    double    threshold = 0.75;
    Actor[][] world;
    State[][] state;
    boolean   toggle    = true;
    long            previousTime = nanoTime();
    static Random   rand         = new Random();
    static int      indexRow;
    static int      indexColumn;

//------------------------------------------------------------

    /** Enumerations: **/
    enum Actor {BLUE, NONE, RED}
    enum State {UNSATISFIED, SATISFIED, NA} // Not applicable (NA), used for NONEs

//------------------------------------------------------------
    /** Methods: **/
        // A. Given Methods
    void updateWorld() {
        if (toggle) {
            state = getUnsatisfied(world, threshold);
        } else {
            world = moveUnsatisfied(state, world);
        }
        toggle = !toggle;
    }

        // B. Methods to define:
    // 1.   DONE
    // Generate a matrix of random Actors (blue
    Actor[][] initWorld() {
        double[] distribtuion = {0.25, 0.25, 0.5};
        int nElements         = 900; // Number of elements
        int side              = (int)Math.sqrt(nElements);
        Actor[][] world = new Actor[side][side];
        for (indexRow = 0; indexRow < side; indexRow++) {
            for (indexColumn = 0; indexColumn < side; indexColumn++) {
                world[indexRow][indexColumn] = Actor.NONE;
                int value = rand.nextInt(100);
                if (value <= distribtuion[0]*100) {
                    world[indexRow][indexColumn] = Actor.RED;
                }else if (value <= (distribtuion[0] + distribtuion[1])*100) {
                    world[indexRow][indexColumn] = Actor.BLUE;
                }
            }
        }
        return world;
    }

    // 2.   DONE
    // Make a Matrix of Status (Satisfied, Unsatisfied, NA) (State = Status)
    State[][] getUnsatisfied(Actor[][] world, double threshold) {
        int side = world.length;
        State[][] state = new State[side][side];
        for (indexRow = 0; indexRow < world.length; indexRow++) {
            for (indexColumn = 0; indexColumn < world.length; indexColumn ++) {
                state[indexRow][indexColumn] = getState(indexRow, indexColumn, world, threshold);
            }
        }
        return state;
    }

    // 2.1. DONE
    // Get status from all actor in world by looking at the colours around them
    State getState(int x, int y, Actor[][] world, double threshold) {
        int sameColour    = -1;
        int totalNeighbor = -1;
        for (int indexRow = -1; indexRow < 2; indexRow ++) {
            for (int indexColumn = -1; indexColumn < 2; indexColumn++) {
                if (getColour(x + indexRow, y + indexColumn, world) == world[x][y]) {
                    sameColour ++;
                }

                if (getColour(x + indexRow, y + indexColumn, world) != Actor.NONE) {
                    totalNeighbor ++;
                }
            }
        }

        if ((double)sameColour/(double)totalNeighbor >= threshold) {
            return State.SATISFIED;
        }
        return State.UNSATISFIED;
    }

    // 2.2. DONE
    // determine a neighbor.
    Actor getColour(int x, int y, Actor[][] world) {
        if (x >= 0 && x <= 29) {
            if (y >= 0 && y <= 29) {
                return world[x][y];
            }
        }
        return Actor.NONE;
    }

    // 3.   DONE
    // Switch an Unsatisfied Actor with a random Actor.NONE (blank square)
    Actor[][] moveUnsatisfied(State[][] mood, Actor[][] world) {
        for(indexRow = 0; indexRow < world.length; indexRow ++) {
            for (indexColumn = 0; indexColumn < world.length; indexColumn ++) {
                if (mood[indexRow][indexColumn] == State.UNSATISFIED) {
                    int[] position = getNone(world);
                    world[position[0]][position[1]] = world[indexRow][indexColumn];
                    world[indexRow][indexColumn] = Actor.NONE;
                }
            }
        }
        return world;
    }

    // 3.1. DONE
    // Get position of a random Actor.NONE (blank square)
    int[] getNone(Actor[][] world) {
        int numberOfNones = 0;
        for (int indexRow = 0; indexRow < world.length; indexRow ++) {
            for (int indexColumn = 0; indexColumn < world.length; indexColumn ++) {
                if (world[indexRow][indexColumn] == Actor.NONE) {
                    numberOfNones ++;
                }
            }
        }

        int[][] listNones = new int[numberOfNones][2];
        int indexRowListNones = 0;
        for (int indexRow = 0; indexRow < world.length; indexRow ++) {
            for (int indexColumn = 0; indexColumn < world.length; indexColumn ++) {
                if (world[indexRow][indexColumn] == Actor.NONE) {
                    listNones[indexRowListNones][0] = indexRow;
                    listNones[indexRowListNones][1] = indexColumn;
                    indexRowListNones++;
                    /*---------------------------------------
                    listNones = new int[]{indexRow, indexColumn};

                    double value1 = rand.nextDouble();
                    double value2 = 1 / (double)numberOfNones;

                    if (value1 < value2){
                        return listNones;
                    }
                    -----------------------------------------*/
                }
            }
        }

        int aRandomRow = rand.nextInt(numberOfNones-1);
        int[] aRandomPair = listNones[aRandomRow];
        return aRandomPair;
    }
}
