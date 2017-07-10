package hr.project;

import hr.project.model.User;

import java.util.ArrayList;
import java.util.List;

public class ActiveUserStore {

    private List<User> users;

    public ActiveUserStore() {
        users = new ArrayList<User>();
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}
