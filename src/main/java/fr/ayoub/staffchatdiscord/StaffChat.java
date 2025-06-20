package fr.ayoub.staffchatdiscord;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StaffChat implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        if (!player.hasPermission("staffchat.use") && !player.isOp()) {
            player.sendMessage("§c§c[StaffChat] You don't have permission");
            return false;
        }

        if (strings.length == 0) {
            if (StaffChatDiscord.staffchatmode.contains(player.getUniqueId())) {
                player.sendMessage("§c[StaffChat] You have exited staff chat mode");
                StaffChatDiscord.staffchatmode.remove(player.getUniqueId());
            } else {
                player.sendMessage("§c[StaffChat] You have entered staff chat mode");
                StaffChatDiscord.staffchatmode.add(player.getUniqueId());
            }
            return false;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (String str : strings) {
            stringBuilder.append(str).append(" ");
        }
        stringBuilder = new StringBuilder(stringBuilder.substring(0, stringBuilder.length() - 1));

        for (Player target : Bukkit.getOnlinePlayers()) {
            if (!target.hasPermission("staffchat.use") && !target.isOp()) continue;
            target.sendMessage("§7[StaffChat] §c" + player.getName() + "§7: " + stringBuilder);
        }
        Manager.sendWebhook(player, stringBuilder.toString());

        return false;
    }
}
