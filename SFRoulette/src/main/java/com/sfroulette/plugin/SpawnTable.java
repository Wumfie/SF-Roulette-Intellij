package com.sfroulette.plugin;

import com.sfroulette.plugin.gameFunction.GameData;
import com.sfroulette.plugin.gameFunction.npc.NewNPC;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.EulerAngle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SpawnTable {
    HashMap<String, Boolean> playersWaitingForClick = new HashMap<String, Boolean>();

    private static SpawnTable spawntableclass = new SpawnTable();

    private SpawnTable() { }

    public static SpawnTable getInstance( ) {
        return spawntableclass;
    }

    public void createTable(Player player) {
        player.sendMessage(ChatColor.YELLOW + "Please click the bottom right block of the table.");
        playersWaitingForClick.put(player.getUniqueId().toString(), true);
    }

    public void registerClick(Player player, Block block, Main main) {
        playersWaitingForClick.put(player.getUniqueId().toString(), false);

        Location tableStandLocation = block.getLocation();
        tableStandLocation.setX(tableStandLocation.getBlockX() - 0.629);
        tableStandLocation.setY(tableStandLocation.getBlockY() - 0.891);
        tableStandLocation.setZ(tableStandLocation.getBlockZ() + 0.19);

        Location wheelStandLocation = block.getLocation();
        wheelStandLocation.setX(wheelStandLocation.getBlockX() - 2.37);
        wheelStandLocation.setY(wheelStandLocation.getBlockY() - 0.65);
        wheelStandLocation.setZ(wheelStandLocation.getBlockZ() + 0.35);

        Location tableLocation = block.getLocation();
        tableLocation.setX(tableLocation.getBlockX() - 0.4);
        tableLocation.setY(tableLocation.getBlockY() + 0.2);
        tableLocation.setZ(tableLocation.getBlockZ() + 0.0);
        tableLocation.setPitch(90F);
        tableLocation.setYaw(180F);

        String signLocation = spawnBlocks(block.getLocation(), main);

        List<String> armorStands = main.getConfig().getStringList("tables.armorStands");
        List<String> tableSigns = main.getConfig().getStringList("tables.tableLocations");

        armorStands.add("table/" + spawnTable(tableStandLocation) + "/" + signLocation);
        armorStands.add("wheel/" + spawnWheel(wheelStandLocation) + "/" + signLocation);
        armorStands.add("playerNPC/" + NewNPC.getInstance().newNPC(tableLocation) + "/" + signLocation);
        tableSigns.add(signLocation);

        main.getConfig().set("tables.armorStands", armorStands);
        main.getConfig().set("tables.tableLocations", tableSigns);

        GameData.getInstance().startedGame.put(signLocation, false);

        player.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + "Table Created");
        player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "NOTE: Don't lose your money like i did...");
        main.saveConfig();
    }

    public String spawnTable(Location loc) {
        ArmorStand as = (ArmorStand) loc.getWorld().spawn(loc, ArmorStand.class);
        as.setArms(true);
        as.setVisible(false);
        as.setGravity(false);
        as.setBasePlate(false);
        as.setInvulnerable(true);
        as.setItemInHand(new ItemStack(Material.PETRIFIED_OAK_SLAB));
        as.setLeftArmPose(new EulerAngle(Math.toRadians(360), Math.toRadians(360), Math.toRadians(360)));
        as.setRightArmPose(new EulerAngle(Math.toRadians(360), Math.toRadians(360), Math.toRadians(360)));
        as.setRotation(0F, 0F);
        return as.getUniqueId().toString();
    }

    public String spawnWheel(Location loc) {
        ArmorStand as = (ArmorStand) loc.getWorld().spawn(loc, ArmorStand.class);
        as.setArms(true);
        as.setVisible(false);
        as.setGravity(false);
        as.setBasePlate(false);
        as.setInvulnerable(true);
        ItemStack wheel = new ItemStack(Material.IRON_INGOT);
        ItemMeta wheelMeta = wheel.getItemMeta();
        wheelMeta.setCustomModelData(1234567);
        wheel.setItemMeta(wheelMeta);
        as.setHelmet(wheel);
        as.setRotation(0F, 0F);
        return as.getUniqueId().toString();
    }

    public String spawnBlocks(Location loc, Main main) {
        World world = loc.getWorld();
        world.getBlockAt(loc).setType(Material.BARRIER);
        loc.setX(loc.getBlockX() - 1);
        world.getBlockAt(loc).setType(Material.BARRIER);
        loc.setX(loc.getBlockX() - 1);
        world.getBlockAt(loc).setType(Material.BARRIER);
        loc.setX(loc.getBlockX() - 1);
        world.getBlockAt(loc).setType(Material.BARRIER);
        loc.setZ(loc.getBlockZ() - 1);
        world.getBlockAt(loc).setType(Material.BARRIER);
        loc.setX(loc.getBlockX() + 1);
        world.getBlockAt(loc).setType(Material.BARRIER);
        loc.setX(loc.getBlockX() + 1);
        world.getBlockAt(loc).setType(Material.BARRIER);
        loc.setX(loc.getBlockX() + 1);
        world.getBlockAt(loc).setType(Material.BARRIER);
        loc.setX(loc.getBlockX() + 1);
        loc.setZ(loc.getBlockZ() + 1);

        //Spawn in sign
        Block block = world.getBlockAt(loc);
        block.setType(Material.DARK_OAK_WALL_SIGN);

        Sign sign = (Sign)block.getState();
        sign.setLine(1, ChatColor.WHITE.toString() + ChatColor.BOLD + "Join Game");
        sign.setLine(2, ChatColor.WHITE + "CLICK");

        WallSign wallsign = (WallSign)block.getBlockData();
        wallsign.setFacing(BlockFace.EAST);

        block.setBlockData(wallsign);
        sign.update(true);

        //Get condensed sign location
        String locationString = UsefulFunctions.getInstance().locationToString(loc);

        //Return condensed sign location
        return locationString;
    }
}