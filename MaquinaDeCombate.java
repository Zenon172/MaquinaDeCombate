package maquinacombate;
import robocode.*;
import java.awt.Color;

/**
 * MaquinaDeCombate - a robot by (Lincoln Silva de Oliveira)
 */
public class MaquinaDeCombate extends AdvancedRobot {
	
	int i = 0;
	int fix = 10;
	
	
	public void run() {
		
		//Definindo as cores: 
		 setBodyColor(Color.blue);
		 setRadarColor(Color.red);
		 setGunColor(Color.gray);
		 setScanColor(Color.red);
		 setBulletColor(Color.orange);

		while(true) {
			
			turnGunLeft(10);
		}
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		
		if(i % fix == 0){
			turnGunRight(180);
		} else {
			double gunTurnAmt = normalRelativeAngle(e.getBearing() + (getHeading() - getRadarHeading()));
            setTurnGunRight(gunTurnAmt);
            fix++;	
            if(getDistanceRemaining() < 500 ){
            	fire(1);
            	ahead(10);
            } else {
            	ahead(20);
            	turnRight(45);
            }
		}
		
	}
	
	public double normalRelativeAngle(double angulo) {
        if (angulo > -180 && angulo <= 180) {
            return angulo;
        }
 
        double fixAngulo = angulo;
        while (fixAngulo <= -180) {
        	fixAngulo += 360;
        }
 
        while (fixAngulo > 180) {
        	fixAngulo -= 360;
        }
 
        return fixAngulo;
    }
		
	public void onHitByBullet(HitByBulletEvent e) {

		back(20);
		turnRight(45);
	}
	
	public void onHitWall(HitWallEvent e) {
		
		back(20);
	}	
}
