package org.ongadha;

import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;

import java.awt.*;
import java.util.Map;

public class FriendsListOverlay extends Overlay
{
    private final LastSeenManager lastSeenManager;
    private final FriendLastSeenPlugin plugin;
    private final PanelComponent panelComponent = new PanelComponent();

    public FriendsListOverlay(LastSeenManager manager, FriendLastSeenPlugin plugin)
    {
        this.lastSeenManager = manager;
        this.plugin = plugin;
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        panelComponent.getChildren().clear();

        // Loop through all friends in lastSeenManager
        for (Map.Entry<String, Long> entry : lastSeenManager.getAllLastSeen().entrySet())
        {
            String friendName = entry.getKey();
            Long timestamp = entry.getValue();
            if (timestamp != null)
            {
                panelComponent.getChildren().add(
                        LineComponent.builder()
                                .left(friendName)
                                .right(plugin.formatElapsedTime(System.currentTimeMillis() - timestamp))
                                .build()
                );
            }
        }
//j
        return panelComponent.render(graphics);
    }
}
