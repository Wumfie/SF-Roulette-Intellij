package com.sfroulette.plugin.gameFunction.betViewerGUI;

import com.sfroulette.plugin.gameFunction.GameData;
import com.sfroulette.plugin.gameFunction.GameLeave;
import com.sfroulette.plugin.gameFunction.betting.bettingPlayerObject;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OpenBetViewerGUI {

    private static OpenBetViewerGUI openBetViewerGUIclass = new OpenBetViewerGUI();

    private OpenBetViewerGUI() { }

    public static OpenBetViewerGUI getInstance( ) {
        return openBetViewerGUIclass;
    }

    public void showBetViewerGUI(Player player) {
        Inventory gui = Bukkit.createInventory(null, 45, ChatColor.DARK_GREEN + "Viewing Current Bets: ");
        if (GameData.getInstance().playersInGames.containsKey(player.getUniqueId().toString())) {
            if (!GameData.getInstance().playersInGames.get(player.getUniqueId().toString()).equals("none")) {
                String playerGameLocationString = GameData.getInstance().playersInGames.get(player.getUniqueId().toString());
                for (String pUUID : GameData.getInstance().players.get(playerGameLocationString)) {
                    if (getFormattedPlayerStatsHeadFromUUID(pUUID, playerGameLocationString) != null) {
                        ItemStack playerHeadToDisplay = getFormattedPlayerStatsHeadFromUUID(pUUID, playerGameLocationString);
                        Integer indexCount = GameData.getInstance().players.get(playerGameLocationString).indexOf(pUUID);

                        gui.setItem(indexCount, playerHeadToDisplay);
                    }
                }
                player.openInventory(gui);
            }
        }
    }

    public ItemStack getFormattedPlayerStatsHeadFromUUID(String pUUID, String playerGameLocationString) {
        if (Bukkit.getPlayer(UUID.fromString(pUUID)) != null) {
            Player playerToDisplay = Bukkit.getPlayer(UUID.fromString(pUUID));

            ItemStack playerStatsItemStack = new ItemStack(Material.PLAYER_HEAD);

            ItemMeta playerStatsItemStackMeta = playerStatsItemStack.getItemMeta();

            playerStatsItemStackMeta.setDisplayName(ChatColor.RED.toString() + ChatColor.BOLD + playerToDisplay.getName());


            List<bettingPlayerObject> bettingPlayerObjectList = GameData.getInstance().bettingPlayerObjectsWithGames.get(playerGameLocationString);

            bettingPlayerObject bettingPlayerObjectToEdit = null;

            for (bettingPlayerObject bettingPlayerObjectData : bettingPlayerObjectList) {
                if (bettingPlayerObjectData.getPlayer().equals(playerToDisplay.getUniqueId().toString())) {
                    bettingPlayerObjectToEdit = bettingPlayerObjectData;
                }
            }

            List<String> mainLore = new ArrayList<String>();

            List<String> loreSingles = new ArrayList<String>();
            List<String> loreSplit = new ArrayList<String>();
            List<String> loreSquare = new ArrayList<String>();
            List<String> loreStreet = new ArrayList<String>();
            List<String> lore6Line = new ArrayList<String>();


            loreSingles.add(ChatColor.BLUE + "Singles: ");
            loreSplit.add(ChatColor.BLUE + "Splits: ");
            loreSquare.add(ChatColor.BLUE + "Squares: ");
            loreStreet.add(ChatColor.BLUE + "Streets: ");
            lore6Line.add(ChatColor.BLUE + "Lines: ");

            if (bettingPlayerObjectToEdit != null) {
                for (int counter = 0; counter < 37; counter++) {
                    if (bettingPlayerObjectToEdit.betAmountAt_Straight.containsKey(counter)) {
                        loreSingles.add(ChatColor.WHITE + "[" + counter + "] :  + " + bettingPlayerObjectToEdit.betAmountAt_Straight.get(counter).toString());
                    }
                    if (bettingPlayerObjectToEdit.betAmountAt_Split.containsKey(counter)) {
                        loreSplit.add(ChatColor.WHITE + "[" + counter + "] :  + " + bettingPlayerObjectToEdit.betAmountAt_Split.get(counter).toString());
                    }
                    if (bettingPlayerObjectToEdit.betAmountAt_Square.containsKey(counter)) {
                        loreSquare.add(ChatColor.WHITE + "[" + counter + "] :  + " + bettingPlayerObjectToEdit.betAmountAt_Square.get(counter).toString());
                    }
                    if (bettingPlayerObjectToEdit.betAmountAt_Street.containsKey(counter)) {
                        loreStreet.add(ChatColor.WHITE + "[" + counter + "] :  + " + bettingPlayerObjectToEdit.betAmountAt_Street.get(counter).toString());
                    }
                    if (bettingPlayerObjectToEdit.betAmountAt_6_Line.containsKey(counter)) {
                        lore6Line.add(ChatColor.WHITE + "[" + counter + "] :  + " + bettingPlayerObjectToEdit.betAmountAt_6_Line.get(counter).toString());
                    }
                }

                mainLore.addAll(loreSingles);
                mainLore.addAll(loreSplit);
                mainLore.addAll(loreSquare);
                mainLore.addAll(loreStreet);
                mainLore.addAll(lore6Line);

                if (bettingPlayerObjectToEdit.betAmountAt_RedBlack.containsKey(0)) {
                    mainLore.add(ChatColor.BLUE + "Red: " + ChatColor.WHITE + bettingPlayerObjectToEdit.betAmountAt_RedBlack.get(0).toString());
                }

                if (bettingPlayerObjectToEdit.betAmountAt_RedBlack.containsKey(1)) {
                    mainLore.add(ChatColor.BLUE + "Black: " + ChatColor.WHITE + bettingPlayerObjectToEdit.betAmountAt_RedBlack.get(1).toString());
                }

                if (bettingPlayerObjectToEdit.betAmountAt_OddsEven.containsKey(0)) {
                    mainLore.add(ChatColor.BLUE + "Odds: " + ChatColor.WHITE + bettingPlayerObjectToEdit.betAmountAt_OddsEven.get(0).toString());
                }

                if (bettingPlayerObjectToEdit.betAmountAt_OddsEven.containsKey(1)) {
                    mainLore.add(ChatColor.BLUE + "Evens: " + ChatColor.WHITE + bettingPlayerObjectToEdit.betAmountAt_OddsEven.get(1).toString());
                }

                if (bettingPlayerObjectToEdit.betAmountAt_LowHigh.containsKey(0)) {
                    mainLore.add(ChatColor.BLUE + "Low: " + ChatColor.WHITE + bettingPlayerObjectToEdit.betAmountAt_LowHigh.get(0).toString());
                }

                if (bettingPlayerObjectToEdit.betAmountAt_LowHigh.containsKey(1)) {
                    mainLore.add(ChatColor.BLUE + "High: " + ChatColor.WHITE + bettingPlayerObjectToEdit.betAmountAt_LowHigh.get(1).toString());
                }

                if (bettingPlayerObjectToEdit.betAmountAt_Columns.containsKey(1)) {
                    mainLore.add(ChatColor.BLUE + "Column 1: " + ChatColor.WHITE + bettingPlayerObjectToEdit.betAmountAt_Columns.get(1).toString());
                }

                if (bettingPlayerObjectToEdit.betAmountAt_Columns.containsKey(2)) {
                    mainLore.add(ChatColor.BLUE + "Column 2: " + ChatColor.WHITE + bettingPlayerObjectToEdit.betAmountAt_Columns.get(2).toString());
                }

                if (bettingPlayerObjectToEdit.betAmountAt_Columns.containsKey(3)) {
                    mainLore.add(ChatColor.BLUE + "Column 3: " + ChatColor.WHITE + bettingPlayerObjectToEdit.betAmountAt_Columns.get(3).toString());
                }

                if (bettingPlayerObjectToEdit.betAmountAt_Dozens.containsKey(1)) {
                    mainLore.add(ChatColor.BLUE + "Dozen 1: " + ChatColor.WHITE + bettingPlayerObjectToEdit.betAmountAt_Dozens.get(1).toString());
                }

                if (bettingPlayerObjectToEdit.betAmountAt_Dozens.containsKey(2)) {
                    mainLore.add(ChatColor.BLUE + "Dozen 2: " + ChatColor.WHITE + bettingPlayerObjectToEdit.betAmountAt_Dozens.get(2).toString());
                }

                if (bettingPlayerObjectToEdit.betAmountAt_Dozens.containsKey(3)) {
                    mainLore.add(ChatColor.BLUE + "Dozen 3: " + ChatColor.WHITE + bettingPlayerObjectToEdit.betAmountAt_Dozens.get(3).toString());
                }

                playerStatsItemStackMeta.setLore(mainLore);

                SkullMeta playerStatsSkullMeta = (SkullMeta) playerStatsItemStackMeta;

                playerStatsSkullMeta.setOwner(playerToDisplay.getName());

                playerStatsItemStack.setItemMeta(playerStatsItemStackMeta);
            } else {
                System.out.println("bettingPlayerObjectToEdit is null");
            }

            return playerStatsItemStack;

        }
        return null;
    }
}
