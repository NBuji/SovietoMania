//NIKOLOZ BUJIASHVILI
//SOVIETOMANIA

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class BujiGame extends ScrollingGameEngine {

    // Dimensions of game window
    private static final int DEFAULT_WIDTH = 900;
    private static final int DEFAULT_HEIGHT = 600;

    // Starting PlayerEntity coordinates
    private static final int STARTING_PLAYER_X = 0;
    private static final int STARTING_PLAYER_Y = 100;

    // Ind-Points needed to win the game
    private static final int SCORE_TO_WIN = 800;

    // Images/GIFS 
    private static final String INTRO_SPLASH_FILE = "assets/Intro2.PNG";
    private static final String BACK_IMAGE1 = "assets/background4.JPG";
    private static final String BACK_IMAGE2 = "assets/background5.JPG";
    private static final String BACK_IMAGE3 = "assets/oTbil3.JPG";
    private static final String BACK_IMAGE4 = "assets/background3.JPG";
    private static final String AVOID_HAMMER = "assets/hammer_sickle1.GIF";

    // Key pressed to advance past the splash screen
    public static final int ADVANCE_SPLASH_KEY = KeyEvent.VK_ENTER;

    // Interval that Entities get spawned in the game window
    // ie: once every how many ticks does the game attempt to spawn new Entities
    private static final int SPAWN_INTERVAL = 45;

    // A Random object for all your random number generation needs!
    public static final Random rand = new Random();

    private ArrayList<Integer> eLocations = new ArrayList<Integer>();
    private ArrayList<Entity> currentCollisions = new ArrayList<Entity>();

    // Player's current score
    private int score;
    private boolean GorbachovTouched = false;
    private int GorbachovTouches = 0;
    private int antiGorbachovTouches = 0;
    private int StalinCount = 0;
    private int RightStalin = 1;
    private int LeftStalin = 1;
    private int DownStalin = 1;
    private int UpStalin = 1;

    // Stores a reference to game's PlayerEntity object for quick reference
    // (This PlayerEntity will also be in the displayList)
    private PlayerEntity player;

    public BujiGame() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public BujiGame(int gameWidth, int gameHeight) {
        super(gameWidth, gameHeight);
    }

    // Performs all of the initialization operations that need to be done before the
    // game starts
    protected void preGame() {
        this.setBackgroundColor(Color.BLACK);
        player = new PlayerEntity(STARTING_PLAYER_X, STARTING_PLAYER_Y);
        this.setSplashImage(INTRO_SPLASH_FILE);
        this.setBackgroundImage(BACK_IMAGE1);
        displayList.add(player);
        score = 0;
    }

    // Called on each game tick
    protected void updateGame() {
        // scroll all scrollable Entities on the game board
        scrollEntities();
        // Spawn new entities only at a certain interval
        if (ticksElapsed % SPAWN_INTERVAL == 0) {
            spawnNewEntities();
            garbageCollectEntities();
        }
        currentCollisions = checkCollision(player);
        if (currentCollisions.size() != 0) {
            for (int j = 0; j < currentCollisions.size(); j++) {
                handlePlayerCollision((Consumable) currentCollisions.get(j));
            }
        }
        CheckStalinCollisions();
        CheckGorbachovCollision();
        checkScoreActivity();
        // Update the title text on the top of the window
        setTitleText("HP: " + player.getHP() + ", Ind-Points: " + score );
    }

    // Scroll all scrollable entities per their respective scroll speeds
    protected void scrollEntities() {
        for (int i = 0; i < displayList.size(); i++) {
            if ((displayList.get(i) instanceof AvoidEntity)) {
                displayList.get(i).setX(displayList.get(i).getX() - ((AvoidEntity) displayList.get(i)).getScrollSpeed());
                displayList.get(i).setY(displayList.get(i).getY() - ((AvoidEntity) displayList.get(i)).getVerticalSpeed());

            } else if ((displayList.get(i) instanceof StalinEntity)) {
                displayList.get(i).setX(displayList.get(i).getX() - ((StalinEntity) displayList.get(i)).getScrollSpeed());

            }else if ((displayList.get(i) instanceof GorbachovEntity)) {
                displayList.get(i).setX(displayList.get(i).getX() - ((GorbachovEntity) displayList.get(i)).getScrollSpeed());
                
            } else if ((displayList.get(i) instanceof SunEntity)) {
                displayList.get(i).setX(displayList.get(i).getX() - ((SunEntity) displayList.get(i)).getScrollSpeed());
            }
        }
    }

    // Handles "garbage collection" of the displayList
    // Removes entities from the displayList that are no longer relevant
    // (i.e. will no longer need to be drawn in the game window).
    protected void garbageCollectEntities() {

        int size1 = displayList.size();
        int collectedEntities = 0;
        for (int i = 0; i < size1; i++) {
            if (displayList.get(i - collectedEntities).getX() < -20) {
                displayList.remove(i - collectedEntities);
                collectedEntities++;
            }
        }
    }

    // Called whenever it has been determined that the PlayerEntity collided with a
    // consumable
    private void handlePlayerCollision(Consumable collidedWith) {

        if (collidedWith instanceof AvoidEntity) {
            player.setHP(player.getHP() - 1);
            displayList.remove(displayList.indexOf((Entity) collidedWith));

        } else if (collidedWith instanceof StalinEntity) {
            player.setHP(player.getHP() - 1);
            if (StalinCount < 4) {
                this.StalinCount++;
            }
            displayList.remove(displayList.indexOf((Entity) collidedWith));

        }else if (collidedWith instanceof GorbachovEntity) {
            player.setHP(player.getHP());
            GorbachovTouched = true;
            antiGorbachovTouches = 0;
            GorbachovTouches++;
            displayList.remove(displayList.indexOf((Entity) collidedWith));

        } else if (collidedWith instanceof GrapesEntity) {
            player.setHP(player.getHP() + 1);
            antiGorbachovTouches ++;
            if (StalinCount > 0) {
                this.StalinCount--;
            }
            this.score = this.score + 20;
            displayList.remove(displayList.indexOf((Entity) collidedWith));

        } else if (collidedWith instanceof SunEntity) {
            this.score = this.score + 20;
            displayList.remove(displayList.indexOf((Entity) collidedWith));
        }

    }

    // Spawn new Entities on the right edge of the game board
    private void spawnNewEntities() {
        int entityRate = 3;
        if(this.score > 400){
            entityRate = 5;
        }
        int randNumberOfSpawns = rand.nextInt(entityRate);

        for (int i = 0; i <= randNumberOfSpawns; i++) {
            int randChoice = rand.nextInt(33);
            int randLocation = 75 * rand.nextInt((this.getWindowHeight() / 75));
            boolean newLocation = !(eLocations.contains(randLocation));
            eLocations.add(randLocation);

            if (randChoice > 0 && randChoice < 17 && newLocation) {
                if(GorbachovTouched){
                    if(GorbachovTouches > 1){
                        int newVerticalSpeed = -3 + 6*rand.nextInt(2);
                        int randNewSpeed = rand.nextInt(7) + 8;
                        displayList.add(new AvoidEntity(this.getWindowWidth(), randLocation, newVerticalSpeed, randNewSpeed));
                    }else{
                        int newVerticalSpeed = -3 + 6*rand.nextInt(2);
                        displayList.add(new AvoidEntity(this.getWindowWidth(), randLocation, newVerticalSpeed));
                    }
                }else{
                    displayList.add(new AvoidEntity(this.getWindowWidth(), randLocation, AVOID_HAMMER));
                }
            } else if (randChoice >= 0 && randChoice < 20 && newLocation) {
                displayList.add(new StalinEntity(this.getWindowWidth(), randLocation));
            }  else if (randChoice > 0 && randChoice < 24 && newLocation) {
                displayList.add(new GorbachovEntity(this.getWindowWidth(), randLocation));
            } else if (randChoice > 0 && randChoice < 31 && newLocation) {
                displayList.add(new SunEntity(this.getWindowWidth(), randLocation));
            } else if (randChoice >= 31 && newLocation) {
                displayList.add(new GrapesEntity(this.getWindowWidth(), randLocation));
            }
            if (i == randNumberOfSpawns) {
                eLocations.clear();
            }
        }

    }

    // Called once the game is over, performs any end-of-game operations
    protected void postGame() {
        if (player.getHP() == 0) {
            super.setTitleText("Game is over! You Couldn't Gain Independence From Soviet Union!");
            this.setBackgroundImage(BACK_IMAGE4);
        } else {
            super.setTitleText("Congratulations! You Freed Your Country!!!");
            this.setBackgroundImage(BACK_IMAGE2);
        }

    }

    // Determines if the game is over or not
    // Game can be over due to either a win or lose state
    protected boolean isGameOver() {

        if (player.getHP() == 0 || score >= SCORE_TO_WIN) {
            return true;
        }
        return false;

    }

    // Reacts to a single key press on the keyboard
    protected void handleKeyPress(int key) {

        setDebugText("Key Pressed!: " + KeyEvent.getKeyText(key));
        if (key == LEFT_KEY && player.getX() > 0 && !this.isPaused) {
            player.setX(player.getX() - (player.getMovementSpeed() * LeftStalin));

        } else if (key == RIGHT_KEY && player.getX() + player.getWidth() < this.getWindowWidth() && !this.isPaused) {
            player.setX(player.getX() + (player.getMovementSpeed() * RightStalin));

        } else if (key == UP_KEY && player.getY() > 0 && !this.isPaused) {
            player.setY(player.getY() - (player.getMovementSpeed() * UpStalin));

        } else if (key == DOWN_KEY && ((player.getY() + player.getHeight()) < this.getWindowHeight())
                && !this.isPaused) {
            player.setY(player.getY() + (player.getMovementSpeed() * DownStalin));
        } else if (key == KEY_PAUSE_GAME && !this.isPaused) {
            this.isPaused = true;

        } else if (key == KEY_PAUSE_GAME && this.isPaused) {
            this.isPaused = false;

        } 
        // if a splash screen is active, only react to the "advance splash" key...
        // nothing else!
        if (getSplashImage() != null) {
            if (key == ADVANCE_SPLASH_KEY)
                super.setSplashImage(null);

            return;
        }

    }

    // Handles reacting to a single mouse click in the game window
    // Won't be used in Simple Game... you could use it in Creative Game though!
    protected MouseEvent handleMouseClick(MouseEvent click) {
        if (click != null) { // ensure a mouse click occurred
            int clickX = click.getX();
            int clickY = click.getY();
            setDebugText("Click at: " + clickX + ", " + clickY);
        }
        return click;// returns the mouse event for any child classes overriding this method
    }

    //Checks how many times player collided with Stalin and Takes away corresponding keys
    protected void CheckStalinCollisions() {
        if (StalinCount == 0) {
            LeftStalin = 1;
            RightStalin = 1;
            UpStalin = 1;
            DownStalin = 1;
        } else if (StalinCount == 1) {
            LeftStalin = 1;
            RightStalin = 0;
            UpStalin = 1;
            DownStalin = 1;
        } else if (StalinCount == 2) {
            LeftStalin = 0;
            RightStalin = 0;
            UpStalin = 1;
            DownStalin = 1;
        } else if (StalinCount == 3) {
            LeftStalin = 0;
            RightStalin = 0;
            UpStalin = 1;
            DownStalin = 0;
        } else if (StalinCount == 4) {
            LeftStalin = 0;
            RightStalin = 0;
            UpStalin = 0;
            DownStalin = 0;
        }
    }
    // Checks whether enough grapes were collected to cancel Gorbachev effects
    protected void CheckGorbachovCollision(){
        if(antiGorbachovTouches >= 3){
            GorbachovTouched = false;
            antiGorbachovTouches = 0;
            GorbachovTouches =0;
        }
    }
    // Changes the background image once player moves to the second stage
    protected void checkScoreActivity(){
        if(score >= 400){
            this.setBackgroundImage(BACK_IMAGE3);
            this.setGameSpeed(120);
        }
    }
}
