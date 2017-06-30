package hr.project;

import hr.project.util.TestPrincipal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSocket
@EnableWebSocketMessageBroker
class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue", "/exchange");
//        config.enableStompBrokerRelay("/topic", "/queue", "/exchange"); // Uncomment for external message broker (ActiveMQ, RabbitMQ)
        config.setApplicationDestinationPrefixes("/topic", "/queue"); // prefix in client queries
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws", "/activeUsers").setHandshakeHandler(new UserHandler()).setAllowedOrigins("*").withSockJS();
    }

    class UserHandler extends DefaultHandshakeHandler {

        @Override
        protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
            String user = request.getHeaders().get("user").get(0);
            return new TestPrincipal(user);
        }
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.setMessageSizeLimit(8 * 1024);
    }


    @Override
    public void configureClientInboundChannel(ChannelRegistration channelRegistration) {
    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration channelRegistration) {
    }

    @Override
    public boolean configureMessageConverters(List<MessageConverter> converters) {
        return true;
    }

}
