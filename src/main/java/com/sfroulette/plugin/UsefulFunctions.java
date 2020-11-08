package com.sfroulette.plugin;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class UsefulFunctions {
    private static UsefulFunctions usefulfunctionsclass = new UsefulFunctions();

    private UsefulFunctions() { }

    public static UsefulFunctions getInstance( ) {
        return usefulfunctionsclass;
    }

    public boolean checkLocationCoords(Location loc1, Location loc2) {
        System.out.println(" X " + loc1.getX() + " X " + loc2.getX() + " Y " +  loc1.getY() + " Y " +  loc2.getY() + " Z " +  loc1.getZ() + " Z " +  loc2.getZ() + " W " + loc1.getWorld().toString() + loc2.getWorld().toString());
        if (loc1.getX() == loc2.getX() && loc1.getY() == loc2.getY() && loc1.getZ() == loc2.getZ() && loc1.getWorld() == loc2.getWorld()) {
            return true;
        }
        return false;
    }

    public Location stringToLocation(String string) {
        String[] parts = string.split(":");
        Location location = new Location(Bukkit.getServer().getWorld(parts[3]), Double.parseDouble(parts[0]),
                Double.parseDouble(parts[1]), Double.parseDouble(parts[2]));
        return location;
    }

    public String locationToString(Location location) {
        String string = location.getX() + ":" + location.getY() + ":" + location.getZ() + ":" + location.getWorld().getName();
        return string;
    }

    public boolean checkStandLocationFromConfig(String standWithLocationFromConfig, Location targetLocation) { //Takes values like this: "e089e915-f53a-4833-bdf0-748647906128/340.0:64.0:-2470.0:world" and compare the location
        String[] tableStand = standWithLocationFromConfig.split("/");

        String tempStandUUID = tableStand[1];
        String tempSignLocationUnformatted = tableStand[2];

        Location tableSignLoc2 = stringToLocation(tempSignLocationUnformatted);

        if (UsefulFunctions.getInstance().checkLocationCoords(tableSignLoc2, targetLocation)) {
            return true;
        }
        return false;
    }

    public void removeStandFromConfigAndWorld(String stand, Main main) {
        String[] tableStand = stand.split("/");

        String tempStandUUID = tableStand[1];

        if (tableStand[0].equals("playerNPC")) {
            NPC npc = CitizensAPI.getNPCRegistry().getByUniqueId(UUID.fromString(tempStandUUID));
            npc.destroy();
        } else {
            if (Bukkit.getEntity(UUID.fromString(tempStandUUID)) != null) {
                Bukkit.getEntity(UUID.fromString(tempStandUUID)).remove();
            }
        }

        List<String> armorStands = main.getConfig().getStringList("tables.armorStands");
        armorStands.remove(stand);
        main.getConfig().set("tables.armorStands", armorStands);
        main.saveConfig();
    }
}
