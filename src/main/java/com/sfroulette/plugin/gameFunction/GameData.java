package com.sfroulette.plugin.gameFunction;

import com.sfroulette.plugin.Main;
import com.sfroulette.plugin.gameFunction.betting.bettingPlayerObject;
import com.sfroulette.plugin.gameFunction.npc.NewNPC;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameData {
    private static GameData gamedataclass = new GameData();

    private GameData() { }

    public static GameData getInstance( ) {
        return gamedataclass;
    }

    public HashMap<String, Boolean> startedGame = new HashMap<String, Boolean>();
    public HashMap<String, List<String>> players = new HashMap<String, List<String>>();

    public HashMap<String, String> playersInGames = new HashMap<String, String>(); //1 - The player UUID. 2 - The Location of the game

    public HashMap<String, Boolean> bettingMode = new HashMap<String, Boolean>(); //1 - The player UUID. 2 - If they are in betting mode

    public HashMap<String, Location> bettingLastLocation = new HashMap<String, Location>();

    public HashMap<String, Boolean> playersReadyToRoll = new HashMap<String, Boolean>(); //Players that are ready to roll - UUID - Boolean - TRUE/FALSE

    public HashMap<String, Boolean> isTableRolling = new HashMap<String, Boolean>(); //Is a table rolling?

    public HashMap<String, Double> playerTotalBetted = new HashMap<String, Double>(); //How much has a player bet

    //ROULETTE bettingPlayerObjects AND GAMES

    public HashMap<String, List<bettingPlayerObject>> bettingPlayerObjectsWithGames = new HashMap<String, List<bettingPlayerObject>>();

    public List<Integer> redNumbers = new ArrayList<Integer>();

    public void loadTables(Main main) {
        List<String> tables = main.getConfig().getStringList("tables.tableLocations");

        redNumbers.add(1);
        redNumbers.add(3);
        redNumbers.add(5);
        redNumbers.add(7);
        redNumbers.add(9);
        redNumbers.add(12);
        redNumbers.add(14);
        redNumbers.add(16);
        redNumbers.add(18);
        redNumbers.add(19);
        redNumbers.add(21);
        redNumbers.add(23);
        redNumbers.add(25);
        redNumbers.add(27);
        redNumbers.add(30);
        redNumbers.add(32);
        redNumbers.add(34);
        redNumbers.add(36);

        for (String table : tables) {
            startedGame.put(table, false);
        }
    }

}
