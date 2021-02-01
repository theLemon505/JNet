package com.company.ClientCode;

public class ClientObject {
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
