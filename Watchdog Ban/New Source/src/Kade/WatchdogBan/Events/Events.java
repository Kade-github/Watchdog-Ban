package Kade.WatchdogBan.Events;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Events implements Listener {
    JavaPlugin pl;

    public Events(JavaPlugin pla)
    {
        pl = pla;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e)
    {
        if (pl.getConfig().contains("players." + e.getPlayer().getUniqueId()))
        {
            if (pl.getConfig().getBoolean("players." + e.getPlayer().getUniqueId() + ".banned") && pl.getConfig().getInt("players." + e.getPlayer().getUniqueId() + ".timeLeft-m") != 0)
            {
                String uuidList = "players." + e.getPlayer().getUniqueId();
                int banTime = pl.getConfig().getInt(uuidList + ".timeLeft-d");
                int hours = pl.getConfig().getInt(uuidList + ".timeLeft-h");
                int mins = pl.getConfig().getInt(uuidList + ".timeLeft-m");
                int banId = pl.getConfig().getInt(uuidList + ".banId");
                int gg = pl.getConfig().getInt(uuidList + ".gg");
                e.getPlayer().kickPlayer(ChatColor.RED + "You are temporarily banned for " + ChatColor.RESET + banTime+ "d " + hours + "h " + mins + "m " + ChatColor.RED + "from this server!" + ChatColor.GRAY + "\nReason: " + ChatColor.WHITE +"WATCHDOG CHEAT DETECTION " + ChatColor.GRAY + "[GG-" + gg + "]\n\nBan ID: " + ChatColor.WHITE + "#" + banId);
            }
            else if (pl.getConfig().getBoolean("players." + e.getPlayer().getUniqueId() + ".banned"))
            {
                String uuidList = "players." + e.getPlayer().getUniqueId();
                int banId = pl.getConfig().getInt(uuidList + ".banId");
                int gg = pl.getConfig().getInt(uuidList + ".gg");
                e.getPlayer().kickPlayer(ChatColor.RED + "You are permanently banned from this server!" + ChatColor.GRAY + "\nReason: " + ChatColor.WHITE +"WATCHDOG CHEAT DETECTION " + ChatColor.GRAY + "[GG-" + gg + "]\n\nBan ID: " + ChatColor.WHITE + "#" + banId);
            }
        }
    }
}
