package org.ongadha;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

/*
(S), två purpose, försök ta bort greeting (FIXAT)
(O), ej tillämpad
(L), bra
(I), kopplad till (S)
(D), bra
 */

@ConfigGroup("FriendLastSeen")
public interface FriendLastSeenConfig extends Config
{
	@ConfigItem(
		keyName = "lastSeenData",
		name = "Last Seen Data",
		description = "Stores serialized friend and puts it in a timestamp map"
	)

	default String lastSeenData() { return "{}"; }

}
