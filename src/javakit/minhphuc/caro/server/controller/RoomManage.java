package javakit.minhphuc.caro.server.controller;

import java.util.*;

public class RoomManage {

    static List<Room> rooms = new ArrayList<>();

    public static Room addRoom(String id){
        Room room = new Room(id);
        rooms.add(room);
        return room;
    }

    public static Room getRoom(String id){
        for (Room r : rooms){
            if(r.id.equals(id)){
                return r;
            }
        }
        return null;
    }

}
