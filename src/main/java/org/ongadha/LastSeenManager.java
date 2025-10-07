package org.ongadha;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import net.runelite.client.config.ConfigManager;

import javax.inject.Inject;
import javax.inject.Singleton;

/*
@Samuel
(S), eventuellt två purpose, åtgärda (fixat)
(O), strong coupling med configmanager (fixat)
(L), bra
(I), bra
(D), strong coupling med configmanager (fixat)
 */

@Singleton
public class LastSeenManager implements LastSeenProvider
{
    private final LastSeenStorage storage;
    private final Map<String, Long> lastSeenMap;

    @Inject
    public LastSeenManager(LastSeenStorage storage)
    {
        this.storage = storage;
        this.lastSeenMap = storage.load();
    }

    public void saveLastSeen(String friendName, Long timestamp)
    {
        lastSeenMap.put(friendName, timestamp);
        storage.save(lastSeenMap);
    }

    public Long getLastSeen(String friendName)
    {
        return lastSeenMap.get(friendName);
    }

}