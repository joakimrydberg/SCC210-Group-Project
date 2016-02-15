package levelcreator;

import game.Constants;
import game.FileHandling;
import game.LevelPart;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author josh
 * @date 15/02/16.
 */
public class LevelCreator extends JFrame {
    private JComboBox<String>[][] comboBoxes = new JComboBox[11][11];
    private javax.swing.JButton btnPreview;
    private javax.swing.JButton btnSave;
    private javax.swing.JTextField txtName;
    private HashMap<String, String> hashMap = new HashMap<>();
    private final static String LEVEL_ID_DIR =  "assets" + Constants.SEP + "levels"  + Constants.SEP;

    public LevelCreator() {
        create();

        addItems();

        this.setSize(1400,600);
        this.setVisible(true);
    }

    /**
     * Does some validation and saves the level
     */
    private void save() {
        boolean valid = true;

        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (comboBoxes[i][j].getSelectedItem() == null)
                    valid = false;
            }
        }

        if (txtName.getText().length() == 0) {
            txtName.setText("Invalid Name. No '.'s");        //TODO make this MUCH better if we use this in the demo
            valid = false;
        }

        String oldFileName = txtName.getText();
        String newFileName = "";


        //formats the file name a little also more validation
        for (int i = 0; i < oldFileName.length(); i++) {
            if (oldFileName.charAt(i) == '.') {
                //TODO make this MUCH better if we use this in the demo
                valid = false;
                txtName.setText("Invalid Name. No '.'s");
                break;
            }
            if ( oldFileName.charAt(i) == ' ')
                newFileName += '_';
            else
                newFileName += oldFileName.charAt(i);
        }

        if (!valid) {
            String[] selectedKeyBits;
            LevelPart levelPart;
            ArrayList<Object> levelPartArrayList = new ArrayList<>();

            //populating arraylist with values in combo boxes
            for (int i = 0; i < 11; i++) {
                for (int j = 0; j < 11; j++) {
                    selectedKeyBits = ((String) comboBoxes[i][j].getSelectedItem()).split(" - ");

                    levelPart = new LevelPart();

                    levelPart.setSpriteFileName(hashMap.get(selectedKeyBits[0]));
                    levelPart.setRotation(Integer.parseInt(selectedKeyBits[1]));
                    levelPart.setRowNo(i);
                    levelPart.setColNo(j);

                    levelPartArrayList.add(levelPart);
                }
            }

            FileHandling.writeToFile(levelPartArrayList, LEVEL_ID_DIR + newFileName); //SAVING
        }
    }


    /**
     * Adds the items to the combo boxes
     *
     */
    private void addItems() {
        readCSV();

        Object[] keys = hashMap.keySet().toArray();

        for (int i = 0; i < 11; i++){
            for (int j = 0; j < 11; j++) {
                comboBoxes[i][j].addItem(null);
                for (Object key : keys) {
                    comboBoxes[i][j].addItem((String) key + " - " + "0");
                    comboBoxes[i][j].addItem((String) key + " - " + "90");
                    comboBoxes[i][j].addItem((String) key + " - " + "180");
                    comboBoxes[i][j].addItem((String) key + " - " + "270");
                }
                //comboBoxes[i][j].setSelectedItem(null);
                comboBoxes[i][j].setSelectedIndex(1);
            }
        }

    }

    /**
     * Parses a CSV File, strangley allows for comments in case we want to add them in the CSV file
     */
    private void readCSV() {
        HashMap<String, String> tempHashMap = new HashMap<>();
        BufferedReader br = null;
        String line = "";
        String[] levelIDMap;

        try {
            br = new BufferedReader(new FileReader(LEVEL_ID_DIR + "level_IDs.csv"));
            while ((line = br.readLine()) != null) {
                if (line.length() != 0
                        && (line.charAt(0) != '/' && line.charAt(1) != '/')) { //allow for any 'comments'

                    levelIDMap = line.split(",");
                    tempHashMap.put(levelIDMap[0], levelIDMap[1]);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null)
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

        hashMap = tempHashMap;
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


        JLabel jLabel1 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        btnSave = new javax.swing.JButton();
        JPanel jPanel1 = new javax.swing.JPanel();

        JComboBox<String> jComboBox1 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox2 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox3 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox4 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox5 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox6 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox7 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox8 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox9 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox10 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox11 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox12 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox13 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox14 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox15 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox16 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox17 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox18 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox19 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox20 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox21 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox22 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox23 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox24 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox25 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox26 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox27 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox28 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox29 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox30 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox31 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox32 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox33 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox34 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox35 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox36 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox37 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox38 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox39 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox40 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox41 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox42 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox43 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox44 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox45 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox46 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox47 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox48 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox49 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox50 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox51 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox52 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox53 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox54 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox55 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox56 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox57 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox58 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox59 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox60 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox61 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox62 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox63 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox64 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox65 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox66 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox67 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox68 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox69 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox70 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox71 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox72 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox73 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox74 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox75 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox76 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox77 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox78 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox79 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox80 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox81 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox82 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox83 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox84 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox85 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox86 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox87 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox88 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox89 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox90 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox91 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox92 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox93 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox94 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox95 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox96 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox97 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox98 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox99 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox100 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox101 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox102 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox103 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox104 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox105 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox106 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox107 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox108 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox109 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox110 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox111 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox112 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox113 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox114 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox115 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox116 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox117 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox118 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox119 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox120 = new javax.swing.JComboBox<>();
        JComboBox<String> jComboBox121 = new javax.swing.JComboBox<>();
        JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        btnPreview = new javax.swing.JButton();

        int i, j;
        i = j = 0;

        comboBoxes[i][j++] = jComboBox1;
        comboBoxes[i][j++] = jComboBox2;
        comboBoxes[i][j++] = jComboBox3;
        comboBoxes[i][j++] = jComboBox4;
        comboBoxes[i][j++] = jComboBox5;
        comboBoxes[i][j++] = jComboBox6;
        comboBoxes[i][j++] = jComboBox7;
        comboBoxes[i][j++] = jComboBox8;
        comboBoxes[i][j++] = jComboBox9;
        comboBoxes[i][j++] = jComboBox10;
        comboBoxes[i++][j++] = jComboBox11;

        j = 0;

        comboBoxes[i][j++] = jComboBox12;
        comboBoxes[i][j++] = jComboBox13;
        comboBoxes[i][j++] = jComboBox14;
        comboBoxes[i][j++] = jComboBox15;
        comboBoxes[i][j++] = jComboBox16;
        comboBoxes[i][j++] = jComboBox17;
        comboBoxes[i][j++] = jComboBox18;
        comboBoxes[i][j++] = jComboBox19;
        comboBoxes[i][j++] = jComboBox20;
        comboBoxes[i][j++] = jComboBox21;
        comboBoxes[i++][j++] = jComboBox22;

        j = 0;

        comboBoxes[i][j++] = jComboBox23;
        comboBoxes[i][j++] = jComboBox24;
        comboBoxes[i][j++] = jComboBox25;
        comboBoxes[i][j++] = jComboBox26;
        comboBoxes[i][j++] = jComboBox27;
        comboBoxes[i][j++] = jComboBox28;
        comboBoxes[i][j++] = jComboBox29;
        comboBoxes[i][j++] = jComboBox30;
        comboBoxes[i][j++] = jComboBox31;
        comboBoxes[i][j++] = jComboBox32;
        comboBoxes[i++][j++] = jComboBox33;

        j = 0;

        comboBoxes[i][j++] = jComboBox34;
        comboBoxes[i][j++] = jComboBox35;
        comboBoxes[i][j++] = jComboBox36;
        comboBoxes[i][j++] = jComboBox37;
        comboBoxes[i][j++] = jComboBox38;
        comboBoxes[i][j++] = jComboBox39;
        comboBoxes[i][j++] = jComboBox40;
        comboBoxes[i][j++] = jComboBox41;
        comboBoxes[i][j++] = jComboBox42;
        comboBoxes[i][j++] = jComboBox43;
        comboBoxes[i++][j++] = jComboBox44;

        j = 0;

        comboBoxes[i][j++] = jComboBox45;
        comboBoxes[i][j++] = jComboBox46;
        comboBoxes[i][j++] = jComboBox47;
        comboBoxes[i][j++] = jComboBox48;
        comboBoxes[i][j++] = jComboBox49;
        comboBoxes[i][j++] = jComboBox50;
        comboBoxes[i][j++] = jComboBox51;
        comboBoxes[i][j++] = jComboBox52;
        comboBoxes[i][j++] = jComboBox53;
        comboBoxes[i][j++] = jComboBox54;
        comboBoxes[i++][j++] = jComboBox55;

        j = 0;

        comboBoxes[i][j++] = jComboBox56;
        comboBoxes[i][j++] = jComboBox57;
        comboBoxes[i][j++] = jComboBox58;
        comboBoxes[i][j++] = jComboBox59;
        comboBoxes[i][j++] = jComboBox60;
        comboBoxes[i][j++] = jComboBox61;
        comboBoxes[i][j++] = jComboBox62;
        comboBoxes[i][j++] = jComboBox63;
        comboBoxes[i][j++] = jComboBox64;
        comboBoxes[i][j++] = jComboBox65;
        comboBoxes[i++][j++] = jComboBox66;

        j = 0;

        comboBoxes[i][j++] = jComboBox67;
        comboBoxes[i][j++] = jComboBox68;
        comboBoxes[i][j++] = jComboBox69;
        comboBoxes[i][j++] = jComboBox70;
        comboBoxes[i][j++] = jComboBox71;
        comboBoxes[i][j++] = jComboBox72;
        comboBoxes[i][j++] = jComboBox73;
        comboBoxes[i][j++] = jComboBox74;
        comboBoxes[i][j++] = jComboBox75;
        comboBoxes[i][j++] = jComboBox76;
        comboBoxes[i++][j++] = jComboBox77;

        j = 0;

        comboBoxes[i][j++] = jComboBox78;
        comboBoxes[i][j++] = jComboBox79;
        comboBoxes[i][j++] = jComboBox80;
        comboBoxes[i][j++] = jComboBox81;
        comboBoxes[i][j++] = jComboBox82;
        comboBoxes[i][j++] = jComboBox83;
        comboBoxes[i][j++] = jComboBox84;
        comboBoxes[i][j++] = jComboBox85;
        comboBoxes[i][j++] = jComboBox86;
        comboBoxes[i][j++] = jComboBox87;
        comboBoxes[i++][j++] = jComboBox88;

        j = 0;

        comboBoxes[i][j++] = jComboBox89;
        comboBoxes[i][j++] = jComboBox90;
        comboBoxes[i][j++] = jComboBox91;
        comboBoxes[i][j++] = jComboBox92;
        comboBoxes[i][j++] = jComboBox93;
        comboBoxes[i][j++] = jComboBox94;
        comboBoxes[i][j++] = jComboBox95;
        comboBoxes[i][j++] = jComboBox96;
        comboBoxes[i][j++] = jComboBox97;
        comboBoxes[i][j++] = jComboBox98;
        comboBoxes[i++][j++] = jComboBox99;

        j = 0;

        comboBoxes[i][j++] = jComboBox100;
        comboBoxes[i][j++] = jComboBox101;
        comboBoxes[i][j++] = jComboBox102;
        comboBoxes[i][j++] = jComboBox103;
        comboBoxes[i][j++] = jComboBox104;
        comboBoxes[i][j++] = jComboBox105;
        comboBoxes[i][j++] = jComboBox106;
        comboBoxes[i][j++] = jComboBox107;
        comboBoxes[i][j++] = jComboBox108;
        comboBoxes[i][j++] = jComboBox109;
        comboBoxes[i++][j++] = jComboBox110;

        j = 0;

        comboBoxes[i][j++] = jComboBox111;
        comboBoxes[i][j++] = jComboBox112;
        comboBoxes[i][j++] = jComboBox113;
        comboBoxes[i][j++] = jComboBox114;
        comboBoxes[i][j++] = jComboBox115;
        comboBoxes[i][j++] = jComboBox116;
        comboBoxes[i][j++] = jComboBox117;
        comboBoxes[i][j++] = jComboBox118;
        comboBoxes[i][j++] = jComboBox119;
        comboBoxes[i][j++] = jComboBox120;
        comboBoxes[i][j] = jComboBox121;

        for (i = 0; i < 11; i++) {
            for (j = 0; j < 11; j++) {
                comboBoxes[i][j].setModel(new DefaultComboBoxModel<String>());
                jPanel1.add(comboBoxes[i][j]);
            }
        }

        jScrollPane1.setViewportView(jPanel1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Level Name: ");

        btnSave.setText("Save Level");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                save();
            }
        });

        jPanel1.setLayout(new java.awt.GridLayout(11, 11));

        btnPreview.setText("Preview");

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
                                                .addGap(0, 679, Short.MAX_VALUE)
                                                .addComponent(btnSave)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 685, Short.MAX_VALUE)
                                                .addComponent(btnPreview)))
                                .addContainerGap())
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(22, 22, 22)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1481, Short.MAX_VALUE)
                                        .addContainerGap()))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 548, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnSave)
                                        .addComponent(btnPreview))
                                .addContainerGap())
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(50, 50, 50)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE)
                                        .addGap(51, 51, 51)))
        );

        pack();
    }

    public static void main(String[] args) {
        new LevelCreator();
    }
}
