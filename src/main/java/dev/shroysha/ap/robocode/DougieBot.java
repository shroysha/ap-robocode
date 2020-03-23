package dev.shroysha.ap.robocode;

import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;

import static robocode.util.Utils.normalRelativeAngleDegrees;

public class DougieBot extends Robot {

    public void run() {
        super.run();

        this.setAdjustRadarForGunTurn(false);

        //noinspection InfiniteLoopStatement
        while (true) {
            myRotatingMove();
        }
    }

    public void myRotatingMove() {
        ahead(25);
        this.turnLeft(15);
        this.turnGunLeft(15);
    }


    public void onScannedRobot(ScannedRobotEvent event) {
        if (event.getDistance() < 25 && this.getEnergy() > 15) {
            fire(5);
        } else {
            fire(1);
        }
    }


    public void onHitWall(HitWallEvent event) {
        super.onHitWall(event);

        this.turnLeft(45);
    }


    public void onHitRobot(HitRobotEvent event) {
        super.onHitRobot(event);
        if (!event.isMyFault() && this.getEnergy() > 10) {
            double turnGunAmt = normalRelativeAngleDegrees(event.getBearing() + getHeading() - getGunHeading());

            turnGunRight(turnGunAmt);
            fire(10);
        }
    }

}
