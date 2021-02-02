package com.company.ClientCode;

import com.company.ServerCode.Server;

public class ClientObject {
    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }
    private Server server;
    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public int getId() {
        return id;
    }

    private String address;
    private int port;
    private int id;

    public ClientObject(String address, int port, int id){
        this.address = address;
        this.port = port;
        this.id = id;
    }
}
