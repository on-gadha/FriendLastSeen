package org.ongadha;

import com.google.common.reflect.TypeToken;
import net.runelite.client.config.ConfigManager;
import com.google.gson.Gson;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class ConfigManagerStorage implements LastSeenStorage{
    private final ConfigManager configManager;
    private final Gson gson = new Gson();

    @Inject
    public ConfigManagerStorage(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @Override
    public Map<String, Long> load(){
        String json = configManager.getConfiguration("friendlastseen", "lastSeenData");
        if(json == null || json.isEmpty()){
            return new HashMap<>();
        }
        Type type = new TypeToken<Map<String, Long>>(){}.getType();
        return gson.fromJson(json, type);
    }

    @Override
    public void save(Map<String, Long> data){
        String json = gson.toJson(data);
        configManager.setConfiguration("friendlastseen", "lastSeenData", json);
    }
}
