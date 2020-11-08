package com.sfroulette.plugin;

import com.mojang.authlib.GameProfile;
import com.sfroulette.plugin.gameFunction.GameData;
import com.sfroulette.plugin.gameFunction.npc.NewNPC;
import net.minecraft.server.v1_15_R1.EntityPlayer;
import net.minecraft.server.v1_15_R1.PlayerInteractManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_15_R1.CraftServer;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("sfRoulette. Coding done by Wumflord and EatPastry (16/06/2020). - Initialised");

        this.getConfig().options().copyDefaults();
        saveDefaultConfig();
        this.saveConfig();

        this.getCommand("roulette").setExecutor(new Roulette(this));

        Bukkit.getPluginManager().registerEvents(new EventListener(this), this);

        RepeatingEvent.getInstance().startLoop(this);

        if (!this.getConfig().contains("tables.armorStands")) {
            List<String> blankArray = new ArrayList<String>();
            this.getConfig().set("tables.armorStands", blankArray);

        }
        if (!this.getConfig().contains("tables.tableLocations")) {
            List<String> blankArray = new ArrayList<String>();
            this.getConfig().set("tables.tableLocations", blankArray);
        }
        this.saveConfig();

        GameData.getInstance().loadTables(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }
}
