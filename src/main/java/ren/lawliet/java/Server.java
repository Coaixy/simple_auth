package ren.lawliet.java;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class Server extends WebSocketServer{
    //免登录
    HashMap<String,String> tokens = new HashMap<String,String>();

    Server(int port) throws UnknownHostException{
        super(new InetSocketAddress(port));
    }
    @Override
    public void onClose(WebSocket arg0, int arg1, String arg2, boolean arg3) {
        System.out.println(arg0+"离开");
    }

    @Override
    public void onError(WebSocket arg0, Exception arg1) {
        arg1.printStackTrace();
    }

    @Override
    public void onMessage(WebSocket ws, String msg) {
        var route = ws.getResourceDescriptor();
        System.out.println(ws.getRemoteSocketAddress().getAddress()+"-"+ws.getRemoteSocketAddress().getPort()+"-"+msg);
        var data = msg.split("&");
        switch(route){
            case "/reg":{
                if(data.length == 2){
                    try {
                        System.out.println(Db.getPwd(data[0]));
                        if (Db.getPwd(data[0]) != "") {
                            ws.send("false");
                        }else{
                            ws.send("true");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            case "/login":{
                
            }
        }
    }

    @Override
    public void onOpen(WebSocket arg0, ClientHandshake arg1) {}

    @Override
    public void onStart() {
        try {
            Db.init();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("服务启动成功");
    }
    
}
