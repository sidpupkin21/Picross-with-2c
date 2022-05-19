/*
 * ModelViewController Class
 * Author Ahmed Mohamed
 * ID 041019389
 * Last Edited Apr 17th, 2022
 * Assignment 4 CST 8221_302
 * Professor Daniel Cormier
 */
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Timer;
/**
 * Purpose: To generate JFrame with 5x5 grid and buttons
 * Name: ModelViewController
 * Methods: ModelViewController | Clock
 */
public class ModelViewController extends JFrame {
    /*
     * Defining the custom colors used in the UI
     */
    protected Color color1 = new Color(167,149,214);
    protected Color color2 = new Color(146,123,204);
    protected Color color3 = new Color(211,202,234);
    protected Color color4 = new Color(168,161,187);
    protected Color color5 = new Color(188,188,188);
    protected Color color6 = new Color(59,47,47);
    /*
     * Define JPanel objects
     */
    protected JPanel logoPane =new JPanel(); //top left
    protected JPanel markPane = new JPanel(); //bottom left
    protected JPanel mainPane = new JPanel(); //5x5
    protected JPanel topPane = new JPanel(); //top middle
    protected JPanel rightPane = new JPanel(); //right
    protected JPanel leftPane = new JPanel(); //left
    protected JPanel bottom = new JPanel(); //bottom center
    protected JPanel bottomLeft = new JPanel(); //bottom left
    protected JPanel scorePane = new JPanel(); //top right
    /*
     * Define JLabel objects
     */
    protected JLabel logoLabel = new JLabel();
    protected JLabel scoreLabel = new JLabel("SCORE:   ");
    protected JLabel timerLabel = new JLabel("TIMER:   ");
    /*
     * Define JTextArea objects
     */
    protected JTextField scoreArea = new JTextField("0");
    protected JTextField timerArea = new JTextField("0");
    protected JTextArea chat = new JTextArea();
    protected JTextArea hints = new JTextArea();
    /*
     * Define JButton objects
     */
    protected JButton[][] gameButtons = new JButton[5][5];
    protected JButton restart_timer = new JButton("RESTART TIMER\n");
    protected JButton restart = new JButton("RESTART GAME\n");
    protected JLabel[] leftCol;
    protected JLabel[] topRow;
    /*
     * Define JCheckBox objects
     */
    protected JCheckBox markBox = new JCheckBox("MARK BOX");
    protected JScrollPane chatbox;
    /*
     * Menu Icons
     */
    protected Icon newgameicon = new ImageIcon("piciconnew.gif");
    protected Icon exitIcon = new ImageIcon("piciconext.gif");
    protected Icon solutionIcon = new ImageIcon("piciconsol.gif");
    protected Icon aboutIcon = new ImageIcon("piciconabt.gif");
    /*
     * MenuBar
     */
    protected JMenuBar mBar = new JMenuBar(); //menu bar
    /*
     * Menu Columns
     */
    protected JMenu GameJM = new JMenu("GAME"); //menu tab with new, debug and exit
    protected JMenu HelpJM = new JMenu("HELP");
    protected JMenu DebugMI = new JMenu("Debug");
    protected JMenu Network = new JMenu("NETWORK");
    /*
     * Menu Items
     */
    protected JMenuItem NewGameMI = new JMenuItem("New",newgameicon);
    protected JMenuItem ExitMI = new JMenuItem("Exit",exitIcon);
    protected JMenuItem SolutionsMI = new JMenuItem("Solutions",solutionIcon);
    protected JMenuItem AboutMI = new JMenuItem("About", aboutIcon);
    protected JMenuItem Debug1 = new JMenuItem("Debug 1");
    protected JMenuItem Debug2 = new JMenuItem("Debug 2");
    protected JMenuItem Debug3 = new JMenuItem("Debug 3");
    protected int tally;
    protected Timer clock;
    protected int secs=0, mins=0,period = 1000, delay =0;
    /*
     *Networking Items
     */
    protected JMenuItem newConnect = new JMenuItem("Start New Connection");
    protected JMenuItem disconnect = new JMenuItem("Disconnect");
    protected JTextArea Input = new JTextArea();
    protected JButton submit = new JButton("SUBMIT");

