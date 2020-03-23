package dev.shroysha.ap.robocode;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

public class Dextroser extends AdvancedRobot {


    public void run() {
        //noinspection InfiniteLoopStatement
        while (true) {
            this.turnRadarLeft(20);
        }
    }


    public void onScannedRobot(ScannedRobotEvent event) {
        if (this.getHeading() != event.getBearing()) {
            this.setTurnRight(event.getBearing());
        }

        setFire(3);
        scan();
    }

}
