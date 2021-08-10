import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Scanner;


public class Server {
	private static final File _configurationFile = new File("./Configuration.json");

	public static void main(String[] args) {
		new Thread(() -> {
			while (true){
				getServerSocket(Integer.parseInt(getConfig("port")));
			}
		}).start();
	}

	// Get the server configuration from the file
	private static String getConfig(String key) {
		JSONParser parser = new JSONParser();
		Hashtable<String, String> ht = new Hashtable<>();
		try {
			JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(_configurationFile));
			ht.put("port", (String) jsonObject.get("port"));
			ht.put("server", (String) jsonObject.get("server"));
		} catch (Exception e) {
			terminalLog("ERROR", e.toString());
		}
		return ht.get(key);
	}

	// Initiate the server socket connection.
	private static void getServerSocket(int PORT_NO) {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(PORT_NO);
			terminalLog("INFO", "New server socket created");
		} catch (Exception e) {
			terminalLog("ERROR", e.toString());
		}
		connectToSocket(serverSocket, PORT_NO);
	}

	// Connect to the initiated socket.
	public static void connectToSocket(ServerSocket serverSocket, int PORT_NO) {
		try {
			Socket conn = serverSocket.accept();
			getRequest(conn);
		} catch (Exception e) {
			terminalLog("ERROR", e.toString());
		}
	}

	private static void getRequest(Socket conn) {
		try {
			PrintStream printStream;
			String url, filePath, pagePath, serverLocation;
			Scanner scannedInputStream = new Scanner(conn.getInputStream());
			while (scannedInputStream.hasNextLine()) {
				url = scannedInputStream.nextLine();
				if (!url.trim().equals("")) {
					if (url.contains("GET")) {
						int pageName = url.indexOf("HTTP");
						pagePath = url.substring(3, pageName - 1).trim();
						try {
							serverLocation = getConfig("server");
							filePath = serverLocation + pagePath;
							File loadedFile = new File(filePath);
							Client client = new Client();
							printStream = new PrintStream(conn.getOutputStream());
							client.serve(loadedFile, printStream);
							conn.close();
						} catch (FileNotFoundException e) {
							terminalLog("ERROR", e.toString());
						}
					}
					else {
						terminalLog("ERROR", "Not a GET request");
					}
				}
				else {
					break;
				}
			}
			conn.close();
			terminalLog("INFO", "Connection closed");
		} catch (Exception e) {
			terminalLog("ERROR", e.toString());
		}
	}

	// Print the log messages in the terminal.
	public static void terminalLog(String type, String msg) {
		System.out.println(type + ":\t" + msg + ".");
	}
}