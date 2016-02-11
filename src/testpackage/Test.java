package testpackage;


//	SCC210 Example code
//
//		Andrew Scott, 2015
//
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.function.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.net.*;
import java.io.*;

import org.jsfml.system.*;
import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;

class Test {
    private static int screenWidth  = 1024;
    private static int screenHeight = 768;

    //
    // The Java install comes with a set of fonts but these will
    // be on different filesystem paths depending on the version
    // of Java and whether the JDK or JRE version is being used.
    //
    private static String JavaVersion =
            Runtime.class.getPackage( ).getImplementationVersion( );
    private static String JdkFontPath =
            "C:\\Program Files\\Java\\jdk" + JavaVersion +
                    "\\jre\\lib\\fonts\\";
    private static String JreFontPath =
            "C:\\Program Files\\Java\\jre" + JavaVersion +
                    "\\lib\\fonts\\";

    private static int fontSize     = 20;
    private static String FontFile  = "LucidaSansRegular.ttf";
    private static String ImageFile = "team.jpg";

    private static String Title   = "Hello SCC210!";
    private static String Message = "Round and round...";

    private String FontPath;	// Where fonts were found

    private ArrayList<Actor> actors = new ArrayList<Actor>( );

    private Message[] m = new Message[6];
    private Actor charMenu;
    private Actor mainMenu;
    private Actor mapMenu;

    public abstract class Actor {
        Transformable obj;   // Base object

        int x  = 0;	// Current X-coordinate
        int y  = 0;	// Current Y-coordinate

        int r  = 0;	// Change in rotation per cycle
        int dx = 5;	// Change in X-coordinate per cycle
        int dy = 5;	// Change in Y-coordinate per cycle

        //
        // Is point x, y within area occupied by this object?
        //
        // This should really be done with bounding boxes not points
        // ...we should check whether any point in bounding box
        //    is covered by this object
        //
        boolean within (int x, int y) {
            // Should check object bounds here
            // -- we'd normally assume a simple rectangle
            //    ...and override as necessary
            return false;
        }

        boolean newWithin(Event e){
            return false;
        }

        void clicked(){

        }

        //
        // Work out where object should be for next frame
        //
        void calcMove(int minx, int miny, int maxx, int maxy) {
            //
            // Add deltas to x and y position
            //
            x += dx;
            y += dy;

            //
            // Check we've not hit screen bounds
            // x and y would normally be the object's centre point
            // rather than its top corner so we can more easily
            // rotate objects and handle circular shapes
            //
            if (x <= minx || x >= maxx) { dx *= -1; x += dx; }
            if (y <= miny || y >= maxy) { dy *= -1; y += dy; }

            //
            // Check we've not collided with any other actor
            //
            for (Actor a : actors) {
                if (a.obj != obj && a.within(x, y)) {
                    dx *= -1; x += dx;
                    dy *= -1; y += dy;
                }
            }
        }

        //
        // Rotate and reposition the object
        //
        void performMove( ) {
            obj.rotate(r);
            obj.setPosition(x,y);
        }

        //
        // Render the object at its new position
        //
        void draw(RenderWindow w) {
            w.draw((Drawable)obj);
        }
        void load(){

        }
        void unload(){

        }
        void moveRight(int i){

        }
        void moveLeft(int i){

        }
    }

    public class Message extends Actor {
        public Text t;
        public Message(int x, int y, int r, String message, Color c , int size) {
            //
            // Load the font
            //
            Font sansRegular = new Font( );
            try {
                sansRegular.loadFromFile(
                        Paths.get(FontPath+FontFile));
            } catch (IOException ex) {
                ex.printStackTrace( );
            }

            Text text = new Text (message, sansRegular, size);
            text.setColor(c);
            //text.setStyle(Text.BOLD | Text.UNDERLINED);

            FloatRect textBounds = text.getLocalBounds( );
            // Find middle and set as origin/ reference point
            text.setOrigin(textBounds.width / 2,
                    textBounds.height / 2);

            this.x = x;
            this.y = y;
            this.r = r;
            t = text;
            obj = text;



            //System.out.println(path);
            //String decodedPath = URLDecoder.decode(path, "UTF-8");
        }
    }

