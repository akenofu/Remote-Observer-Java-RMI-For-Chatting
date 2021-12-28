package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIService extends Remote {

    void addObserver(RemoteObserver o) throws RemoteException;
    void sendMessage(String message) throws RemoteException;

}