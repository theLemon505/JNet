package com.company.ClientCode;

import com.company.ServerCode.Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {

    private DatagramSocket socket;

    public String getMessage() {
        String message = lastMessage;
        lastMessage = null;
        return message;
    }

    public int getClientID() {
        return clientID;
    }

    private InetAddress serverAddress;
    private int port;
    private boolean online;
    private String lastMessage;
    private int clientID;
    public Client(String address, int port){
        try{
            this.serverAddress = InetAddress.getByName(address);
            this.port = port;
            this.socket = new DatagramSocket();
            this.online = true;
            Send("\\c");
            recieve();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void recieve(){
        Thread thread = new Thread() {
            public void run() {
        while (online) {
            try {
                byte[] rawData = new byte[1024];
                DatagramPacket packet = new DatagramPacket(rawData, rawData.length);
                socket.receive(packet);

                String message = new String(rawData);
                message = message.substring(0, message.indexOf("\\e"));
                if (!parseCommand(message)) {
                    lastMessage = message;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
            }
        }; thread.start();
    }
    public  boolean parseCommand(String message){
        if(message.startsWith("\\cid:")){
            this.clientID = Integer.parseInt(message.substring(5));
            return true;
        }
        return false;
    }
    public void Send(String message){
        try {
            message = message + "\\e";
            byte[] data = message.getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress, port);
            socket.send(packet);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
