//Gorbachev
public class GorbachovEntity extends Entity implements Consumable, Scrollable {
    
    //Location of image file to be drawn for an GorbachevEntity
    private static final String AVOID_IMAGE_FILE1 = "assets/gorbachov1.PNG";
    //Dimensions of the GorbachevEntity   
    private static final int AVOID_WIDTH = 75;
    private static final int AVOID_HEIGHT = 75;
    //Speed that the avoid moves each time the game scrolls
    private static final int AVOID_SCROLL_SPEED = 10;
    
    public GorbachovEntity(){
        this(0, 0);        
    }
    
    public GorbachovEntity(int x, int y){
        super(x, y, AVOID_WIDTH, AVOID_HEIGHT, AVOID_IMAGE_FILE1);  
    }
    public GorbachovEntity(int x, int y, String avoid_image){
        super(x, y, AVOID_WIDTH, AVOID_HEIGHT, avoid_image);  
    }
    
    
    public int getScrollSpeed(){
        return AVOID_SCROLL_SPEED;
    }
    
    //Move the avoid left by the scroll speed
    public void scroll(){
        setX(getX() - AVOID_SCROLL_SPEED);
    }
    
    //Colliding with an GorbachevEntity does not affect the player's score
    public int getPointsValue(){
       return 0;
    }
    
    //Colliding with an GorbachevEntity Reduces players HP by 1
    public int getDamageValue(){
        return -1;
    }
    
}