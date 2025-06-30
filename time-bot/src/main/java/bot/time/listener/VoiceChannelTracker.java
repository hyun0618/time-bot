package bot.time.listener;


import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

@Component
public class VoiceChannelTracker extends ListenerAdapter {

    private final Map<String, LocalDateTime> joinTimes = new ConcurrentHashMap<>();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public void onGuildVoiceUpdate(@NotNull GuildVoiceUpdateEvent event) {
        Member member = event.getMember();
        String userId = member.getId();

        if (event.getChannelJoined() != null) {
            LocalDateTime joinTime = LocalDateTime.now();
            joinTimes.put(userId, joinTime);

            String message = String.format("🟢 %s님이 입장했습니다. (입장: %s)",
                    member.getEffectiveName(),
                    joinTime.format(formatter)
            );
            sendTextMessage(event, message);
        }

        if (event.getChannelLeft() != null) {
            LocalDateTime leaveTime = LocalDateTime.now();
            LocalDateTime joinTime = joinTimes.remove(userId);

            if (joinTime != null) {
                Duration duration = Duration.between(joinTime, leaveTime);

                String message = String.format("🔴 %s님이 퇴장했습니다. (퇴장: %s)",
                        member.getEffectiveName(),
                        leaveTime.format(formatter)
                );
                sendTextMessage(event, message);
            }
        }
    }

    private void sendTextMessage(GuildVoiceUpdateEvent event, String message) {
        String textChannelName = "일반";

        TextChannel targetChannel = event.getGuild()
                .getTextChannelsByName(textChannelName, true)
                .stream()
                .findFirst()
                .orElse(null);

        if (targetChannel != null) {
            targetChannel.sendMessage(message).queue();
        } else {
            System.out.println("❗ 텍스트 채널 '" + textChannelName + "' 을(를) 찾을 수 없습니다.");
        }
    }
}