/*#################################################
 * Game (Main) Class
 * Author Ahmed Sid Mohamed
 * Last Edited Apr 15th, 2022
 * Based on SplashScreen by Daniel Cormier
 #################################################*/
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

/**
 *Defining the main method
 */
public class Game extends JFrame implements Serializable{

    private static final long serialVersionUID = 2369696670020714054L;
    /**Splash Screen Duration*/
    private final int SSduration;
    /**Default constructor*/
    public Game(int SSduration) {
        this.SSduration = SSduration;
    }

    JProgressBar progressBar = new JProgressBar();
    /**SplashScreen method*/
    public void SplashScreen(){
        JLabel text = new JLabel();
        /**Custom colors*/
        Color color1 = new Color(167,149,214);
        Color color2 = new Color(146,123,204);
        /**Create Constant panel*/
        JPanel contents = (JPanel)getContentPane();
        contents.setBackground(color1);
        /**Window boundaries*/
        int width = 534+10;
        int height = 363+10;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = 2/(screen.width-width);
        int y = 2/(screen.height-height);
        /**set boundaries on window*/
        setBounds(x,y,width,height);

        /** Set screen image inside a label*/
        JLabel label = new JLabel(new ImageIcon("src/Pictures/splashLogo.png"));
        /**Set Label underneath the image label*/
        JLabel name = new JLabel("",JLabel.CENTER);//"Welcome To Piccross Game!!"
        name.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 20)); //modify font and size
        contents.add(label, BorderLayout.CENTER);
        contents.add(name, BorderLayout.NORTH);
        /**create and set border*/
        contents.setBorder(BorderFactory.createLineBorder(color2, 10));


       contents.add(progressBar,BorderLayout.SOUTH);


        /**set visibility*/
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(contents);
        setVisible(true);

        int i=0;
        /**Timer for splash screen*/
        while(i<=100){
            setCursor(Cursor.getDefaultCursor());
            try{
                Thread.sleep(100);
                progressBar.setValue(i);
                text.setText(""+Integer.toString(i)+"%");
                i++;
                setCursor(null);
                //setCursor(Cursor.getDefaultCursor());
                if(i ==100){
                    dispose();
                }
            } catch (InterruptedException exception) {
                exception.printStackTrace();
               // dispose();
            }
        }
        //dispose();
    }
    /**
     * Method main calling on the viewer class
     * @param args
     * CallingModelViewController
     */
    public static void main(String[] args){
        int SSduration = 5000;
        if(args.length==1){
            try{
                SSduration = Integer.parseInt(args[0]);
            }catch (NumberFormatException numberFormatException){
                System.out.println("WRONG COMMAND LINE ARGUMENT");
                System.out.println("DEFAULT DURATION 2500 MILLISECONDS IS USED");
                numberFormatException.printStackTrace();
            }
        }
        Game splash = new Game(SSduration);
        //splash.SplashScreen();
        String messageOfHints = "How to Play " +
                "\n1.Press ResetTimer to reset clock"+
                "\n2.Press ResetGame to reset board"+
                "\n3.Press Mark to mark boxes on board"+
                "\n4.Press Alt+S to check solution"+
                "\n5.Press Ctrl+C to start new game"+
                "\n6.Press Ctrl+X to Exit Game"+
                "\n7.For each correct box you will gain 1 points"+
                "\n8.For each incorrect box you will lose 1 point"+
                "\n9.To Win you must score 15 points or more"+
                "\n10.Score below -5 and you lose the game";
        //JOptionPane.showMessageDialog(null, messageOfHints);
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                //ToDo
                ModelViewController mvc = new ModelViewController();
                Model mod = new Model();
                Controller c = new Controller(mvc,mod);
                c.setGame();
            }
        });
    }
}
