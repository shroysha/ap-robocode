package dev.shroysha.ap.robocode;

import lombok.Getter;
import robocode.AdvancedRobot;
import robocode.HitWallEvent;
import robocode.Rules;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;

public class BestBotEver extends AdvancedRobot {

    @Getter
    private double previousEnergy = 100;
    @Getter
    private int movementDirection = 1;
    @Getter
    private int gunDirection = 1;
    @Getter
    private double energy = 10;

    public void run() {
        /*
         * http://robowiki.net/wiki/Linear_Targeting
         * http://www.ibm.com/developerworks/java/library/j-dodge/
         */
        //this.setAdjustRadarForGunTurn(true);
        this.setAdjustRadarForRobotTurn(true);
        this.setAdjustGunForRobotTurn(true);

        //noinspection InfiniteLoopStatement
        while (true) {
            this.turnRadarRight(20);
        }
    }

    public void onScannedRobot(ScannedRobotEvent event) {
        double absoluteBearing = getHeadingRadians() + event.getBearingRadians();
        setTurnGunRightRadians(Utils.normalRelativeAngle(absoluteBearing -
                getGunHeadingRadians() + (event.getVelocity() * Math.sin(event.getHeadingRadians() -
                absoluteBearing) / Rules.getBulletSpeed(3.0))));
        setFire(3.0);
        // Stay at right angles to the opponent
        setTurnRight(event.getBearing() + 90 - 30 * movementDirection);

        // If the bot has small energy drop, assume it fired
        double changeInEnergy = previousEnergy - event.getEnergy();
        if (changeInEnergy > 0 && changeInEnergy <= 3) {   // Dodge!
            movementDirection = -movementDirection;
            setAhead((event.getDistance() / 4 + 25) * movementDirection);
        }
        // When a bot is spotted,
        // sweep the gun and radar
        gunDirection = -gunDirection;

        // Fire directly at target
        //fire(2);

        // Track the energy level
        previousEnergy = event.getEnergy();
        scan();
    }

    public void onHitWall(HitWallEvent event) {
        super.onHitWall(event);
        this.turnLeft(90);
        this.ahead(100);
    }

}
