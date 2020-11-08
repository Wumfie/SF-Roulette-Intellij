package com.sfroulette.plugin.gameFunction.betting;

import com.sfroulette.plugin.UsefulFunctions;
import com.sfroulette.plugin.gameFunction.GameData;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class betPositionTranslator {
    private static betPositionTranslator betpositiontranslatorclass = new betPositionTranslator();

    private betPositionTranslator() { }

    public static betPositionTranslator getInstance( ) {
        return betpositiontranslatorclass;
    }

    public Location translatePosition(Double xLocation, Double yLocation, String playerUUID) {
        Location location = UsefulFunctions.getInstance().stringToLocation(GameData.getInstance().playersInGames.get(playerUUID));

        location.setX(location.getX() - 2.048);
        location.setY(location.getY() - 0.5);
        location.setZ(location.getZ() - 0.61);

        Location originPoint = location;

        if ((yLocation >= 0 && yLocation <= 1) && (xLocation >= 12.5 && xLocation <= 13)) {
            //2 to 1 - 1
            Double toMoveHorizontally = 12.5 * 251/1440;
            Double toMoveVertically = 0.5 * 19/72;

            location.setX(originPoint.getX() + toMoveHorizontally);
            location.setZ(originPoint.getZ() - toMoveVertically);

            return location;
        } else if ((yLocation >= 1.5 && yLocation <= 2) && (xLocation >= 12.5 && xLocation <= 13)) {
            //2 to 1 - 2
            Double toMoveHorizontally = 12.5 * 251/1440;
            Double toMoveVertically = 1.5 * 19/72;

            location.setX(originPoint.getX() + toMoveHorizontally);
            location.setZ(originPoint.getZ() - toMoveVertically);

            return location;
        } else if ((yLocation >= 2.5 && yLocation <= 3) && (xLocation >= 12.5 && xLocation <= 13)) {
            //2 to 1 - 3
            Double toMoveHorizontally = 12.5 * 251/1440;
            Double toMoveVertically = 2.5 * 19/72;

            location.setX(originPoint.getX() + toMoveHorizontally);
            location.setZ(originPoint.getZ() - toMoveVertically);

            return location;
        } else if ((yLocation == -0.5) && (xLocation >= 0 && xLocation <= 4)) {
            //1st 12
            Double toMoveHorizontally = 2.0 * 251/1440;
            Double toMoveVertically = -0.25 * 19/72;

            location.setX(originPoint.getX() + toMoveHorizontally);
            location.setZ(originPoint.getZ() - toMoveVertically);

            return location;
        } else if ((yLocation == -0.5) && (xLocation >= 4.5 && xLocation <= 8)) {
            //2nd 12
            Double toMoveHorizontally = 6.0 * 251/1440;
            Double toMoveVertically = -0.25 * 19/72;

            location.setX(originPoint.getX() + toMoveHorizontally);
            location.setZ(originPoint.getZ() - toMoveVertically);

            return location;
        } else if ((yLocation == -0.5) && (xLocation >= 8.5 && xLocation <= 12)) {
            //3rd 12
            Double toMoveHorizontally = 10.0 * 251/1440;
            Double toMoveVertically = -0.25 * 19/72;

            location.setX(originPoint.getX() + toMoveHorizontally);
            location.setZ(originPoint.getZ() - toMoveVertically);

            return location;
        } else if ((yLocation == -1.5) && (xLocation >= 0 && xLocation <= 2)) {
            //1 - 18
            Double toMoveHorizontally = 1.0 * 251/1440;
            Double toMoveVertically = -0.75 * 19/72;

            location.setX(originPoint.getX() + toMoveHorizontally);
            location.setZ(originPoint.getZ() - toMoveVertically);

            return location;
        } else if ((yLocation == -1.5) && (xLocation >= 2.5 && xLocation <= 4)) {
            //EVEN
            Double toMoveHorizontally = 3.0 * 251/1440;
            Double toMoveVertically = -0.75 * 19/72;

            location.setX(originPoint.getX() + toMoveHorizontally);
            location.setZ(originPoint.getZ() - toMoveVertically);

            return location;
        } else if ((yLocation == -1.5) && (xLocation >= 4.5 && xLocation <= 6)) {
            //RED
            Double toMoveHorizontally = 5.0 * 251/1440;
            Double toMoveVertically = -0.75 * 19/72;

            location.setX(originPoint.getX() + toMoveHorizontally);
            location.setZ(originPoint.getZ() - toMoveVertically);

            return location;
        } else if ((yLocation == -1.5) && (xLocation >= 6.5 && xLocation <= 8)) {
            //BLACK
            Double toMoveHorizontally = 7.0 * 251/1440;
            Double toMoveVertically = -0.75 * 19/72;

            location.setX(originPoint.getX() + toMoveHorizontally);
            location.setZ(originPoint.getZ() - toMoveVertically);

            return location;
        } else if ((yLocation == -1.5) && (xLocation >= 8.5 && xLocation <= 10)) {
            //ODD
            Double toMoveHorizontally = 9.0 * 251/1440;
            Double toMoveVertically = -0.75 * 19/72;

            location.setX(originPoint.getX() + toMoveHorizontally);
            location.setZ(originPoint.getZ() - toMoveVertically);

            return location;
        } else if ((yLocation == -1.5) && (xLocation >= 10.5 && xLocation <= 12)) {
            //19-36
            Double toMoveHorizontally = 11.0 * 251/1440;
            Double toMoveVertically = -0.75 * 19/72;

            location.setX(originPoint.getX() + toMoveHorizontally);
            location.setZ(originPoint.getZ() - toMoveVertically);

            return location;
        } else if ((yLocation >= 0  && yLocation <= 3) && (xLocation >= -1 && xLocation <= -0.5)) {
            //0
            Double toMoveHorizontally = -0.5 * 251/1440;
            Double toMoveVertically = 1.5 * 19/72;

            location.setX(originPoint.getX() + toMoveHorizontally);
            location.setZ(originPoint.getZ() - toMoveVertically);

            return location;
        } else if ((yLocation >= 0  && yLocation <= 3) && (xLocation >= -1 && xLocation <= 13)) {
            Double toMoveHorizontally = xLocation * 251/1440;
            Double toMoveVertically = yLocation * 19/72;

            location.setX(location.getX() + toMoveHorizontally);
            location.setZ(location.getZ() - toMoveVertically);

            return location;
        } else if ((yLocation >= -2  && yLocation <= -0.5) && (xLocation >= -1 && xLocation <= 13)) {
            Double toMoveHorizontally = xLocation * 251/1440;
            Double toMoveVertically = yLocation * 19/144;

            location.setX(location.getX() + toMoveHorizontally);
            location.setZ(location.getZ() - toMoveVertically);

            return location;
        }
        return null;
    }
}
