## Project idea

A small webserver that takes localhost requests on a pre-defined port, predefined location, and server the requested file back to the client(user).
If the file does not exist, it will return status code 404, and the page can not be found.



## To run the server

1. Edit the "Configuration.json" file located in the project folder to include the server location also the port to which the server should listen.

   ```json
   # Sample of the config.
   {
     "server": "/Users/mustafa.azzawie/Documents/test_we_server",
     "port": "3333"
   }
   ```

2. Run the main method found in the `server.java` file.

3. The server will keep listed to the port defined in the config. File, and it will not stop unless the user hard stops the server.



## Notes

- Lambda expression: located in the `server.java` file inside the main method while creating a new thread.
- Thread: located in the `server.java` file inside the main method.
- Inner class: located in `client.java` file and named as `NotFound`.
- Used the `org.json.simple` library to retrieve the server configurations from the configuration file.
- Used the `hash table` for the server configuration.
- Used `java.awt.*` library for displaying the file if we find it.



## List of methods

* ***Server methods...***

    1. `public static void terminalLog(String type, String msg)`
       Takes string for the log message type and String for the message which is meant to be logged.

    2. `public static void main(String[] args)`
       Start the web server by calling this method which will create a new thread.

    3. `private static String getConfig(String key)`
       Takes String as a key to retrieve the value from the configuration file.

    4. `private static void getServerSocket(int PORT_NO)`
       Takes an integer as a port and establishes new Server Socket.

    5. `public static void connectToSocket(ServerSocket serverSocket, int PORT_NO)`
       Takes a server socket and integer as the port to accept the server socket connection.

    6. `private static void getRequest(Socket conn)`
       Takes a socket, checks if the call is a GET request, and gets the requested page name.

* ***Client methods…***

    1. `public void serve(File fileObj, PrintStream ps)`
       Takes a file, print stream, and does not return anything, this method will serve the file to the client(user).
    2. `public static void terminalLog(String type, String msg)`
       Takes a string for the log message type and a String for the message meant to be logged.

    3. `public void show404Error(PrintStream ps)` (Located inside the `NotFound` inner class)
       Takes a print stream, will set the page status code to 404, and the content type to text/html.