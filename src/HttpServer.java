import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

	ServerSocket serverSocket;
	
	public static void main(String[] args) throws Exception {
		if (args.length == 2) {
			if (Integer.parseInt(args[0]) < 65536 && Integer.parseInt(args[0]) > 1023) {
				new HttpServer().runServer(args[0], args[1]);
			} else {
				System.err.println("Use port 1024-65535");
				System.exit(0);
			}
		} else {
			System.err.println("Start program with java HttpServer port filepath");
			System.exit(0);
		}
	}
	
	public void runServer(String port, String path) throws Exception {
		System.out.println("Starting server");
		serverSocket = new ServerSocket(Integer.parseInt(port));
		
		acceptRequests(path);
	}
	
	private void acceptRequests(String path) throws Exception {
		while (true) {
			Socket s = serverSocket.accept();
			ConnectionHandler ch = new ConnectionHandler(s, path);
			
			ch.run();
		}
	}

}
