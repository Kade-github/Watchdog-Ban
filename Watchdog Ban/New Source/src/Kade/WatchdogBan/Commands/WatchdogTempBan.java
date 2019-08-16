package Kade.WatchdogBan.Commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class WatchdogTempBan implements CommandExecutor {

    private JavaPlugin plugin;

    public WatchdogTempBan(JavaPlugin pl) {
        plugin = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player)
        {
            Player p = (Player) sender;
            if (p.hasPermission("watchdog.tempban"))
            {
                Player banPlayer = Bukkit.getPlayer(args[1]);
                Random r = new Random();
                int banid = r.nextInt(99999999); // generate a random 8 digit number
                int gg = r.nextInt(9999999); // generate a random 7 digit number
                Calendar calendar = Calendar.getInstance();
                Date date = calendar.getTime();
                final int[] banTime = {Integer.parseInt(args[0]) - 1};
                final int[] hours = {24};
                final int[] mins = {59};
                banPlayer.kickPlayer(ChatColor.RED + "You are temporarily banned for " + ChatColor.RESET + banTime[0] + "d " + "24h 59m " + ChatColor.RED + "from this server!" + ChatColor.GRAY + "\nReason: " + ChatColor.WHITE +"WATCHDOG CHEAT DETECTION " + ChatColor.GRAY + "[GG-" + gg + "]\n\nBan ID: " + ChatColor.WHITE + "#" + banid);
                Bukkit.broadcastMessage(ChatColor.WHITE + "[WATCHDOG CHEAT DETECTION]" + ChatColor.RED + ChatColor.BOLD + " A player has been removed from your game for hacking or abuse. Thanks for reporting it!");
                String uuidList = "players." + banPlayer.getUniqueId();
                plugin.getConfig().set(uuidList + ".banned", true);
                plugin.getConfig().set(uuidList + ".timeLeft-d", banTime[0]);
                plugin.getConfig().set(uuidList + ".timeLeft-h", 24);
                plugin.getConfig().set(uuidList + ".timeLeft-m", 59);
                plugin.getConfig().set(uuidList + ".timeLeft-lc", date.toString());
                plugin.getConfig().set(uuidList + ".banId", banid);
                plugin.getConfig().set(uuidList + ".gg", gg);
                List<String> uuidThreads = plugin.getConfig().getStringList("threads");
                uuidThreads.add("" + banPlayer.getUniqueId());
                plugin.getConfig().set("threads", uuidThreads);
                plugin.saveConfig();
                // Every day count down
                plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
                            public void run() {
                                if (banTime[0] != 0) {
                                    banTime[0]--;
                                    plugin.getConfig().set(uuidList + ".timeLeft-d", banTime[0]);
                                    plugin.saveConfig();
                                }
                            }
                        }, 1728000L, 1728000L);
                // Every hour count down
                plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
                    public void run() {
                        if (mins[0] != 0 && banTime[0] != 0) {
                            if (hours[0] == 0)
                                hours[0] = 24;
                            else
                                hours[0]--;
                            plugin.getConfig().set(uuidList + ".timeLeft-h", hours[0]);
                            plugin.saveConfig();
                        }
                    }
                }, 72000L, 72000L);
                // Every min count down
                plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
                    public void run() {
                        if (hours[0] != 0 || banTime[0] != 0) {
                            if (mins[0] == 0)
                                mins[0] = 59;
                            else
                                mins[0]--;
                            plugin.getConfig().set(uuidList + ".timeLeft-h", mins[0]);
                            plugin.saveConfig();
                        }
                        else
                        {
                            List<String> uuidThreads = plugin.getConfig().getStringList("threads");
                            uuidThreads.remove("" + banPlayer.getUniqueId());
                            plugin.getConfig().set("threads", uuidThreads);
                            plugin.getConfig().set(uuidList + ".banned", false);
                            plugin.saveConfig();
                        }
                    }
                }, 1200L, 1200L);
                p.sendMessage("" + org.bukkit.ChatColor.GREEN + org.bukkit.ChatColor.BOLD + "That player was banned!");
            }
        }
        return false;
    }
}
