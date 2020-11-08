package com.sfroulette.plugin.gameFunction;

import com.sfroulette.plugin.SpawnTable;
import com.sfroulette.plugin.UsefulFunctions;
import com.sfroulette.plugin.gameFunction.betting.bettingPlayerObject;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameStart {
    private static GameStart gamestartclass = new GameStart();

    private GameStart() { }

    public static GameStart getInstance( ) {
        return gamestartclass;
    }

    public void StartGame(Player player, Location location) {
        GameData.getInstance().startedGame.put(UsefulFunctions.getInstance().locationToString(location), true);
        List<String> playerList = new ArrayList<String>();
        playerList.add(player.getUniqueId().toString());
        GameData.getInstance().players.put(UsefulFunctions.getInstance().locationToString(location), playerList);

        GameData.getInstance().playersReadyToRoll.put(player.getUniqueId().toString(), false);

        GameData.getInstance().isTableRolling.put(UsefulFunctions.getInstance().locationToString(location), false);

        GameData.getInstance().playerTotalBetted.put(player.getUniqueId().toString(), 0.0);

        List<bettingPlayerObject> bettingPlayerObjectList = new ArrayList<bettingPlayerObject>();
        bettingPlayerObjectList.add(new bettingPlayerObject(player.getUniqueId().toString()));
        if (GameData.getInstance().bettingPlayerObjectsWithGames.containsKey(UsefulFunctions.getInstance().locationToString(location))) {
            bettingPlayerObjectList.addAll(GameData.getInstance().bettingPlayerObjectsWithGames.get(UsefulFunctions.getInstance().locationToString(location)));
        }
        GameData.getInstance().bettingPlayerObjectsWithGames.put(UsefulFunctions.getInstance().locationToString(location), bettingPlayerObjectList);
    }

}
