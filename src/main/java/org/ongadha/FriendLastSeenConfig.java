package org.ongadha;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("FriendLastSeen")
public interface FriendLastSeenConfig extends Config
{
	@ConfigItem(
		keyName = "lastSeenData",
		name = "Last Seen Data",
		description = "Stores serialized friend and puts it in a timestamp map"
	)
	default String lastSeenData()
	{
		return "{}";
	}

}
