package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MyClientSocket3 {
	
	Socket socket;
	BufferedReader reader;
	PrintWriter writer;
	Scanner sc;
	
	public MyClientSocket3() {
		try {
			//accept()호출되고, 쓰레드 만들어지고, 백터에 추가됨.
			socket = new Socket("localhost", 20000);
			SocketThread st = new SocketThread();
			st.start();
			
			writer = new PrintWriter(socket.getOutputStream(), true);
			sc = new Scanner(System.in);
			while(true) {
				String line = sc.nextLine();
				writer.println(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	class SocketThread extends Thread {
		@Override
		public void run() {
			try {
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String line = null;
				while((line = reader.readLine()) != null) {
					System.out.println("from server : "+line);
				}
			} catch (Exception e) {
				
			}
		}
	}
	
	public static void main(String[] args) {
		new MyClientSocket3();
	}
}