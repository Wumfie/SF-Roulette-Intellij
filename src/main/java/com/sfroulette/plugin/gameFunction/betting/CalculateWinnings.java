package com.sfroulette.plugin.gameFunction.betting;

import com.sfroulette.plugin.Main;
import com.sfroulette.plugin.gameFunction.GameData;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CalculateWinnings {
    private static CalculateWinnings calculateWinningsclass = new CalculateWinnings();

    private CalculateWinnings() { }

    public static CalculateWinnings getInstance( ) {
        return calculateWinningsclass;
    }

    public void restartGame(String gameLocation, Main main) {
        GameData.getInstance().isTableRolling.put(gameLocation, false);
        List<bettingPlayerObject> allBettingPlayerObjects = new ArrayList<bettingPlayerObject>();
        for (String allPlayerUUIDs : GameData.getInstance().players.get(gameLocation)) {
            GameData.getInstance().playersReadyToRoll.put(allPlayerUUIDs, false);
            GameData.getInstance().playerTotalBetted.put(allPlayerUUIDs, 0.0);

            Bukkit.getScheduler().runTaskLater(main, new Runnable() {
                public void run() {
                    showPlayerRoundEndMessage(Bukkit.getPlayer(UUID.fromString(allPlayerUUIDs)));
                }
            }, 30L);

            allBettingPlayerObjects.add(new bettingPlayerObject(Bukkit.getPlayer(UUID.fromString(allPlayerUUIDs)).getUniqueId().toString()));
        }
        GameData.getInstance().bettingPlayerObjectsWithGames.put(gameLocation, allBettingPlayerObjects);
    }

    public void showPlayerRoundEndMessage(Player player) {
        player.sendMessage("═════════" + ChatColor.DARK_RED.toString() + ChatColor.BOLD + " S" + ChatColor.GOLD.toString() + ChatColor.BOLD + "F " + ChatColor.WHITE + "Roulette ═════════");
        player.sendMessage("");
        player.sendMessage("Round Finished, Starting another round!");
        player.sendMessage("");
        player.sendMessage("Place your bets:");
        TextComponent message = new TextComponent( ChatColor.GREEN.toString() + ChatColor.BOLD + "[CLICK HERE]" );
        message.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/roulette bet" ) );
        message.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( "Click me to place a bet!" ).create() ) );
        player.spigot().sendMessage(message);
        player.sendMessage("");
        player.sendMessage("═══════ When the Fun Stops, " + ChatColor.RED.toString() + ChatColor.BOLD + "STOP " + ChatColor.WHITE.toString() + ChatColor.RESET + "═══════");
    }

    public void calculateWinnings(String gameLocation, Integer winningNumber, Main main) {
        Boolean isOnRed = GameData.getInstance().redNumbers.contains(winningNumber);
        Boolean isOnBlack = !GameData.getInstance().redNumbers.contains(winningNumber);
        Boolean isLow = winningNumber < 19;
        Boolean isHigh = winningNumber > 18;
        Boolean isOdd = !(winningNumber % 2 == 0);
        Boolean isEven = (winningNumber + 2) % 2 == 0;
        Boolean first12 = (winningNumber + 2) % 3 == 0;
        Boolean second12 = (winningNumber + 1) % 3 == 0;
        Boolean third12 = winningNumber % 3 == 0;
        Boolean firstDozen = winningNumber <= 12;
        Boolean secondDozen = (winningNumber >= 13) && (winningNumber <= 24);
        Boolean thirdDozen = winningNumber >= 25;
        for (String allTablePlayersUUIDs : GameData.getInstance().players.get(gameLocation)) {
            Player player = Bukkit.getPlayer(UUID.fromString(allTablePlayersUUIDs));

            Double reward = 0.0;

            bettingPlayerObject playersBettingObject = null;

            for (bettingPlayerObject bettingPlayerObjectTemp : GameData.getInstance().bettingPlayerObjectsWithGames.get(gameLocation)) {
                if (bettingPlayerObjectTemp.getPlayer().equals(allTablePlayersUUIDs)) {
                    playersBettingObject = bettingPlayerObjectTemp;
                }
            }

            if (playersBettingObject == null) {
                return;
            }

            if (playersBettingObject.betAmountAt_Straight.containsKey(winningNumber)) {
                //Player has won a straight!
                reward = reward + (playersBettingObject.betAmountAt_Straight.get(winningNumber) * 35);
            }

            if (playersBettingObject.betAmountAt_Split.containsKey(winningNumber)) {
                //Player has won a split
                reward = reward + (playersBettingObject.betAmountAt_Split.get(winningNumber) * 17);
            }

            if (playersBettingObject.betAmountAt_Square.containsKey(winningNumber)) {
                //Player has won a square
                reward = reward + (playersBettingObject.betAmountAt_Square.get(winningNumber) * 8);
            }

            if (playersBettingObject.betAmountAt_Street.containsKey(winningNumber)) {
                //Player has won a street
                reward = reward + (playersBettingObject.betAmountAt_Street.get(winningNumber) * 11);
            }

            if (playersBettingObject.betAmountAt_6_Line.containsKey(winningNumber)) {
                //Player has won a line
                reward = reward + (playersBettingObject.betAmountAt_6_Line.get(winningNumber) * 5);
            }

            if (isOnBlack) {
                //Player has won a black
                if (playersBettingObject.betAmountAt_RedBlack.containsKey(1)) {
                    reward = reward + (playersBettingObject.betAmountAt_RedBlack.get(1));
                }
            }

            if (isOnRed) {
                //Player has won a red
                if (playersBettingObject.betAmountAt_RedBlack.containsKey(0)) {
                    reward = reward + (playersBettingObject.betAmountAt_RedBlack.get(0));
                }
            }

            if (isOdd) {
                //Player has won a odd
                if (playersBettingObject.betAmountAt_OddsEven.containsKey(0)) {
                    reward = reward + (playersBettingObject.betAmountAt_OddsEven.get(0));
                }
            }

            if (isEven) {
                //Player has won a even
                if (playersBettingObject.betAmountAt_OddsEven.containsKey(1)) {
                    reward = reward + (playersBettingObject.betAmountAt_OddsEven.get(1));
                }
            }

            if (isLow) {
                //Player has won a low
                if (playersBettingObject.betAmountAt_LowHigh.containsKey(0)) {
                    reward = reward + (playersBettingObject.betAmountAt_LowHigh.get(0));
                }
            }

            if (isHigh) {
                //Player has won a high
                if (playersBettingObject.betAmountAt_LowHigh.containsKey(1)) {
                    reward = reward + (playersBettingObject.betAmountAt_LowHigh.get(1));
                }
            }

            if (first12) {
                //Player has won a 12
                if (playersBettingObject.betAmountAt_Columns.containsKey(1)) {
                    reward = reward + (playersBettingObject.betAmountAt_Columns.get(1) * 2);
                }
            }

            if (second12) {
                //Player has won a 12
                if (playersBettingObject.betAmountAt_Columns.containsKey(2)) {
                    reward = reward + (playersBettingObject.betAmountAt_Columns.get(2) * 2);
                }
            }

            if (third12) {
                //Player has won a 12
                if (playersBettingObject.betAmountAt_Columns.containsKey(3)) {
                    reward = reward + (playersBettingObject.betAmountAt_Columns.get(3) * 2);
                }
            }

            if (firstDozen) {
                //Player has won a dozen
                if (playersBettingObject.betAmountAt_Dozens.containsKey(1)) {
                    reward = reward + (playersBettingObject.betAmountAt_Dozens.get(1) * 2);
                }
            }

            if (secondDozen) {
                //Player has won a dozen
                if (playersBettingObject.betAmountAt_Dozens.containsKey(2)) {
                    reward = reward + (playersBettingObject.betAmountAt_Dozens.get(2) * 2);
                }
            }

            if (thirdDozen) {
                //Player has won a dozen
                if (playersBettingObject.betAmountAt_Dozens.containsKey(3)) {
                    reward = reward + (playersBettingObject.betAmountAt_Dozens.get(3) * 2);
                }
            }

            Double totalWinnings = reward - GameData.getInstance().playerTotalBetted.get(player.getUniqueId().toString());

            if (totalWinnings > 0.0) {
                player.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + "You won " + totalWinnings + " from that game! Well done.");
            } else {
                player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "Sorry, you made no winnings that game...");
            }

        }
        restartGame(gameLocation, main);
    }
}
