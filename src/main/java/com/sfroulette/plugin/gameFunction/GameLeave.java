package com.sfroulette.plugin.gameFunction;

import com.comphenix.packetwrapper.WrapperPlayServerEntityDestroy;
import com.sfroulette.plugin.UsefulFunctions;
import com.sfroulette.plugin.gameFunction.betting.betSelector;
import com.sfroulette.plugin.gameFunction.betting.bettingPlayerObject;
import com.sfroulette.plugin.gameFunction.betting.clientSideArmorStand;
import com.sfroulette.plugin.gameFunction.betting.placeBet;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameLeave {
    private static GameLeave gamejoinclass = new GameLeave();

    private GameLeave() { }

    public static GameLeave getInstance( ) {
        return gamejoinclass;
    }

    public void leaveGame(String playerUUID) {
        Player player = null;
        if (Bukkit.getPlayer(UUID.fromString(playerUUID)) != null) {
            player = Bukkit.getPlayer(UUID.fromString(playerUUID));
        }

        String currentGame = GameData.getInstance().playersInGames.get(playerUUID);
        if (currentGame != "none") {
            GameData.getInstance().playersInGames.put(playerUUID, "none");
            GameData.getInstance().bettingMode.put(playerUUID, false);
            GameData.getInstance().playersReadyToRoll.put(playerUUID, false);
            GameData.getInstance().playerTotalBetted.put(player.getUniqueId().toString(), 0.0);
            if (clientSideArmorStand.getInstance().standID.containsKey(player.getUniqueId().toString())) {
                clientSideArmorStand.getInstance().standID.remove(player.getUniqueId().toString());
            }

            if (player != null) {
                player.sendMessage("Left the current game!");
            }
            List<String> playersInGame = GameData.getInstance().players.get(currentGame);
            playersInGame.remove(playerUUID);
            GameData.getInstance().players.put(currentGame, playersInGame);

            List<bettingPlayerObject> bettingPlayerObjectList = GameData.getInstance().bettingPlayerObjectsWithGames.get(currentGame);
            bettingPlayerObjectList.removeIf(bettingPlayerObjectTemp -> bettingPlayerObjectTemp.getPlayer().equals(playerUUID));
            GameData.getInstance().bettingPlayerObjectsWithGames.put(currentGame, bettingPlayerObjectList);

            for (String pUUID : GameData.getInstance().players.get(currentGame)) {
                Bukkit.getPlayer(UUID.fromString(pUUID)).sendMessage(player.getDisplayName() + " left your game!");
            }

            if (playersInGame.size() == 0) { //Game has no players so is ended
                GameData.getInstance().startedGame.put(currentGame, false);
            }
        } else {
            if (player != null) {
                player.sendMessage("No game to leave.");
            }
        }
    }
}
