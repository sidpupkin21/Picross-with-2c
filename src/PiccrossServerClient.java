/*
 * PiccrossServerClient Class
 * Author: Ahmed Mohamed
 * ID: 041019389
 * Last Edited Apr 17th, 2022
 * Assignment 4 CST 8221_302
 * Professor Daniel Cormier
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;
/**
 * Class Name: PiccrossServerClient
 * Purpose: This class will handle the connection between Clients. It will also update client's name and disconnect them from server.
 * Methods: PiccrossServerClient | updateClient | displayInChat | getInChat | getClientNames | toString | disconnectClient
 */
public class PiccrossServerClient{
    protected String clientList; //String to store clients connected to server
    protected InputStream inputStream;  //object to read input from server
    protected PrintStream printStream; //object to (print/output) input from server
    PiccrossServer piccrossServer;

    /**
     * Purpose: This will send and receive input and output from one user to another within the same port connection
     * Name: PiccrossServerClient
     * @param socket obj of a socket
     * @param clientName string holding client names
     * @exception IOException throws IOException when needed
     */
    public PiccrossServerClient(Socket socket, String clientName){
        try{
            this.inputStream = socket.getInputStream();
            this.printStream = new PrintStream(socket.getOutputStream());
            this.clientList = clientName;
        }
        catch (IOException ioException){
            ioException.printStackTrace();
        }
        socket.isClosed(); //closing socket
    }

    /**
     * Purpose: This method will update the client's name when /name is invoked
     * Name: updateClient
     * @param clientName string containing clientNames
     */
    public void updateClient(String clientName){
        String oldName = this.clientList;
        String newName = clientName;

        newName = newName.replace("/name (",""); //replace name when user enter /name + ( + )
        newName = newName.replace(")","");

        this.clientList = newName;
        displayInChat().println(oldName + " has renamed to "+ newName);

        System.out.println(oldName + " has renamed to "+ newName);

    }

    /**
     * Purpose: This method will implement PrintStream object in order to display inside of chat box when connected to server
     * Name: displayInChat
     * @return printStream
     */
    public PrintStream displayInChat() {
        return this.printStream;
    }

    /**
     * Purpose: This method is a getter that will get user input and store inside inputStream
     * Name: getInChat
     * @return inputStream
     */
    public InputStream getInChat(){
        return this.inputStream;
    }

    /**
     * Purpose: This method is a getter that will get client names that are connected to the server and store inside of string
     * Name: getClientNames
     * @return clientList
     */
    public String getClientNames(){
        return this.clientList;
    }

    /**
     * Purpose: This method will return value given to it in a string format and store inside getClientNames method
     * Name: toString
     * @return getClientNames
     */
    public String toString(){
        return getClientNames();
    }

    /**
     * Purpose: This method will inform the server that a client has disconnected from the network
     * Name: disconnectClient
     */
    public void disconnectClient(){
        String clientName = this.clientList;
        System.out.println(clientName + " has disconnected from server");
        displayInChat().println(clientName + " has disconnected from server\n");
    }
}

/**
 * Sub-Class Name: ServerClient
 * Purpose: This class will be used
 * Methods: ServerClient | Run
 */
class ServerClient implements Runnable{
    private final PiccrossServer pServer;
    private final PiccrossServerClient psClient;
    private  ModelViewController mvc;
    private Controller controller;
    private Model model;

    /**
     * Purpose: This Constructor will assign server and clientServer
     * Name: ServerClient
     * @param pServer obj of PiccrossServer class
     * @param psClient obj of PiccrossServerClient class
     *
     */
    public ServerClient(PiccrossServer pServer, PiccrossServerClient psClient) {
        this.pServer = pServer;
        this.psClient = psClient;
        this.pServer.sendClientsToChat();
    }

    /**
     * Purpose: The run method will be used to exchange communication via chat between clients connected to the network
                It will also find if one of the clients decides to change their name or disconnect from the server and
                remove them from the chat so no messages are received
     * Name: run
     * @exception  NoSuchElementException throws exception when no element is found and returns user with warning message
     */
    @Override
    public void run() {
        try {
            Scanner scan = new Scanner(this.psClient.getInChat());
            String name;
            do {
                name = scan.nextLine();
                if (name.contains("/name (") &&
                        name.contains(")")) {
                    psClient.updateClient(name);
                }
                if (name.contains("/bye")) {
                    psClient.disconnectClient();
                    pServer.disconnectClient(psClient);
                    pServer.sendClient(psClient.clientList + " has disconnected from server");
                    this.pServer.sendClientsToChat();
                }
                pServer.sendChatToClient(name, psClient);
            } while (scan.hasNextLine());
            pServer.disconnectClient(psClient);
            this.pServer.sendClientsToChat();
        }catch (NoSuchElementException no){
            System.err.println("ERROR: YOU HAVE DISCONNECTED WITHOUT INFORMING THE SERVER....PLEASE ENTER /bye");
        }
    }
}