package com.company.ServerCode;

import com.company.ClientCode.ClientObject;

import javax.swing.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class Server {
    public int getPort() {
        return port;
    }

    public boolean isOnline() {
        return online;
    }

    public String getMessage() {
        String message = lastMessage;
        lastMessage = null;
        return message;
    }

    public ArrayList<ClientObject> getClients() {
        return clients;
    }

    private DatagramSocket socket;
    private int port;
    private int clientID = 999;
    private boolean online;
    private String lastMessage;
    private ArrayList<ClientObject> clients = new ArrayList<ClientObject>();


    public Server(int port){
        try{
            this.port = port;
            socket = new DatagramSocket(port);
            online = true;

            recieve();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void recieve(){
                try{
                    System.out.println(clients.size());
                    byte[] rawData = new byte[1024];
                    DatagramPacket packet = new DatagramPacket(rawData, rawData.length);
                    socket.receive(packet);

                    String message = new String(rawData);
                    message = message.substring(0, message.indexOf("\\e"));
                    if (!parseCommand(message, packet)) {
                        lastMessage = message;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    public void Send(String message, ClientObject client){
        try {
            message = message + "\\e";
            byte[] data = message.getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getByName(client.getAddress()), client.getPort());
            socket.send(packet);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void updateClients(String message){
        for(int i = 0; i < clients.size(); i++){
            Send(message, clients.get(i));
        }
    }
    public  boolean parseCommand(String message, DatagramPacket packet){
        if(message.startsWith("\\c")){
            ClientObject client = new ClientObject(packet.getAddress().toString(), packet.getPort(), clientID+1);
            clients.add(client);
            Send("\\cid:" + client.getId(), client);
            clientID++;
            return true;
        }
        else if(message.startsWith("\\d:")){
            int id  = Integer.parseInt(message.substring(3));
            for(int i = 0; i < clients.size(); i++) {
                if (clients.get(i).getId() == id) {
                    clients.remove(clients.get(i));
                    return true;
                }
            }
            System.out.println("Server was unable to find client: "+id);
        }
        return false;
    }
}
