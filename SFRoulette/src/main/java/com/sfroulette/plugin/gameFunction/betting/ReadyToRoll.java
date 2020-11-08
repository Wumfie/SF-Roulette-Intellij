package com.sfroulette.plugin.gameFunction.betting;

import com.sfroulette.plugin.Main;
import com.sfroulette.plugin.gameFunction.GameData;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.EulerAngle;

import java.util.UUID;

public class ReadyToRoll {
    private static ReadyToRoll readyToRollclass = new ReadyToRoll();

    private ReadyToRoll() { }

    public static ReadyToRoll getInstance( ) {
        return readyToRollclass;
    }

    int taskID = 0;

    public void playerReadyToRoll(Player player, Main main) {
        if (GameData.getInstance().playersInGames.containsKey(player.getUniqueId().toString())) {
            if (!GameData.getInstance().playersInGames.get(player.getUniqueId().toString()).equals("none")) {
                if (!GameData.getInstance().playersReadyToRoll.get(player.getUniqueId().toString())) {
                    GameData.getInstance().playersReadyToRoll.put(player.getUniqueId().toString(), true);
                    for (String allPlayersUUIDs : GameData.getInstance().players.get(GameData.getInstance().playersInGames.get(player.getUniqueId().toString()))) {
                        Bukkit.getPlayer(UUID.fromString(allPlayersUUIDs)).sendMessage(player.getName() + " is ready to roll. ");
                    }
                    TextComponent message = new TextComponent( ChatColor.RED.toString() + ChatColor.BOLD + "Click here if you are not ready to roll! ✘" );
                    message.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/roulette notReadyToRoll" ) );
                    message.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( "Click me!" ).create() ) );
                    player.spigot().sendMessage(message);
                    checkIfGameReadyToStart(player, main);
                }
            }
        }
    }

    public void playerNotReadyToRoll(Player player) {
        if (!GameData.getInstance().isTableRolling.get(GameData.getInstance().playersInGames.get(player.getUniqueId().toString()))) {
            if (GameData.getInstance().playersInGames.containsKey(player.getUniqueId().toString())) {
                if (!GameData.getInstance().playersInGames.get(player.getUniqueId().toString()).equals("none")) {
                    if (GameData.getInstance().playersReadyToRoll.get(player.getUniqueId().toString())) {
                        GameData.getInstance().playersReadyToRoll.put(player.getUniqueId().toString(), false);
                        for (String allPlayersUUIDs : GameData.getInstance().players.get(GameData.getInstance().playersInGames.get(player.getUniqueId().toString()))) {
                            Bukkit.getPlayer(UUID.fromString(allPlayersUUIDs)).sendMessage(player.getName() + " is no longer ready to roll. ");
                        }
                        Bukkit.getScheduler().cancelTask(taskID);
                    }
                }
            }
        }
    }

    public void checkIfGameReadyToStart(Player player, Main main) {
        for (String allPlayersUUIDs : GameData.getInstance().players.get(GameData.getInstance().playersInGames.get(player.getUniqueId().toString()))) {
            if (!GameData.getInstance().playersReadyToRoll.get(allPlayersUUIDs)) {
                return;
            }
        }
        for (String allPlayersUUIDs : GameData.getInstance().players.get(GameData.getInstance().playersInGames.get(player.getUniqueId().toString()))) {
            Bukkit.getPlayer(UUID.fromString(allPlayersUUIDs)).sendMessage(ChatColor.WHITE.toString() + "Rolling in 3 seconds!");
        }

        BukkitTask startChecker = Bukkit.getScheduler().runTaskLater(main, new Runnable() {
            public void run() {
                for (String allPlayersUUIDs : GameData.getInstance().players.get(GameData.getInstance().playersInGames.get(player.getUniqueId().toString()))) {
                    if (!GameData.getInstance().playersReadyToRoll.get(allPlayersUUIDs)) {
                        return;
                    }
                }
                for (String allPlayersUUIDs : GameData.getInstance().players.get(GameData.getInstance().playersInGames.get(player.getUniqueId().toString()))) {
                    Bukkit.getPlayer(UUID.fromString(allPlayersUUIDs)).sendMessage(ChatColor.GREEN.toString() + "Rolling!");
                }
                //Roll the wheel!
                startRolling.getInstance().tableStartRolling(GameData.getInstance().playersInGames.get(player.getUniqueId().toString()), main);
            }
        },60L);

        taskID = startChecker.getTaskId();
    }
}
