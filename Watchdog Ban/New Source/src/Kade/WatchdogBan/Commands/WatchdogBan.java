package Kade.WatchdogBan.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class WatchdogBan implements CommandExecutor {
    private JavaPlugin plugin;

    public WatchdogBan(JavaPlugin pl) {
        plugin = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("watchdog.ban")) {
                Player banPlayer = Bukkit.getPlayer(args[0]);
                String uuidList = "players." + banPlayer.getUniqueId();
                plugin.getConfig().set(uuidList + ".banned", true);
                p.sendMessage("" + ChatColor.GREEN + ChatColor.BOLD + "That player was banned!");
                plugin.saveConfig();
            }
        }
        return true;
    }
}
