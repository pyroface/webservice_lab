package x.snowroller;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import x.snowroller.fileutils.FileReader;
import x.snowroller.models.Todo;
import x.snowroller.models.Todos;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Data
public class ServerExample {

    public static void main(String[] args) {

        //Build with:
        // mvn package
        //Open Terminal and do:
        // cd core\target
        //run this line in the terminal
        //java --module-path core-1.0-SNAPSHOT.jar;modules -m core/x.snowroller.ServerExample

        ExecutorService executorService = Executors.newCachedThreadPool();

        try {
            //TCP/IP
            ServerSocket serverSocket = new ServerSocket(5050);
            System.out.println(Thread.currentThread());

            while (true) {
                Socket socket = serverSocket.accept();
                executorService.execute(() -> handleConnection(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleConnection(Socket socket) {
        System.out.println(Thread.currentThread());
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            Request request = readHeaders(input);

            UserDAO pdao = new UserDAOWithJPAImpl();

            if( request.requestedType.equals("POST") && request.requestedUrl.equals("/upload") ){
                //reads the Body
                char[] body = new char[request.length];
                input.read(body);
                String bodyText = new String(body);

                System.out.println(bodyText);
                String[] arrOfStr = bodyText.split("[= & ? +]");

                System.out.println("Created!");
                User user = new User("1", arrOfStr[1], arrOfStr[3],"-", "-", "-", "-");
                pdao.create(user);

            }

            var output = new PrintWriter(socket.getOutputStream());

            //File file = new File("web" + File.separator + "index.html");
            File file = new File("web" + File.separator + request.requestedUrl);
            byte[] page = FileReader.readFromFile(file);

            String contentType = Files.probeContentType(file.toPath());

            output.println("HTTP/1.1 200 OK");
            output.println("Content-Length:" + page.length);
            output.println("Content-Type:"+contentType);
            output.println("");
            output.flush();

            //Handling missing page - 404
            if(page.length == 0){
                output.println("HTTP/1.1 404 OK");
                output.println("Content-Length:" + page.length);
                output.println("Content-Type:"+contentType);
                output.println("");
                output.flush();
            }

            var dataOut = new BufferedOutputStream(socket.getOutputStream());
            dataOut.write(page);
            dataOut.flush();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Request readHeaders(BufferedReader input) throws IOException {
        String requestedUrl = "";

        var request = new Request();

        while (true) {
            String headerLine = input.readLine();
            if( headerLine.startsWith("GET") || headerLine.startsWith("POST") || headerLine.startsWith("HEAD")){
                request.requestedType = headerLine.split(" ")[0];
                requestedUrl = headerLine.split(" ")[1];
                request.requestedUrl = requestedUrl.split("[?]")[0];
            }

            if(headerLine.startsWith("Content-Length: ")){
                request.length = Integer.parseInt(headerLine.split(" ")[1]);
            }

            System.out.println(headerLine);
            if (headerLine.isEmpty()) {
                break;
            }
        }
        return request;
    }
}

