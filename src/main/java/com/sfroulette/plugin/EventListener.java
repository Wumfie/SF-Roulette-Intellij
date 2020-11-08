package com.sfroulette.plugin;

import com.sfroulette.plugin.gameFunction.GameData;
import com.sfroulette.plugin.gameFunction.GameJoin;
import com.sfroulette.plugin.gameFunction.GameLeave;
import com.sfroulette.plugin.gameFunction.betting.placeBet;
import com.sfroulette.plugin.gameFunction.betting.SubmitBet;
import com.sfroulette.plugin.gameFunction.npc.NewNPC;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;

public class EventListener implements Listener {

    private Main main;

    public EventListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent join) {
        SpawnTable.getInstance().playersWaitingForClick.put(join.getPlayer().getUniqueId().toString(), false);
        DeleteTable.getInstance().playersWaitingForClick.put(join.getPlayer().getUniqueId().toString(), false);

        if (GameData.getInstance().playersInGames.containsKey(join.getPlayer().getUniqueId().toString()) == false) {
            GameData.getInstance().playersInGames.put(join.getPlayer().getUniqueId().toString(), "none");
        }

        NewNPC.getInstance().loadNPCs(main);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent leave) {
        GameLeave.getInstance().leaveGame(leave.getPlayer().getUniqueId().toString());
    }

    @EventHandler
    public void blockBreakEvent(BlockBreakEvent blockBreak) {
        if (blockBreak.getPlayer() != null) {
            Player player = blockBreak.getPlayer();
            if (SpawnTable.getInstance().playersWaitingForClick.get(player.getUniqueId().toString())) {
                SpawnTable.getInstance().registerClick(player, blockBreak.getBlock(), main);
                blockBreak.setCancelled(true);
            }
            if (DeleteTable.getInstance().playersWaitingForClick.get(player.getUniqueId().toString())) {
                DeleteTable.getInstance().registerClick(player, blockBreak.getBlock().getLocation(), main);
                blockBreak.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerClickSign(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getClickedBlock().getType().toString().contains("SIGN")) {
                Sign sign = (Sign) event.getClickedBlock().getState();
                if (sign.getLine(1).equalsIgnoreCase(ChatColor.WHITE.toString() + ChatColor.BOLD + "Join Game")) {
                    GameJoin.getInstance().JoinGame(player, main, sign.getLocation());
                    sign.update();
                }
            }
        }
    }

    @EventHandler
    public void playerToggleSneak(PlayerToggleSneakEvent event) {
        if (GameData.getInstance().bettingMode.containsKey(event.getPlayer().getUniqueId().toString())) {
            if (GameData.getInstance().bettingMode.get(event.getPlayer().getUniqueId().toString())) {
                placeBet.getInstance().exitBettingMode(event.getPlayer());
            }
        }
    }

    @EventHandler
    public void playerChatEvent(AsyncPlayerChatEvent event) {
        if (GameData.getInstance().bettingMode.get(event.getPlayer().getUniqueId().toString())) {
            if (NumberUtils.isCreatable(event.getMessage())) {
                Double betAmount = Double.valueOf(event.getMessage());
                SubmitBet.getInstance().submitBet(betAmount, event.getPlayer());
                event.setCancelled(true);
            }
        }

    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        if (ChatColor.translateAlternateColorCodes('&', event.getView().getTitle()).equals(ChatColor.DARK_GREEN + "Viewing Current Bets: ")) {
            event.setCancelled(true);
        }

    }
}
