package hr.project.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


public class ListeningGameQuestionRegistry {

    private String roomGame, user1, user2;
    private ConcurrentHashMap<String, List<String>> roomMap;

    public ListeningGameQuestionRegistry(){
        this.roomMap = new ConcurrentHashMap<>();
    }

    public void addUserToRoom(String roomGame, String user){
        List<String> list;
        String user1="", user2="";

        if(roomMap.containsKey(roomGame)){
            list = roomMap.get(roomGame);
        }else{
            list = new ArrayList<String>();
            roomMap.put(roomGame, list);
        }

        if(!list.contains(user)) {
            list.add(list.size(), user);
        }

    }

    public ConcurrentHashMap<String, List<String>> getRoomMap() {
        return roomMap;
    }

    public void show(){
        System.out.println("User Listening");
        for (String i : this.roomMap.keySet()) {
            System.out.println("Room: "+ i + " Users: " + roomMap.get(i));
        }
    }

    public void removeUsersFromRoom(String roomGame) {
        roomMap.remove(roomGame);
    }

}
