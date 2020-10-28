package maquinacombate;

import robocode.*;
import java.awt.Color;

/**
 * MaquinaDeCombate - a robot by (Lincoln Silva de Oliveira)
 */
public class MaquinaDeCombate extends AdvancedRobot {
	
	int dist = 80;
	private String inimigo = null; 


	public void run() {
		// Define as cores
		setBodyColor(Color.blue);
		setGunColor(Color.black);
		setRadarColor(Color.red);
		setScanColor(Color.red);
		setBulletColor(Color.red);
		
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);
		
		while (true) {

			setTurnRadarRight(360);
			ahead(80);
			turnRight(90);

			if(pertoParede()) {
				turnLeft(45);
				back(100);
			} else {
				ahead(10);
			}			
		}
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		
		//Localizando o Robô inimigo
		
		if(inimigo == null){
			inimigo = e.getName();
		} else {
			setTurnRadarRightRadians(
					robocode.util.Utils.normalRelativeAngle((getHeadingRadians() + e.getBearingRadians()) - getGunHeadingRadians()));
		setTurnGunRightRadians(
				robocode.util.Utils.normalRelativeAngle((getHeadingRadians() + e.getBearingRadians()) - getGunHeadingRadians()));
		}
		if (e.getDistance() < 200 && getEnergy() > 30) {
			setFire(3);
		} else {
			setFire(2);
		}
	}


	public void onHitByBullet(HitByBulletEvent e) {
		
		inimigo = e.getName();
		
		setTurnRadarRightRadians(
				robocode.util.Utils.normalRelativeAngle((getHeadingRadians() + e.getBearingRadians()) - getGunHeadingRadians()));
		setTurnGunRightRadians(
				robocode.util.Utils.normalRelativeAngle((getHeadingRadians() + e.getBearingRadians()) - getGunHeadingRadians()));
		
		ahead(dist);
		dist *= -1; 
	}

	
	public void onHitRobot(HitRobotEvent e) {
		
		double turnGunAmt = robocode.util.Utils.normalRelativeAngleDegrees(e.getBearing() + getHeading() - getGunHeading());
		turnGunRight(turnGunAmt);
		fire(3);
	}
	
	public boolean pertoParede() {
		return (getX() < 50 || getX() > getBattleFieldWidth() - 50 || getY() < 50 || getY() > getBattleFieldHeight() - 50);
	}
	
	public void onHitWall(HitWallEvent e){
		turnRight(90);
		ahead(100);
	}

}