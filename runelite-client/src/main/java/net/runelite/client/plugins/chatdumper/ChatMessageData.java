package net.runelite.client.plugins.chatdumper;

import lombok.Setter;
import net.runelite.api.MessageNode;

public class ChatMessageData
{
    @Setter
    private String name = null;
    @Setter
    private String sender = null;
    @Setter
    private int timestamp = -1;
    @Setter
    private String type = null;
    @Setter
    private String value = null;
    @Setter
    private int world = -1;

    public void populateChatMessageData(MessageNode messageNode, int world)
    {
        this.name = messageNode.getName();
        this.sender = messageNode.getSender();
        this.timestamp = messageNode.getTimestamp();
        this.type = messageNode.getType().name();
        this.value = messageNode.getValue();
        this.world = world;
    }
}