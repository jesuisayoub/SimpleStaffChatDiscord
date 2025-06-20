package fr.ayoub.staffchatdiscord;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import net.dv8tion.jda.api.entities.Webhook;
import net.dv8tion.jda.api.managers.WebhookManager;
import org.bukkit.entity.Player;

public class Manager {

    public static void sendWebhook(Player player, String message) {
        try {
            WebhookClient client = WebhookClient.withUrl(StaffChatDiscord.webhook);
            WebhookMessageBuilder builder = new WebhookMessageBuilder();
            builder.setUsername(player.getName());
            builder.setAvatarUrl("https://mc-heads.net/avatar/" + player.getName());
            builder.setContent(message);
            client.send(builder.build());
        } catch (IllegalArgumentException e) {
            StaffChatDiscord.logger.severe("In config.yml, the webhook url is not valid!");
            player.sendMessage("§c[StaffChat] In config.yml, the webhook url is not valid!");
            StaffChatDiscord.main.getPluginLoader().disablePlugin(StaffChatDiscord.main);
        }
    }
}
