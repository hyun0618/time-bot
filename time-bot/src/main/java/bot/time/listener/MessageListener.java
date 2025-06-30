package bot.time.listener;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

@Component
public class MessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        if (event.getMessage().getContentRaw().equals("!ping")) {
            event.getChannel().sendMessage("🏓 Pong!").queue();
        }
    }
}