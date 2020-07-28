package net.runelite.client.plugins.xpoverlay;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Skill;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
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

    @Inject
    private XpOverlayConfig config;

    @Provides
    XpOverlayConfig getConfig(ConfigManager configManager) {
        return configManager.getConfig(XpOverlayConfig.class);
    }

    @Subscribe
    public void onConfigChanged(ConfigChanged event) {
        System.out.println("Welcome, " + config.nickname());
        System.out.println("Good luck on your goal level of "+ config.goalLevel());
    }

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
