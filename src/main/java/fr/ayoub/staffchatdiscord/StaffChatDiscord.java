package fr.ayoub.staffchatdiscord;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Webhook;
import net.dv8tion.jda.api.entities.WebhookClient;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public final class StaffChatDiscord extends JavaPlugin {

    public static StaffChatDiscord main;
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(StaffChatDiscord.class);
    public static JDA jda;
    public static List<UUID> staffchatmode = new ArrayList<>();
    private File config;

    public static String token;
    public static String webhook;
    public static String textchannel;
    public static Logger logger = Logger.getLogger("SimpleStaffChat");

    @Override
    public void onEnable() {
        main = this;
        config = new File(this.getDataFolder(), "config.yml");
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(config);
        boolean check = true;
        logger.info("====== SIMPLESTAFFCHAT DISCORD ======");
        if (!config.exists()) {
            saveResource("config.yml", true);
            logger.warning("Thank you for downloading this plugin. Now that you've started it, a configuration file has just appeared in the plugin folder. Configure it and then restart the server to make it work!");
        } else {
                token = fileConfiguration.getString("tokenbot");
                if (token == null || token.isEmpty()) {
                    logger.warning("In config.yml, the token is empty!");
                    check = false;
                }
                webhook = fileConfiguration.getString("webhook");
                if (webhook == null || webhook.isEmpty()) {
                    logger.warning("In config.yml, the webhook is empty!");
                    check = false;
                }
                textchannel = fileConfiguration.getString("textchannel");
                if (textchannel == null || textchannel.isEmpty()) {
                    logger.warning("In config.yml, the textchannel is empty!");
                }
            if (check) {
                try {
                    jda = JDABuilder.create(token, Arrays.asList(GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT)).disableCache(CacheFlag.ACTIVITY, CacheFlag.VOICE_STATE, CacheFlag.EMOJI, CacheFlag.STICKER, CacheFlag.CLIENT_STATUS, CacheFlag.ONLINE_STATUS, CacheFlag.SCHEDULED_EVENTS).build();
                    jda.addEventListener(new GuildReceive());
                    PluginManager pluginManager = Bukkit.getPluginManager();
                    pluginManager.registerEvents(new Chat(), this);
                    getCommand("staffchat").setExecutor(new StaffChat());
                    logger.info("It work!");
                    logger.info("I'm an independent developer who doesn't make any money from this plugin.");
                    logger.info("If you like it, please support me!");
                    logger.info("https://www.paypal.com/paypalme/aybuild");
                } catch (InvalidTokenException | IllegalArgumentException e) {
                    logger.severe("In config.yml, the bot token is not valid!");
                    this.getPluginLoader().disablePlugin(this);
                }
            } else {
                logger.severe("The config is wrong!");
                this.getPluginLoader().disablePlugin(this);
            }
        }
        logger.info("=====================================");
    }

    @Override
    public void onDisable() {
        if (jda != null) jda.shutdown();
    }
}
