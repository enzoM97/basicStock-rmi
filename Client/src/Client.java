import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import org.apache.log4j.*;

import utils.LOGGER;

public class Client {

	static Logger logger = Logger.getLogger(Client.class);
	static final String LINE = "\n\n****************************************************************\n";
	
	public static void main(String[] args) {
		
		String code;
		final String codigoP = "Ingrese el codigo del producto:";
		int opcion = 0;
		
		PropertyConfigurator.configure("log4j.properties");
		
		try {
			
			Scanner scan = new Scanner(System.in);
			
			logger.info("Conectando con servidor remoto...");
			StockRI remoteObject = (StockRI) Naming.lookup("rmi://localhost//stock");
			logger.info("Ok.");
			
			while(opcion != 4) {
				clearScreen();
				printMenu();
				logger.info("Ingrese una opcion:");
				opcion = scan.nextInt();
				switch (opcion) {
				case 1:
					logger.info(codigoP);
					code = scan.next();
					if (!remoteObject.agregarStock(code)) {
						logger.info("Error. No se pudo agregar el producto con codigo " + code);
						System.exit(1);
					}
					else logger.info("Producto agregado con exito.");
					pause();
					break;
				case 2:
					logger.info(codigoP);
					code = scan.next();
					printStock(remoteObject, code);
					pause();
					break;
				case 3:
					logger.info(codigoP);
					code = scan.next();
					if (!remoteObject.descontarStock(code)) {
						logger.info("No se pudo descontar el stock.");
						logger.info("El producto no existe o posee stock 0.");
					}
					else logger.info("Producto descontado del stock con exito.");
					pause();
					break;
				case 4:
					logger.info("Saliendo...");
					Thread.sleep(2000);
					break;
				default:
					logger.info("Opcion no valida. Vuelva a intentarlo.");
					pause();
					break;
				}
			}
			
			scan.close();
		} catch (MalformedURLException | NotBoundException e) {
			LOGGER.loggException(e);
		} catch (RemoteException | InterruptedException e) {
			LOGGER.loggException(e);
		}
	}
	
	public static void printMenu() {
		logger.info(LINE);
		logger.info("GESTOR BASICO DE STOCK");
		logger.info("\n\n");
		logger.info("1. Agregar producto.");
		logger.info("2. Consultar stock.");
		logger.info("3. Descontar stock.");
		logger.info("4. Salir");
		logger.info(LINE);
	}
	public static void printStock(StockRI remoteObject, String code) throws RemoteException {
		logger.info(LINE);
		logger.info("STOCK ACTUAL\n");
		logger.info("Codigo-Producto: " + code.toUpperCase());
		if (remoteObject.consultarStock(code) == -1) logger.info("El producto solicitado no esta registrado.");
		else logger.info("Cantidad: " + remoteObject.consultarStock(code));
		logger.info(LINE);
	}
	
	/*
	 * metodo obtenido de: https://stackoverflow.com/questions/2979383/java-clear-the-console
	 * */
	public static void clearScreen() {
		try {
	        if (System.getProperty("os.name").contains("Windows"))
	            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	        else
	            Runtime.getRuntime().exec("clear");
	    } catch (IOException | InterruptedException e) {
	    	LOGGER.loggException(e);
	    }
	}
	public static void pause() {
		logger.info("Presione cualquier tecla para continuar...");
		new Scanner(System.in).nextLine();
	}
}
