package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class MyServerSocket3 {
	
	ServerSocket serverSocket;
	Vector<SocketThread> vc; //����ȭ�� ���ؼ� vactor
	
	public MyServerSocket3() { //V3 (�ٴ�� ����� ��� - ������ ���� �޽����� ��� Ŭ���̾�Ʈ���� �ǵ�����)
		try {
			// ���� ���� ���� 65536�� �߿� 0~1023(well known port)�� ������ ��� ��Ʈ
			serverSocket = new ServerSocket(20000);
			vc = new Vector<>();
			
			//���ξ������ ������ accept()�ϰ� vector�� ��� ������ ��.
			while(true) {
				System.out.println("��û ���");
				Socket socket = serverSocket.accept(); //Ŭ���̾�Ʈ ��û�� ����.
				System.out.println("��û ����");
				SocketThread st = new SocketThread(socket);
				st.start();
				vc.add(st); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//�������� + Ÿ��(run) + �ĺ���(id)
	class SocketThread extends Thread {
		
		Socket socket;
		String id;
		BufferedReader reader;
		PrintWriter writer;
		
		public SocketThread(Socket socket) {
			this.socket = socket;
		}
		
		@Override
		public void run() {
			try {
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				writer = new PrintWriter(socket.getOutputStream(), true);
				String line = null;
				while((line = reader.readLine()) != null) {
					System.out.println("from client : "+line);
					for (SocketThread socketThread : vc) { //����
						socketThread.writer.println(line);
						

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		new MyServerSocket3();
	}

}