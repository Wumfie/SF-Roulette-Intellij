package com.sfroulette.plugin;

import com.sfroulette.plugin.gameFunction.GameData;
import com.sfroulette.plugin.gameFunction.betting.startRolling;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.util.EulerAngle;

import java.util.UUID;

public class RepeatingEvent {
    private static RepeatingEvent repeatingeventclass = new RepeatingEvent();

    private RepeatingEvent() { }

    public static RepeatingEvent getInstance( ) {
        return repeatingeventclass;
    }

    public int rotation = 0;

    public void startLoop(Main main) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(main, new Runnable() {
            public void run() {//Runs code every 50ms
                for (String standUUIDUnchecked : main.getConfig().getStringList("tables.armorStands")) {
                    Entity standUnsure = Bukkit.getEntity(UUID.fromString(standUUIDUnchecked.split("/")[1]));
                    if (standUnsure instanceof ArmorStand && standUUIDUnchecked.split("/")[0].equals("wheel")) {
                        ArmorStand stand = (ArmorStand) standUnsure;
                        stand.setHeadPose(new EulerAngle(Math.toRadians(0), Math.toRadians(rotation), Math.toRadians(0)));
                    }
                }
                rotation += 5;
                if (rotation >= 360) {
                    rotation = 0;
                }
            }
        }, 1L, 1L);
    }
}
