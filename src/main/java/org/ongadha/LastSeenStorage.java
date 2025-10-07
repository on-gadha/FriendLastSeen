package org.ongadha;

import java.util.Map;

public interface LastSeenStorage {

    Map<String, Long> load();

    void save(Map<String, Long> data);
}
