package hr.project.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class STOMPConnectEventListener implements ApplicationListener<SessionConnectEvent> {

    @Autowired
    ListeningGameQuestionRegistry listeningGameQuestionRegistry;

    @Autowired
    WebAgentSessionRegistry webAgentSessionRegistry;

    @Override
    public void onApplicationEvent(SessionConnectEvent event) {
        TestPrincipal user = (TestPrincipal) event.getMessage().getHeaders().get("simpUser");
        String session = event.getMessage().getHeaders().get("simpSessionId").toString();

        /** add new session to registry */
        webAgentSessionRegistry.addSession(session, user.getName());

        //debug: show connected to stdout
        webAgentSessionRegistry.show();
    }

    @EventListener
    public void onSessionDisconnect(SessionDisconnectEvent event) {
        TestPrincipal user = (TestPrincipal) event.getMessage().getHeaders().get("simpUser");
        String session = event.getMessage().getHeaders().get("simpSessionId").toString();

        webAgentSessionRegistry.removeUser(user.getName());

        webAgentSessionRegistry.show();
    }

    @EventListener
    public void onSessionSubscribe(SessionSubscribeEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String destination = headerAccessor.getMessageHeaders().get("simpDestination").toString();
        String session = headerAccessor.getMessageHeaders().get("simpSessionId").toString();

        TestPrincipal user = (TestPrincipal) event.getMessage().getHeaders().get("simpUser");

        String[] myData = destination.split("/");

        if (myData[1].equals("user") && myData[2].equals("queue") && myData[3].equals("question")){
            List<String> owner = headerAccessor.getNativeHeader("owner");
            listeningGameQuestionRegistry.addUserToRoom(owner.get(0), user.getName());
            listeningGameQuestionRegistry.show();
        }
    }

    @EventListener
    public void onSessionUnsubscribe(SessionUnsubscribeEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
    }
}