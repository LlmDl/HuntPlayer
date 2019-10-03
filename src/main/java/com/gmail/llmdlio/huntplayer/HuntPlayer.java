package com.gmail.llmdlio.huntplayer;

import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.TownyAPI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public final class HuntPlayer extends JavaPlugin {
    private static String pluginPrefix;
    private static Boolean usingTowny;

    public void onEnable() {
        getLogger().info("HuntPlayer " + getDescription().getVersion() + " by LlmDl Enabled.");
        saveDefaultConfig();
        pluginPrefix = getConfig().getString("prefix");
        usingTowny = Bukkit.getServer().getPluginManager().getPlugin("Towny").isEnabled();

        if (usingTowny.booleanValue()) {
            getLogger().info("Towny present and hooked. Plot location will be shown.");
        }
        if (pluginPrefix != null) {
            pluginPrefix = ChatColor.translateAlternateColorCodes('&', pluginPrefix);
        } else {
            getLogger().severe("Invalid Config.");
            onDisable();
        }
    }

    public void onDisable() {
        getLogger().info("HuntPlayer Disabled.");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("hunt")) {

            if (args.length == 0 || args[0].equalsIgnoreCase(sender.getName())) {

                Player target = (Player) sender;

                sender.sendMessage(String.valueOf(pluginPrefix) + "Hunting Self:");
                sender.sendMessage("World: " + ChatColor.YELLOW + target.getLocation().getWorld().getName().toString());
                sender.sendMessage("X: " + String.valueOf(target.getLocation().getBlockX()));
                sender.sendMessage("Y: " + String.valueOf(target.getLocation().getBlockY()));
                sender.sendMessage("Z: " + String.valueOf(target.getLocation().getBlockZ()));
                return true;
            }

            if (args.length > 1) {
                sender.sendMessage(String.valueOf(pluginPrefix) + "Too many arguments!");
                return true;
            }

            @SuppressWarnings("deprecation")
            Player target = sender.getServer().getPlayer(args[0]);

            if (target == null) {
                sender.sendMessage(String.valueOf(pluginPrefix) + args[0] + " is not currently online.");
                return true;
            }

            boolean isInvisible = target.hasPotionEffect(PotionEffectType.INVISIBILITY);
            if (isInvisible || target.getGameMode() == GameMode.SPECTATOR) {
                sender.sendMessage(String.valueOf(pluginPrefix) + args[0] + " cannot be found, they are invisible!");
                return true;
            }

            Player seeker = (Player) sender;
            sender.sendMessage(String.valueOf(pluginPrefix) + target.getName() + " found!");
            sender.sendMessage("World: " + ChatColor.YELLOW + target.getLocation().getWorld().getName().toString());

            if (usingTowny) {
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

            if (seeker.getLocation().getWorld() == target.getLocation().getWorld()) {
                int distanceToSeeker = (int) target.getLocation().distance(seeker.getLocation());
                int negDistanceToSeeker = -distanceToSeeker;
                Vector vector = target.getLocation().toVector().subtract(seeker.getLocation().toVector());

                if (vector.getX() > (negDistanceToSeeker / 2) && vector.getX() < (distanceToSeeker / 2) && vector.getZ() < 0.0D) {
                    sender.sendMessage("Direction: North");
                } else if (vector.getX() > 0.0D && vector.getZ() > (negDistanceToSeeker / 2) && vector.getZ() < (distanceToSeeker / 2)) {
                    sender.sendMessage("Direction: East");
                } else if (vector.getX() < (distanceToSeeker / 2) && vector.getX() > (negDistanceToSeeker / 2) && vector.getZ() > 0.0D) {
                    sender.sendMessage("Direction: South");
                } else if (vector.getX() < 0.0D && vector.getZ() < (distanceToSeeker / 2) && vector.getZ() > (negDistanceToSeeker / 2)) {
                    sender.sendMessage("Direction: West");
                } else if (vector.getX() < 0.0D && vector.getZ() < 0.0D) {
                    sender.sendMessage("Direction: NorthWest");
                } else if (vector.getX() > 0.0D && vector.getZ() < 0.0D) {
                    sender.sendMessage("Direction: NorthEast");
                } else if (vector.getX() < 0.0D && vector.getZ() > 0.0D) {
                    sender.sendMessage("Direction: SouthWest");
                } else if (vector.getX() > 0.0D && vector.getZ() > 0.0D) {
                    sender.sendMessage("Direction: SouthEast");
                }

                int seekerHeight = seeker.getLocation().getBlockY();
                int targetHeight = target.getLocation().getBlockY();

                if (seekerHeight == targetHeight) {
                    sender.sendMessage("They are level with you.");
                }

                if (seekerHeight > targetHeight && seekerHeight - targetHeight >= 192) {
                    sender.sendMessage("They are very very far below you.");
                } else if (seekerHeight > targetHeight && seekerHeight - targetHeight >= 128) {
                    sender.sendMessage("They are very far below you.");
                } else if (seekerHeight > targetHeight && seekerHeight - targetHeight >= 64) {
                    sender.sendMessage("They are far below you.");
                } else if (seekerHeight > targetHeight && seekerHeight - targetHeight >= 32) {
                    sender.sendMessage("They are below you.");
                } else if (seekerHeight > targetHeight && seekerHeight - targetHeight >= 16) {
                    sender.sendMessage("They are slightly below you.");
                } else if (seekerHeight > targetHeight && seekerHeight - targetHeight >= 8) {
                	sender.sendMessage("They are very slightly below you.");
                }

                if (seekerHeight < targetHeight && targetHeight - seekerHeight >= 192) {
                    sender.sendMessage("They are very very far above you.");
                } else if (seekerHeight < targetHeight && targetHeight - seekerHeight >= 128) {
                    sender.sendMessage("They are very far above you.");
                } else if (seekerHeight < targetHeight && targetHeight - seekerHeight >= 64) {
                    sender.sendMessage("They are far above you.");
                } else if (seekerHeight < targetHeight && targetHeight - seekerHeight >= 32) {
                    sender.sendMessage("They are above you.");
                } else if (seekerHeight < targetHeight && targetHeight - seekerHeight >= 16) {
                    sender.sendMessage("They are slightly above you.");
                } else if (seekerHeight < targetHeight && targetHeight - seekerHeight >= 8) {
                    sender.sendMessage("They are very slightly above you.");
                }


                sender.sendMessage("Blocks Away: " + distanceToSeeker);
                if (distanceToSeeker < 100) {
                    sender.sendMessage(ChatColor.RED + "Subject is Danger Close!");
                }
            }
            return true;
        }
        return false;
    }
}
