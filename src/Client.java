import java.awt.*;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;

public class Client {
	// Serve the request
	public void serve(File fileObj, PrintStream ps) {
		DataInputStream din;
		try {
			try {
				FileInputStream a = new FileInputStream(fileObj);
				din = new DataInputStream(a);
				ps.println("HTTP:/1.0 200 OK\nContent-type: text\nContent-length: " + (int) fileObj.length());
				int file_len = (int) fileObj.length();
				byte[] buffer = new byte[file_len];
				din.readFully(buffer);
				ps.write(buffer, 0, file_len);
				din.close();
				Desktop.getDesktop().browse(fileObj.toURI());
			} catch (Exception e) {
				NotFound notFound = new NotFound();
				notFound.show404Error(ps);
			}
			ps.flush();
		} catch (Exception e) {
			terminalLog("ERROR", e.toString());
		}
	}

	// Print the log messages in the terminal.
	public static void terminalLog(String type, String msg) {
		System.out.println(type + ":\t" + msg + ".");
	}

	// Handle the page not found error
	public static class NotFound {
		public void show404Error(PrintStream ps) {
			try {
				String str = "HTTP:/1.0 404 Not Found\nContent-type: text/html\n";
				byte[] buffer = str.getBytes();
				ps.write(buffer);
			} catch (Exception e) {
				terminalLog("ERROR", e.toString());
			}
		}
	}
}