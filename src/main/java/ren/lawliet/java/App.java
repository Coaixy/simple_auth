package ren.lawliet.java;

import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;

public class App 
{
    public static void main( String[] args ) throws UnknownHostException
    {
        int port = 8080;
        Server server = new Server(8080);
        server.start();
    }
}
