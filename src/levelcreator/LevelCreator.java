package levelcreator;

import game.*;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.ContextActivationException;
import org.jsfml.window.VideoMode;
import org.jsfml.window.WindowStyle;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author josh
 * @date 15/02/16.
 */
public class LevelCreator extends JFrame implements TreeSelectionListener, MouseListener {
    private SwingSquare[][] squares = new SwingSquare[11][11];
    private javax.swing.JButton btnPreview;
    private javax.swing.JButton btnSave;
    private javax.swing.JTextField txtName;
    private HashMap<String, String> hashMap = null;
    private final static String LEVELPARTS_ID_DIR =  "assets" + Constants.SEP + "levelparts"  + Constants.SEP;
    private final static String LEVEL_ID_DIR =  "assets" + Constants.SEP + "levels"  + Constants.SEP;
    private JTree menu;
    private SwingSquare selectedButton;
    private final static String ROTATION_PROMPT = "Rotation: ";
    private final static String PLACEHOLDER = "placeholder.png";
    private javax.swing.JScrollPane jScrollPane1;
    private String selectedSquareFileName;
    private int selectedSquareRotation;

    public LevelCreator() {

        create();

        this.setSize(1000,900);
        this.setVisible(true);

        displayError("Hint: Right click to place the last placed block");
    }

    public void displayError(String message) {
        JFrame popUp = new JFrame();

        popUp.setSize(800, 150);
        popUp.setLayout(new BorderLayout());
        popUp.add(new JLabel(message, JLabel.CENTER), BorderLayout.CENTER);

        popUp.setVisible(true);
    }

    private void preview() {
        new Thread(() -> {
            RenderWindow window = new RenderWindow();
            window.create(new VideoMode(900, 900), "Preview", WindowStyle.DEFAULT);

            window.setFramerateLimit(30); // Avoid excessive updates
            Entity.setWindow(window);     //important
            RoomEntity roomEntity = new RoomEntity().create(new ArrayList<>(getParts()));
            roomEntity.load();


            try {
                window.setActive(true);
            } catch (ContextActivationException e) {
                e.printStackTrace();
            }

            while (window.isOpen()) {
                window.clear(Color.BLACK);
                Iterable<org.jsfml.window.event.Event> tempEvents = window.pollEvents();
                ArrayList<org.jsfml.window.event.Event> events = new ArrayList<>();

                for (org.jsfml.window.event.Event e : tempEvents)
                    events.add(e);

                roomEntity.update(events);
                window.display();
            }
        }).start();
    }

    /**
     * Does some validation and saves the level
     */
    private void save() {
        boolean valid = true;

        for (int i = 0; i < 11 && valid; i++) {
            for (int j = 0; j < 11 && valid; j++) {
                if (squares[i][j].toString().equals(PLACEHOLDER)) {
                    displayError("Please ensure you place a level part (block) in every spot. " +
                            "I know this is annoying.");
                    valid = false;
                }
            }
        }

        if (txtName.getText().length() == 0) {
            displayError("Please input a name");
            valid = false;
        }

        String oldFileName = txtName.getText();
        String newFileName = "";


        //formats the file name a little also more validation
        for (int i = 0; i < oldFileName.length(); i++) {
            if (oldFileName.charAt(i) == '.') {
                valid = false;
                displayError("Invalid Name. No '.'s");
                break;
            }
            if ( oldFileName.charAt(i) == ' ')
                newFileName += '_';
            else
                newFileName += oldFileName.charAt(i);
        }

        if (valid) {
            newFileName = newFileName.toLowerCase();
            FileHandling.writeToFile(new ArrayList<>(getParts()), LEVEL_ID_DIR + newFileName); //SAVING
        }
    }

