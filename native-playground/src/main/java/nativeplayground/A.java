package nativeplayground;

/**
 * @author Moritz Halbritter
 */
@SomeAnnotation
public class A {
	private int privateField;

	int packageField;

	public int publicField;

	private A() {
	}

	A(int a) {
	}

	public A(int a, int b) {
	}

	private void privateMethod() {
	}

	void packageMethod() {
	}

	public void publicMethod() {
	}

	public static class PublicInnerStaticClass {
	}

	static class PackageInnerStaticClass {
	}

	private static class PrivateInnerStaticClass {
	}

	public static class PublicInnerClass {
	}

	static class PackageInnerClass {
	}

	private static class PrivateInnerClass {
	}

	private interface PrivateInnerInterface {
	}

	interface PackageInnerInterface {
	}

	public interface PublicInnerInterface {
	}
}
