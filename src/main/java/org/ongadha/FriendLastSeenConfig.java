package org.ongadha;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

/*
(S), två purpose, försök ta bort greeting
(O), ej tillämpad
(L), bra
(I), kopplad till (S)
(D), bra
 */

@ConfigGroup("FriendLastSeen")
public interface FriendLastSeenConfig extends Config
{
	@ConfigItem(
			keyName = "greeting",
			name = "Welcome Greeting",
			description = "The message to show to the user when they login"
	)

	default String greeting(){ return "abow"; }


	@ConfigItem(
		keyName = "lastSeenData",
		name = "Last Seen Data",
		description = "Stores serialized friend and puts it in a timestamp map"
	)

	default String lastSeenData() { return "{}"; }

}
