package Kade.WatchdogBan.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class WatchdogUnban implements CommandExecutor {
    private JavaPlugin plugin;

    public WatchdogUnban(JavaPlugin pl) {
        plugin = pl;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("watchdog.tempban")) {
                OfflinePlayer unbanPlayer = Bukkit.getOfflinePlayer(args[0]);
                String uuidList = "players." + unbanPlayer.getUniqueId();
                plugin.getConfig().set(uuidList + ".banned", false);
                List<String> uuidThreads = plugin.getConfig().getStringList("threads");
                if (uuidThreads.contains(unbanPlayer.getUniqueId().toString()))
                    uuidThreads.remove(unbanPlayer.getUniqueId().toString());
                plugin.getConfig().set("threads", uuidThreads);
                plugin.saveConfig();
                p.sendMessage("" + ChatColor.GREEN + ChatColor.BOLD + "That player was unbanned!");
            }
        }
        return true;
    }
}
