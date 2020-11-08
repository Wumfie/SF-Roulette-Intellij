package com.sfroulette.plugin.gameFunction.betting;

import com.sfroulette.plugin.Main;
import com.sfroulette.plugin.gameFunction.GameData;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.UUID;

public class placeBet {

    private static placeBet usefulfunctionsclass = new placeBet();

    private placeBet() { }

    public static placeBet getInstance( ) {
        return usefulfunctionsclass;
    }

    public void enterBettingMode(Player player, Main main) {
        if (!GameData.getInstance().playersInGames.containsKey(player.getUniqueId().toString())) {
            return;
        }
        if (GameData.getInstance().playersInGames.get(player.getUniqueId().toString()).equals("none")) {
            return;
        }
            if (GameData.getInstance().isTableRolling.get(GameData.getInstance().playersInGames.get(player.getUniqueId().toString()))) {
            return;
        }
        List<String> armorStands = main.getConfig().getStringList("tables.armorStands");
        for (String standUnformatted : armorStands) {
            if (standUnformatted.split("/")[0].equals("playerNPC")) {
                System.out.println("Is a playerNPC");
                System.out.println("Stand Location: " + standUnformatted.split("/")[2]);
                System.out.println("Player's Table Location: " + GameData.getInstance().playersInGames.get(player.getUniqueId().toString()));
                if (standUnformatted.split("/")[2].equals(GameData.getInstance().playersInGames.get(player.getUniqueId().toString()))) {
                    GameData.getInstance().bettingLastLocation.put(player.getUniqueId().toString(), player.getLocation());

                    System.out.println("Spectating...");

                    NPC npc = CitizensAPI.getNPCRegistry().getByUniqueId(UUID.fromString(standUnformatted.split("/")[1]));

                    ((CraftPlayer) player).getHandle().setSpectatorTarget(((CraftEntity) npc.getEntity()).getHandle());

                    betSelector.getInstance().getStandLocationSelector(player);

                    player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 255, false, false));

                    GameData.getInstance().bettingMode.put(player.getUniqueId().toString(), true);

                    showBettingControls(player);
                }
            }
        }
    }

    public void exitBettingMode(Player player) {
        player.teleport(GameData.getInstance().bettingLastLocation.get(player.getUniqueId().toString()));
        GameData.getInstance().bettingMode.put(player.getUniqueId().toString(), false);

        betSelector.getInstance().removeStandLocation(player);

        player.removePotionEffect(PotionEffectType.INVISIBILITY);

        System.out.println("Exiting betting mode for: " + player.getName());

        TextComponent message = new TextComponent( ChatColor.GREEN.toString() + ChatColor.BOLD + "Click here if you are ready to roll! ✔" );
        message.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/roulette readyToRoll" ) );
        message.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( "Click me!" ).create() ) );
        player.spigot().sendMessage(message);
    }

    public void showBettingControls(Player player) {
        player.sendMessage(ChatColor.WHITE.toString() + ChatColor.BOLD + "Please move the cursor to the desired bet location.");

        TextComponent right = new TextComponent( " →        " );
        right.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/roulette right" ) );
        right.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( "→").create() ) );

        TextComponent left = new TextComponent( "        ← " );
        left.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/roulette left" ) );
        left.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( "←").create() ) );
        left.addExtra(right);

        TextComponent up = new TextComponent( "          ↑        " );
        up.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/roulette up" ) );
        up.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( "↑").create() ) );

        TextComponent down = new TextComponent( "          ↓        " );
        down.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/roulette down" ) );
        down.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( "↓").create() ) );

        player.spigot().sendMessage(up);
        player.spigot().sendMessage(left);
        player.spigot().sendMessage(down);

        player.sendMessage(ChatColor.WHITE.toString() + ChatColor.BOLD + "When you are done, please type the desired amount into chat.");
        player.sendMessage(ChatColor.WHITE.toString() + ChatColor.BOLD + "Hint: " + ChatColor.RESET +  "Press SHIFT to exit betting mode.");
    }
}
