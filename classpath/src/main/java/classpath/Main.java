package classpath;

/**
 * @author Moritz Halbritter
 */
class Main {
	private static final boolean JAVAX_SERVLET_ON_THE_CLASSPATH = isClassPresent("javax.servlet.ServletRequest");

	private static final boolean JAKARTA_SERVLET_ON_THE_CLASSPATH = isClassPresent("jakarta.servlet.ServletRequest");

	public static void main(String[] args) {
		Object delegate = new Object();

		if (JAVAX_SERVLET_ON_THE_CLASSPATH && delegate instanceof javax.servlet.ServletRequest) {
			javax.servlet.ServletRequest servletRequest = (javax.servlet.ServletRequest) delegate;
			System.out.println("javax.servlet.ServletRequest");
		}
		else if (JAKARTA_SERVLET_ON_THE_CLASSPATH && delegate instanceof jakarta.servlet.ServletRequest) {
			jakarta.servlet.ServletRequest servletRequest = (jakarta.servlet.ServletRequest) delegate;
			System.out.println("jakarta.servlet.ServletRequest");
		}
	}

	private static boolean isClassPresent(String clazz) {
		try {
			Class.forName(clazz);
			return true;
		}
		catch (ClassNotFoundException e) {
			return false;
		}
	}
}