    public class Image extends Actor {

        private int xSize;
        private int ySize;
        private String name;

        public Image(int x, int y, int r, String textureFile) {
            //
            // Load image/ texture
            //
            Texture imgTexture = new Texture( );
            try {
                imgTexture.loadFromFile(Paths.get(textureFile));
            } catch (IOException ex) {
                ex.printStackTrace( );
            }
            imgTexture.setSmooth(true);

            Sprite img = new Sprite(imgTexture);
            img.setOrigin(Vector2f.div(
                    new Vector2f(imgTexture.getSize()), 2));

            this.x = x;
            this.y = y;
            this.r = r;
            this.ySize = imgTexture.getSize().y;
            this.xSize = imgTexture.getSize().x;
            String split[] = textureFile.split(".");
            name  = textureFile;


            obj = img;
        }

        @Override
        public boolean newWithin(Event e){

            Vector2i v = e.asMouseButtonEvent().position;
            int j = (this.x - (xSize / 2));
            int g = (this.x + (xSize / 2));
            int h = (this.y - (ySize / 2));
            int k = (this.y + (ySize / 2));
            if(j < v.x && v.x < g && h < v.y && v.y < k){
                return true;
            }

            return false;

        }

        public void clicked(){

            if(name.contains("magic")){
                System.out.println("magic selected");
                setStats(0, 10, 0, 3, 2);
            }
            else if(name.contains("ranged")){
                System.out.println("ranged selected");
                setStats(0, 0, 5, 5, 5);
            }
            else if(name.contains("Strength")){
                System.out.println("Strength selected");
                setStats(5, 0, 0, 5, 5);
            }

        }

        public void setStats(int a, int b, int c , int d, int e){

            m[1].t.setString("Attack power : " + a);
            m[2].t.setString("Intellect : " + b);
            m[3].t.setString("Agility : " + c);
            m[4].t.setString("Endurance : " + d);
            m[5].t.setString("Vitality : " + e);


        }
    }

    public class Bubble extends Actor {
        private int radius;


        public Bubble(int x, int y, int radius, Color c,
                      int transparency) {
            CircleShape circle = new CircleShape(radius);
            circle.setFillColor(new Color(c, transparency));
            circle.setOrigin(radius, radius);

            this.x = x;
            this.y = y;
            this.radius = radius;

            obj = circle;
        }

        //
        // Default method typically assumes a rectangle,
        // so do something a little different
        //
        @Override
        public boolean within (int px, int py) {
            //
            // In this example, For simplicty...
            // - We just treat circle as a box
            // - x, y is the top corner rather than the centre
            // - We use points x,y and px, py not bounding boxes
            //
            if (px > x && px < x + 2 * radius &&
                    py > y && py < y + 2 * radius) {
                return true;
            }
            else {
                return false;
            }
        }
    }

    private class Rect extends Actor {
        private int radius;

        public Rect(int x, int y, int height, int width, Color c,
                    int transparency) {
            Vector2f vec = new Vector2f(height,width);
            RectangleShape rect = new RectangleShape(vec);
            rect.setFillColor(new Color(c, transparency));
            rect.setOrigin(radius, radius);

            this.x = x;
            this.y = y;
            this.radius = radius;

            obj = rect;
        }

        //
        // Default method typically assumes a rectangle,
        // so do something a little different
        //

    }

    public class Button extends Rect {
        private int heightt;
        private int widthh;
        private String name;

        private ArrayList<Transformable> objs = new ArrayList<Transformable>( );
        private Actor menu;

        private int i = 0;

