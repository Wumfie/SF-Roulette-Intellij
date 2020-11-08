package com.sfroulette.plugin.gameFunction.betting;

import com.sfroulette.plugin.Main;
import com.sfroulette.plugin.gameFunction.GameData;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.Hash;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class startRolling {
    private static startRolling startrollingclass = new startRolling();

    private startRolling() { }

    public static startRolling getInstance( ) {
        return startrollingclass;
    }

    HashMap<String, Integer> currentNumber = new HashMap<String, Integer>();

    HashMap<String, Integer> totalLoopsToRun = new HashMap<String, Integer>();

    HashMap<String, Integer> delay = new HashMap<String, Integer>();

    HashMap<String, Integer> slowerDelay = new HashMap<String, Integer>();

    public void tableStartRolling(String tableString, Main main) {
        GameData.getInstance().isTableRolling.put(tableString, true);

        Random rand = new Random();

        int n = rand.nextInt(36);

        n += 100;

        currentNumber.put(tableString, 0);
        delay.put(tableString, 1);

        totalLoopsToRun.put(tableString, n);

        updateRollingCounter(tableString, main);
        slowRollingCounter(tableString, main);
    }

    public void updateRollingCounter(String tableLocation, Main main) {
        totalLoopsToRun.put(tableLocation, totalLoopsToRun.get(tableLocation) - 1);
        if (totalLoopsToRun.get(tableLocation) < 0) {
            return;
        }
        currentNumber.put(tableLocation, currentNumber.get(tableLocation) + 1);
        if (currentNumber.get(tableLocation) > 36) {
            currentNumber.put(tableLocation, 0);
        }
        for (String allPlayersUUIDS : GameData.getInstance().players.get(tableLocation)) {
            Player player = Bukkit.getPlayer(UUID.fromString(allPlayersUUIDS));
            String colour = ChatColor.RED.toString();
            if (totalLoopsToRun.get(tableLocation) % 2 == 0) {
                colour = ChatColor.GREEN.toString();
            }
            player.sendTitle(colour + "↠" + ChatColor.WHITE +  currentNumber.get(tableLocation) + colour + "↞", ChatColor.GREEN + "spinning" ,0, 100, 1);
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
            if (totalLoopsToRun.get(tableLocation) == 0) {
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                player.sendMessage(ChatColor.BLUE.toString() + ChatColor.BOLD + "The spinner landed on " + ChatColor.GREEN + currentNumber.get(tableLocation) + ChatColor.BLUE.toString() + ChatColor.BOLD + "!");
            }
        }
        if (totalLoopsToRun.get(tableLocation) == 0) {
            CalculateWinnings.getInstance().calculateWinnings(tableLocation, currentNumber.get(tableLocation), main);
        }
        Bukkit.getScheduler().runTaskLater(main, new Runnable() {
            public void run() {
                updateRollingCounter(tableLocation, main);
            }
        }, delay.get(tableLocation));
    }

    public void slowRollingCounter(String tableLocation, Main main) {
        if (totalLoopsToRun.get(tableLocation) < 0) {
            return;
        }
        delay.put(tableLocation, delay.get(tableLocation) + 1);
        Bukkit.getScheduler().runTaskLater(main, new Runnable() {
            public void run() {
                slowRollingCounter(tableLocation, main);
            }
        },90L);
    }

}
