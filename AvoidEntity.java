// Hammer & Sickle
public class AvoidEntity extends Entity implements Consumable, Scrollable {
    
    //Location of image file to be drawn for an AvoidEntity
    private static final String AVOID_IMAGE_FILE = "assets/hammer_sickle1.GIF";
    //Dimensions of the AvoidEntity    
    private static final int AVOID_WIDTH = 70;
    private static final int AVOID_HEIGHT = 70;
    //Speed that the avoid moves each time the game scrolls
    private static final int AVOID_SCROLL_SPEED = 7;
    private int AVOID_SPEED = AVOID_SCROLL_SPEED;
    private int verticalSpeed = 0;
    
    public AvoidEntity(){
        this(0, 0);        
    }
    
    public AvoidEntity(int x, int y){
        super(x, y, AVOID_WIDTH, AVOID_HEIGHT, AVOID_IMAGE_FILE);  
    }
    public AvoidEntity(int x, int y, String avoid_image){
        super(x, y, AVOID_WIDTH, AVOID_HEIGHT, avoid_image);  
    }
    public AvoidEntity(int x, int y, String avoid_image, int newSpeed){
        super(x, y, AVOID_WIDTH, AVOID_HEIGHT, avoid_image); 
        AVOID_SPEED = newSpeed; 
    }
    public AvoidEntity(int x, int y, int newVerticalSpeed){
        super(x, y, AVOID_WIDTH, AVOID_HEIGHT, AVOID_IMAGE_FILE); 
        verticalSpeed = newVerticalSpeed; 
    }
    public AvoidEntity(int x, int y, int newVerticalSpeed, int newSpeed){
        super(x, y, AVOID_WIDTH, AVOID_HEIGHT, AVOID_IMAGE_FILE); 
        AVOID_SPEED = newSpeed; 
        verticalSpeed = newVerticalSpeed;
    }
    
    public void ChangeScrollingSpeed(int newSpeed){
        this.AVOID_SPEED = newSpeed;
    }
    public int getVerticalSpeed(){
        return verticalSpeed;
    }
    public int getScrollSpeed(){
        return this.AVOID_SPEED;
    }
    
    //Move the avoid left by the scroll speed
    public void scroll(){
        setX(getX() - AVOID_SCROLL_SPEED);
    }
    
    //Colliding with an AvoidEntity does not affect the player's score
    public int getPointsValue(){
       return 0;
    }
    
    //Colliding with an AvoidEntity Reduces players HP by 1
    public int getDamageValue(){
        return -1;
    }
    
}
