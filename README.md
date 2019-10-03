## HuntPlayer
Author: LlmDl

Version: 1.0

HuntPlayer is a simple plugin for Bukkit that allows players to use /hunt playername to receive limited information on the whereabouts of other players on the server.

HuntPlayer will show the hunter cardinal directions to the player being hunted, how many blocks away the player is and varying height messages, as long as they are in the same world as the hunter. Only the world the hunted is in is given to the hunter when the two players do not occupy the same world.

Another novel feature of HuntPlayer is that spectating and invisible players’ coordinates are not shown.

If you want to support the developer consider becoming a patron: 
[<img align=right src="https://user-images.githubusercontent.com/879756/65957602-d3795800-e412-11e9-8b27-dda76b6fed13.PNG">](https://www.patreon.com/bePatron?u=25096724)

### Config:
prefix:  A string which defines the prefix used in the plugin output.

### Commands:
/hunt – Displays the hunter’s coordinates.

/hunt {playername} – Displays information of a hunted player.

### Permission node:
– huntplayer.hunt – allows a player to use the hunt command.

### Changelog:
1.0: 

  - Initial release on Spigot.

0.0.5: 

  - Update to 1.14 and new Towny API.

0.0.4: 

  – Made players who are in spectator mode appear invisible when hunted.

0.0.3: 

  – Added town name and plot names to towny output.

0.0.2: 

  – Added Towny integration. Delete old config or add ‘usingtowny: true’. Will show Wilderness or Town a hunted player is in.

0.0.1: 

  – Initial Release.
