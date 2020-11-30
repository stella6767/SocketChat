package client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class MyClientSocket { // �Ϲ������ 1��1

	private Socket socket;

	public MyClientSocket() {
		try {
			socket = new Socket("127.0.0.1", 20000);// 127.0.0.1 = ip �ּ��� 10000�� ��Ʈ�� ����ϴ� ���α׷��� �����϶�

			// Ű����κ��� �Է� �޾Ƽ�
			Scanner sc = new Scanner(System.in);
			// ���Ͽ� ���۴ޱ�(����)
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

			while (true) {
				String input = sc.nextLine();// ���ڿ� �ޱ�
				// ������ ����
				bw.write(input + "\n"); // �������� �ϸ� ����
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
