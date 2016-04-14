public class HttpRequest {
	String filename;
	
	public HttpRequest(String request) {		
		String lines[] = request.split("\n");
		
		if (lines.length > 0) {
			lines = lines[0].split(" ");
			if (lines.length > 1) {
				filename = lines[1];
			}
		}
	}
}