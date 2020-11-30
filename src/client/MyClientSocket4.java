package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class MyClientSocket4 {
	
	private Socket socket;
	private BufferedReader reader;
	
	private Scanner sc;
	private PrintWriter writer;

	
	public MyClientSocket4() {
		try {
			socket = new Socket("localhost",10000);
			
			SocketThread st = new SocketThread(); //while�� ���ʿ� ����ߵȴ�.
			st.start();
			
			sc = new Scanner(System.in);
			writer = new PrintWriter(socket.getOutputStream(),true);//true�� flush �ڵ�
			
			while(true) {
				//ALL:�ȳ�, MSG:ssar1:�ȳ�
				String keyboard = sc.nextLine();
				writer.println(keyboard);
				//writer.flush();
			}
			
		} catch (Exception e) {
			System.out.println("���� ���� ����");
			e.printStackTrace();
		}
	}
	
	class SocketThread extends Thread{
		@Override
		public void run() {			
			try {
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String input = null;
				
				while ((input = reader.readLine()) != null) {
					System.out.println(input);
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	public static void main(String[] args) {
		new MyClientSocket4();
	}
}
