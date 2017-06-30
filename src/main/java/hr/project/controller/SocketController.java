package hr.project.controller;

import hr.project.model.*;
import hr.project.repository.AnswerRepository;
import hr.project.repository.QuestionRepository;
import hr.project.util.ListeningGameQuestionRegistry;
import hr.project.model.Session;
import hr.project.util.WebAgentSessionRegistry;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.security.Principal;
import java.util.List;

@Log4j
@RestController
public class SocketController {

    private int counter = 0;

    @PersistenceContext
    private EntityManager em;

    private org.hibernate.Session configurarSession() {
        return  em.unwrap(org.hibernate.Session.class);
    }

    private  QuestionRepository questionRepository;
    private  AnswerRepository answerRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    WebAgentSessionRegistry webAgentSessionRegistry;

    @Autowired
    ListeningGameQuestionRegistry listeningGameQuestionRegistry;


    @MessageMapping("/question/{username}")
    @Transactional
    public void gameGetQuestions(String message, Principal principal) {
        String strExamid;
        String owner;

        String[] myData = message.split("/");
        for (String s: myData) {
            System.out.println(s);
        }

        strExamid = myData[1].replace("\n", "");
        int examId = Integer.valueOf(strExamid);
        owner = myData[0];


        if(listeningGameQuestionRegistry.getRoomMap().get(owner).size() != 2) {
            return;
        }

        List questions = configurarSession().createQuery("select o from Question o where o.exam_id = :examId  order by rand()")
                .setParameter("examId", examId)
                .setMaxResults(10)
                .list();

        QuestionsSTOMP questionsStomp = new QuestionsSTOMP(questions);

        String s_id1 = webAgentSessionRegistry.getSession_id(listeningGameQuestionRegistry.getRoomMap().get(owner).get(0));
        String s_id2 = webAgentSessionRegistry.getSession_id(listeningGameQuestionRegistry.getRoomMap().get(owner).get(1));

        simpMessagingTemplate.convertAndSendToUser(s_id1,"/queue/question/", questionsStomp, createHeaders(s_id1));
        simpMessagingTemplate.convertAndSendToUser(s_id2,"/queue/question/", questionsStomp, createHeaders(s_id2));

        listeningGameQuestionRegistry.removeUsersFromRoom(owner);
    }


    @MessageMapping("/gameInvite")
    public void gameInvite(Principal principal, String recipient) {
        String reply = "hello " + principal.getName();
        System.out.println("sending " + reply);

        String[] myData = recipient.split("/");
        for (String s: myData) {
            System.out.println(s);
        }

        recipient = myData[0].replace("\n", "");

        String s_id = webAgentSessionRegistry.getSession_id(recipient);

        simpMessagingTemplate.convertAndSendToUser(s_id,"/queue/invite", new Session(s_id,principal.getName(), myData[1].replace("\n", "")), createHeaders(s_id));
    }

    private MessageHeaders createHeaders(String sessionId) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();
    }
}
