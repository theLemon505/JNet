package com.company;

import com.company.ClientCode.Client;
import com.company.ServerCode.Server;

import java.io.IOException;
import java.net.DatagramSocket;

public class Interface {
    private Server serveri;
    public boolean hostAvailabilityCheck(int port) {
        try (DatagramSocket s = new DatagramSocket(port)) {
            return true;
        } catch (IOException ex) {
            /* ignore */
        }
        return false;
    }
    public Server CreateServer( int port){
        Server server = new Server(port);
        serveri = server;
        return server;
    }
    public void send(Server server, String message) {
        server.updateClients(message);
    }
    public Client joinServer(String ip, int port){

        Client client = new Client(ip, port);
        return client;
    }
}
