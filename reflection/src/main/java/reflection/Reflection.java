package reflection;

/**
 * @author Moritz Halbritter
 */
class Reflection {
	public static void main(String[] args) {
		Test[] annotationsOnA = A.class.getAnnotationsByType(Test.class);
		System.out.println("Annotations on A");
		for (Test annotation : annotationsOnA) {
			System.out.println("\t" + annotation);
		}

		Test[] annotationsOnB = B.class.getAnnotationsByType(Test.class);
		System.out.println("Annotations on B");
		for (Test annotation : annotationsOnB) {
			System.out.println("\t" + annotation);
		}

		Test[] annotationsOnC = C.class.getAnnotationsByType(Test.class);
		System.out.println("Annotations on C");
		for (Test annotation : annotationsOnC) {
			System.out.println("\t" + annotation);
		}
	}

	@Test("A")
	private class A {

	}

	@Test("B1")
	@Test("B2")
	private class B {

	}

	private class C extends A {

	}
}
