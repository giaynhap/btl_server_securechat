package com.giaynhap.objects;

import com.giaynhap.model.Users;
import com.giaynhap.manager.UserManager;
import com.google.gson.JsonObject;
import org.kurento.client.*;
import org.kurento.jsonrpc.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class UserSession implements Closeable {

    private static final Logger log = LoggerFactory.getLogger(UserManager.class);
    private WebSocketSession session;
    private MediaPipeline pipeline;
    private WebRtcEndpoint outgoingMedia;
    private final ConcurrentMap<String, WebRtcEndpoint> incomingMedia = new ConcurrentHashMap<>();
    private Users info;
    public ThreadCall thread;

    public UserSession(Users user, final WebSocketSession session,
                       MediaPipeline pipeline){
        this.info = user;
        this.pipeline = pipeline;
        this.session = session;

        this.outgoingMedia = new WebRtcEndpoint.Builder(pipeline).build();
        this.outgoingMedia.addIceCandidateFoundListener(new EventListener<IceCandidateFoundEvent>() {
            @Override
            public void onEvent(IceCandidateFoundEvent event) {
                JsonObject response = new JsonObject();
                response.addProperty("id", "iceCandidate");
                response.addProperty("uuid", UserSession.this.info.getUUID());
                response.add("candidate", JsonUtils.toJsonObject(event.getCandidate()));
                try {
                    synchronized (session) {
                        session.sendMessage(new TextMessage(response.toString()));
                    }

                } catch (IOException e) {
                    log.debug(e.getMessage());
                }

            }
        });

    }


    public WebRtcEndpoint getOutgoingWebRtcPeer() {
        return outgoingMedia;
    }

    public WebSocketSession getSession() {
        return session;
    }


    public void receiveVideoFrom(UserSession sender, String sdpOffer) throws IOException {

        final String ipSdpAnswer = this.getEndpointForUser(sender).processOffer(sdpOffer);
        final JsonObject scParams = new JsonObject();
        scParams.addProperty("id", "receiveVideoAnswer");
        scParams.addProperty("uuid", sender.info.getUUID());
        scParams.addProperty("sdpAnswer", ipSdpAnswer);
        this.sendMessage(scParams);
        this.getEndpointForUser(sender).gatherCandidates();
    }

    private WebRtcEndpoint getEndpointForUser(final UserSession sender) {
        if (sender.info.getUUID().equals(this.info.getUUID())) {
            return outgoingMedia;
        }

        WebRtcEndpoint incoming = incomingMedia.get(sender.info.getUUID());
        if (incoming == null) {
            incoming = new WebRtcEndpoint.Builder(pipeline).build();
            incoming.addIceCandidateFoundListener(new EventListener<IceCandidateFoundEvent>() {

                @Override
                public void onEvent(IceCandidateFoundEvent event) {
                    JsonObject response = new JsonObject();
                    response.addProperty("id", "iceCandidate");
                    response.addProperty("uuid", sender.info.getUUID());
                    response.add("candidate", JsonUtils.toJsonObject(event.getCandidate()));
                    try {
                        synchronized (session) {
                            session.sendMessage(new TextMessage(response.toString()));
                        }
                    } catch (IOException e) {
                        log.debug(e.getMessage());
                    }
                }
            });

            incomingMedia.put(sender.info.getUUID(), incoming);
        }

        sender.getOutgoingWebRtcPeer().connect(incoming);

        return incoming;
    }


    public void cancelVideoFrom(final UserSession sender) {
        this.cancelVideoFrom(sender.info.getUUID());
    }

    public void cancelVideoFrom(final String senderName) {
        final WebRtcEndpoint incoming = incomingMedia.remove(senderName);

        incoming.release(new Continuation<Void>() {
            @Override
            public void onSuccess(Void result) throws Exception {
              /*  log.trace("PARTICIPANT {}: Released successfully incoming EP for {}",
                        UserSession.this.name, senderName);*/
            }

            @Override
            public void onError(Throwable cause) throws Exception {
              /*  log.warn("PARTICIPANT {}: Could not release incoming EP for {}", UserSession.this.name,
                        senderName);*/
            }
        });
    }

    public void addCandidate(IceCandidate candidate, Users u) {
        if (this.info.getUUID().compareTo(u.getUUID()) == 0) {
            outgoingMedia.addIceCandidate(candidate);
        } else {
            WebRtcEndpoint webRtc = incomingMedia.get(u.getUUID());
            if (webRtc != null) {
                webRtc.addIceCandidate(candidate);
            }
        }
    }


    @Override
    public void close() throws IOException {

        for (final String remoteParticipantName : incomingMedia.keySet()) {

            final WebRtcEndpoint ep = this.incomingMedia.get(remoteParticipantName);

            ep.release(new Continuation<Void>() {

                @Override
                public void onSuccess(Void result) throws Exception {
                  /*  log.trace("PARTICIPANT {}: Released successfully incoming EP for {}",
                            UserSession.this.name, remoteParticipantName);*/
                }

                @Override
                public void onError(Throwable cause) throws Exception {
                /*    log.warn("PARTICIPANT {}: Could not release incoming EP for {}", UserSession.this.name,
                            remoteParticipantName);*/
                }
            });
        }

        outgoingMedia.release(new Continuation<Void>() {

            @Override
            public void onSuccess(Void result) throws Exception {
               /* log.trace("PARTICIPANT {}: Released outgoing EP", UserSession.this.name);*/
            }

            @Override
            public void onError(Throwable cause) throws Exception {
              /*  log.warn("USER {}: Could not release outgoing EP", UserSession.this.name);*/
            }
        });
    }

    public void sendMessage(JsonObject message) throws IOException {
        log.debug("USER {}: Sending message {}", this.info.getName(), message);
        synchronized (session) {
            session.sendMessage(new TextMessage(message.toString()));
        }
    }


    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof UserSession)) {
            return false;
        }
        UserSession other = (UserSession) obj;
        boolean eq = this.info.getUUID().equals(other.info.getUUID());
        return eq;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + info.hashCode();
        return result;
    }
    public String getUUID(){
        return this.info.getUUID();
    }
    public String getName(){
        return this.info.getName();
    }
    public Users getInfo(){
        return this.info;
    }
}
