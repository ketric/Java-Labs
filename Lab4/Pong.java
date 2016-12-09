package assignment.product;


/** LIBRARIES **/
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.util.DoubleSummaryStatistics;
import java.util.Random;

import static java.lang.System.exit;
import static javafx.scene.paint.Color.BLUE;
import static javafx.scene.paint.Color.RED;

//-----------------------------------------------------------------------------------

public class Pong extends Application {

    // Test
    private final boolean doTest = false;
    void test() {
        // Testing

    }

    // JavaFX graphics
    @Override
    public void start(Stage primaryStage) throws Exception {

        if (doTest) {
            test();
            exit(0);
        }

        initWorld();

        Group root = new Group();
        Canvas canvas = new Canvas(width, height);
        root.getChildren().addAll(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        new AnimationTimer() {
            // Param not used
            public void handle(long currentNanoTime) {
                updateWorld();
                renderWorld(gc);

            }
        }.start();

        Scene scene = new Scene(root);
        scene.setOnKeyPressed(this::handleKeysPressed);
        scene.setOnKeyReleased(this::handleKeysReleased);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pong");
        primaryStage.show();
    }

    void renderWorld(GraphicsContext g) {
        g.clearRect(0, 0, width, height);    // Clear everything
        g.fillText("Points: " + pointsLeft, 10, 20);
        g.fillText("Points: " + pointsRight, 500, 20);
        drawBall(g, ball);
        drawPaddle(g, pLeft);
        drawPaddle(g, pRight);

    }

    void drawBall(GraphicsContext g, Ball b) {
        Paint old = g.getFill();
        g.setFill(RED);
        g.fillOval(b.x - 10, b.y - 10, b.width, b.height);
        g.setFill(old);
    }

    void drawPaddle(GraphicsContext g, Paddle p) {
        Paint old = g.getFill();
        g.setFill(BLUE);
        g.fillRect(p.x,p.y, p.width, p.height);
        g.setFill(old);
    }


//-----------------------------------------------------------------------------------

    // Variables:

    // Final variables:
    final Random rand = new Random();
    final int width = 700;     // Size of window
    final int height = 500;    // Size of window

    // Variables for intializing:
    Paddle pRight;
    Paddle pLeft;
    Ball ball = getBall();
    int pointsLeft = 0;
    int pointsRight = 0;

//-----------------------------------------------------------------------------------

    // METHODs:

    //1.
    // Initialize the world, create all objects
    void initWorld() {
        pRight = new Paddle(670, 200, 10, 80);
        pLeft = new Paddle(20, 200, 10, 80);
        ball = getBall();
    }

    //2.
    // Update game state (the logic, i.e. move, bounce, etc.)
    void updateWorld() {
        pRight.move();
        pLeft.move();
        ball.move();
        if (ball.x < -10) {
            ball = getBall();
            pointsRight = pointsRight + 1;
        }
        if (ball.x >710) {
            ball = getBall();
            pointsLeft = pointsLeft + 1;
        }
        collisionChecker(ball, pLeft, pRight);

    }

    //3.
    // Methods for collision detection:
    void collisionChecker(Ball ball, Paddle pLeft, Paddle pRight) {
        if (ball.x <= 40) {
            if (ball.y >= pLeft.y && ball.y < pLeft.y + 80){
                ball.vel[0] = -2*ball.vel[0];
                ball.vel[1] = 2*ball.vel[1];
            }
        }
        else if (ball.x >= 660) {
            if (ball.y >= pRight.y && ball.y <= pRight.y + 80){
                ball.vel[0] = -2*ball.vel[0];
                ball.vel[1] = 2*ball.vel[1];
            }
        }
    }

    //4.
    // Methods for keyboard handling:

        //4a
    void handleKeysPressed(KeyEvent event) {
        // TODO
        switch (event.getCode()) {
            case UP:
                pRight.setVelocity("upAcceleration");
                break;
            case DOWN:
                pRight.setVelocity("downAcceleration");
                break;
            case W:
                pLeft.setVelocity("upAcceleration");
                break;
            case S:

                pLeft.setVelocity("downAcceleration");
                break;
            default:
                ;
        }
    }
        //4b
    void handleKeysReleased(KeyEvent event) {
        // TODO
        switch (event.getCode()) {
            case UP:
                pRight.setVelocity("deceleration");
                break;
            case DOWN:
                pRight.setVelocity("deceleration");
                break;
            case W:
                pLeft.setVelocity("deceleration");
                break;
            case S:

                pLeft.setVelocity("deceleration");
                break;
            default:
                ;
        }
    }

    //5.
    // Utility to create a ball with random speed and direction(
    Ball getBall() {
        Ball ball = new Ball(rand.nextInt(width/2), rand.nextInt(width/2), 20, 20);
        ball.randomVelocities();
        if(ball.y == 0 || ball.x == 0) {
            return getBall();
        }else{
            return ball;}
    }

//-----------------------------------------------------------------------------------

    // CLASSes:

    // 1.
    class Ball {

        // State
        double[] vel = new double[2]; // Velocity dx, dy
        double x;
        double y;
        double width;
        double height;


        // Constructors
        Ball(double x, double y, double width, double height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        // Behaviors
        void move() {
            x = x + vel[0];
            y = y + vel[1];

            if(y < ball.height/2 || y >  500 - ball.height/2) {
                vel[1] = -vel[1];
            }
        }

        void randomVelocities() {
            this.vel[0] = (double)rand.nextInt(2)+(-2);
            this.vel[1] = (double)rand.nextInt(2)+(-2);
        }
    }

    // 2.
    class Paddle {
        // State
        double vel = 0; //Velocity dy
        double x;
        double y;
        double width;
        double height;

        // Constructors
        Paddle(double x, double y, double width, double height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        // behaviors
        void move() {
            y = y + vel;
            if (y > 420) {
                y = 420;
            }else if(y < 0) {
                y = 0;}
        }


        void setVelocity(String type) {
            if (type.equals("upAcceleration")) {
                vel = vel - 2;
            }
            if (type.equals("downAcceleration")) {
                vel = vel + 2;
            }
            if (type.equals("deceleration")) {
                vel *= 0.15;
            }

            if (vel < -5) {
                vel = -5;
            }else if(vel > 5) {
                vel = 5;
            }
        }


    }
//-----------------------------------------------------------------------------------
}
