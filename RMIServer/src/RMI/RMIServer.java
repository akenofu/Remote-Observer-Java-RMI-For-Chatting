package  RMI;

import java.io.Serializable;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

public class RMIServer extends Observable implements RMIService {

    private class WrappedObserver implements Observer, Serializable {

        private static final long serialVersionUID = 1L;

        private RemoteObserver ro = null;

        public WrappedObserver(RemoteObserver ro) {
            this.ro = ro;
        }

        @Override
        public void update(Observable o, Object arg) {
            try {
                ro.update(o.toString(), arg);
            } catch (RemoteException e) {
                System.out
                        .println("Remote exception removing observer:" + this);
                o.deleteObserver(this);
            }
        }

    }

    @Override
    public void addObserver(RemoteObserver o) throws RemoteException {
        WrappedObserver mo = new WrappedObserver(o);
        addObserver(mo);
        System.out.println("Added observer:" + mo);
    }

    @Override
    public void sendMessage(String message) throws RemoteException {
        setChanged();
        notifyObservers(message);
    }

//    Thread thread = new Thread() {
//        @Override
//        public void run() {
//            while (true) {
//                try {
//                    // Runs every 0.5 seconds
//                    Thread.sleep(1 * 500);
//                } catch (InterruptedException e) {
//                    // ignore
//                }
//                setChanged();
//                notifyObservers(new Date());
//            }
//        };
//    };
//
//    public RMIServer() {
//        thread.start();
//    }

    public RMIServer(){

    }

    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
        try {
            Registry RMIRegistry = LocateRegistry.createRegistry(9999);
            RMIService RMIService = (RMIService) UnicastRemoteObject
                    .exportObject(new RMIServer(), 9999);
            RMIRegistry.bind("RMIService", RMIService);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}