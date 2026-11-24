package org.example;

public class StrExceptionDemo {
	static String str;
	public static void main(String s[]) {
		try {
			System.out.println("Truoc ngoai le");
			staticLengthmethod();
			System.out.println("Sau ngoai le");
		} catch (NullPointerException ne) {
			System.out.println("Da xay ra loi");
		}

		finally {
			System.out.println("Trong finally");
		}
	}

	static void staticLengthmethod() {
		System.out.println(str.length());
	}
}
