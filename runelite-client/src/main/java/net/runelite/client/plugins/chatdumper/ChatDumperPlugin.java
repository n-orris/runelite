package net.runelite.client.plugins.chatdumper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Provides;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import lombok.extern.slf4j.Slf4j;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.api.events.CommandExecuted;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.MessageNode;

@Slf4j
@PluginDescriptor(
        name = "Chat Dumper",
        description = "Extract chat messages and save to an external JSON file",
        tags = {"osrsbox", "chat", "scraper"},
        enabledByDefault = false
)

public class ChatDumperPlugin extends Plugin
{
    private final ArrayList<ChatMessageData> messages = new ArrayList<ChatMessageData>();

    @Inject
    private Client client;

    @Inject
    private ChatDumperConfig config;

    @Provides
    ChatDumperConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(ChatDumperConfig.class);
    }

    @Override
    protected void startUp() throws Exception
    {
        log.debug(">>> Starting up ChatDumper...");
    }

    @Override
    protected void shutDown() throws Exception
    {
        log.debug(">>> Shutting down ChatDumper...");
        messages.clear();
    }

    @Subscribe
    public void onCommandExecuted(CommandExecuted commandExecuted)
    {
        switch (commandExecuted.getCommand())
        {
            case "csave":
            {
                // When command is found, dump data to JSON and clear array
                saveChatMessageData();
                messages.clear();
                break;
            }
        }
    }

    @Subscribe(priority = -2) // run after ChatMessageManager
    public void onChatMessage(ChatMessage chatMessage)
    {
        final MessageNode messageNode = chatMessage.getMessageNode();

        // If the config states only public, skip other chat types
        // This is only really included as an example to filter chat types
        if (config.saveOnlyPublicChat())
        {
            if (messageNode.getType().name() != "PUBLICCHAT")
            {
                return;
            }
        }

        // Create new chat message object, populate and add to array
        ChatMessageData chatMessageData = new ChatMessageData();
        chatMessageData.populateChatMessageData(messageNode, client.getWorld());
        messages.add(chatMessageData);
    }

    private void saveChatMessageData()
    {
        // Get timestamp for a unqiue file name
        String pattern = "yyyy-MM-dd-HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);
        Date today = Calendar.getInstance().getTime();
        String fileOut = df.format(today);
        fileOut = fileOut + ".json";

        // Create JSON export
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(messages);

        // Save the JSON file
        try (FileWriter fw = new FileWriter(fileOut))
        {
            fw.write(json);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}










