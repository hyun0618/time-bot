package bot.time.config;

import org.springframework.context.annotation.Configuration;

import bot.time.listener.MessageListener;
import bot.time.listener.VoiceChannelTracker;
import jakarta.annotation.PostConstruct;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

@Configuration
public class JdaConfig {
	
	private final DiscordConfig discordConfig;
    private final MessageListener messageListener;
    private final VoiceChannelTracker voiceChannelTracker;

    public JdaConfig(DiscordConfig discordConfig, MessageListener messageListener, VoiceChannelTracker voiceChannelTracker) {
        this.discordConfig = discordConfig;
        this.messageListener = messageListener;
        this.voiceChannelTracker = voiceChannelTracker;
    }

    @PostConstruct
    public void startBot() throws Exception {
        JDA jda = JDABuilder.createDefault(discordConfig.getToken())
                .enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_VOICE_STATES)
                .addEventListeners(messageListener, voiceChannelTracker)
                .build();

        jda.awaitReady();
        System.out.println("🤖 봇 실행 완료!");
    }
}
