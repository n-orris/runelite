package net.runelite.client.plugins.xpoverlay;

import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;

import javax.inject.Inject;
import java.awt.*;

public class XpOverlay extends Overlay {

    private XpOverlayPlugin plugin;
    private PanelComponent panelComponent = new PanelComponent();

    @Inject
    public XpOverlay(XpOverlayPlugin plugin) {
        super(plugin);
        setPosition(OverlayPosition.TOP_CENTER);
        setLayer(OverlayLayer.ABOVE_SCENE);
        this.plugin = plugin;
    }


    @Override
    public Dimension render(Graphics2D graphics) {

        panelComponent.getChildren().clear();

        panelComponent.getChildren().add(LineComponent.builder()
        .left("Skill Level")
        .right(Integer.toString(plugin.getSkillLevel()))
        .build());

        return panelComponent.render(graphics);
    }
}
