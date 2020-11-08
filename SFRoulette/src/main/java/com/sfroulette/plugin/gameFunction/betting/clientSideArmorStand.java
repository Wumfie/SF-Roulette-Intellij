package com.sfroulette.plugin.gameFunction.betting;

import com.comphenix.packetwrapper.*;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.Vector3F;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import net.minecraft.server.v1_15_R1.PacketPlayOutSpawnEntityLiving;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.UUID;

public class clientSideArmorStand {

    private static clientSideArmorStand clientsidearmorstandclass = new clientSideArmorStand();

    private clientSideArmorStand() { }

    public static clientSideArmorStand getInstance( ) {
        return clientsidearmorstandclass;
    }

    public HashMap<String, Integer> standID = new HashMap<String, Integer>();

    public void showStandAt(Location location, Player player) {
        if (standID.containsKey(player.getUniqueId().toString())) {
            WrapperPlayServerEntityTeleport packetTeleport = new WrapperPlayServerEntityTeleport();
            packetTeleport.setEntityID(standID.get(player.getUniqueId().toString()));
            packetTeleport.setX(location.getX());
            packetTeleport.setY(location.getY());
            packetTeleport.setZ(location.getZ());
            packetTeleport.sendPacket(player);
        } else {
            standID.put(player.getUniqueId().toString(), (int) (Math.random() * Integer.MAX_VALUE));
            createNewStand(location, player);
        }
    }

    public void removeStandAt(Location location, Player player) {
        if (standID.containsKey(player.getUniqueId().toString())) {
            WrapperPlayServerEntityDestroy packetDestroy = new WrapperPlayServerEntityDestroy();
            int[] ids = new int[1];
            ids[0] = (int) standID.get(player.getUniqueId().toString());
            packetDestroy.setEntityIds(ids);
            packetDestroy.sendPacket(player);

            standID.remove(player.getUniqueId().toString());
        }
    }

    public void createNewStand(Location location, Player player) {
        WrapperPlayServerSpawnEntityLiving wrapperPlayServerSpawnEntity = new WrapperPlayServerSpawnEntityLiving();

        wrapperPlayServerSpawnEntity.setType(EntityType.DROPPED_ITEM);

        Integer entityID2 = standID.get(player.getUniqueId().toString());
        wrapperPlayServerSpawnEntity.setEntityID(entityID2);

        wrapperPlayServerSpawnEntity.setUniqueId(UUID.randomUUID());

        wrapperPlayServerSpawnEntity.setX(location.getX());
        wrapperPlayServerSpawnEntity.setY(location.getY());
        wrapperPlayServerSpawnEntity.setZ(location.getZ());

        wrapperPlayServerSpawnEntity.sendPacket(player);

        WrapperPlayServerEntityMetadata packet2 = new WrapperPlayServerEntityMetadata();
        // Set the entity to associate the packet with, which in this case is the client-side entity we created before.
        packet2.setEntityID(entityID2);
        // Create a ProtocolLib WrappedDataWatcher from the entity's current metadata.
        WrappedDataWatcher dataWatcher = new WrappedDataWatcher(packet2.getMetadata());

        WrappedDataWatcher.WrappedDataWatcherObject isInvisibleIndex = new WrappedDataWatcher.WrappedDataWatcherObject(0, WrappedDataWatcher.Registry.get(Byte.class));

        dataWatcher.setObject(isInvisibleIndex, (byte) 0x20);

        WrappedDataWatcher.WrappedDataWatcherObject hasArmsIndex = new WrappedDataWatcher.WrappedDataWatcherObject(14, WrappedDataWatcher.Registry.get(Byte.class));

        dataWatcher.setObject(hasArmsIndex, (byte) 0x04);

        WrappedDataWatcher.WrappedDataWatcherObject rightArmRotation = new WrappedDataWatcher.WrappedDataWatcherObject(18, WrappedDataWatcher.Registry.get(Vector3F.getMinecraftClass()));
        dataWatcher.setObject(rightArmRotation, new Vector3F(270.0f, 0.0f, 0.0f));

        packet2.setMetadata(dataWatcher.getWatchableObjects());
        packet2.sendPacket(player);

        WrapperPlayServerEntityEquipment wrapperPlayServerEntityEquipment = new WrapperPlayServerEntityEquipment();

        wrapperPlayServerEntityEquipment.setEntityID(entityID2);

        wrapperPlayServerEntityEquipment.setSlot(EnumWrappers.ItemSlot.MAINHAND);

        ItemStack selectorItem = new ItemStack(Material.IRON_INGOT);
        ItemMeta selectorMeta = selectorItem.getItemMeta();
        selectorMeta.setCustomModelData(1234569);
        selectorItem.setItemMeta(selectorMeta);

        wrapperPlayServerEntityEquipment.setItem(selectorItem);

        wrapperPlayServerEntityEquipment.sendPacket(player);
    }
}
