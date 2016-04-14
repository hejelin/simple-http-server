import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class HttpResponse {

	HttpRequest request;
	int responseCode;
	private String filename = "", root = "";
	private ArrayList<byte[]> fileContent;
	
	public HttpResponse(HttpRequest request, String path) {
		this.request = request;
		this.root = path;
		this.fileContent = new ArrayList<byte[]>();
		System.out.println(request.filename);
		
		if (request.filename != null && validPath(request.filename)) {
			if (request.filename != null && request.filename.length() > 0) {
				File f = new File(this.root, request.filename);
				this.filename = request.filename;
				
				try {
					FileInputStream fis = new FileInputStream(f);
					
					byte[] temp = new byte[1];
					while (fis.read(temp) == 1) {
						fileContent.add(temp);
						temp = new byte[1];
					}
					fis.close();
					
					responseCode = 200;
				} catch (FileNotFoundException e) {
					responseCode = 404;
				} catch (IOException e) {
					responseCode = 500;
				}
			} else {
				responseCode = 400;
			}
		} else {
			responseCode = 403;
		}
	}
	
	private boolean validPath(String s) {
		String[] temp = s.split("/");
		
		int j = 0;
		
		for (int i = 0; i < temp.length; i++) {
			if (temp[i].equals("..")) {
				j--;
			} else if (temp[i].length() != 0) {
				j++;
			}
			
			if (j < 0) {
				return false;
			}
		}
		return true;
	}

	public String getHeaders() {
		String response;
		response = "HTTP/1.0 " + responseCode + " \r\n";
		response += "Server: Our Java Server/1.0 \r\n";
		response += "Content-Type: " + getContentType() + " \r\n";
		response += "Connection: close \r\n";
		response += "Content-Length: " + fileContent.size() + "\r\n";
		response += "\r\n";
		
		return response;
	}
	
	public byte[] getContent() {
		byte[] temp = new byte[fileContent.size()];
		for (int i = 0; i < fileContent.size(); i++) {
			temp[i] = fileContent.get(i)[0];
		}
		return temp;
	}
		
	private String getContentType() {
		String[] temp = this.filename.split("\\.");
		
		switch(temp[temp.length - 1]) {
		case ("txt") : 
			return "text/plain"; 
		case ("html") : 
			return "text/html"; 
		case ("gif") : 
			return "image/gif";
		case ("jpeg") : 
			return "image/jpeg";
		case ("jpg") : 
			return "image/jpeg";
		case ("css") : 
			return "text/css";
		case ("js") : 
			return "text/javascript"; 
		}
		return "text/plain";
	}
}
