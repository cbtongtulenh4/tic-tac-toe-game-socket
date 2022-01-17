package javakit.minhphuc.caro.server.controller;

import javakit.minhphuc.util.StreamData;

import java.util.ArrayList;
import java.util.List;

public class ClientManage {
    public static List<Client> clients = new ArrayList<>();

    public ClientManage(){

    }


    public static void addClient(Client client){
        if(!clients.contains(client)){
            clients.add(client);
        }
    }

    public static void removeClient(Client client){
        if(clients.contains(client)){
            clients.remove(client);
        }
    }

    public static Client findClientMatch(Client client){
//        getAllByName();
//        System.out.println();
        for (Client c : clients){
//            try{
//                if(c != client && c.getBase().getResult().
//                        equals(StreamData.Types.FIND_MATCH.name()) )
//                {
//                    return c;
//                }
//            } catch(NullPointerException e){
//                continue;
//            }
            if(c != client && c.isFindingMatch()){
                return c;
            }
        }
        return null;
    }

    public static Boolean checkOnline(String email){
        for (Client c : clients){
            String playerEmail = c.getPlayer().getEmail();
            if(playerEmail != null && playerEmail.equals(email))
                return true;
        }
        return false;
    }

    public static void getAllByName(){
        for (Client c : clients){
            System.out.println(c.getPlayer().getName() + "   -   " +  c.getPlayer().getType());
        }
    }

}
