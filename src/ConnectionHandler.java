import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

public class ConnectionHandler implements Runnable {
	
	Socket s;
	BufferedReader br;
	String path;
	
	public ConnectionHandler(Socket s, String path) throws Exception {
		this.s = s;
		this.br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		this.path = path;
	}
	
	@Override
	public void run() {
		try {
			String reqString = "";
			
			while(br.ready() || reqString.length() == 0) {
				reqString += (char) br.read();
			}
			
			HttpRequest req = new HttpRequest(reqString);
			HttpResponse res = new HttpResponse(req, path);
			
			s.getOutputStream().write(res.getHeaders().getBytes());
			s.getOutputStream().write(res.getContent());
			br.close();
			s.close();
		} catch (SocketException e) {
			// Connection broken. Tough luck.
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
