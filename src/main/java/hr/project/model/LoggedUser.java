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
        List<User> users = activeUserStore.getUsers();
        boolean check = true;

        for (int i = 0 ; i< users.size(); i++){
            if (users.get(i).getEmail().equals(user.getEmail())) {
                check = false;
                break;
            }
        }

        if ( check ){
            users.add(user);
        }
    }

    public void setLoggedOut(User user) {
        List<User> users = activeUserStore.getUsers();

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(user.getEmail())) {
                users.remove(user);
                break;
            }
        }

    }

    public ActiveUserStore getActiveUserStore() {
        return activeUserStore;
    }

    public void setActiveUserStore(ActiveUserStore activeUserStore) {
        this.activeUserStore = activeUserStore;
    }
}
