package com.sfroulette.plugin.gameFunction.betting;

import com.sfroulette.plugin.gameFunction.GameData;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SubmitBet {
    private static SubmitBet submitbetclass = new SubmitBet();

    private SubmitBet() { }

    public static SubmitBet getInstance( ) {
        return submitbetclass;
    }

    public void submitBet(Double amount, Player player) {
        String playerGameLocation = GameData.getInstance().playersInGames.get(player.getUniqueId().toString());

        String betReturnString = "error";

        bettingPlayerObject playersCurrentBetsObject = null;
        List<bettingPlayerObject> bettingPlayerObjectList = GameData.getInstance().bettingPlayerObjectsWithGames.get(playerGameLocation);

        if (GameData.getInstance().playersInGames.containsKey(player.getUniqueId().toString())) {
            if (GameData.getInstance().bettingPlayerObjectsWithGames.containsKey(playerGameLocation)) { ;
                for (bettingPlayerObject bettingPlayerObjectTemp : bettingPlayerObjectList) {
                    if (bettingPlayerObjectTemp.getPlayer().equals(player.getUniqueId().toString())) {
                        playersCurrentBetsObject = bettingPlayerObjectTemp;
                    }
                }
            } else {
                playersCurrentBetsObject = new bettingPlayerObject(player.getUniqueId().toString());
            }
        }

        if (playersCurrentBetsObject == null) {
            return;
        }

        Double xLocation = betSelector.getInstance().xLocation.get(player.getUniqueId().toString());
        Double yLocation = betSelector.getInstance().yLocation.get(player.getUniqueId().toString());

        if ((yLocation >= 0 && yLocation <= 1) && (xLocation >= 12.5 && xLocation <= 13)) {
            //2 to 1 - 1
            if (playersCurrentBetsObject.betAmountAt_Columns.containsKey(1)) {
                playersCurrentBetsObject.betAmountAt_Columns.put(1, playersCurrentBetsObject.betAmountAt_Columns.get(1) + amount);
            } else {
                playersCurrentBetsObject.betAmountAt_Columns.put(1, amount);
            }
            betReturnString = "2 to 2 - 1";
        } else if ((yLocation >= 1.5 && yLocation <= 2) && (xLocation >= 12.5 && xLocation <= 13)) {
            //2 to 1 - 2
            if (playersCurrentBetsObject.betAmountAt_Columns.containsKey(2)) {
                playersCurrentBetsObject.betAmountAt_Columns.put(2, playersCurrentBetsObject.betAmountAt_Columns.get(2) + amount);
            } else {
                playersCurrentBetsObject.betAmountAt_Columns.put(2, amount);
            }
            betReturnString = "2 to 2 - 2";
        } else if ((yLocation >= 2.5 && yLocation <= 3) && (xLocation >= 12.5 && xLocation <= 13)) {
            //2 to 1 - 3
            if (playersCurrentBetsObject.betAmountAt_Columns.containsKey(3)) {
                playersCurrentBetsObject.betAmountAt_Columns.put(3, playersCurrentBetsObject.betAmountAt_Columns.get(3) + amount);
            } else {
                playersCurrentBetsObject.betAmountAt_Columns.put(3, amount);
            }
            betReturnString = "2 to 2 - 3";
        } else if ((yLocation == -0.5) && (xLocation >= 0 && xLocation <= 4)) {
            //1st 12
            if (playersCurrentBetsObject.betAmountAt_Dozens.containsKey(1)) {
                playersCurrentBetsObject.betAmountAt_Dozens.put(1, playersCurrentBetsObject.betAmountAt_Dozens.get(1) + amount);
            } else {
                playersCurrentBetsObject.betAmountAt_Dozens.put(1, amount);
            }
            betReturnString = "1st 12";
        } else if ((yLocation == -0.5) && (xLocation >= 4.5 && xLocation <= 8)) {
            //2nd 12
            if (playersCurrentBetsObject.betAmountAt_Dozens.containsKey(2)) {
                playersCurrentBetsObject.betAmountAt_Dozens.put(2, playersCurrentBetsObject.betAmountAt_Dozens.get(2) + amount);
            } else {
                playersCurrentBetsObject.betAmountAt_Dozens.put(2, amount);
            }
            betReturnString = "2nd 12";
        } else if ((yLocation == -0.5) && (xLocation >= 8.5 && xLocation <= 12)) {
            //3rd 12
            if (playersCurrentBetsObject.betAmountAt_Dozens.containsKey(3)) {
                playersCurrentBetsObject.betAmountAt_Dozens.put(3, playersCurrentBetsObject.betAmountAt_Dozens.get(3) + amount);
            } else {
                playersCurrentBetsObject.betAmountAt_Dozens.put(3, amount);
            }
            betReturnString = "3rd 12";
        } else if ((yLocation == -1.5) && (xLocation >= 0 && xLocation <= 2)) {
            //1 - 18
            if (playersCurrentBetsObject.betAmountAt_LowHigh.containsKey(0)) {
                playersCurrentBetsObject.betAmountAt_LowHigh.put(0, playersCurrentBetsObject.betAmountAt_LowHigh.get(0) + amount);
            } else {
                playersCurrentBetsObject.betAmountAt_LowHigh.put(0, amount);
            }
            betReturnString = "1 - 18 - Low Numbers";
        } else if ((yLocation == -1.5) && (xLocation >= 2.5 && xLocation <= 4)) {
            //EVEN
            if (playersCurrentBetsObject.betAmountAt_OddsEven.containsKey(1)) {
                playersCurrentBetsObject.betAmountAt_OddsEven.put(1, playersCurrentBetsObject.betAmountAt_OddsEven.get(1) + amount);
            } else {
                playersCurrentBetsObject.betAmountAt_OddsEven.put(1, amount);
            }
            betReturnString = "Even Numbers";
        } else if ((yLocation == -1.5) && (xLocation >= 4.5 && xLocation <= 6)) {
            //RED
            if (playersCurrentBetsObject.betAmountAt_RedBlack.containsKey(0)) {
                playersCurrentBetsObject.betAmountAt_RedBlack.put(0, playersCurrentBetsObject.betAmountAt_RedBlack.get(0) + amount);
            } else {
                playersCurrentBetsObject.betAmountAt_RedBlack.put(0, amount);
            }
            betReturnString = "Red Numbers";
        } else if ((yLocation == -1.5) && (xLocation >= 6.5 && xLocation <= 8)) {
            //BLACK
            if (playersCurrentBetsObject.betAmountAt_RedBlack.containsKey(1)) {
                playersCurrentBetsObject.betAmountAt_RedBlack.put(1, playersCurrentBetsObject.betAmountAt_RedBlack.get(1) + amount);
            } else {
                playersCurrentBetsObject.betAmountAt_RedBlack.put(1, amount);
            }
            betReturnString = "Black Numbers";
        } else if ((yLocation == -1.5) && (xLocation >= 8.5 && xLocation <= 10)) {
            //ODD
            if (playersCurrentBetsObject.betAmountAt_OddsEven.containsKey(0)) {
                playersCurrentBetsObject.betAmountAt_OddsEven.put(0, playersCurrentBetsObject.betAmountAt_OddsEven.get(0) + amount);
            } else {
                playersCurrentBetsObject.betAmountAt_OddsEven.put(0, amount);
            }
            betReturnString = "Odd Numbers";
        } else if ((yLocation == -1.5) && (xLocation >= 10.5 && xLocation <= 12)) {
            //19-36
            if (playersCurrentBetsObject.betAmountAt_LowHigh.containsKey(1)) {
                playersCurrentBetsObject.betAmountAt_LowHigh.put(1, playersCurrentBetsObject.betAmountAt_LowHigh.get(1) + amount);
            } else {
                playersCurrentBetsObject.betAmountAt_LowHigh.put(1, amount);
            }
            betReturnString = "19 - 36 - High Numbers";
        } else if ((yLocation >= 0  && yLocation <= 3) && (xLocation >= -1 && xLocation <= -0.5)) {
            //0
            if (playersCurrentBetsObject.betAmountAt_Straight.containsKey(0)) {
                playersCurrentBetsObject.betAmountAt_Straight.put(0, playersCurrentBetsObject.betAmountAt_Straight.get(0) + amount);
            } else {
                playersCurrentBetsObject.betAmountAt_Straight.put(0, amount);
            }
            betReturnString = "0";
        } else if ((yLocation >= 0  && yLocation <= 3) && (xLocation >= -1 && xLocation <= 13)) {
            //Numbers
            if (((yLocation - 0.5) % 1 == 0) && ((xLocation - 0.5) % 1 == 0)) {
                //Is on a straight
                int singleBetLocation = (int) ((3*(xLocation - 0.5)) + (yLocation + 0.5));
                betReturnString = "" + singleBetLocation;

                if (playersCurrentBetsObject.betAmountAt_Straight.containsKey(singleBetLocation)) {
                    playersCurrentBetsObject.betAmountAt_Straight.put(singleBetLocation, playersCurrentBetsObject.betAmountAt_Straight.get(singleBetLocation) + amount);
                } else {
                    playersCurrentBetsObject.betAmountAt_Straight.put(singleBetLocation, amount);
                }
            } else if ((((yLocation - 0.5) % 1 == 0) && (xLocation % 1 == 0)) && (xLocation >= 0.5 && yLocation <= 11.5)) {
                //Is on a split
                List<Integer> splitBetLocations = new ArrayList<Integer>();
                splitBetLocations.add((int) (((3 * xLocation) - 2) + (yLocation - 0.5))); //Left number Formula
                splitBetLocations.add((int) (((3 * xLocation) + 1) + (yLocation - 0.5))); //Right number Formula

                betReturnString = "" + splitBetLocations;

                for (int splitBetLocation : splitBetLocations) {
                    if (playersCurrentBetsObject.betAmountAt_Split.containsKey(splitBetLocation)) {
                        playersCurrentBetsObject.betAmountAt_Split.put(splitBetLocation, playersCurrentBetsObject.betAmountAt_Split.get(splitBetLocation) + amount);
                    } else {
                        playersCurrentBetsObject.betAmountAt_Split.put(splitBetLocation, amount);
                    }
                }

            } else if (((yLocation % 1 == 0) && (xLocation % 1 == 0)) && ((yLocation >= 0.5  && yLocation <= 2.5) && (xLocation >= 0.5 && xLocation <= 11.5))) {
                //Is on a square
                List<Integer> squareBetLocations = new ArrayList<Integer>();
                squareBetLocations.add((int) (((3 * xLocation) - 2) + (yLocation - 1.0))); //Bottom Left number Formula
                squareBetLocations.add((int) (((3 * xLocation) + 1) + (yLocation - 1.0))); //Bottom right number Formula
                squareBetLocations.add((int) (((3 * xLocation) - 2) + (yLocation))); //Top Left number Formula
                squareBetLocations.add((int) (((3 * xLocation) + 1) + (yLocation))); //Top right number Formula

                betReturnString = "" + squareBetLocations;

                for (int squareBetLocation : squareBetLocations) {
                    if (playersCurrentBetsObject.betAmountAt_Square.containsKey(squareBetLocation)) {
                        playersCurrentBetsObject.betAmountAt_Square.put(squareBetLocation, playersCurrentBetsObject.betAmountAt_Square.get(squareBetLocation) + amount);
                    } else {
                        playersCurrentBetsObject.betAmountAt_Square.put(squareBetLocation, amount);
                    }
                }
            } else if ((yLocation == 0) && ((xLocation - 0.5) % 1 == 0)) {
                //Is on a street
                List<Integer> streetBetLocations = new ArrayList<Integer>();
                int streetBetLocationBottom = (int) ((3 * (xLocation + 0.5)) - 2); //Bottom number formula
                streetBetLocations.add( streetBetLocationBottom );
                streetBetLocations.add( streetBetLocationBottom + 1);
                streetBetLocations.add( streetBetLocationBottom + 2);

                betReturnString = "" + streetBetLocations;

                for (int streetBetLocation : streetBetLocations) {
                    if (playersCurrentBetsObject.betAmountAt_Street.containsKey(streetBetLocation)) {
                        playersCurrentBetsObject.betAmountAt_Street.put(streetBetLocation, playersCurrentBetsObject.betAmountAt_Street.get(streetBetLocation) + amount);
                    } else {
                        playersCurrentBetsObject.betAmountAt_Street.put(streetBetLocation, amount);
                    }
                }
            } else if (((yLocation == 0) && (xLocation % 1 == 0)) && xLocation > 0.5 && xLocation < 11.5) {
                //Is on a line
                List<Integer> lineBetLocations = new ArrayList<Integer>();
                int lineBetLocationBottom = (int) ((3 * (xLocation)) - 2); //Bottom number line
                lineBetLocations.add( lineBetLocationBottom );
                lineBetLocations.add( lineBetLocationBottom + 1);
                lineBetLocations.add( lineBetLocationBottom + 2);
                lineBetLocations.add( lineBetLocationBottom + 3);
                lineBetLocations.add( lineBetLocationBottom + 4);
                lineBetLocations.add( lineBetLocationBottom + 5);

                betReturnString = "" + lineBetLocations;

                for (int lineBetLocation : lineBetLocations) {
                    if (playersCurrentBetsObject.betAmountAt_6_Line.containsKey(lineBetLocation)) {
                        playersCurrentBetsObject.betAmountAt_6_Line.put(lineBetLocation, playersCurrentBetsObject.betAmountAt_6_Line.get(lineBetLocation) + amount);
                    } else {
                        playersCurrentBetsObject.betAmountAt_6_Line.put(lineBetLocation, amount);
                    }
                }
            } else {
                player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "We do not recognise that to be a valid betting location.");
                return;
            }
        } else {
            player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "We do not recognise that to be a valid betting location.");
            return;
        }
        bettingPlayerObjectList.removeIf(bettingPlayerObjectTemp -> bettingPlayerObjectTemp.getPlayer().equals(player.getUniqueId().toString()));
        bettingPlayerObjectList.add(playersCurrentBetsObject);
        GameData.getInstance().bettingPlayerObjectsWithGames.put(playerGameLocation, bettingPlayerObjectList);
        GameData.getInstance().playerTotalBetted.put(player.getUniqueId().toString(), GameData.getInstance().playerTotalBetted.get(player.getUniqueId().toString()) + amount);
        player.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + "You Successfully Placed " + amount + " on " + betReturnString + ".");
    }
}
