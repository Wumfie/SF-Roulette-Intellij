package com.sfroulette.plugin.gameFunction.npc;

import com.mojang.authlib.GameProfile;
import com.sfroulette.plugin.Main;
import com.sfroulette.plugin.UsefulFunctions;
import com.sfroulette.plugin.gameFunction.GameData;
import com.sfroulette.plugin.gameFunction.GameStart;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.minecraft.server.v1_15_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_15_R1.CraftServer;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class NewNPC {

    private static NewNPC newnpcclass = new NewNPC();

    private NewNPC() { }

    public static NewNPC getInstance( ) {
        return newnpcclass;
    }

    public String newNPC(Location loc) {
        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "TableViewNPC");

        npc.spawn(loc);

        npc.teleport(loc, PlayerTeleportEvent.TeleportCause.COMMAND);

        Player entityPlayer = (Player) npc.getEntity();

        entityPlayer.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 255, false, false));

        entityPlayer.setCollidable(false);

        entityPlayer.teleport(loc);

        entityPlayer.setGravity(false);

        entityPlayer.setSilent(true);

        System.out.println("Created a new NPC");

        return npc.getUniqueId().toString();
    }

    public void loadNPCs(Main main) {
        List<String> armorStands = main.getConfig().getStringList("tables.armorStands");
        for (String standUnformatted : armorStands) {
            if (standUnformatted.split("/")[0].equals("playerNPC")) {
                NPC npc = CitizensAPI.getNPCRegistry().getByUniqueId(UUID.fromString(standUnformatted.split("/")[1]));
                Player entityPlayer = (Player) npc.getEntity();
                entityPlayer.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 255, false, false));
            }
        }
    }

}
