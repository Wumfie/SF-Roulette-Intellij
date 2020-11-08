package com.sfroulette.plugin;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class DeleteTable {
    HashMap<String, Boolean> playersWaitingForClick = new HashMap<String, Boolean>();

    private static DeleteTable deletetableclass = new DeleteTable();

    private DeleteTable() { }

    public static DeleteTable getInstance( ) {
        return deletetableclass;
    }

    public void removeTable(Player player) {
        player.sendMessage(ChatColor.YELLOW + "Please click the sign at the end of the table.");
        playersWaitingForClick.put(player.getUniqueId().toString(), true);
    }

    public void registerClick(Player player, Location loc, Main main) {
        for (String tablebigloc : main.getConfig().getStringList("tables.tableLocations")) {
            Location tableSignLoc = UsefulFunctions.getInstance().stringToLocation(tablebigloc);

            if (UsefulFunctions.getInstance().checkLocationCoords(tableSignLoc, loc)) {
                removeBlocks(loc);

                List<String> standUUIDs = main.getConfig().getStringList("tables.armorStands");

                for (String stands : standUUIDs) {
                    if (UsefulFunctions.getInstance().checkStandLocationFromConfig(stands, loc)) {
                        System.out.println("Found one to Remove!");
                        UsefulFunctions.getInstance().removeStandFromConfigAndWorld(stands, main);
                    }
                }

                List<String> tableLocations = main.getConfig().getStringList("tables.tableLocations");
                tableLocations.remove(tablebigloc);
                main.getConfig().set("tables.tableLocations", tableLocations);
                main.saveConfig();

                player.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + "Table Deleted");

                playersWaitingForClick.put(player.getUniqueId().toString(), false);
                return;
            }
        }
        player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "No Table Found");
        playersWaitingForClick.put(player.getUniqueId().toString(), false);
    }



    private static void removeBlocks(Location locLIDL) {
        Location locASDA = new Location(Bukkit.getWorld("world"), 0, 0, 0, 0, 0);
        locASDA.setX(locLIDL.getX());
        locASDA.setY(locLIDL.getY());
        locASDA.setZ(locLIDL.getZ());
        locASDA.setWorld(locLIDL.getWorld());

        locASDA.getWorld().getBlockAt(locASDA).setType(Material.AIR); //Sets sign to
        //Barriers on 1st row
        locASDA.setX(locASDA.getX() - 1);
        locASDA.getWorld().getBlockAt(locASDA).setType(Material.AIR);
        locASDA.setX(locASDA.getX() - 1);
        locASDA.getWorld().getBlockAt(locASDA).setType(Material.AIR);
        locASDA.setX(locASDA.getX() - 1);
        locASDA.getWorld().getBlockAt(locASDA).setType(Material.AIR);
        locASDA.setX(locASDA.getX() - 1);
        locASDA.getWorld().getBlockAt(locASDA).setType(Material.AIR);
        //Barriers on 2nd row
        locASDA.setZ(locASDA.getZ() - 1);
        locASDA.getWorld().getBlockAt(locASDA).setType(Material.AIR);
        locASDA.setX(locASDA.getX() + 1);
        locASDA.getWorld().getBlockAt(locASDA).setType(Material.AIR);
        locASDA.setX(locASDA.getX() + 1);
        locASDA.getWorld().getBlockAt(locASDA).setType(Material.AIR);
        locASDA.setX(locASDA.getX() + 1);
        locASDA.getWorld().getBlockAt(locASDA).setType(Material.AIR);
    }
}
