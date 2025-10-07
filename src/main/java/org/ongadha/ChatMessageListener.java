package org.ongadha;

import net.runelite.api.ChatMessageType;
import net.runelite.client.eventbus.Subscribe;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ChatMessageListener {

    private final LastSeenManager lastSeenManager;

    @Inject
    public ChatMessageListener(LastSeenManager lastSeenManager){
        this.lastSeenManager = lastSeenManager;
    }

    @Subscribe
    public void onChatMessage(net.runelite.api.events.ChatMessage message)
    {
        if (message.getType() == ChatMessageType.LOGINLOGOUTNOTIFICATION)
        {
            String text = message.getMessageNode().getValue();
            if (text.contains("has logged out")) {
                String name = text.substring(0, text.indexOf(" "));
                long timestamp = System.currentTimeMillis();
                lastSeenManager.saveLastSeen(name, timestamp);

            }else if (text.contains("has logged in")){
                String name = text.substring(0, text.indexOf(" "));
                long timestamp = System.currentTimeMillis();
                lastSeenManager.saveLastSeen(name, null);
            }
        }
    }
}
