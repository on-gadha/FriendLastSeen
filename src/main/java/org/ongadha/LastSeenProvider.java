package org.ongadha;

public interface LastSeenProvider {

    void saveLastSeen(String friendName, Long timestamp);

    Long getLastSeen(String friendName);
}
