package org.example.server;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.logging.Logger;


@ServerEndpoint(value = "/server")
public class WebServerEndpoint {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @OnOpen
    public void onOpen(Session session){
      logger.info("Connected..."+ session.getId());
  }

    @OnMessage
    public void onMessage(Session session, String msg) {

        try {
            for (Session session1 : session.getOpenSessions()) {
                if (session1.isOpen())
                    session1.getBasicRemote().sendText("Message from server" + msg + " " + session1.getId());
            }
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        logger.info(String.format("Session %s closed because of %s", session.getId(), closeReason));
    }

    @OnError
    public void error(Session session, Throwable error) {
        logger.info("ERROR");
        logger.info("SessionId " + session.getId());
        logger.info("ErrorMsg " + error.getMessage());
    }
}
