package hr.project.util;

import hr.project.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class WebAgentSessionRegistry {

    private String session_id, agent_id;
    private ConcurrentHashMap<String, String> concurrentMap;

    public WebAgentSessionRegistry(){
        this.concurrentMap = new ConcurrentHashMap<>();
    }

    public void addSession(String agent_id, String session_id){
        this.concurrentMap.put(session_id, agent_id);
    }

    public void show(){
        System.out.println("Session user list");
        for (String i : this.concurrentMap.keySet()) {
            System.out.println("Session: "+ concurrentMap.get(i) + " User: " + i);
        }
    }

    public String getSession_id(String user) {
        return concurrentMap.get(user);
    }

    public ConcurrentHashMap<String, String> getConcurrentMap() {
        return concurrentMap;
    }

    public void removeUser(String session) {
        concurrentMap.remove(session);
    }

}
