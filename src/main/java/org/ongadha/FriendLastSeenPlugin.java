package org.ongadha;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;
import java.time.LocalDateTime;

@Slf4j
@PluginDescriptor(
		name = "FriendLastSeen"
)
public class FriendLastSeenPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private FriendLastSeenConfig config;

	@Inject
	private ConfigManager configManager;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private ClientThread clientThread;

	private LastSeenManager lastSeenManager;
	private FriendsListOverlay friendsListOverlay;

	@Override
	protected void startUp() throws Exception
	{
		lastSeenManager = new LastSeenManager(configManager);
		friendsListOverlay = new FriendsListOverlay(lastSeenManager, this, client);

		overlayManager.add(friendsListOverlay);

		log.info("FriendLastSeen started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(friendsListOverlay);
		log.info("FriendLastSeen stopped!");
	}

	@Provides
	FriendLastSeenConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(FriendLastSeenConfig.class);
	}

	public String formatElapsedTime(long elapsedMillis)
	{
		long seconds = elapsedMillis / 1000;
		long minutes = seconds / 60;
		long hours = minutes / 60;
		long days = hours / 24;

		if (days > 0)
		{
			return days + " days " + (hours % 24) + " hours " + (minutes % 60) + " minutes ago";
		}
		else if (hours > 0)
		{
			return hours + " hours " + (minutes % 60) + " minutes ago";
		}
		else
		{
			return minutes + " minutes ago";
		}
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
				lastSeenManager.saveLastSeen(name, timestamp);

			}else if (text.contains("has logged in")){
				String name = text.substring(0, text.indexOf(" "));
				long timestamp = System.currentTimeMillis();
				lastSeenManager.saveLastSeen(name, timestamp);
			}
		}
	}

	@Subscribe
	public void onMenuEntryAdded(MenuEntryAdded event)
	{
		// Update hovered friend in overlay
		friendsListOverlay.updateHoveredFriend(event.getTarget().replaceAll("<.*?>", "").trim());
	}
}
