package net.runelite.client.plugins.xpoverlay;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("xpoverlay")
public interface XpOverlayConfig extends Config {

    @ConfigItem(
            keyName = "goalLevel",
            name = "Goal Level",
            description = "The user's goal level"
    )
    default int goalLevel() { return 99; }

    @ConfigItem(
            keyName = "skills",
            name = "Choose Skill",
            description = "Choose which skill you want to display"
    )
    default String skills() {return "";}

}
