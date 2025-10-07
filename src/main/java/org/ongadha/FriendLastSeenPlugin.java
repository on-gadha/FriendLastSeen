package org.ongadha;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.eventbus.EventBus;

import javax.inject.Inject;

/*
(S), olika purpose, bryt ut till mindre classer: formatting time, chat message (FIXAT)
(O), onchatmessage skapar problem, kan ignoreras
(L), bra
(I), bra
(D), bra eventuellt lÃ¤gga till interface
 */

@Slf4j
@PluginDescriptor(
		name = "FriendLastSeen"
)
public class FriendLastSeenPlugin extends Plugin
{
	@Inject
	private Client client;

	//Unused
	@Inject
	private FriendLastSeenConfig config;

	@Inject
	private ConfigManager configManager;

	@Inject
	private OverlayManager overlayManager;

	//Unused
	@Inject
	private ClientThread clientThread;

	@Inject
	private FormatForTime formatTime;

	@Inject
	private ChatMessageListener chatMessageListener;

	@Inject
	private EventBus eventBus;

	@Inject
	private LastSeenManager lastSeenManager;

	private FriendsListOverlay friendsListOverlay;

	@Override
	protected void startUp() throws Exception
	{
		friendsListOverlay = new FriendsListOverlay(lastSeenManager, this, client, formatTime);

		overlayManager.add(friendsListOverlay);
		eventBus.register(chatMessageListener);

		log.info("FriendLastSeen started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(friendsListOverlay);
		eventBus.unregister(chatMessageListener);
		log.info("FriendLastSeen stopped!");
	}

	//Unused
	@Provides
	FriendLastSeenConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(FriendLastSeenConfig.class);
	}

	//Unused
	@Subscribe
	public void onMenuEntryAdded(MenuEntryAdded event)
	{
		// Update hovered friend in overlay
		friendsListOverlay.updateHoveredFriend(event.getTarget().replaceAll("<.*?>", "").trim());
	}
}