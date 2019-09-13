import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

import org.apache.log4j.*;

public class Stock extends UnicastRemoteObject implements StockRI {
	
	static HashMap<String, Integer> products = new HashMap<>();
	static Logger logger = Logger.getLogger(Stock.class);
	
	protected Stock() throws RemoteException {
		super();
		PropertyConfigurator.configure("log4j.properties");
	}
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public boolean agregarStock(String code) throws RemoteException {
		int stock = 1;
		
		if (products.containsKey(code)) {
			stock += products.get(code);
			products.put(code, stock);
		}
		else {
			products.put(code, stock);
		}
		
		return true;
	}

	@Override
	public int consultarStock(String code) throws RemoteException {
		int stock = 0;
		
		if (products.containsKey(code)) stock = products.get(code);
		else stock = -1;
		
		return stock;
	}

	@Override
	public boolean descontarStock(String code) throws RemoteException {
		int stock = 0;
		
		if (products.containsKey(code) && products.get(code) > 0) {
			stock = products.get(code) - 1;
			products.put(code, stock);
			return true;
		}
		
		return false;
	}
}