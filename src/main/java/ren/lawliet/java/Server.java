package ren.lawliet.java;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class Server extends WebSocketServer {
    // 免登录
    HashMap<String, WebSocket> tokens = new HashMap<String, WebSocket>();

    Server(int port) throws UnknownHostException {
        super(new InetSocketAddress(port));
    }

    @Override
    public void onClose(WebSocket arg0, int arg1, String arg2, boolean arg3) {
        System.out.println(arg0 + "离开");
    }

    @Override
    public void onError(WebSocket arg0, Exception arg1) {
        arg1.printStackTrace();
    }

    private String makeToken() throws NoSuchAlgorithmException {
        String token = Db.makeToken();
        Boolean flag = true;
        while (flag) {
            Boolean temp = flag;
            for (String t : tokens.keySet()) {
                if (t.equals("token")) {
                    temp = false;
                }
            }
            if (temp) {
                flag = false;
            } else {
                token = Db.makeToken();
            }
        }
        return token;
    }

    @Override
    public void onMessage(WebSocket ws, String msg) {
        var route = ws.getResourceDescriptor();
        System.out.println(
                ws.getRemoteSocketAddress().getAddress() + "-" + ws.getRemoteSocketAddress().getPort() + "-" + msg);
        var data = msg.split("&");
        switch (route) {
            case "/reg": {
                if (data.length == 2) {
                    try {
                        System.out.println(Db.getPwd(data[0]));
                        if (!Db.getPwd(data[0]).equals("")) {
                            ws.send("false");
                        } else {
                            ws.send("true");
                            Db.addData(data[0] + "-" + Db.md5(data[1]));
                            tokens.put(makeToken(), ws);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
            case "/login": {
                if (data.length == 2) {
                    try {
                        if (Db.getPwd(data[0]).equals(Db.md5(data[1]))) {
                            ws.send("true");
                            tokens.put(makeToken(), ws);
                        } else {
                            ws.send("false");
                        }
                    } catch (NoSuchAlgorithmException | IOException e) {
                    }
                }
                break;
            }
            default: {
            }
        }
    }

    @Override
    public void onOpen(WebSocket arg0, ClientHandshake arg1) {
    }

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
