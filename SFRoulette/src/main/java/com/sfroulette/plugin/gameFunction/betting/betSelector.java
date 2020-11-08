package com.sfroulette.plugin.gameFunction.betting;

import com.sfroulette.plugin.gameFunction.GameData;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class betSelector {

    private static betSelector betselectorclass = new betSelector();

    private betSelector() { }

    public static betSelector getInstance( ) {
        return betselectorclass;
    }

    int counter = 0;

    public HashMap<String, Double> xLocation = new HashMap<String, Double>();
    public HashMap<String, Double> yLocation = new HashMap<String, Double>();

    public HashMap<String, Location> cursorLocation = new HashMap<String, Location>();

    public boolean updateStandLocation(Player player) {
        if (betPositionTranslator.getInstance().translatePosition(xLocation.get(player.getUniqueId().toString()),
                yLocation.get(player.getUniqueId().toString()), player.getUniqueId().toString()) == null) {
            return false;
        }
        Location location = betPositionTranslator.getInstance().translatePosition(xLocation.get(player.getUniqueId().toString()),
                yLocation.get(player.getUniqueId().toString()), player.getUniqueId().toString());

        cursorLocation.put(player.getUniqueId().toString(), location);

        clientSideArmorStand.getInstance().showStandAt(location, player);

        return true;
    }

    public void removeStandLocation(Player player) {
        Location location = betPositionTranslator.getInstance().translatePosition(xLocation.get(player.getUniqueId().toString()),
                yLocation.get(player.getUniqueId().toString()), player.getUniqueId().toString());

        clientSideArmorStand.getInstance().removeStandAt(location, player);
    }

    public void getStandLocationSelector(Player player) {
        String UUID = player.getUniqueId().toString();
        xLocation.put(UUID, 0.5);
        yLocation.put(UUID, 0.5);
        updateStandLocation(player);
    }

    public void standLocationSelectorRight(Player player) {
        String UUID = player.getUniqueId().toString();
        if (!xLocation.containsKey(player.getUniqueId().toString())) {
            getStandLocationSelector(player);
        }
        Double currentXLocation = xLocation.get(UUID);
        xLocation.put(UUID, currentXLocation + 0.5);
        Boolean dontRemoveLastUpdate = updateStandLocation(player);
        if (dontRemoveLastUpdate == false) {
            xLocation.put(UUID, currentXLocation);
            updateStandLocation(player);
        }
    }

    public void standLocationSelectorLeft(Player player) {
        String UUID = player.getUniqueId().toString();
        if (!xLocation.containsKey(player.getUniqueId().toString())) {
            getStandLocationSelector(player);
        }
        Double currentXLocation = xLocation.get(UUID);
        xLocation.put(UUID, currentXLocation - 0.5);
        Boolean dontRemoveLastUpdate = updateStandLocation(player);
        if (dontRemoveLastUpdate == false) {
            xLocation.put(UUID, currentXLocation);
            updateStandLocation(player);
        }
    }

    public void standLocationSelectorUp(Player player) {
        String UUID = player.getUniqueId().toString();
        if (!xLocation.containsKey(player.getUniqueId().toString())) {
            getStandLocationSelector(player);
        }
        Double currentYLocation = yLocation.get(UUID);
        yLocation.put(UUID, currentYLocation + 0.5);
        updateStandLocation(player);
        Boolean dontRemoveLastUpdate = updateStandLocation(player);
        if (dontRemoveLastUpdate == false) {
            yLocation.put(UUID, currentYLocation );
            updateStandLocation(player);
        }
    }

    public void standLocationSelectorDown(Player player) {
        String UUID = player.getUniqueId().toString();
        if (!xLocation.containsKey(player.getUniqueId().toString())) {
            getStandLocationSelector(player);
        }
        Double currentYLocation = yLocation.get(UUID);
        yLocation.put(UUID, currentYLocation - 0.5);
        Boolean dontRemoveLastUpdate = updateStandLocation(player);
        if (dontRemoveLastUpdate == false) {
            yLocation.put(UUID, currentYLocation);
            updateStandLocation(player);
        }
    }
}
