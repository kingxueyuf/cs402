package webserver;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer extends Thread {

    private int port;

    public static void main(String[] args) {
        WebServer server = new WebServer(88);
        server.start();
    }

    private void log(String msg) {
        System.out.println(msg);
    }

    public WebServer(int listen_port) {
        port = listen_port;
    }

    private void logStack(Exception e) {
        for (StackTraceElement el : e.getStackTrace()) {
            log(el.toString());
        }
    }

    public void run() {
        ServerSocket serverSocket = null;
        log("Starting HTTP Server");

        try {
            log("Binding to port " + Integer.toString(port) + " on local host.\n");
            serverSocket = new ServerSocket(port);
        } catch (Exception e) {
            log("Fatal Error:" + e.getMessage());
            return;
        }

        log("Successful bind.\n");
        while (true) {
            log("Ready, Waiting for requests...\n");
            try {
                Socket connection = serverSocket.accept();
                InetAddress client = connection.getInetAddress();
                log(client.getHostName() + " connected to server.\n");
                DataOutputStream output = null;
                try {
                    BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    ByteArrayOutputStream fout = new ByteArrayOutputStream();
                    output = new DataOutputStream(fout);
                    WebRequest request = WebRequestFactory.createRequest(input);
                    WebRequestHandler handler = new WebRequestHandler();
                    handler.handleRequest(request, output);
                    output.close();
                    byte[] data = fout.toByteArray();
                    for (byte b : data) {
                        connection.getOutputStream().write(b);
                        log("" + (char) b);
                    }
                    connection.close();
                } catch (Exception internalException) {
                    try {                       
                        output.writeBytes("HTTP:/1.1 500 ERROR\n");
                        output.writeBytes("Content-type: text/plain\n");
                        output.writeBytes("Content-length: " + internalException.getMessage().length() + "\n");
                        output.writeBytes("\n");
                        output.writeBytes(internalException.getMessage());
                        output.close();
                        logStack(internalException);
                    } catch (Exception e2) {
                        logStack(e2);
                    }
                }
            } catch (Exception e) {
                logStack(e);
            }
        }
    }
}
