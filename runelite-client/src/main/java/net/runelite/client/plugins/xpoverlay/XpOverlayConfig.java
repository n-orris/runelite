package net.runelite.client.plugins.xpoverlay;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("xpoverlay")
public interface XpOverlayConfig extends Config {

    @ConfigItem(
            keyName = "nickname",
            name = "Xp Overlay",
            description = "An overlay that displays skill Xp",
            position = 1
    )
    default String nickname() { return "bob"; }

    @ConfigItem(
            keyName = "goalLevel",
            name = "Goal Level",
            description = "The user's goal level",
            position = 2
    )
    default int goalLevel() { return 99; }


}
