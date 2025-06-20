package fr.ayoub.staffchatdiscord;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class GuildReceive extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        Member member = event.getMember();
        Message message = event.getMessage();
        if (message.getContentDisplay().isEmpty()) return;
        TextChannel textChannel = event.getGuildChannel().asTextChannel();
        if (!textChannel.getId().equals(StaffChatDiscord.textchannel)) return;
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!player.hasPermission("staffchat.use") && !player.isOp()) continue;
            player.sendMessage("§7[StaffChat] §c" + member.getEffectiveName() + "§7: " + message.getContentDisplay());
        }
    }

}
