package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class MyServerSocket3 {
	
	ServerSocket serverSocket;
	Vector<SocketThread> vc; //동기화를 위해서 vactor
	
	public MyServerSocket3() { //V3 (다대다 양방향 통신 - 서버가 받은 메시지를 모든 클라이언트에게 되돌려줌)
		try {
			// 서버 소켓 생성 65536번 중에 0~1023(well known port)를 제외한 모든 포트
			serverSocket = new ServerSocket(20000);
			vc = new Vector<>();
			
			//메인쓰레드는 소켓을 accept()하고 vector에 담는 역할을 함.
			while(true) {
				System.out.println("요청 대기");
				Socket socket = serverSocket.accept(); //클라이언트 요청을 받음.
				System.out.println("요청 받음");
				SocketThread st = new SocketThread(socket);
				st.start();
				vc.add(st); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//소켓정보 + 타겟(run) + 식별자(id)
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
					for (SocketThread socketThread : vc) { //뭐야
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