//Suns 
public class SunEntity extends Entity implements Consumable, Scrollable {
    
    //Location of image file to be drawn for a SunEntity
    private static final String GET_IMAGE_FILE = "assets/borjgalo4.PNG";
    //Dimensions of the SunEntity  
    private static final int GET_WIDTH = 50;
    private static final int GET_HEIGHT = 50;
    //Speed that the SunEntity moves (in pixels) each time the game scrolls
    private static final int GET_SCROLL_SPEED = 5;
    //Amount of points received when player collides with a SunEntity
    private static final int GET_POINT_VALUE = 20;
    
    
    public SunEntity(){
        this(0, 0);        
    }
    
    public SunEntity(int x, int y){
        super(x, y, GET_WIDTH, GET_HEIGHT, GET_IMAGE_FILE);  
    }
    
    public SunEntity(int x, int y, String imageFileName){
        super(x, y, GET_WIDTH, GET_HEIGHT, imageFileName);
    }
    
    public int getScrollSpeed(){
        return GET_SCROLL_SPEED;
    }
    
    //Move the SunEntity left by its scroll speed
    public void scroll(){
        setX(getX() - GET_SCROLL_SPEED);
    }
    
    //Colliding with a SunEntity increases the player's score by the specified amount
    public int getPointsValue(){
        return GET_POINT_VALUE;
    }
    
    //Colliding with a SunEntity does not affect the player's HP
    public int getDamageValue(){
        return 0;
    }
    
}
