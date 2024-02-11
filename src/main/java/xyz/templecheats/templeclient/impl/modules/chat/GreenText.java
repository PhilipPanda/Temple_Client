package xyz.templecheats.templeclient.impl.modules.chat;

import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.templecheats.templeclient.impl.modules.Module;

public class GreenText extends Module {

    public GreenText() {
        super("GreenText", "Places > behind your message", 0, Category.Chat);
    }

    @SubscribeEvent
    public void onChat(ClientChatEvent event) {
        if (event.getMessage().startsWith("/") || event.getMessage().startsWith(".")) {
            return;
        }

        String greenText = "> " + event.getMessage();
        event.setMessage(greenText);
    }
}
