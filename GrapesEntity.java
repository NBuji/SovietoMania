//Grapes 
public class GrapesEntity extends SunEntity{
    
    //Location of image file to be drawn for a RareGetEntity
    private static final String RAREGET_IMAGE_FILE = "assets/grapes1_1.PNG";
    
    public GrapesEntity(){
        this(0, 0);        
    }
    
    public GrapesEntity(int x, int y){
        super(x, y, RAREGET_IMAGE_FILE);  
    }
    
    
    
}
