package qz.test.actiontest;

import java.util.concurrent.TransferQueue;

public class C implements A,B{
	public String getStr(){
		return "123";
	};
	
	public static void main(String[] args) {
		A a = new C();
		B b = new C();
		System.out.println(a.getStr());
		System.out.println(b.getStr());
		TransferQueue xx= null;
	}
}
