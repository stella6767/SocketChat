package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import protocol.Chat;

public class MyServerSocket4 {

	private ServerSocket serverSocket;
	Vector<SocketThread> vc; // 대기열(큐)

	public MyServerSocket4() {
		try {
			serverSocket = new ServerSocket(10000);
			vc = new Vector<>();

			while (true) {
				System.out.println("요청 대기중....");
				Socket socket = serverSocket.accept();
				// 1.새로운 소켓 생성 socket
				// 2.새로운 스레드 생성
				// 3.새로운 스레드한테 socket 변수 넘기기
				// 4.새로운 스레드 자체를 vc에 담기
				System.out.println("요청받음...");
				SocketThread st = new SocketThread(socket);
				st.start();
				vc.add(st);

			}

		} catch (Exception e) {
			System.out.println("서버 연결 오류");
			e.printStackTrace();
		}
	}

	class SocketThread extends Thread { // 굳이 이렇게 하는 이유는 구분자(id)도 같이 넘겨주기 위해

		private Socket socket;
		private String id;
		private BufferedReader reader;
		private PrintWriter writer;

		public SocketThread(Socket socket) {
			this.socket = socket;

		}

		@Override
		public void run() {
			try {
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				writer = new PrintWriter(socket.getOutputStream());

				String input = null;
				while ((input = reader.readLine()) != null) {
					// Routing(라우팅 하기)
					routing(input);

				}
			} catch (Exception e) {

				e.printStackTrace();
			}

		}

		private void routing(String input) {
			String gubun[] = input.split(":");

			if (id == null) {
				if (gubun[0].equals("ID")) {
					// 변수에 ID 저장
					id = gubun[1];
					writer.println("당신의 아이디는 " + id + "입니다.");
					writer.flush();
				} else {
					writer.println("아이디를 먼저 입력하세요!");
					writer.flush();
					return;
				}
			}

			if (gubun[0].equals(Chat.ALL) ) { // 전체채팅 ALL:안녕 , 인터페이스 프로토콜 사용
				for (int i = 0; i < vc.size(); i++) {
					if (vc.get(i) != this) {
						vc.get(i).writer.println(id + "-->" + gubun[1]);
						vc.get(i).writer.flush();
					}
				}
			} else if (gubun[0].equals(Chat.MSG) ) { // MSG:ssar1:안녕
				String tempId = gubun[1];
				String tempMsg = gubun[2];

				for (int i = 0; i < vc.size(); i++) {
					if (vc.get(i).id != null && vc.get(i).id.equals(tempId)) {
						vc.get(i).writer.println(id + "-->" + tempMsg);
						vc.get(i).writer.flush();
					}
				}
			}
		}

	}// End of socketThread

	public static void main(String[] args) {
		new MyServerSocket4();
	}
}
