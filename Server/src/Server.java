import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class Server {
	
	public static void main(String[] args) {
		new Server();
	}
	
	public Server() {
		
		try {
			
			System.out.println("Registrando objetos remotos...");
			final StockRI service = new Stock();
			System.out.println("Ok.");
			Naming.rebind("rmi://localhost//stock", service);
			
		}catch(RemoteException e) {
			e.printStackTrace();
		}catch(MalformedURLException e) {
			e.printStackTrace();
		}
		
	}
	
}
