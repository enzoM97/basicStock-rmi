import java.rmi.Remote;
import java.rmi.RemoteException;

public interface StockRI extends Remote {

	public int consultarStock(String code) throws RemoteException;
	
	public boolean agregarStock(String code) throws RemoteException;
	
	public boolean descontarStock(String code) throws RemoteException;
	
}
