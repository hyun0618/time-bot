package bot.time;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import bot.time.config.DiscordConfig;

@SpringBootApplication
@EnableConfigurationProperties(DiscordConfig.class)
public class TimeBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimeBotApplication.class, args);
	}

}
