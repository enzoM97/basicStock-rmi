package utils;

public class LOGGER {
	public static void loggException(Exception e) {
		System.err.println(e.getMessage());
		System.exit(1);
	}
}
