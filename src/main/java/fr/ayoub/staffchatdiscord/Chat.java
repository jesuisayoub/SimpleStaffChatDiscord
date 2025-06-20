package fr.ayoub.staffchatdiscord;

import net.dv8tion.jda.api.entities.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Chat implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (event.isCancelled()) return;

        Player player = event.getPlayer();
        String message = event.getMessage();

        if (!StaffChatDiscord.staffchatmode.contains(player.getUniqueId())) return;
        if (!player.hasPermission("staffchat.use") && !player.isOp()) {
            player.sendMessage("§c[StaffChat] You have exited staff chat mode");
            StaffChatDiscord.staffchatmode.remove(player.getUniqueId());
            return;
        }
        event.setCancelled(true);

        for (Player target : Bukkit.getOnlinePlayers()) {
            if (!target.hasPermission("staffchat.use") && !target.isOp()) continue;
            target.sendMessage("§7[StaffChat] §c" + player.getName() + "§7: " + message);
        }
        Manager.sendWebhook(player, message);
    }
}