    private ArrayList<LevelPart> getParts() {
        LevelPart levelPart;
        ArrayList<LevelPart> levelPartArrayList = new ArrayList<>();

        //populating arraylist with values in combo boxes
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                levelPart = new LevelPart();

                levelPart.setSpriteFileName(squares[i][j].toString());
                levelPart.setRotation(squares[i][j].getRotation());
                levelPart.setRowNo(i);
                levelPart.setColNo(j);

                levelPartArrayList.add(levelPart);
            }
        }

        return levelPartArrayList;
    }


    /**
     * Parses a CSV File, strangely allows for comments in case we want to add them in the CSV file
     */
    private void readCSV() {
        hashMap = new HashMap<>();
        ArrayList<String[]> csvContents = CSVReader.read();

        for (String[] line : csvContents) {
            if (line.length >= 2)
                hashMap.put(line[0], line[1]);
        }

        // or... csvContents.stream().filter(line -> line.length >= 2).forEach(line -> hashMap.put(line[0], line[1]));
    }

    /**
     * Creates an returns a reference to the main menu. This will be a JTree.
     *
     * @return JTree
     */
    private JTree createMenu() {
        readCSV();

        Object[] keys = hashMap.keySet().toArray();

        menu = new JTree();
        menu.setVisible(false);

        menu.setRootVisible(false);
        menu.setPreferredSize(new Dimension(150, 0));

        menu.setModel(new DefaultTreeModel(
                new DefaultMutableTreeNode("Selection") {
                    {
                        for (Object key : keys) {
                            DefaultMutableTreeNode root;
                            root = new DefaultMutableTreeNode((String)key);
                            root.add(new DefaultMutableTreeNode(ROTATION_PROMPT + "0"));
                            root.add(new DefaultMutableTreeNode(ROTATION_PROMPT + "90"));
                            root.add(new DefaultMutableTreeNode(ROTATION_PROMPT + "180"));
                            root.add(new DefaultMutableTreeNode(ROTATION_PROMPT + "270"));
                            add(root);
                        }
                    }
                }
        ));

        menu.addTreeSelectionListener(this);
        jScrollPane1.setViewportView(menu);
        return menu;
    }

    /**
     * Called whenever the value of the selection changes.
     *
     * @param e the event that characterizes the change.
     */
    @Override
    public void valueChanged(TreeSelectionEvent e) {
        if (selectedButton != null) {
            //DefaultMutableTreeNode node = (DefaultMutableTreeNode)menu.getLastSelectedPathComponent();
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getNewLeadSelectionPath().getLastPathComponent();

            final String nodeDesc = node.toString();

            if (hashMap == null)
                readCSV();

            boolean validEvent = true;
            Object[] keys = hashMap.keySet().toArray();
            for (Object key : keys) {
                if (nodeDesc.equals((String) key))
                    validEvent = false;
            }

            if (validEvent) {
                String nodestr = node.getParent().toString();
                String fileName = hashMap.get(nodestr);

                selectedSquareFileName = LEVELPARTS_ID_DIR + fileName;
                selectedSquareRotation = Integer.parseInt(nodeDesc.substring(ROTATION_PROMPT.length()));

                setSquareImage(selectedSquareFileName, selectedSquareRotation); //get rotation from node selectedButton

                menu.setVisible(false);

            }
        }
    }

    private void setSquareImage(String fileName, int rotation) {
        try {
            selectedButton.setImage(fileName);

            selectedButton.setRotation(rotation); //get rotation from node selectedButton
        } catch (IOException e1) {
            try {
                displayError("Check the error log...");
                throw new IOException("Invalid file name from .csv, please add the sprite image to assets/levelparts/ or correct .csv file.");
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }
    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            selectedButton = (SwingSquare)e.getSource();
            menu = createMenu();
            menu.setVisible(true);
        } else {
            selectedButton = (SwingSquare)e.getSource();

            setSquareImage(selectedSquareFileName, selectedSquareRotation);
        }

    }

    /**
     * Don't even bother looking at this it's disgusting, it basically just creates the form.
     *
     */
    private void create() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        } catch (Exception e1) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        javax.swing.JButton btnPreview;
        javax.swing.JButton btnSave;
        javax.swing.JLabel jLabel1;
        javax.swing.JPanel jPanel2;
        javax.swing.JTree jTree1;


        SwingSquare square1 = new SwingSquare();
        SwingSquare square2 = new SwingSquare();
        SwingSquare square3 = new SwingSquare();
        SwingSquare square4 = new SwingSquare();
        SwingSquare square5 = new SwingSquare();
        SwingSquare square6 = new SwingSquare();
        SwingSquare square7 = new SwingSquare();
        SwingSquare square8 = new SwingSquare();
        SwingSquare square9 = new SwingSquare();
        SwingSquare square10 = new SwingSquare();
        SwingSquare square11 = new SwingSquare();
        SwingSquare square12 = new SwingSquare();
        SwingSquare square13 = new SwingSquare();
        SwingSquare square14 = new SwingSquare();
        SwingSquare square15 = new SwingSquare();
        SwingSquare square16 = new SwingSquare();
        SwingSquare square17 = new SwingSquare();
        SwingSquare square18 = new SwingSquare();
        SwingSquare square19 = new SwingSquare();
        SwingSquare square20 = new SwingSquare();
        SwingSquare square21 = new SwingSquare();
        SwingSquare square22 = new SwingSquare();
        SwingSquare square23 = new SwingSquare();
        SwingSquare square24 = new SwingSquare();
        SwingSquare square25 = new SwingSquare();
        SwingSquare square26 = new SwingSquare();
        SwingSquare square27 = new SwingSquare();
        SwingSquare square28 = new SwingSquare();
        SwingSquare square29 = new SwingSquare();
        SwingSquare square30 = new SwingSquare();
        SwingSquare square31 = new SwingSquare();
        SwingSquare square32 = new SwingSquare();
        SwingSquare square33 = new SwingSquare();
        SwingSquare square34 = new SwingSquare();
        SwingSquare square35 = new SwingSquare();
        SwingSquare square36 = new SwingSquare();
        SwingSquare square37 = new SwingSquare();
        SwingSquare square38 = new SwingSquare();
        SwingSquare square39 = new SwingSquare();
        SwingSquare square40 = new SwingSquare();
        SwingSquare square41 = new SwingSquare();
        SwingSquare square42 = new SwingSquare();
        SwingSquare square43 = new SwingSquare();
        SwingSquare square44 = new SwingSquare();
        SwingSquare square45 = new SwingSquare();
        SwingSquare square46 = new SwingSquare();
        SwingSquare square47 = new SwingSquare();
        SwingSquare square48 = new SwingSquare();
        SwingSquare square49 = new SwingSquare();
        SwingSquare square50 = new SwingSquare();
        SwingSquare square51 = new SwingSquare();
        SwingSquare square52 = new SwingSquare();
        SwingSquare square53 = new SwingSquare();
        SwingSquare square54 = new SwingSquare();
        SwingSquare square55 = new SwingSquare();
        SwingSquare square56 = new SwingSquare();
        SwingSquare square57 = new SwingSquare();
        SwingSquare square58 = new SwingSquare();
        SwingSquare square59 = new SwingSquare();
        SwingSquare square60 = new SwingSquare();
        SwingSquare square61 = new SwingSquare();
        SwingSquare square62 = new SwingSquare();
        SwingSquare square63 = new SwingSquare();
        SwingSquare square64 = new SwingSquare();
        SwingSquare square65 = new SwingSquare();
        SwingSquare square66 = new SwingSquare();
        SwingSquare square67 = new SwingSquare();
        SwingSquare square68 = new SwingSquare();
        SwingSquare square69 = new SwingSquare();
        SwingSquare square70 = new SwingSquare();
        SwingSquare square71 = new SwingSquare();
        SwingSquare square72 = new SwingSquare();
        SwingSquare square73 = new SwingSquare();
        SwingSquare square74 = new SwingSquare();
        SwingSquare square75 = new SwingSquare();
        SwingSquare square76 = new SwingSquare();
        SwingSquare square77 = new SwingSquare();
        SwingSquare square78 = new SwingSquare();
        SwingSquare square79 = new SwingSquare();
        SwingSquare square80 = new SwingSquare();
        SwingSquare square81 = new SwingSquare();
        SwingSquare square82 = new SwingSquare();
        SwingSquare square83 = new SwingSquare();
        SwingSquare square84 = new SwingSquare();
        SwingSquare square85 = new SwingSquare();
        SwingSquare square86 = new SwingSquare();
        SwingSquare square87 = new SwingSquare();
        SwingSquare square88 = new SwingSquare();
        SwingSquare square89 = new SwingSquare();
        SwingSquare square90 = new SwingSquare();
        SwingSquare square91 = new SwingSquare();
        SwingSquare square92 = new SwingSquare();
        SwingSquare square93 = new SwingSquare();
        SwingSquare square94 = new SwingSquare();
        SwingSquare square95 = new SwingSquare();
        SwingSquare square96 = new SwingSquare();
        SwingSquare square97 = new SwingSquare();
        SwingSquare square98 = new SwingSquare();
        SwingSquare square99 = new SwingSquare();
        SwingSquare square100 = new SwingSquare();
        SwingSquare square101 = new SwingSquare();
        SwingSquare square102 = new SwingSquare();
        SwingSquare square103 = new SwingSquare();
        SwingSquare square104 = new SwingSquare();
        SwingSquare square105 = new SwingSquare();
        SwingSquare square106 = new SwingSquare();
        SwingSquare square107 = new SwingSquare();
        SwingSquare square108 = new SwingSquare();
        SwingSquare square109 = new SwingSquare();
        SwingSquare square110 = new SwingSquare();
        SwingSquare square111 = new SwingSquare();
        SwingSquare square112 = new SwingSquare();
        SwingSquare square113 = new SwingSquare();
        SwingSquare square114 = new SwingSquare();
        SwingSquare square115 = new SwingSquare();
        SwingSquare square116 = new SwingSquare();
        SwingSquare square117 = new SwingSquare();
        SwingSquare square118 = new SwingSquare();
        SwingSquare square119 = new SwingSquare();
        SwingSquare square120 = new SwingSquare();
        SwingSquare square121 = new SwingSquare();

        int i, j;
        i = j = 0;

        squares[i][j++] = square1;
        squares[i][j++] = square2;
        squares[i][j++] = square3;
        squares[i][j++] = square4;
        squares[i][j++] = square5;
        squares[i][j++] = square6;
        squares[i][j++] = square7;
        squares[i][j++] = square8;
        squares[i][j++] = square9;
        squares[i][j++] = square10;
        squares[i++][j] = square11;

        j = 0;

        squares[i][j++] = square12;
        squares[i][j++] = square13;
        squares[i][j++] = square14;
        squares[i][j++] = square15;
        squares[i][j++] = square16;
        squares[i][j++] = square17;
        squares[i][j++] = square18;
        squares[i][j++] = square19;
        squares[i][j++] = square20;
        squares[i][j++] = square21;
        squares[i++][j] = square22;

        j = 0;

        squares[i][j++] = square23;
        squares[i][j++] = square24;
        squares[i][j++] = square25;
        squares[i][j++] = square26;
        squares[i][j++] = square27;
        squares[i][j++] = square28;
        squares[i][j++] = square29;
        squares[i][j++] = square30;
        squares[i][j++] = square31;
        squares[i][j++] = square32;
        squares[i++][j] = square33;

        j = 0;

        squares[i][j++] = square34;
        squares[i][j++] = square35;
        squares[i][j++] = square36;
        squares[i][j++] = square37;
        squares[i][j++] = square38;
        squares[i][j++] = square39;
        squares[i][j++] = square40;
        squares[i][j++] = square41;
        squares[i][j++] = square42;
        squares[i][j++] = square43;
        squares[i++][j] = square44;

        j = 0;

        squares[i][j++] = square45;
        squares[i][j++] = square46;
        squares[i][j++] = square47;
        squares[i][j++] = square48;
        squares[i][j++] = square49;
        squares[i][j++] = square50;
        squares[i][j++] = square51;
        squares[i][j++] = square52;
        squares[i][j++] = square53;
        squares[i][j++] = square54;
        squares[i++][j] = square55;

        j = 0;

        squares[i][j++] = square56;
        squares[i][j++] = square57;
        squares[i][j++] = square58;
        squares[i][j++] = square59;
        squares[i][j++] = square60;
        squares[i][j++] = square61;
        squares[i][j++] = square62;
        squares[i][j++] = square63;
        squares[i][j++] = square64;
        squares[i][j++] = square65;
        squares[i++][j] = square66;

        j = 0;

        squares[i][j++] = square67;
        squares[i][j++] = square68;
        squares[i][j++] = square69;
        squares[i][j++] = square70;
        squares[i][j++] = square71;
        squares[i][j++] = square72;
        squares[i][j++] = square73;
        squares[i][j++] = square74;
        squares[i][j++] = square75;
        squares[i][j++] = square76;
        squares[i++][j] = square77;

        j = 0;

        squares[i][j++] = square78;
        squares[i][j++] = square79;
        squares[i][j++] = square80;
        squares[i][j++] = square81;
        squares[i][j++] = square82;
        squares[i][j++] = square83;
        squares[i][j++] = square84;
        squares[i][j++] = square85;
        squares[i][j++] = square86;
        squares[i][j++] = square87;
        squares[i++][j] = square88;

        j = 0;

        squares[i][j++] = square89;
        squares[i][j++] = square90;
        squares[i][j++] = square91;
        squares[i][j++] = square92;
        squares[i][j++] = square93;
        squares[i][j++] = square94;
        squares[i][j++] = square95;
        squares[i][j++] = square96;
        squares[i][j++] = square97;
        squares[i][j++] = square98;
        squares[i++][j] = square99;

        j = 0;

        squares[i][j++] = square100;
        squares[i][j++] = square101;
        squares[i][j++] = square102;
        squares[i][j++] = square103;
        squares[i][j++] = square104;
        squares[i][j++] = square105;
        squares[i][j++] = square106;
        squares[i][j++] = square107;
        squares[i][j++] = square108;
        squares[i][j++] = square109;
        squares[i++][j] = square110;

        j = 0;

        squares[i][j++] = square111;
        squares[i][j++] = square112;
        squares[i][j++] = square113;
        squares[i][j++] = square114;
        squares[i][j++] = square115;
        squares[i][j++] = square116;
        squares[i][j++] = square117;
        squares[i][j++] = square118;
        squares[i][j++] = square119;
        squares[i][j++] = square120;
        squares[i][j] = square121;

        jLabel1 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        btnSave = new javax.swing.JButton();
        btnPreview = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = menu;

        jPanel2 = new javax.swing.JPanel();

        for (i = 0; i < 11; i++) {
            for (j = 0; j < 11; j++) {
                squares[i][j].addMouseListener(this);
                try {
                    squares[i][j].setImage(LEVELPARTS_ID_DIR + PLACEHOLDER);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                jPanel2.add(squares[i][j]);
            }
        }

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Level Name: ");

        btnPreview.setText("Preview");

        jScrollPane1.setViewportView(jTree1);

        jPanel2.setLayout(new java.awt.GridLayout(11, 11));

        btnSave.setText("Save Level");

        btnSave.addActionListener(evt -> save()); //funky lambda that i didn't even know java could do
        btnPreview.addActionListener(evt -> preview()); //funky lambda that i didn't even know java could do

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtName))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(0, 319, Short.MAX_VALUE)
                                                .addComponent(btnSave)
                                                .addGap(285, 285, 285))
                                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(11, 11, 11)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnPreview, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel1)
                                                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnSave)
                                        .addComponent(btnPreview))
                                .addContainerGap())
        );

        pack();
    }

    public static void main(String[] args) {
        new LevelCreator();
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }
}
