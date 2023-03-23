package ren.lawliet.java;

import java.net.UnknownHostException;

public class App 
{
    public static void main( String[] args ) throws UnknownHostException
    {
        int port = 8080;
        Server server = new Server(8080);
        server.start();
        System.out.println("测试账号:root"+"\n"+"测试密码:"+"root");
    }
}
