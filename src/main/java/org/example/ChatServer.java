package org.example;

import org.example.model.ChatImpl;
import org.example.model.ChatInt;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

public class ChatServer {
    public static void main(String[] args) {
        try {
            System.setProperty("java.security.policy", "security.policy");

            if(System.getSecurityManager() == null){
                System.setSecurityManager(new SecurityManager());
            }
            System.setProperty("java.rmi.server.hostname", "82.139.136.125");
            System.setProperty("java.rmi.server.codebase", "file:\\C:\\Users\\Konrad\\IdeaProjects\\RMIServer");
            LocateRegistry.createRegistry(1099);

            Scanner s = new Scanner(System.in);
            System.out.println("Enter Your name and press Enter:");
            String name = s.nextLine().trim();

            ChatImpl server = new ChatImpl(name);
            Naming.rebind("//82.139.136.125/ABC", server);
            System.out.println("[System] Chat Remote Object is ready:");

            while(true){
                String message = s.nextLine().trim();
                if(server.getClient() != null){
                    ChatInt client = server.getClient();
                    message = "[" + server.getName() + "]: " + message;
                    client.send(message);
                }
            }

        } catch(RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }
    }
}