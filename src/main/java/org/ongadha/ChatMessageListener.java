package org.ongadha;

import net.runelite.api.ChatMessageType;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.api.events.ChatMessage;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ChatMessageListener {

    private final LastSeenProvider lastSeenProvider;
    private final ChatMessage message;

    @Inject
    public ChatMessageListener(LastSeenProvider lastSeenProvider, ChatMessage message){
        this.lastSeenProvider = lastSeenProvider;
        this.message = message;
    }

    @Subscribe
    public void onChatMessage(ChatMessage message)
    {
        if (message.getType() == ChatMessageType.LOGINLOGOUTNOTIFICATION)
        {
            String text = message.getMessageNode().getValue();
            if (text.contains("has logged out")) {
                String name = text.substring(0, text.indexOf(" "));
                long timestamp = System.currentTimeMillis();
                lastSeenProvider.saveLastSeen(name, timestamp);

            }else if (text.contains("has logged in")){
                String name = text.substring(0, text.indexOf(" "));
                long timestamp = System.currentTimeMillis();
                lastSeenProvider.saveLastSeen(name, null);
            }
        }
    }
}
