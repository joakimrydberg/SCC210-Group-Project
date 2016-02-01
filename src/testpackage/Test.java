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
        
         
	private abstract class Actor {
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
	}

	private class Message extends Actor {
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

	private class Image extends Actor {
            
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
            

	private class Bubble extends Actor {
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
        
        private class Button extends Rect {
            private int heightt;
            private int widthh;
            private String name;
            
            private ArrayList<Transformable> objs = new ArrayList<Transformable>( );
            
            public Button(int x, int y, int height, int width, Color c, int transparency, String textt) {
                
                super(x,y,height,width,c,transparency);
                Font sansRegular = new Font( );
			try {
				sansRegular.loadFromFile(
						Paths.get(FontPath+FontFile));
			} catch (IOException ex) {
				ex.printStackTrace( );
			}

			Text text = new Text (textt, sansRegular, 11);
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
                        tra.setPosition(x+50,y+20);
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
                    if(this.x < v.x && v.x < this.x+widthh && this.y < v.y && v.y < this.y+heightt){ //NEEDSS MASSIVE WORK
                        
                        return true;
                    }
                    
                    return false;
                    
                }
                        
            void clicked(){
                switch(name){
                    case "CREATE": System.out.println("Create clicked");
                }
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
                
               
                
                actors.add(new Rect(0, 0, 1024, 768, Color.BLACK, 128));
                actors.add(new Rect(550, 50, 350, 700, Color.WHITE, 128));
                actors.add(new Rect(550, 450, 350, 150, Color.WHITE, 128));
                Button b = new Button(685, 650, 100, 50, Color.WHITE, 100, "CREATE");
                URL url = getClass().getProtectionDomain().getCodeSource().getLocation();
                try{
                    File myfile = new File(url.toURI());
                    File dir = myfile.getParentFile().getParentFile(); // strip off .jar file
                    String a = (dir.toString() + "\\assets\\art\\magic.png");
                    String d = (dir.toString() + "\\assets\\art\\ranged.png");
                    String c = (dir.toString() + "\\assets\\art\\Strength.png");
                    actors.add(new Image(600, 100, 0, a));
                    actors.add(new Image(725, 100, 0, c));
                    actors.add(new Image(850, 100, 0, d));
                } catch (URISyntaxException e) { }
                
                
                actors.add(b);
                System.out.println(getClass().getClassLoader().getResource("\\SCC210-Group-Project\\assets\\art\\magic.png"));
                m[0] = (new Message(575, 430, 0, "Stats", Color.BLACK, 20));
                m[1] = (new Message(675, 475, 0, "Attack power : 5", Color.BLACK, 12));
                m[2] = (new Message(675, 500, 0, "Intellect : 0", Color.BLACK, 12));
                m[3] = (new Message(675, 525, 0, "Agility : 0", Color.BLACK, 12));
                m[4] = (new Message(675, 550, 0, "Endurance : 5", Color.BLACK, 12));
                m[5] = (new Message(675, 575, 0, "Vitality : 5", Color.BLACK, 12));
                
                actors.add(m[0]);
                actors.add(m[1]);
                actors.add(m[2]);
                actors.add(m[3]);
                actors.add(m[4]);
                actors.add(m[5]);
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
                                    for (Actor actor : actors){
                                        if(actor.newWithin(event)){
                                            actor.clicked();
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
