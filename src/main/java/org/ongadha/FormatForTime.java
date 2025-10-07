package org.ongadha;

import javax.inject.Singleton;

@Singleton
public class FormatForTime {

    public String formatElapsedTime(long elapsedMillis)
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