    /**
     * Purpose: containing main grid, placement of each panel on the grid
     * Name: ModelViewController
     * will be called on method main
     */
    ModelViewController(){
        super("PICCROSS GAME");
        setTitle("PICCROSS");
        setSize(1250,1000);
        clock = new Timer(); clock();
        /*
         * Define Layout Manager as GridBagLayout
         * Set V&H spacing
         */
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints gridBagConstraints = new GridBagConstraints(); //used to set each panel
        gridBagConstraints.fill = GridBagConstraints.BOTH; // fill the grid
        gridBagConstraints.weightx =1; //extra horizontal space
        gridBagConstraints.weighty=1; //extra vertical space
        setLayout(grid); // set layout as gridbaglayout

        /*
         * Logo label containing the logo of the game wrapped around the logo panel
         */
        Icon logo = new ImageIcon("Logo.png");
        logoLabel = new JLabel(logo); // store logo inside JLabel
        logoPane.setBackground(color2); // set background color of panel to match
        logoPane.add(logoLabel); // add logo label to logo panel
        add(logoPane, gridBagConstraints); //set panel using gridBagConstraints

        /*
         * List of hints
         * will be set inside the mark panel on bottom left
         */
        String hint = "How to Play " +
                "\n1.Press ResetTimer to reset clock"+
                "\n2.Press ResetGame to reset board"+
                "\n3.Press Mark to mark boxes on board"+
                "\n4.Press Alt+S to check solution"+
                "\n5.Press Ctrl+C to start new game"+
                "\n6.Press Ctrl+X to Exit Game"+
                "\n7.For each correct box you will gain 1 points"+
                "\n8.For each incorrect box you will lose 1 point"+
                "\n9.To Win you must score 15 points or more";
        hints = new JTextArea(hint); //store the game guide inside a JTextarea
        hints.setFont(new Font(Font.SERIF,Font.ITALIC,10)); //set the font as Serif|Italic|10
        hints.setEditable(false); // cannot be edited
        hints.setBackground(color3); //set background color
        markPane.add(hints); // add game hints into markPanel

        /*
         * Mark checkbox and panel at bottom left
         * will be set beside the hints text area
         */
        gridBagConstraints.gridx=0; //x-axis position
        gridBagConstraints.gridy=2; //y-axis position
        markPane.setLayout(new GridLayout(1,0,0,0)); //using the gridlayout to separate the game hints from the checkbox
        markBox.setFont(new Font(Font.SERIF,Font.BOLD,25)); //set font for mark box label
        markBox.setBackground(color3); //set background color for mark box to match background
        markPane.add(markBox); // add checkbox to the mark panel
        add(markPane,gridBagConstraints); // set mark panel into grid

        /*
         * Timer Panel
         * will be set on bottom panel
         */
        gridBagConstraints.gridx=1; //x-axis position
        gridBagConstraints.gridy=2; //y-axis position
        bottom.setBackground(color1); //set background color
        timerArea.setFont(new Font(Font.SERIF,Font.BOLD,25)); //set Font for area
        timerArea.setPreferredSize(new Dimension(80,50)); //set dimensions for timer
        timerLabel.setFont(new Font(Font.SERIF,Font.PLAIN,25)); //set font for timer label
        timerArea.setBorder(BorderFactory.createLineBorder(color1));
        timerArea.setEditable(false); //cannot be edited
        timerArea.setBackground(color1);//set background color for timer
        bottom.add(timerLabel); //add to panel
        bottom.add(timerArea); //add to panel
        add(bottom,gridBagConstraints); //add to main grid
        /*
         * Networking Input Box
         */
        Input.setPreferredSize(new Dimension(200,50));
        Input.setEditable(true);
        //Input.setHorizontalAlignment(JTextField.LEFT);
        Input.setAlignmentX(JTextArea.LEFT_ALIGNMENT);
        Input.setBackground(color3);
        bottom.add(Input);
        bottom.add(submit);
        add(bottom,gridBagConstraints);

        /*
         *Score Panel
         * will be set on top right
         * using a score label and panel and textarea
         */
        gridBagConstraints.gridx=3; //x-axis position
        gridBagConstraints.gridy=0; //y-axis position
        scoreLabel.setFont(new Font(Font.SERIF,Font.PLAIN,25)); //create and set font
        scoreArea.setFont(new Font(Font.SERIF,Font.BOLD,25)); //create and set font
        scoreArea.setPreferredSize(new Dimension(50,50));
        scoreArea.setEditable(false); //cannot be edited
        scoreArea.setBorder(BorderFactory.createLineBorder(color2));
        scoreArea.setBackground(color2); //set background color for score panel
        scorePane.setBackground(color2); //set background color for score box
        scorePane.add(scoreLabel); //add score label to panel
        scorePane.add(scoreArea); // add score box to panel
        add(scorePane,gridBagConstraints); //add to main grid

        /*
         * ChatBox/Control Panel
         * will be set on right side
         * using ScrollPanel inside a Panel
         */
        gridBagConstraints.gridx=3; //x-axis position
        gridBagConstraints.gridy=1; //y-axis position
        //create scrollbar that is vertical and horizontal
        chatbox = new JScrollPane(chat, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        chatbox.setPreferredSize(new Dimension(300,500)); //set preferred size of the chatbox
        chatbox.setFont(new Font(Font.SERIF, Font.ITALIC,15)); //create and set fonts
        rightPane.setBackground(color3); //set background color
        rightPane.add(chatbox,gridBagConstraints); //add chatbox to right panel
        add(rightPane,gridBagConstraints); //add to main grid

        /*
         * Start Button
         * will start game
         */
        gridBagConstraints.gridx=3;
        gridBagConstraints.gridy=2;
        restart_timer.setPreferredSize(new Dimension(150,100));
        restart_timer.setFont(new Font(Font.SERIF,Font.BOLD,10));
        restart_timer.setBorder(BorderFactory.createMatteBorder(1,4,1,1,color3));
        bottomLeft.add(restart_timer);

        /*
         *Restart Button
         * will be set inside bottom left panel
         * using restart Button
         */
        gridBagConstraints.gridx=3; //x-axis position
        gridBagConstraints.gridy=2; //y-axis position
        bottomLeft.setBackground(color3); //set background color
        restart.setPreferredSize(new Dimension(150,100));
        restart.setFont(new Font(Font.SERIF,Font.BOLD,10));
        bottomLeft.add(restart); //add button to panel

        restart.setBorder(BorderFactory.createMatteBorder(1,4,1,1,color3));
        add(bottomLeft,gridBagConstraints); //add to main grid

        /*
         * Main 5x5 Button Grid
         * will contain buttons inside a 500x500 panel
         * using nested for loop to implement the buttons and their position
         */
        gridBagConstraints.gridx=1; //x-axis position on gridbaglayout
        gridBagConstraints.gridy=1; //y-axis position on gridbaglayout
        mainPane.setBackground(color4); //set grid background color
        mainPane.setSize(new Dimension(500,500)); //set size of 5x5 grid
        mainPane.setLayout(new GridLayout(0,5,10,10)); //setLayout to have 5 columns and 10pixel gap between each button
        /*for loop to set each button*/
        for(int a=0;a<5;a++){
            for(int b=0; b<5;b++){
                gameButtons[a][b]= new JButton();
                gameButtons[a][b].setPreferredSize(new Dimension(100,100)); //set size of 75x75 for each button
                gameButtons[a][b].setBackground(color3); //set button color
                mainPane.add(gameButtons[a][b]); //add buttons to panel
            }
        }
        add(mainPane,gridBagConstraints); //add panel to grid

        /*
         * Top Panel
         * will place the game hints on the top panel
         * will be linked to the model and controller
         */
        gridBagConstraints.gridx=1; //x-axis position
        gridBagConstraints.gridy=0; //y-axis position
        topPane.setBackground(color1); //set background color
        topPane.setLayout(new GridLayout(0,5,0,0)); //setLayout 5 columns and 0 gap between each column hint
        topRow = new JLabel[5]; //labels array containing 5 elements
        for(int c=0;c<5;c++){
            topRow[c]= new JLabel(); //set temporary text inside label array
            topRow[c].setVerticalAlignment(JLabel.BOTTOM); //alignment
            topPane.add(topRow[c]);
        }
        add(topPane,gridBagConstraints); //add to main grid
        /*
         * Left Panel
         * will place the game hints
         * will be linked to the model and controller
         */
        gridBagConstraints.gridx=0; //x-axis position
        gridBagConstraints.gridy=1; //y-axis position
        leftPane.setLayout(new GridLayout(5,0,0,0)); //setLayout to have 5 rows and 0 gap between each row hint
        leftPane.setBackground(color3); //labels array containing 5 elements
        leftCol = new JLabel[5]; //5 labels
        for(int d=0; d<5; d++){
            leftCol[d] = new JLabel(); //set temporary text inside label array
            leftCol[d].setHorizontalAlignment(JLabel.RIGHT); //alignment
            leftPane.add(leftCol[d]);
        }
        add(leftPane,gridBagConstraints); //add to main grid
        /*
         * Accelerator
         * New game button Ctrl+n
         * Solution button Alt+s
         * Exit button Ctrl+x
         */
        NewGameMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK,true));
        SolutionsMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK,true));
        ExitMI.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,ActionEvent.CTRL_MASK,true));
        /*
         *Game MenuBar Item
         */
        mBar.add(GameJM);
        GameJM.add(NewGameMI);
        GameJM.add(DebugMI);
        GameJM.add(ExitMI);
        /*
         * Networking Submenu Items
         */
        mBar.add(Network);
        Network.add(newConnect);
        Network.add(disconnect);
        /*
         * Help MenuBar Items
         */
        mBar.add(HelpJM);
        HelpJM.add(SolutionsMI);
        HelpJM.add(AboutMI);
        /*
         * Debug Submenu Items
         */
        DebugMI.add(Debug1);
        DebugMI.add(Debug2);
        DebugMI.add(Debug3);
        /*
         * Set menubar visible
         * adding items in submenu's and on help and game menu
         */
        setJMenuBar(mBar);
        /*
         * Set Close operation on application closure
         * set frame as visible
         * set as resizable as false
         */
        disconnect.setEnabled(false);
        submit.setEnabled(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }
    /**
     * Purpose: This method will contain the timerTask that will be used inside the controller to count the number of seconds passed
     * Name: Clock
     */
    public void clock() {
        /*
         * Using timer task to set up a second and minute counter
         */
        clock.schedule(new TimerTask() {
            @Override
            /*
             * Purpose: to start the timerTask and store value of string inside of secs
             * Name: Run()
             */
            public void run() {
                timerArea.setText(String.valueOf(secs));
                secs++;
                mins++;

            }
        },delay,period);
    }

}

