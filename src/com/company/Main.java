package com.company;

import com.company.ClientCode.Client;
import com.company.ServerCode.Server;

public class Main {
    private static Interface inter = new Interface();
    private static Client client;
    private static Server server;
    public static void main(String[] args){
        if(inter.hostAvailabilityCheck(2404) == true) {
            server = inter.CreateServer(2404);
        }
        else {
            System.out.println("starting client on server");
            client = inter.joinServer("localhost", 2404);
            System.out.println("starting client on server");
        }
    }
}