        public Button(int x, int y, int height, int width, Color c, int transparency, String textt, int size) {

            super(x,y,height,width,c,transparency);
            Font sansRegular = new Font( );
            try {
                sansRegular.loadFromFile(
                        Paths.get(FontPath+FontFile));
            } catch (IOException ex) {
                ex.printStackTrace( );
            }

            Text text = new Text (textt, sansRegular, size);
            text.setColor(Color.BLACK);
            //text.setStyle(Text.BOLD | Text.UNDERLINED);

            FloatRect textBounds = text.getLocalBounds( );
            // Find middle and set as origin/ reference point
            text.setOrigin(textBounds.width / 2,
                    textBounds.height / 2);
            //obj = text;
            objs.add(text);

            objs.add(super.obj);
            widthh = width;
            heightt = height;
            name = textt;

        }


        void draw(RenderWindow w) {
            for (Transformable tra : objs) {
                w.draw((Drawable)tra);
            }
        }

        void performMove( ) {
            for (Transformable tra : objs) {
                if(tra instanceof Text){
                    tra.setPosition(x+(heightt / 2),y + (widthh /2));
                }
                else{
                    tra.setPosition(x,y);
                }
            }
        }

        public boolean newWithin(Event e){

            Vector2i v = e.asMouseButtonEvent().position;
            //int j = (this.x - (xSize / 2));
            //int g = (this.x + (xSize / 2));
            //int h = (this.y - (ySize / 2));
            //int k = (this.y + (ySize / 2));
            //System.out.format("X : %d  Y : %d  This but x : %d   This but y : %d \n", v.x, v.y, this.x, this.y);
            //System.out.format("Height : %d, Width : %d \n", heightt, widthh);
            if(this.x < v.x && v.x < this.x+(heightt)&& this.y < v.y && v.y < this.y+(widthh)){ //NEEDSS MASSIVE WORK

                return true;
            }

            return false;

        }

        void clicked(){

            if(name.equals("NEW GAME")){
                mainMenu.unload(); charMenu.load(); System.out.println("NEW GAME clicked");
            }
            else if(name.equals("CREATE")){
                charMenu.unload(); mapMenu.load(); System.out.println("Create clicked");
            }
            else if(name.equals(">>")){
                System.out.format("%d", this.i);
                if(this.i < 2){
                    this.i++;
                    charMenu.moveRight(i);
                }
            }
            else if(name.equals("<<")){
                System.out.format("%d", this.i);
                if(this.i > 0){
                    this.i--;
                    charMenu.moveLeft(i);
                }
            }
            else if(name.equals("BACK")){
                mapMenu.unload(); charMenu.load(); System.out.println("Back clicked");
            }
        }

    }

    private class MainMenu extends Actor {

        public int loaded = 0;
        private ArrayList<Transformable> objs = new ArrayList<Transformable>( );
        public ArrayList<Actor> act = new ArrayList<Actor>( );

        public MainMenu(){

            add(new Rect(0, 0, 1024, 768, Color.BLACK, 128));
            Button b = new Button((screenWidth / 2) - 100, screenHeight / 2, 250, 100, Color.WHITE, 100, "NEW GAME", 22);
            add(b);
        }

        public void add(Actor a){
            objs.add(a.obj);
            act.add(a);
        }

        void draw(RenderWindow w) {
            for (Actor a : act) {
                if(loaded==1){
                    a.draw(w);
                }
            }
        }

        void performMove(){
            for(Actor a : act){
                a.performMove();
            }
        }

        void load(){
            loaded++;
        }

        void unload(){
            loaded--;
        }
    }

    private class CharMenu extends Actor{

        private ArrayList<Transformable> objs = new ArrayList<Transformable>( );
        public ArrayList<Actor> act = new ArrayList<Actor>( );
        private String[] hairCols = new String[3];

        private Message hairCol = new Message(725, 260, 0, "Red", Color.BLACK, 11);

        public int loaded = 0;

