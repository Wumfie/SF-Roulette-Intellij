package com.sfroulette.plugin;

import com.sfroulette.plugin.gameFunction.GameLeave;
import com.sfroulette.plugin.gameFunction.betViewerGUI.OpenBetViewerGUI;
import com.sfroulette.plugin.gameFunction.betting.ReadyToRoll;
import com.sfroulette.plugin.gameFunction.betting.betSelector;
import com.sfroulette.plugin.gameFunction.betting.placeBet;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Roulette implements CommandExecutor {

    Main main;
    public Roulette(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length >= 1) {
                if (args[0].equalsIgnoreCase("leave")) {
                    GameLeave.getInstance().leaveGame(player.getUniqueId().toString());
                } else if (args[0].equalsIgnoreCase("newtable")) {
                    if (player.hasPermission("roulette.admin")){
                        SpawnTable.getInstance().createTable(player);
                    } else {
                        player.sendMessage(ChatColor.RED + "You are lacking the permission node: roulette.admin");
                    }
                } else if (args[0].equalsIgnoreCase("removetable")) {
                    if (player.hasPermission("roulette.admin")){
                        DeleteTable.getInstance().removeTable(player);
                    } else {
                        player.sendMessage(ChatColor.RED + "You are lacking the permission node: roulette.admin");
                    }
                } else if (args[0].equalsIgnoreCase("bet")) {
                    placeBet.getInstance().enterBettingMode(player, main);
                } else if (args[0].equalsIgnoreCase("left")) {
                    betSelector.getInstance().standLocationSelectorLeft(player);
                } else if (args[0].equalsIgnoreCase("right")) {
                    betSelector.getInstance().standLocationSelectorRight(player);
                } else if (args[0].equalsIgnoreCase("up")) {
                    betSelector.getInstance().standLocationSelectorUp(player);
                } else if (args[0].equalsIgnoreCase("down")) {
                    betSelector.getInstance().standLocationSelectorDown(player);
                } else if (args[0].equalsIgnoreCase("showOtherBets")) {
                    OpenBetViewerGUI.getInstance().showBetViewerGUI(player);
                } else if (args[0].equalsIgnoreCase("readyToRoll")) {
                    ReadyToRoll.getInstance().playerReadyToRoll(player, main);
                } else if (args[0].equalsIgnoreCase("notReadyToRoll")) {
                    ReadyToRoll.getInstance().playerNotReadyToRoll(player);
                }
            }
        }
        return false;
    }
}
