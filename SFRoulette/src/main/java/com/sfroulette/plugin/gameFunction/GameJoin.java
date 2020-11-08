package com.sfroulette.plugin.gameFunction;

import com.sfroulette.plugin.Main;
import com.sfroulette.plugin.SpawnTable;
import com.sfroulette.plugin.UsefulFunctions;
import com.sfroulette.plugin.gameFunction.betting.bettingPlayerObject;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class GameJoin {
    private static GameJoin gamejoinclass = new GameJoin();

    private GameJoin() { }

    public static GameJoin getInstance( ) {
        return gamejoinclass;
    }

    public void JoinGame(Player player, Main main, Location signLoc) {
        for (String tablebigloc : main.getConfig().getStringList("tables.tableLocations")) {

            Location tableSignLoc = UsefulFunctions.getInstance().stringToLocation(tablebigloc);

            if (UsefulFunctions.getInstance().checkLocationCoords(tableSignLoc, signLoc)) { //Clicked a valid sign - Check if player is already in a game
                if (GameData.getInstance().playersInGames.get(player.getUniqueId().toString()).equals("none")) { //Player is not already in a game
                    if (GameData.getInstance().startedGame.get(tablebigloc)) {
                        player.sendMessage("Attempting to join game...");
                        if (GameData.getInstance().players.get(tablebigloc).size() <= 3) {
                            player.sendMessage("Joining game");

                            for (String pUUID : GameData.getInstance().players.get(tablebigloc)) {
                                Bukkit.getPlayer(UUID.fromString(pUUID)).sendMessage(player.getDisplayName() + " has joined your game!");
                            }

                            GameData.getInstance().playersInGames.put(player.getUniqueId().toString(), tablebigloc);

                            GameData.getInstance().playersReadyToRoll.put(player.getUniqueId().toString(), false);

                            GameData.getInstance().playerTotalBetted.put(player.getUniqueId().toString(), 0.0);

                            List<String> playerList = GameData.getInstance().players.get(tablebigloc);
                            playerList.add(player.getUniqueId().toString());
                            GameData.getInstance().players.put(tablebigloc, playerList);

                            List<bettingPlayerObject> bettingPlayerObjectList = new ArrayList<bettingPlayerObject>();
                            bettingPlayerObjectList.add(new bettingPlayerObject(player.getUniqueId().toString()));
                            if (GameData.getInstance().bettingPlayerObjectsWithGames.containsKey(tablebigloc)) {
                                bettingPlayerObjectList.addAll(GameData.getInstance().bettingPlayerObjectsWithGames.get(tablebigloc));
                            }
                            GameData.getInstance().bettingPlayerObjectsWithGames.put(tablebigloc, bettingPlayerObjectList);

                            welcomePlayer(player, tablebigloc);
                        } else {
                            player.sendMessage( ChatColor.RED.toString() + ChatColor.BOLD + "Game is full!");
                        }
                    } else {
                        player.sendMessage( ChatColor.GREEN.toString() + ChatColor.BOLD + "Starting a new game");

                        GameStart.getInstance().StartGame(player, signLoc);

                        GameData.getInstance().playersInGames.put(player.getUniqueId().toString(), tablebigloc);

                        welcomePlayer(player, tablebigloc);
                    }
                } else {
                    player.sendMessage( ChatColor.RED + "You are already in a game. Please leave the current one with" + ChatColor.WHITE.toString() + ChatColor.BOLD + " /roulette leave");
                }
            }
        }
    }

    public void welcomePlayer(Player player, String locationCondensed) {
        player.sendMessage("═══════ Welcome to" + ChatColor.DARK_RED.toString() + ChatColor.BOLD + " S" + ChatColor.GOLD.toString() + ChatColor.BOLD + "F " + ChatColor.WHITE + "Roulette ═══════");
        player.sendMessage("");
        List<String> players = GameData.getInstance().players.get(locationCondensed);
        int playerCount = GameData.getInstance().players.get(locationCondensed).size();
        if (playerCount == 1) {
            player.sendMessage("Looks like you're the only one playing.");
        } else {
            player.sendMessage("You will be playing with: ");
            for (String playerUUID : players) {
                String playerdisplayname = Bukkit.getPlayer(UUID.fromString(playerUUID)).getName();
                if (!playerdisplayname.equals(player.getName())) {
                    player.sendMessage(ChatColor.WHITE.toString() + ChatColor.BOLD + " ➙ " + ChatColor.BOLD.toString() + ChatColor.GRAY + playerdisplayname);
                }
            }
        }
        player.sendMessage("");
        player.sendMessage("Place your bets:");
        TextComponent message = new TextComponent( ChatColor.GREEN.toString() + ChatColor.BOLD + "[CLICK HERE]" );
        message.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/roulette bet" ) );
        message.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( "Click me to place a bet!" ).create() ) );
        player.spigot().sendMessage(message);
        player.sendMessage("");
        player.sendMessage("═══════ When the Fun Stops, " + ChatColor.RED.toString() + ChatColor.BOLD + "STOP " + ChatColor.WHITE.toString() + ChatColor.RESET + "═══════");
    }

}