/**
 * Sub-Class Name: Controller extending JFrame
 * Purpose: Containing Methods, ActionListener and ActionEvents
 * Methods: Controller | viewListener | rHint | cHints | setGame | buttonPressed | newBoard | buttonMarking | solutions | newGameClock
 */
class Controller extends JFrame {
    /*
     * ImageIcons for game win and game lose
     */
    ImageIcon winPic = new ImageIcon("gamepicwinner.png");
    ImageIcon losePic = new ImageIcon("gamepicend.png");
    /*
     * Inherits ModelViewController and Model class and stores inside of objects
     */
    protected ModelViewController mvc;
    protected Model mod;
    /*
    * Variable declarations
     */
    protected int x,y;
    protected String winnerScore, loserScore;
    protected String timerSecs;
    protected String client;
    protected int port;
    protected boolean isConnected = false;
    protected BufferedReader buffReader;
    protected PrintWriter textOut;
    protected Socket socket;
    protected List<String> play;

    /**
     * Purpose: Parameterized constructor
     * Name: Controller
     * @param mvc storing object of ModelViewController class
     * @param mod storing object of Model class
     */
    public Controller(ModelViewController mvc, Model mod){
        this.mvc = mvc;
        this.mod = mod;
    }

    /**
     * Purpose: will add action listeners to all buttons and actions introduced inside the view class
     * Name: ViewListener
     * @param actionListener object of actionListener
     */
    void viewListener(ActionListener actionListener){
        for(int a=0;a<5;a++){
            for(int b=0;b<5;b++){
                mvc.gameButtons[a][b].addActionListener(actionListener); //5x5 grid button listener
                mvc.gameButtons[a][b].setEnabled(true); //set as enabled
            }
        }
        mvc.markBox.addActionListener(actionListener); //mark button listener
        mvc.restart_timer.addActionListener(actionListener); //timer button listener
        mvc.restart.addActionListener(actionListener); //restart button listener
        mvc.NewGameMI.addActionListener(actionListener); //new game button listener
        mvc.Debug1.addActionListener(actionListener); //Scenario 1 button listener
        mvc.Debug2.addActionListener(actionListener); //Scenario 2 button listener
        mvc.Debug3.addActionListener(actionListener); //Scenario 3 button listener
        mvc.ExitMI.addActionListener(actionListener); //exit button listener
        mvc.AboutMI.addActionListener(actionListener); //about button listener
        mvc.SolutionsMI.addActionListener(actionListener);  //solution button listener
        //Network ActionListeners
        mvc.newConnect.addActionListener(actionListener);
        mvc.disconnect.addActionListener(actionListener);
        mvc.submit.addActionListener(actionListener);
    }