        public CharMenu(){
            System.out.println("Building charMenu");

            add(new Rect(0, 0, 1024, 768, Color.BLACK, 128));
            add(new Rect(550, 50, 350, 700, Color.WHITE, 128));
            add(new Rect(550, 450, 350, 150, Color.WHITE, 128));
            Button b = new Button(685, 650, 100, 50, Color.WHITE, 100, "CREATE", 11);
            URL url = getClass().getProtectionDomain().getCodeSource().getLocation();
            try{
                File myfile = new File(url.toURI());
                File dir = myfile.getParentFile().getParentFile(); // strip off .jar file
                String a = (dir.toString() + "\\assets\\art\\magic.png");
                String d = (dir.toString() + "\\assets\\art\\ranged.png");
                String e = (dir.toString() + "\\assets\\art\\Strength.png");
                add(new Image(600, 100, 0, a));
                add(new Image(725, 100, 0, e));
                add(new Image(850, 100, 0, d));
            } catch (URISyntaxException e) { }
            add(b);
            //System.out.println(getClass().getClassLoader().getResource("\\SCC210-Group-Project\\assets\\art\\magic.png"));
            m[0] = (new Message(575, 430, 0, "Stats", Color.BLACK, 20));
            m[1] = (new Message(675, 475, 0, "Attack power : 5", Color.BLACK, 12));
            m[2] = (new Message(675, 500, 0, "Intellect : 0", Color.BLACK, 12));
            m[3] = (new Message(675, 525, 0, "Agility : 0", Color.BLACK, 12));
            m[4] = (new Message(675, 550, 0, "Endurance : 5", Color.BLACK, 12));
            m[5] = (new Message(675, 575, 0, "Vitality : 5", Color.BLACK, 12));
            add(m[0]);
            add(m[1]);
            add(m[2]);
            add(m[3]);
            add(m[4]);
            add(m[5]);

            hairCols[0] = "Red";
            hairCols[1] = "Blue";
            hairCols[2] = "Green";

            add(new Button(775, 250, 25, 25, Color.WHITE, 100 , ">>", 9 ));
            add(new Button(650, 250, 25, 25, Color.WHITE, 100 , "<<", 9 ));
            add(hairCol);

        }
        public void add(Actor a){
            objs.add(a.obj);
            act.add(a);
        }

        void draw(RenderWindow w) {
            for (Actor a : act) {
                if(loaded==1){
                    a.draw(w);
                }
            }
        }
        void performMove(){
            for(Actor a : act){
                a.performMove();
            }
        }
        void load(){
            loaded++;
        }
        void unload(){
            loaded--;
        }

        void moveRight(int i){
            hairCol.t.setString(hairCols[i]);
        }

        void moveLeft(int i){
            System.out.println("move left");
        }
    }

    private class MapMenu extends Actor{

        private ArrayList<Transformable> objs = new ArrayList<Transformable>( );
        public ArrayList<Actor> act = new ArrayList<Actor>( );

        private Bubble[] nodes = new Bubble[10];
        public int loaded = 0;

        /**
         *
         */
        public MapMenu(){

            System.out.println("Opening map");

            //add(new Rect(0, 0, 1024, 768, Color.BLACK, 128));
                /*read and display map*/
            URL url = getClass().getProtectionDomain().getCodeSource().getLocation();
            try{
                File myfile = new File(url.toURI());
                File dir = myfile.getParentFile().getParentFile(); // strip off .jar file

                System.out.println("About to display image");

                String a = (dir.toString() + "\\assets\\art\\game-map.png");
                add(new Image(screenWidth / 2, screenHeight / 2, 0, a));

                System.out.println("Image displayed");
            } catch (URISyntaxException e) {  }

            // back button
            add(new Button(10, 10, 100, 50, Color.WHITE, 200 , "BACK", 15 ));
                
                /*creating the nodes*/
            nodes[0] = new Bubble(200, 600, 10, Color.BLACK, 300);
            nodes[1] = new Bubble(200, 500, 10, Color.BLACK, 300);
            nodes[2] = new Bubble(500, 500, 10, Color.BLACK, 300);
            nodes[3] = new Bubble(550, 400, 10, Color.BLACK, 300);
            nodes[4] = new Bubble(350, 300, 10, Color.BLACK, 300);
            nodes[5] = new Bubble(500, 100, 10, Color.BLACK, 300);
            nodes[6] = new Bubble(700, 200, 10, Color.BLACK, 300);
            nodes[7] = new Bubble(700, 400, 10, Color.BLACK, 300);
            nodes[8] = new Bubble(800, 300, 10, Color.BLACK, 300);
            nodes[9] = new Bubble(100, 300, 10, Color.BLACK, 300);
            for(int i = 0; i < 10; i++){
                add(nodes[i]);
            }
        }

