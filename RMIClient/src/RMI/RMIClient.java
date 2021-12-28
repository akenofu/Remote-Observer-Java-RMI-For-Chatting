package RMI;

import org.w3c.dom.ls.LSInput;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIClient extends UnicastRemoteObject implements RemoteObserver {
    protected RMIClient() throws RemoteException {
        super();
    }

    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
        try {
            RMIService remoteService = (RMIService) Naming
                    .lookup("//localhost:9999/RMIService");

            RMIClient client = new RMIClient();
            remoteService.addObserver(client);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(System.in));
            while(true){
                System.out.printf("Enter a message: ");
                String input = reader.readLine();
                remoteService.sendMessage(input);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(Object observable, Object updateMsg)
            throws RemoteException {
        System.out.println("got message:" + updateMsg);
    }
}