/*
 * Class Main Class
 * @author Ahmed Mohamed
 * ID 041019389
 * Last Edited Apr 15th, 2022
 * Assignment 4 CST 8221_302
 * Professor Daniel Cormier
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

/**
 *Defining the main method
 */
public class Game extends JFrame implements Serializable{

    private static final long serialVersionUID = 2369696670020714054L;
    /**Splash Screen Duration*/
    private final int SSduration;
    /**Default constuctor*/
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
        /**Create Constent panel*/
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
        JLabel label = new JLabel(new ImageIcon("piccross.png"));
        /**Set Label underneath the image label*/
        JLabel name = new JLabel("Welcome To Piccross Game!!",JLabel.CENTER);
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
        splash.SplashScreen();
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
