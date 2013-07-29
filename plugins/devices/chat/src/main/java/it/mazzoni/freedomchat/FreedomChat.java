/*
 Copyright (c) Matteo Mazzoni 2012  
   
 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.

 */
package it.mazzoni.freedomchat;

import it.freedomotic.api.EventTemplate;
import it.freedomotic.api.Protocol;
import it.freedomotic.app.Freedomotic;
import it.freedomotic.core.NaturalLanguageProcessor;
import it.freedomotic.exceptions.UnableToExecuteException;
import it.freedomotic.reactions.Command;
import it.freedomotic.reactions.Reaction;
import it.freedomotic.reactions.ReactionPersistence;
import it.freedomotic.reactions.Trigger;
import it.freedomotic.reactions.TriggerPersistence;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

public class FreedomChat extends Protocol {

    final int POLLING_WAIT;
    private String hostname;
    private int port;
    private String username;
    private String password;
    private String acceptancePassword;
    private boolean tls;
    Connection conn;
    public static String IF = "if";
    public static String THEN = "then";
    public static String WHEN = "when";
    // private ChatNLP nlp = new ChatNLP();

    public FreedomChat() {
        //every plugin needs a name and a manifest XML file
        super("Chat console", "/it.mazzoni.freedomchat/chat-manifest.xml");
        //read a property from the manifest file below which is in
        //FREEDOMOTIC_FOLDER/plugins/devices/it.freedomotic.hello/hello-world.xml
        POLLING_WAIT = configuration.getIntProperty("time-between-reads", -1);
        //POLLING_WAIT is the value of the property "time-between-reads" or 2000 millisecs,
        //default value if the property does not exist in the manifest
        setPollingWait(POLLING_WAIT); //millisecs interval between hardware device status reads

    }

    @Override
    protected void onStart() {
        hostname = configuration.getStringProperty("hostname", "jabber.org");
        port = configuration.getIntProperty("port", 5222);
        username = configuration.getStringProperty("username", "");
        password = configuration.getStringProperty("password", "");
        acceptancePassword = configuration.getStringProperty("acceptance-password", "");
        tls = configuration.getBooleanProperty("use-tls", false);

        ConnectionConfiguration config = new ConnectionConfiguration("jabber.org", 5222);
        config.setCompressionEnabled(true);
        config.setSASLAuthenticationEnabled(tls);
        config.setSelfSignedCertificateEnabled(true);


        conn = new XMPPConnection(config);
        try {
            conn.connect();
            conn.login(username, password, "Freedomotic");
        } catch (XMPPException ex) {
            Logger.getLogger(FreedomChat.class.getName()).log(Level.SEVERE, null, ex);
            this.stop();
        }
        // Create a new presence. Pass in false to indicate we're unavailable.
        Presence presence = new Presence(Presence.Type.available);
        presence.setStatus("Ready");
        // Send the packet (assume we have a Connection instance called "con").
        conn.sendPacket(presence);

        // wait for messages
        ChatManager chatmanager = conn.getChatManager();
        chatmanager.addChatListener(new ChatManagerListener() {
            @Override
            public void chatCreated(Chat chat, boolean createdLocally) {
                if (!createdLocally) {
                    chat.addMessageListener(new MessageListener() {
                        public void processMessage(Chat chat, Message message) {
                            try {
                                // the user is in my list, OK accepc messages
                                Freedomotic.logger.info(chat.getParticipant());
                                String uname[] = chat.getParticipant().split("/");
                                if (conn.getRoster().getEntry(uname[0]) != null) {
                                    // Send back the same text the other user sent us.
                                    //chat.sendMessage(message.getBody());
                                    chat.sendMessage(manageMessage(message.getBody()));

                                } else {
                                    // expect a password in order to add user to friends' list
                                    chat.sendMessage(manageSubscription(chat, message));
                                }

                            } catch (XMPPException ex) {
                                Logger.getLogger(FreedomChat.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                }
            }
        });

        Freedomotic.logger.info("Chat plugin has started");
        setDescription("Chat: " + username);
    }

    @Override
    protected void onStop() {
        // Disconnect from the server
        if (conn.isConnected()) {
            conn.disconnect();
        }
        Freedomotic.logger.info("Chat plugin has stopped ");
    }

    @Override
    protected void onCommand(Command c) throws IOException, UnableToExecuteException {
        // Assume we've created a Connection name "connection".

        Freedomotic.logger.info("Chat plugin receives a command called " + c.getName()
                + " with parameters " + c.getProperties().toString());
    }

    @Override
    protected boolean canExecute(Command c) {
        //don't mind this method for now
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void onEvent(EventTemplate event) {
        //don't mind this method for now
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private String manageMessage(String mess) {
        Command c;
        Trigger t = null;
        Reaction r;
        NaturalLanguageProcessor nlp2 = new NaturalLanguageProcessor();
        //  String sentenceMess[] = nlp.getSentenceDetector().sentDetect(mess);
        String tokenMess[] = mess.split(" "); //nlp.getTokenizer().tokenize(sentenceMess[0]);
        String triggername = "";
        int conditionSep = 0;
        if (tokenMess[0].equals(IF) || tokenMess[0].equals(WHEN)) {
            for (int i = 1; i < tokenMess.length; i++) {
                if (tokenMess[i].equals(THEN)) {
                    triggername = unsplit(tokenMess, 1, i - 1, " ");
                    conditionSep = i + 1;
                    break;
                }
            }
            t = TriggerPersistence.getTrigger(triggername);
        }

        String commandName = unsplit(tokenMess, conditionSep, tokenMess.length - conditionSep, " ");
        List<NaturalLanguageProcessor.Rank> mostSimilar = nlp2.getMostSimilarCommand(commandName, 3);

        if (!mostSimilar.isEmpty() && mostSimilar.get(0).getSimilarity() > 0) {
            c = mostSimilar.get(0).getCommand();
        } else {
            return "No available commands similar to: " + commandName;
        }
        if (tokenMess[0].equals(IF)) {
            Trigger NEWt = t.clone();
            NEWt.setNumberOfExecutions(1);
            r = new Reaction(NEWt, c);
            ReactionPersistence.add(r);
        } else if (tokenMess[0].equals(WHEN)) {
            // do something
            r = new Reaction(t, c);
            ReactionPersistence.add(r);
        } else {
            Freedomotic.sendCommand(c);
            return c.getName() + "\n DONE.";
        }
        return "DONE";
    }

    private String manageSubscription(Chat chat, Message message) {
        if (message.getBody().equals(acceptancePassword)) {
            try {
                conn.getRoster().createEntry(chat.getParticipant(), "", null);
                return "Just wait and see.";
            } catch (XMPPException ex) {
                Logger.getLogger(FreedomChat.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "";
    }

    public static String unsplit(String[] parts, int index, int length, String splitter) {
        if (parts == null) {
            return null;
        }
        if ((index < 0) || (index >= parts.length)) {
            return null;
        }
        if (index + length > parts.length) {
            return null;
        }

        StringBuilder buf = new StringBuilder();
        for (int i = index; i < index + length; i++) {
            if (parts[i] != null) {
                buf.append(parts[i]);
            }
            buf.append(splitter);
        }

        // remove the trailing splitter
        buf.setLength(buf.length() - splitter.length());
        return buf.toString();
    }

    @Override
    protected void onRun() {
    }
}