package client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class MyClientSocket { // 일방향통신 1대1

	private Socket socket;

	public MyClientSocket() {
		try {
			socket = new Socket("127.0.0.1", 20000);// 127.0.0.1 = ip 주소의 10000번 포트를 사용하는 프로그램에 접근하라

			// 키보드로부터 입력 받아서
			Scanner sc = new Scanner(System.in);
			// 소켓에 버퍼달기(쓰기)
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

			while (true) {
				String input = sc.nextLine();// 문자열 받기
				// 서버에 전송
				bw.write(input + "\n"); // 내려쓰기 하면 전송
				bw.flush();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new MyClientSocket();
	}
}
