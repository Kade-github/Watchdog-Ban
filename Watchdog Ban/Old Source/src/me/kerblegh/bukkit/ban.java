package me.kerblegh.bukkit;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;

@SuppressWarnings("unused")
public class ban implements CommandExecutor {
	
	private main plugin;

	public ban(main pl) {
		plugin = pl;
	}
	
//Command
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("wdub")) {
			if (sender instanceof Player) {
			if (sender.hasPermission("wd.unban")) {
				if (args.length == 0) {
					sender.sendMessage(ChatColor.WHITE + "[WATCHDOG BAN] " + ChatColor.RED + "Please specify a player first!");
				} else {
					List<String> Bans = plugin.getConfig().getStringList("Bans");
					Player player = (Player) sender;
					Player banPlayer = player.getServer().getPlayer(args[0]);
					banPlayer.setBanned(false);
					Bans.remove(args[0]);
					plugin.getConfig().set("Bans", Bans);
					plugin.saveConfig();
					sender.sendMessage(ChatColor.WHITE + "[WATCHDOG BAN] " + ChatColor.GREEN + "Unbaned: " + ChatColor.RED + args[0]);
				}
			} else {
				sender.sendMessage(ChatColor.WHITE + "[WATCHDOG BAN] " + ChatColor.RED + "No Permission!");
			}
			}
		}
		if (cmd.getName().equalsIgnoreCase("wdb")) {
			if (sender instanceof Player) {
			if (sender.hasPermission("wd.ban")) {
				if (args.length == 0) {
					sender.sendMessage(ChatColor.WHITE + "[WATCHDOG BAN] " + ChatColor.RED + "Please specify a player first!");
				} else {
				List<String> Bans = plugin.getConfig().getStringList("Bans");
				Player player = (Player) sender;
				Player banPlayer = player.getServer().getPlayer(args[0]);
				Random r = new Random();
				int banid = r.nextInt(99999999); // generate a random 8 digit number
				int gg = r.nextInt(9999999); // generate a random 7 digit number
				sender.sendMessage(ChatColor.WHITE + "[WATCHDOG BAN] " + ChatColor.GREEN + "Tried to Ban " + ChatColor.RED + args[0]);
				banPlayer.setBanned(true);
				plugin.getConfig().set("Bans", Bans);
				plugin.saveConfig();
				Bans.add(args[0] + banid);
				banPlayer.kickPlayer(ChatColor.RED + "You are permanently banned from this server!" + ChatColor.GRAY + "\nReason: " + ChatColor.WHITE +"WATCHDOG CHEAT DETECTION " + ChatColor.GRAY + "[GG-" + gg + "]\n\nBan ID: " + ChatColor.WHITE + "#" + banid);
				Bukkit.broadcastMessage(ChatColor.WHITE + "[WATCHDOG]" + ChatColor.RED + ChatColor.BOLD + " A player has been removed \nFrom your game for Cheating! ");
				}
			} else {
				sender.sendMessage(ChatColor.WHITE + "[WATCHDOG BAN] " + ChatColor.RED + "No Permission!");
			}
		} else {
			sender.sendMessage(ChatColor.WHITE + "[WATCHDOG BAN] " + ChatColor.RED + "You must be a player!");
		}
			}
		return false;
		}
	}