package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class MyClientSocket2 { // 양방향 통신 1대1

	private Socket socket; // 내부 클래스에 쓰기 위해 전역으로 선언
	private Scanner sc;
	private BufferedWriter writer;
	private BufferedReader reader;

	public MyClientSocket2() {
		try {
			socket = new Socket("113.198.238.78", 10000);// 127.0.0.1 = ip 주소의 10000번 포트를 사용하는 프로그램에 접근하라

			// 키보드로부터 입력 받아서
			sc = new Scanner(System.in);
			// 소켓에 버퍼달기(쓰기)
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			new Thread(new Runnable() { // 익명 클래스로 처리
				@Override
				public void run() {
					String input = null;
					try {
						while ((input = reader.readLine()) != null) {
							System.out.println("서버로부터 온 메시지: " + input);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}).start();

			// 메인쓰레드는 키보드로부터 입력을 받은 뒤 클라이언트 소켓에 전송
			while (true) {
				String input = sc.nextLine();// 문자열 받기
				// 서버에 전송
				writer.write(input + "\n"); // 내려쓰기 하면 전송
				writer.flush();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new MyClientSocket2();
	}
}
