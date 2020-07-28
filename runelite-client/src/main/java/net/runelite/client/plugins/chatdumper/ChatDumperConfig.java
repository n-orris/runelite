package net.runelite.client.plugins.chatdumper;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("chatdumper")
public interface ChatDumperConfig extends Config
{
    @ConfigItem(
            position = 1,
            keyName = "saveOnlyPublicChat",
            name = "Public chat only",
            description = "Save only chat from public channel"
    )
    default boolean saveOnlyPublicChat() { return false; }
}