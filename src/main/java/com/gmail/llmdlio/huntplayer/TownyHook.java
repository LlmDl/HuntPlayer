package com.gmail.llmdlio.huntplayer;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;

public class TownyHook {
	public static void townyHook(Player target, CommandSender sender) {
		if (TownyAPI.getInstance().isTownyWorld(target.getLocation().getWorld())) {
			Location location = target.getLocation();
			if (TownyAPI.getInstance().isWilderness(location)) {
	
				try {
					sender.sendMessage("Plot: " + ChatColor.DARK_GREEN + TownyAPI.getInstance().getDataSource()
							.getWorld(target.getWorld().getName()).getUnclaimedZoneName().toString());
				} catch (NotRegisteredException e) {
					e.printStackTrace();
				}
	
			} else if (TownyAPI.getInstance().getTownBlock(location).getName().toString() != "") {
				sender.sendMessage("Plot: " + ChatColor.AQUA + TownyAPI.getInstance().getTownName(location)
						+ ChatColor.WHITE + " Plot Name: " + ChatColor.GREEN
						+ TownyAPI.getInstance().getTownBlock(location).getName().toString());
			} else {
				sender.sendMessage("Plot: " + ChatColor.AQUA + TownyAPI.getInstance().getTownName(location));
			}
		}
	}
}