        public void add(Actor a){
            objs.add(a.obj);
            act.add(a);
        }

        void draw(RenderWindow w) {
            for (Actor a : act) {
                if(loaded==1){
                    a.draw(w);
                }
            }
        }

        void performMove(){
            for(Actor a : act){
                a.performMove();
            }
        }

        void load(){
            loaded++;
        }

        void unload(){
            loaded--;
        }

        private int randomInt(int aStart, int aEnd){
            Random rand = new Random();
            if (aStart > aEnd) {
                throw new IllegalArgumentException("Start cannot exceed End.");
            }
            //get the range, casting to long to avoid overflow problems
            long range = (long)aEnd - (long)aStart + 1;
            // compute a fraction of the range, 0 <= frac < range
            long fraction = (long)(range * rand.nextDouble());
            int randomNumber =  (int)(fraction + aStart);
            return(randomNumber);
        }
    }

    public void run ( ) {

        //
        // Check whether we're running from a JDK or JRE install
        // ...and set FontPath appropriately.
        //
        if ((new File(JreFontPath)).exists( )) FontPath = JreFontPath;
        else FontPath = JdkFontPath;

        //
        // Create a window
        //
        RenderWindow window = new RenderWindow( );
        window.create(new VideoMode(screenWidth, screenHeight),
                Title,
                WindowStyle.DEFAULT);

        window.setFramerateLimit(30); // Avoid excessive updates

        //
        // Create some actors

        //actors.add(new Message(screenWidth / 2, screenHeight / 2,
        //			10, Message, Color.BLACK));
		
                /*
		actors.add(new Image(screenWidth / 4, screenHeight / 4,
					10, ImageFile));
		actors.add(new Bubble(500, 500, 20, Color.MAGENTA, 128));
		actors.add(new Bubble(600, 600, 20, Color.YELLOW,  128));
		actors.add(new Bubble(500, 600, 20, Color.BLUE,    128));
		actors.add(new Bubble(600, 500, 20, Color.BLACK,   128));
                */

        CharMenu c = new CharMenu();
        MainMenu m = new MainMenu();
        MapMenu map = new MapMenu();
        charMenu = c;
        mainMenu = m;
        mapMenu = map;
        //c.load();
        m.load();
        actors.add(c);
        actors.add(m);
        actors.add(map);
        // b.addActionListener(this);
        //
        // Main loop
        //
        while (window.isOpen( )) {
            // Clear the screen
            window.clear(Color.WHITE);

            // Move all the actors around
            for (Actor actor : actors) {
                //actor.calcMove(0, 0, screenWidth, screenHeight);
                actor.performMove( );
                actor.draw(window);
            }

            // Update the display with any changes
            window.display( );

            // Handle any events
            for (Event event : window.pollEvents( )) {
                if (event.type == Event.Type.CLOSED) {
                    // the user pressed the close button
                    window.close( );
                }
                if (event.type == Event.Type.MOUSE_BUTTON_PRESSED) {
                    //   if(event.asMouseButtonEvent()){
                    if(c.loaded != 0){
                        for (Actor actor : c.act){
                            if(actor.newWithin(event)){
                                actor.clicked();
                            }

                        }
                    }

                    if(m.loaded != 0){
                        for (Actor actor : m.act){
                            if(actor.newWithin(event)){
                                actor.clicked();
                            }

                        }
                    }

                    if(map.loaded != 0){
                        for (Actor actor : m.act){
                            if(actor.newWithin(event)){
                                actor.clicked();
                            }
                        }
                    }
                    //Vector2i v = event.position;
                }
            }
        }
    }

    public static void main (String args[ ]) {
        Test t = new Test( );
        t.run( );
    }
}	
