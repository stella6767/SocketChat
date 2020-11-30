package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServerSocket {
	
	private ServerSocket serverSocket;
	private Socket socket;
	
	public MyServerSocket() {
		try {
			serverSocket = new ServerSocket(10000); //�������� ��ü ����
			System.out.println("Ŭ���̾�Ʈ ��û ��� ��...");
			socket = serverSocket.accept();//����(������)
			System.out.println("��û�� ������");
			
			BufferedReader reader = //�б⸸ �ϴ¿뵵
					new BufferedReader(new InputStreamReader(socket.getInputStream())); //socket���� �о�ð��̴� inputstream
			
			String input = null;
			while((input = reader.readLine()) != null) {
				System.out.println("Ŭ���̾�Ʈ �޽���: "+input);
			}
			
			reader.close();
			socket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public static void main(String[] args) {
		new MyServerSocket();
	}
}
