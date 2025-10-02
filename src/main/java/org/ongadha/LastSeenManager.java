package org.ongadha;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import net.runelite.client.config.ConfigManager;

public class LastSeenManager
{
    private final ConfigManager configManager;
    private final Gson gson = new Gson();
    private Map<String, Long> lastSeenMap;

    public LastSeenManager(ConfigManager configManager)
    {
        this.configManager = configManager;
        load();
    }

    private void load()
    {
        String json = configManager.getConfiguration("friendlastseen", "lastSeenData");
        if (json == null || json.isEmpty())
        {
            lastSeenMap = new HashMap<>();
        }
        else
        {
            Type type = new TypeToken<Map<String, Long>>() {}.getType();
            lastSeenMap = gson.fromJson(json, type);
        }
    }

    private void save()
    {
        String json = gson.toJson(lastSeenMap);
        configManager.setConfiguration("friendlastseen", "lastSeenData", json);
    }

    public void saveLastSeen(String friendName, long timestamp)
    {
        lastSeenMap.put(friendName, timestamp);
        save();
    }

    public Long getLastSeen(String friendName)
    {
        return lastSeenMap.get(friendName);
    }

    public Map<String, Long> getAllLastSeen()
    {
        return new HashMap<>(lastSeenMap);
    }


}