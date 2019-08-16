package Kade.WatchdogBan;

import Kade.WatchdogBan.Commands.WatchdogBan;
import Kade.WatchdogBan.Commands.WatchdogTempBan;
import Kade.WatchdogBan.Commands.WatchdogUnban;
import Kade.WatchdogBan.Events.Events;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Main extends JavaPlugin {
    @Override
    public void onEnable()
    {
        File cfg = new File(getDataFolder(), "config.yml");
        if (!cfg.exists())
            saveDefaultConfig();
        getLogger().info("Enabled!");
        getServer().getPluginManager().registerEvents(new Events(this), this);
        getServer().getPluginCommand("wban").setExecutor(new WatchdogBan(this));
        getServer().getPluginCommand("wtban").setExecutor(new WatchdogTempBan(this));
        getServer().getPluginCommand("wunban").setExecutor(new WatchdogUnban(this));
        for (String uuid : getConfig().getStringList("threads"))
        {
            getLogger().info("Started a ban thread for " + uuid);
            String uuidList = "players." + uuid;
            final int[] banTime = {getConfig().getInt(uuidList + ".timeLeft-d")};
            final int[] hours = {getConfig().getInt(uuidList + ".timeLeft-h")};
            final int[] mins = {getConfig().getInt(uuidList + ".timeLeft-m")};
            // Every day count down
            getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                public void run() {
                    if (banTime[0] != 0) {
                        banTime[0]--;
                        getConfig().set(uuidList + ".timeLeft-d", banTime[0]);
                        saveConfig();
                    }
                }
            }, 1728000L, 1728000L);
            // Every hour count down
            getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                public void run() {
                    if (mins[0] != 0 && banTime[0] != 0) {
                        if (hours[0] == 0)
                            hours[0] = 24;
                        else
                            hours[0]--;
                        getConfig().set(uuidList + ".timeLeft-h", hours[0]);
                        saveConfig();
                    }
                }
            }, 72000L, 72000L);
            // Every min count down
            getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                public void run() {
                    if (hours[0] != 0 || banTime[0] != 0) {
                        if (mins[0] == 0)
                            mins[0] = 59;
                        else
                            mins[0]--;
                        getConfig().set(uuidList + ".timeLeft-h", mins[0]);
                        saveConfig();
                    }
                    else
                    {
                        List<String> uuidThreads = getConfig().getStringList("threads");
                        uuidThreads.remove(uuid);
                        getConfig().set("threads", uuidThreads);
                        getConfig().set(uuidList + ".banned", false);
                        saveConfig();
                    }
                }
            }, 1200L, 1200L);
        }
    }
}