    /**
     * Purpose: calculate the number of hints on the row from the plain and filled boxes generated by the model
     * Name: rHints
     * @param count of rows
     */
    void rHints(int count){
        int x,y,z;
        for(x =0; x<count; x++){
            z=0;
            String s="";
            for(y =0;y<count;y++){
                if(mod.getClickedBox(y,x)==1){
                    z++;
                }
            }
            if(s == ""){
                mvc.topRow[x].setText(""+z);
            }
            if(z>0){
                mvc.topRow[x].setText(s+z);
            }
            else{
                mvc.topRow[x].setText(s);
            }
        }
    }

    /**
     * Purpose: calculate the number of hints on the column from the plain and filled boxes generated by the model
     * Name: cHints
     * @param count of columns
     */
    void cHints(int count){
        int x,y,z;
        for(x=0;x<count;x++){
            z=0;
            String s= "";
            for(y=0;y<count;y++){
                if(mod.getClickedBox(x,y)==1){
                    z++;
                }
            }
            if(s==""){
                mvc.leftCol[x].setText(""+z);
            }
            if(z>0){
                mvc.leftCol[x].setText(s+z);
            }
            else{
                mvc.leftCol[x].setText(s);
            }
        }
    }

    /**
     * Purpose: will set the game board and will add the hints to each row and column
     * Name: setGame
     */
    public void setGame(){
        mod.Game(); //add random game
        mod.newGameBoard(); //setBoard
        rHints(5);cHints(5); //set hits for 5 columns and rows
        viewListener(new ControllerActions()); //add listener class to actionListeners
    }
    /**
     * Purpose: Will set color to each button based on their value and will set color when mark box is selected
     * Name:  buttonMarking
     * @param x containing row button position
     * @param y containing column button position
     */
    void buttonMarking(int x, int y){
        if(mvc.markBox.isSelected()==true){
            if(mod.getClickedBox(x,y)==0){
                mvc.gameButtons[x][y].setBackground(Color.BLACK);
            }
            else if(mod.getClickedBox(x,y)!=0){
                mvc.tally = mvc.tally;
                mvc.gameButtons[x][y].setBackground(mvc.color4);
            }
        }
        if(mvc.markBox.isSelected()==false) {
            if (mod.getClickedBox(x, y) == 1) {
                mvc.tally = mvc.tally + 2;
                mvc.gameButtons[x][y].setBackground(mvc.color1);

            } else if(mod.getClickedBox(x, y) != 1) {
                mvc.tally = mvc.tally - 1;
                mvc.gameButtons[x][y].setBackground(Color.BLACK);
            }
        }

    }
    /**
     * Purpose: to change score for every button pressed. Will return JOptionPane with win and lose
     * Name: ButtonPressed
     * @param x containing row button positions
     * @param y containing column button positions
     */
    void buttonPressed(int x, int y){
        buttonMarking(x,y);
        mvc.gameButtons[x][y].setEnabled(false);
        mvc.scoreArea.setText(mvc.tally+"");
        mvc.gameButtons[x][y].addMouseListener(new MouseAdapter() {
            /**
             * MouseListener using MouseAdapter
             * Purpose: to get click count of gameButton and return win or lose
             * @param e
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==25){
                    if(mvc.tally >=15){
                        JOptionPane.showMessageDialog(null,"YOU WON","Winner",JOptionPane.WARNING_MESSAGE,winPic);

                    }
                    if(mvc.tally <0){
                        JOptionPane.showMessageDialog(null,"YOU LOSE","Loser",JOptionPane.WARNING_MESSAGE,losePic);
                    }
                }
               if(e.getClickCount()>25){
                   if(mvc.tally >=15){
                       JOptionPane.showMessageDialog(null,"YOU WON WITH A FEW BOXES REMAINING!!!","Winner",JOptionPane.WARNING_MESSAGE,winPic);
                   }
                   if(mvc.tally <0){
                       JOptionPane.showMessageDialog(null,"YOU LOST WITH A FEW BOXES REMAINING!!!","Loser",JOptionPane.WARNING_MESSAGE,losePic);
                   }
               }
            }
        });
        if(mvc.tally >=15){
            //JOptionPane.showMessageDialog(null,"YOU WON","Winner",JOptionPane.WARNING_MESSAGE,winPic);
            int win = JOptionPane.showConfirmDialog(null,"", "WINNER",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.ERROR_MESSAGE,winPic);
            switch (win){
                case JOptionPane.YES_OPTION:
                    System.out.println("You have chosen to upload your score");
                    winnerScore = mvc.scoreArea.getText();
                    timerSecs = mvc.timerArea.getText();
                    //this.mvc.secs = Integer.parseInt(timerSecs);
                    mvc.clock.cancel();
                    System.out.println("Score Table updated with \n");
                    System.out.println("Score :"+winnerScore);
                    System.out.println("Timer :"+timerSecs);
                    mvc.gameButtons[x][y].setDefaultCapable(false);
                    mvc.gameButtons[x][y].setEnabled(false);
                    mvc.restart.setEnabled(false);
                    mvc.restart_timer.setEnabled(false);
                    mvc.markBox.setEnabled(false);
                    mvc.clock.cancel();
                    mod.Game();
                    break;
                case JOptionPane.NO_OPTION:
                    System.out.println("You have chosen NOT to upload your score");
                    break;
                case JOptionPane.CANCEL_OPTION:
                    System.out.println("You have chosen to cancel");
                    break;
            }
        }
        if(mvc.tally <-5){
            //JOptionPane.showMessageDialog(null,"YOU LOST","Loser",JOptionPane.WARNING_MESSAGE,losePic);
            int lose = JOptionPane.showConfirmDialog(null, "","LOSER", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE, losePic);
            switch (lose){
                case JOptionPane.YES_OPTION:
                    System.out.println("You have chosen to upload your score");
                    loserScore = mvc.scoreArea.getText();
                    System.out.println("Score Table updated with \n");
                    System.out.println("Score :"+winnerScore);
                    System.out.println("Timer :"+timerSecs);
                    mvc.gameButtons[x][y].setDefaultCapable(false);
                    mvc.gameButtons[x][y].setEnabled(false);
                    mvc.restart.setEnabled(false);
                    mvc.restart_timer.setEnabled(false);
                    mvc.markBox.setEnabled(false);
                    mvc.clock.cancel();
                    mod.Game();
                    break;
                case JOptionPane.NO_OPTION:
                    System.out.println("You have chosen NOT to upload your score");
                    break;
                case JOptionPane.CANCEL_OPTION:
                    System.out.println("You have chosen to cancel");
                    break;
            }
        }
    }

    /**
     * Purpose: Will set score back to zero and generated a new game board of buttons
     * Names: newBoard
     */
    void newBoard(){
        mvc.scoreArea.setText("0");
        mvc.tally=0;
        for(x=0;x<5;x++){
            for(y=0;y<5;y++){
                mvc.gameButtons[x][y].setBackground(mvc.color3);
                mvc.gameButtons[x][y].setEnabled(true);
            }
        }
    }


    /**
     * Purpose: will display Solutions to board and set colors to each block that is correct and incorrect.
     * Name: Solutions
     * @param m model object
     */
    void solutions(Model m){
        for(x=0;x<5;x++){
            for(y=0;y<5;y++){
                if(m.getClickedBox(x,y)==0){
                    mvc.gameButtons[x][y].setBackground(mvc.color6);
                }
                else if(m.getClickedBox(x,y)==1){
                    mvc.gameButtons[x][y].setBackground(mvc.color5);
                }
                else{
                    mvc.gameButtons[x][y].setBackground(mvc.color4);
                }
                mvc.gameButtons[x][y].setEnabled(false);
                mvc.restart.setEnabled(false);
                mvc.restart_timer.setEnabled(false);
                mvc.markBox.setEnabled(false);
            }
        }
    }

    /**
     * Purpose: will reset the game clock and will display another countdown
     * Name: NewGameCLock
     * @param mod model object
     */
    void newGameClock(Model mod){
        mvc.secs = 0;
        mvc.timerArea.setText(String.valueOf(mvc.secs));
        mvc.clock = new Timer();
        mvc.clock();
    }

    /**
     * Purpose: Once a message is written and share it will clear the input box
     * Name: shareChat
     * @param s will store then clear the user input into server
     */
    private void shareChat(String s) {
        textOut.println(s);
        mvc.Input.setText(null); //clears message box
    }

    /**
     * Purpose: Is used to identify server actions and what they produce in terms of output
     * Name: updateChat
     * @param s stores the value of the user input into server
     * @return 1
     */
    private int UpdateChat(String s) {
        int True =1, False = 0;
        if(s.contains("/help")){
            mvc.chat.append("/help: This message\n");
            mvc.chat.append("/get: gets the current challenge game\n");
            mvc.chat.append("/who: shows the names of all connected players\n");
            mvc.chat.append("/name (name): Rename Yourself\n");
            mvc.chat.append("/score: to generate score table\n");
            return True;
        }
        if(s.contains("/bye")){
            mvc.chat.append("Disconnecting from Server. \n");
            isConnected = false;
            connection(isConnected);
            return False;
        }
        if(s.contains("/who")){
            for(String player: play){
                mvc.chat.append("Current Player: "+ player + "\n");
            }
            return True;
        }
        if(s.contains("/score")){
            mvc.chat.append("PLAYER   SCORE   TIME\n");
            mvc.chat.append("======================\n");
            for(String player: play) {
                mvc.chat.append(player + ",     " + winnerScore + ",    " +mvc.secs+"\n");
            }
            return True;
        }
        if(s.contains("/get")){
            System.out.println("Game Board Received "+mod.newgrid+"\n");
            mvc.chat.append("Game Board Received "+mod.newgrid+ "\n");
            return True;
        }
        return False;
    }
    /**
     * Purpose: This method will check if the game is connected to network and will block off menu items and submit button, vice versa
     * Name : connection
     * @param isConnected boolean value to check connection always false unless connected to server
     */
    private void connection(boolean isConnected) {
        if(isConnected == true){
            mvc.disconnect.setEnabled(true);
            mvc.submit.setEnabled(true);
            mvc.newConnect.setEnabled(false);
        }
        if(isConnected == false){
            mvc.disconnect.setEnabled(false);
            mvc.submit.setEnabled(false);
            mvc.newConnect.setEnabled(true);
        }
    }

    /**
     * Purpose: This innerClass will be a thread that send information from users, will identify when server is interrupted
     * Name: serverController
     * @exception IOException throws message when server isInterrupted
     * @exception Exception throws message when other exceptions
     */
    private class serverController extends Thread {
        @Override
        public void run(){
            String str;
            while(!Thread.currentThread().isInterrupted()){
                try{
                    str = buffReader.readLine();

                    if(str != null){
                        if(str.charAt(0)== '['){
                            str = str.substring(1, str.length()-1);
                            play = new ArrayList<String>(Arrays.asList(str.split(",")));
                        }
                        else {
                            mvc.chat.append(str+ "\n");
                        }
                    }
                } catch (IOException ioException) {
                    mvc.chat.append("YOU HAVE DISCONNECTED THE SERVER..PLEASE START A NEW SERVER");
                    System.err.println("YOU HAVE DISCONNECTED THE SERVER..PLEASE START A NEW SERVER");
                }catch (Exception e){
                    mvc.chat.append("YOU HAVE DISCONNECTED THE SERVER..PLEASE START A NEW SERVER\n");
                    System.err.println("YOU HAVE DISCONNECTED THE SERVER..PLEASE START A NEW SERVER");
                }
            }
        }
    }

    /**
     * Purpose: add actions to each button option in game
     * Name: ControllerActions extending JFrame & implements ActionListener
     */
    class ControllerActions extends JFrame implements ActionListener{
        /**
         * ActionEvent
         * @param ae;
         */
        //ActionEvent ae;
        /**
         * Invoked when an action occurs.
         *
         * @param actionEvent
         */
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Object obj = actionEvent.getSource();
            PiccrossNetworkModalVC pnmvc = new PiccrossNetworkModalVC(this);
            /*
             * GameButton Listener
             */
            if(obj != mvc.gameButtons){
                for(int x=0;x<5;x++){
                    for(int y=0;y<5;y++){
                        if(obj==mvc.gameButtons[x][y]){
                            buttonPressed(x,y);
                            mvc.chat.append(mvc.gameButtons[x][y].getText()+x+" : "+y+" is Pressed \n");
                            System.out.println(mvc.gameButtons[x][y].getText()+x+" : "+y+" is Pressed");
                        }
                    }
                }
            }
            /*
             * Restart Board Button Listener
             */
            if(obj == mvc.restart){
                mvc.chat.setText("Restart Game\n");
                newBoard();
                System.out.println("Restart Game Selected");
            }
            /*
             * Restart Timer Button Listener
             */
            if(obj == mvc.restart_timer){
                mvc.chat.setText("Restart Timer\n");
                mvc.clock.cancel();
                newGameClock(mod);
                System.out.println("Restart Timer Selected");
            }
            /*
             * MarkBox Button Listener
             */
            if(obj == mvc.markBox){
                if(mvc.markBox.isSelected()==true){
                    mvc.chat.append("MARK is Selected\n");
                    System.out.println("MARK is Selected");
                }
                if(mvc.markBox.isSelected()== false){
                    mvc.chat.append("MARK is unSelected\n");
                    System.out.println("MARK is unSelected");
                }
            }
            /*
             * NewGame Button Listener
             */
            if(obj == mvc.NewGameMI){
                mvc.chat.append("New Game is Selected\n");
                System.out.println("New Game is Selected");
                mvc.restart.setEnabled(true);
                mvc.restart_timer.setEnabled(true);
                mvc.markBox.setEnabled(true);
                newBoard();
                mod.Game();
                mod.newGameBoard();
                rHints(5);cHints(5);
                mvc.clock.cancel();
                newGameClock(mod);

            }
            /*
             * Solution Button Listener
             */
            if(obj == mvc.SolutionsMI) {
                mvc.chat.append("Solutions is Selected\n");
                mvc.clock.cancel();
                solutions(mod);
            }
            /*
             * Exit Button Listener
             */
            if(obj==mvc.ExitMI){
                JOptionPane.showMessageDialog(null,"THANKS FOR PLAYING!!! PRESS OK TO EXIT GAME");
                System.exit(0);
            }
            /*
             * About Button Listener
             */
            if(obj==mvc.AboutMI) {
                mvc.clock.cancel();
                JOptionPane.showMessageDialog(null, "Piccross-Game-With-Two-C's\n" +
                        "\nBy Ahmed (SID) Mohamed\n" +
                        "\nStudent ID: 041019389\n" +
                        "\nWinter Term 2022\n");
                newGameClock(mod);
            }
            /*
             * Debug1 button listener
             */
            if(obj==mvc.Debug1){
                mvc.chat.append("Debug Scenario 1 Selected\n");
                solutions(mod);
                mvc.clock.cancel();
            }
            /*
             * Debug2 button listener
             */
            if(obj==mvc.Debug2){
                mvc.chat.append("Debug Scenario 2 Selected\n");
                newBoard();
                mod.Game();
                mod.newGameBoard();
                mvc.clock.cancel();
                newGameClock(mod);
            }
            /*
             * Debug3 button listener
             */
            if(obj==mvc.Debug3){
                mvc.chat.append("Debug Scenario 3 Selected\n");
                mvc.clock.cancel();
                mvc.restart.setEnabled(false);
                mvc.restart_timer.setEnabled(false);
                mvc.markBox.setEnabled(false);
            }
            /*
             * Submit to chat
             */
            if (obj == mvc.submit) {
                String chatMessage = mvc.Input.getText();
                if(UpdateChat(chatMessage)==0){
                    shareChat(chatMessage);
                }
            }
            /*
             * Connect To network
             */
            if(obj==mvc.newConnect){
                Thread thread;
                String piccrossServer;
                pnmvc.PiccrossNetworkModalVC();
                /*Instantiate Network Dialog Box*/
                Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (int)((dimension.getWidth()- pnmvc.getWidth())/2);
                int y = (int)((dimension.getHeight()- pnmvc.getHeight())/2);
                pnmvc.setLocation(x,y);
                pnmvc.setVisible(true);
                try{
                    client = pnmvc.getName();
                    piccrossServer = pnmvc.getAddress();
                    port = pnmvc.getPort();
                    mvc.chat.append("Negotiating Connection to: "+ piccrossServer + "\n on port: "+ port+ "\n");
                    socket = new Socket(piccrossServer, port);
                    mvc.chat.append("Connection is Successful!!\n");
                    mvc.chat.append("Welcome to Ahmed's Piccross Server\n");
                    mvc.chat.append("Enter '/help' for Commands.\n");

                    buffReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    textOut= new PrintWriter(socket.getOutputStream(), true);
                    textOut.println(client);
                    thread = new serverController();
                    thread.start();
                    isConnected = true;
                    connection(isConnected);
                }
                catch (ConnectException connectException){
                    //connectException.printStackTrace();
                    mvc.chat.append("ERROR: CONNECTION REFUSED, SERVER IS NOT AVAILABLE\n" );
                }
                catch (SocketTimeoutException socketException){
                    //socketException.printStackTrace();
                    mvc.chat.append("ERROR: CONNECTION REFUSED, SOCKET TIMED OUT\n");
                }
                catch (UnknownHostException unknownHostException) {
                    //unknownHostException.printStackTrace();
                    mvc.chat.append("ERROR: UNKNOWN HOST. THE ADDRESS CANT BE FOUND\n");
                }
                catch (IllegalArgumentException illegalArgumentException){
                    //illegalArgumentException.printStackTrace();
                    mvc.chat.append("ERROR: YOU HAVE CLOSED THE NETWORK BOX\n");
                }
                catch (IOException ioException) {
                    //ioException.printStackTrace();
                    mvc.chat.append("ERROR: UNKNOWN HOST. THE ADDRESS CANT BE FOUND\n");
                }

            }
            /*
             * Disconnect From network
             */
            if(obj==mvc.disconnect){
               // do {
                    if (UpdateChat("/bye") == 0) {
                        shareChat("/bye");
                    }
                    isConnected = false;
                    connection(isConnected);
               // }while(Thread.currentThread().isInterrupted());
            }
        }
    }

}
/**
 * Sub-Class Name: Model
 * Purpose: to generate continuous random game boards using 5 element string of 1s and 0s
 * Methods: getClickedBox | Game | newGameBoard
 */
class Model {
    protected ModelViewController mvc;
    protected Controller c;
    protected int grid[][];
    protected String gridNums = "11110",newgrid="";
    protected int size=5,nextNum;
    protected StringTokenizer stringTokenizer;
    protected Random r = new Random();
    String shareGrid;
    /**
     * Purpose: will use a 2D grid and place values of x and y inside it, used to check where each element is in the grid
     * Name: getClickedBox
     * @param x storing row values
     * @param y storing column values
     * @return z of from x and y
     */
    public int getClickedBox(int x, int y) {
        int z = grid[x][y];
        return z;
    }
    /**
     * Purpose: Generates Board of 25 elements using string of 5 0s and 1s
     * Name: Game
     */
    public void Game() {
        nextNum = r.nextInt(10);
        int x, y=0;
        this.grid = new int[size][size];
        if(nextNum>=1){
            String newgrid="";
            for(int z=0; z<5;z++){
                int nextRand=r.nextInt((int)Math.pow(2,5));
                newgrid= newgrid + Integer.toBinaryString((1<<5) | nextRand).substring(1) + " ";
            }this.gridNums=newgrid;
        }
        stringTokenizer = new StringTokenizer(gridNums, " "); //to tokenize each number of the 25 element string of 1 and 0
        do {
            gridNums = stringTokenizer.nextToken();
            for (x = 0; x < 5; x++) {
                //for(y=0;y<5;y++) {
                if (gridNums.charAt(x) == '1') {
                    grid[x][y] = 1;
                }
                else if (gridNums.charAt(x) == '0') {
                    grid[x][y] = 0;
                }
                //}
            }
            y++;
            x++;
        }while (stringTokenizer.hasMoreTokens());

    }
    /**
     * Purpose: to generate a new random board for 25 elements
     * Name: newGameBoard
     */
    public void newGameBoard(){
        nextNum = r.nextInt(25);
        if(nextNum>=1){
            for(int z=0; z<5;z++){
                int nextRand=r.nextInt((int)(Math.pow(2,5)));
                newgrid=newgrid + Integer.toBinaryString((1<<5)|nextRand).substring(1)+" ";
            }this.gridNums=newgrid;
        }
    }


}