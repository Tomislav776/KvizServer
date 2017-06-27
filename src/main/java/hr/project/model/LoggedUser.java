package hr.project.model;

import hr.project.ActiveUserStore;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoggedUser {

    private ActiveUserStore activeUserStore;

    public LoggedUser(ActiveUserStore activeUserStore) {
        this.activeUserStore = activeUserStore;
    }

    public LoggedUser() {}

    public void setLoggedIn(User user) {
        List<String> users = activeUserStore.getUsers();
        if (!users.contains(user.getName())) {
            users.add(user.getName());
        }
    }

    public void setLoggedOut(User user) {
        List<String> users = activeUserStore.getUsers();
        if (users.contains(user.getName())) {
            users.remove(user.getName());
        }
    }

    public ActiveUserStore getActiveUserStore() {
        return activeUserStore;
    }

    public void setActiveUserStore(ActiveUserStore activeUserStore) {
        this.activeUserStore = activeUserStore;
    }
}
