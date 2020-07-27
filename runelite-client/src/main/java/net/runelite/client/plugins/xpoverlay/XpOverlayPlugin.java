package net.runelite.client.plugins.xpoverlay;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Skill;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;

@Slf4j
@PluginDescriptor(
        name = "Xp Overlay",
        description = "An overlay that displays skill xp",
        enabledByDefault = true,
        tags = {"xp", "overlay","experience"}

)

public class XpOverlayPlugin extends Plugin {

    @Inject
    private Client client;

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private XpOverlay xpOverlay;

    @Override
    public void startUp() {
        overlayManager.add(xpOverlay);
    }

    @Override
    public void shutDown() {
        overlayManager.remove(xpOverlay);
    }

    public int getSkillLevel() {
        return client.getRealSkillLevel(Skill.COOKING);
    }



}
