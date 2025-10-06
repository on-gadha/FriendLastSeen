package org.ongadha;

import net.runelite.api.Client;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;

import java.awt.*;

/*
@Tom
(S), men nästan. hoveredFriend bör flyttas till en egen klass
(O), bra
(L), bra
(I), bra
(D), problem = lastseenmanager och firendlastseenpluin är konkreta klasser
 */

public class FriendsListOverlay extends Overlay
{
    private final LastSeenManager lastSeenManager;
    private final FriendLastSeenPlugin plugin;
    private final Client client;
    private final PanelComponent panelComponent = new PanelComponent();

    private String hoveredFriend = null;

    public FriendsListOverlay(LastSeenManager manager, FriendLastSeenPlugin plugin, Client client)
    {
        this.lastSeenManager = manager;
        this.plugin = plugin;
        this.client = client;

        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
    }

    public void updateHoveredFriend(String friendName)
    {
        this.hoveredFriend = friendName;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        panelComponent.getChildren().clear();

        if (hoveredFriend == null)
            return null;

        Long lastSeen = lastSeenManager.getLastSeen(hoveredFriend);
        if (lastSeen == null)
            return null;

        panelComponent.getChildren().add(
                LineComponent.builder()
                        .left(hoveredFriend)
                        .right(plugin.formatElapsedTime(System.currentTimeMillis() - lastSeen))
                        .build()
        );

        // Get mouse position and convert to java.awt.Point
        net.runelite.api.Point mouse = client.getMouseCanvasPosition();
        if (mouse != null)
            panelComponent.setPreferredLocation(new Point(mouse.getX() + 15, mouse.getY()));

        return panelComponent.render(graphics);
    }
}
