package org.ongadha;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.ChatMessage;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

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

	private LastSeenManager lastSeenManager;



	@Override
	protected void startUp() throws Exception
	{
		lastSeenManager = new LastSeenManager(configManager);	//used for methods get or save lastseen
		log.info("FriendLastSeen started!");

	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("FriendLastSeen stopped!");
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Example says " + config.greeting(), null);
		}
	}

	@Provides
	FriendLastSeenConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(FriendLastSeenConfig.class);
	}

	/*@Subscribe
	public void onChatMessage(ChatMessage event)
	{
		String message = event.getMessage().trim();

		// Check if it starts with your command
		if (message.startsWith("!lastseen "))
		{
			onLastSeenCommand(message);
		}
	}*/


	/*private void onLastSeenCommand(String command)
	{
		String[] parts = command.split(" ");
		if (parts.length < 2)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Usage: !lastseen <friendName>", null);
			return;
		}
		String friendName = parts[1];
		Long lastSeen = lastSeenManager.getLastSeen(friendName);
		if (lastSeen == null)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", friendName + " has no last seen data.", null);
			return;
		}

		String formatted = formatElapsedTime(System.currentTimeMillis() - lastSeen);
		client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", friendName + " was last seen " + formatted, null);
	}*/

	private String formatElapsedTime(long elapsedMillis)
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
}
