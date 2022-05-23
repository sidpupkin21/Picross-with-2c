/*#################################################
 * PicrossServer Class
 * Author Ahmed Sid Mohamed
 * Last Edited Apr 15th, 2022
 #################################################*/
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Class Name: PiccrossServer
 * Purpose: This class will launch and prompt user to enter port number which will then be used to connect different clients
 * Methods: PiccrossServer | Main | StartServer | SendClient | sendChatToClient | sendClientsToChat | disconnectClient
 */
public class PicrossServer {
    static int connected; // to check if in connection or not
    static int portNum = 0; //port number to store chosen port number
    static int defaultPort = 61001; //default port number
    static ArrayList<PicrossServerClient> clientList; //arraylist to store clients
    ServerSocket socket; //main socket
    /**
     * Purpose: Parameterized Constructor that will store list of clients inside off an arrayList gathered from the PiccrossServerClient Class
     * Name: PiccrossServer
     * @param portNum will store the port value
     */
    public PicrossServer(int portNum) {
        this.clientList = new ArrayList<PicrossServerClient>(); //Array list of PiccrossServerClient
        this.portNum = portNum;
    }
    /**
     * Purpose: Main Method That will launch prompt to user to enter port number
     * Name:  Main
     * @param args
     * @exception InputMismatchException throws an exception when input is invalid
     */
    public static void main(String[] args){
        while(true) {
            Scanner scan = new Scanner(System.in);
            System.out.print("Please Enter Port Number: ");
            try {
                portNum = scan.nextInt();
                if(!(portNum > 9999 && portNum < 65536)) {
                    System.out.println("ERROR: Invalid port number: " + portNum);
                    portNum = defaultPort;
                    System.out.println("Using default port: " + defaultPort);

                }
                if(portNum > 9999 && portNum < 65536){
                    System.out.print("Using port: " + portNum);
                }
                scan.close();
            }
            catch (InputMismatchException inputMismatchException){
               System.out.println("ERROR: You have not entered a valid integer value\nPlease try again with value between 9999-65536");
               scan.next();
               continue;
            }
            new PicrossServer(portNum).startServer();
        }

    }
    /**
     * Purpose: This method will be used to run server and assign each user with a number on the list once the user has joined the server
     * Name: startServer
     * @exception SocketTimeoutException throws exception when Socket timeout or when Socket is invalid
     * @exception  IOException throws exception for any other error
     */
    public void startServer(){
        try {
            socket = new ServerSocket(portNum);
            System.out.println();
            try {
                do {
                    Socket newSock = socket.accept();
                    connected++;

                    String getClient = (new Scanner(newSock.getInputStream())).nextLine();
                    System.out.println("INBOUND CONNECTION #" + connected);
                    System.out.println(getClient + " has connected to the network");

                    PicrossServerClient psc = new PicrossServerClient(newSock, getClient);

                    this.clientList.add(psc);
                     sendClient(getClient + " has connected to the network");
                    new Thread(new ServerClient(this, psc)).start();
                } while (true);
            }catch (SocketTimeoutException socketTimeoutException){
                System.err.println("ERROR: SOCKET TIMEOUT....PLEASE TRY AGAIN");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /**
     * Purpose: This method will be used to send chat messages from Server to each client connected
     * Name: sendClient
     * @param s is used to store the message that will be sent
     */
    public void sendClient(String s) {
        for(PicrossServerClient psc: this.clientList){
            psc.displayInChat().println(s);//s
        }
    }
    /**
     * Purpose: This method will be used to send chat messages from clients to other clients,
     * Name: SendChatToClients
     * @param s is used to store the message that will be sent
     * @param p is used to identify the name of the client that sent the message
     */
    public void sendChatToClient(String s, PicrossServerClient p){ //printEveryoneElse
        for(PicrossServerClient psc:this.clientList){
            psc.displayInChat().println(p + " : "+s);
        }
    }
    /**
     * Purpose: This method will be used to send the clientList to the clients on the server when /who is invoked
     * Name: sendClientToChat
     */
    public void sendClientsToChat(){
        for(PicrossServerClient psc: this.clientList){
            psc.displayInChat().println(this.clientList);
        }
    }
    /**
     * Purpose: This method will get the disconnected client from the server and then remove them from the list of clients
     * Name: disconnectClient
     * @param psc piccrossServerClient object
     */
    public void disconnectClient(PicrossServerClient psc){ //removeClient
        this.clientList.remove(psc);
    }



}