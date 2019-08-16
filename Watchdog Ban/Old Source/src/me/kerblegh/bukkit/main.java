package me.kerblegh.bukkit; // The package
import java.util.Random;
//Import stuff
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.plugin.java.JavaPlugin;

//Add the Class
public class main extends JavaPlugin{
	
//Logs
	@Override
	public void onEnable() {
		String debug = "on";
		Logger logger = getLogger();
		if (debug == "on") {
		logger.info("Debug mode on!");
		}
		logger.info("Loading config!");
		registerConfig();
		logger.info("Loaded");
		logger.info("Loading the plugin!");
		registerCommand();
		logger.info("Loaded");
		logger.info("<------------------>");
		logger.info("<-->Watchdog Ban<-->");
		logger.info("<-->Version: 2.0<-->");
		logger.info("<------------------>");
		logger.info("Enabled!");
	}
private void registerConfig() {
	getConfig().options().copyDefaults(true);		
	saveConfig();
	
}
// Command register
	private void registerCommand() {
	getCommand("wdb").setExecutor(new ban(this)); 
	getCommand("wdub").setExecutor(new ban(this)); 
}
	public void onDisable() {
		Logger logger = getLogger();
		logger.info("<------------------>");
		logger.info("<-->Watchdog Ban<-->");
		logger.info("<-->Version: 2.0<-->");
		logger.info("<------------------>");
		logger.info("Disabled");
	}
	
	@EventHandler (priority = EventPriority.HIGHEST)
	public void onPlayerBanned(PlayerLoginEvent event){
	if (event.getResult() == Result.KICK_BANNED) {
	String debug = "on";
	if (debug == "on") {
	Logger logger = getLogger();
	logger.info("Kick a banned player!");
	}
	Random r = new Random();
	int gg = r.nextInt(9999999); // generate a random 7 digit number
	event.setKickMessage(ChatColor.RED + "You are permanently banned from this server!" + ChatColor.GRAY + "\nReason: " + ChatColor.WHITE +"WATCHDOG CHEAT DETECTION " + ChatColor.GRAY + "[GG-" + gg + "]");
	} else {
		String debug = "on";
		if (debug == "on") {
		Logger logger = getLogger();
		logger.info("Player registered as Unbaned!");
		}
	}
	}
}
