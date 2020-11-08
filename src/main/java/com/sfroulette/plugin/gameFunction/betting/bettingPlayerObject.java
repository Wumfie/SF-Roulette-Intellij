package com.sfroulette.plugin.gameFunction.betting;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class bettingPlayerObject {
    private String player;

    public HashMap<Integer, Double> betAmountAt_Straight = new HashMap<Integer, Double>(); //Integer - 1to36

    public HashMap<Integer, Double> betAmountAt_Split = new HashMap<Integer, Double>(); //Group of 2

    public HashMap<Integer, Double> betAmountAt_Square = new HashMap<Integer, Double>(); //Group of 4

    public HashMap<Integer, Double> betAmountAt_Street = new HashMap<Integer, Double>(); //Line of 3

    public HashMap<Integer, Double> betAmountAt_6_Line = new HashMap<Integer, Double>(); //Line of 6

    public HashMap<Integer, Double> betAmountAt_Columns = new HashMap<Integer, Double>(); //Vertical Dozens - Integer1 = Column 1

    public HashMap<Integer, Double> betAmountAt_Dozens = new HashMap<Integer, Double>(); //Group of 12

    public HashMap<Integer, Double> betAmountAt_RedBlack = new HashMap<Integer, Double>(); //0 - Red, 1 - Black

    public HashMap<Integer, Double> betAmountAt_LowHigh = new HashMap<Integer, Double>(); //0 - Low, 1 - High

    public HashMap<Integer, Double> betAmountAt_OddsEven = new HashMap<Integer, Double>(); //0 - Odd, 1 - Evens

    public String getPlayer() {
        return player;
    }

    public bettingPlayerObject(String player) {
        this.player = player;
    }
}
