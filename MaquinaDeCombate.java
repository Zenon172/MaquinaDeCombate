package maquinacombate;

import robocode.*;
import java.awt.Color;

/**
 * MaquinaDeCombate - a robot by (Lincoln Silva de Oliveira)
 */
public class MaquinaDeCombate extends AdvancedRobot {
	
	private String inimigo = null; 


	public void run() {
		// Define as cores
		setBodyColor(Color.black);
		setGunColor(Color.gray);
		setRadarColor(Color.red);
		setScanColor(Color.red);
		setBulletColor(Color.yellow);
		
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);
		
		while (true) {

				setTurnRadarRight(360);
				setTurnGunRight(1);
				//ahead(10);
				//turnRight(45);
				//execute();
				

			if(pertoParede()) {
				turnRight(45);
				back(60);
			} else {
				ahead(30);
			}			
		}
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		
		//Localizando o Robô inimigo
		
		if(inimigo == null){
			inimigo = e.getName();
		} else {
				//Mirar radar no inimigo
			setTurnRadarRight(anguloRelativo(e.getBearing()+getHeading()-getRadarHeading()));

				//Mirar o canhão no inimigo.
			setTurnGunRight(anguloRelativo(e.getBearing()+getHeading()-getGunHeading())); 

				//virar na direção do inimigo
			setTurnRight(anguloRelativo(e.getBearing())); 
		}
		double distancia = e.getDistance();
		fogo(distancia);
	}
	public void fogo(double distancia){
		if (distancia < 150 && getEnergy() > 60) {
			setFire(3);
		} else if ( distancia > 150 && getEnergy() > 40){
			setFire(2);
		} else {
			setFire(1);
		}
	}
	public double anguloRelativo(double ANG) {
		if (ANG > -180 && ANG <= 180) {
			return ANG;
		}
		double REL = ANG;
		while (REL <= -180) {
			REL += 360;
		}
		while (ANG > 180) {
			REL -= 360;
		}
	return REL;
	}


	public void onHitByBullet(HitByBulletEvent e) {
			
		inimigo = e.getName();
		setTurnRadarRight(anguloRelativo(e.getBearing()+getHeading()-getRadarHeading()));
		setTurnGunRight(anguloRelativo(e.getBearing()+getHeading()-getGunHeading()));
		//turnRight(robocode.util.Utils.normalRelativeAngleDegrees(90 - (getHeading() - e.getHeading())));
		turnRight(90);
		ahead(10);
		scan();
		
	}

	
	public void onHitRobot(HitRobotEvent e) {
		
		double turnGunAmt = robocode.util.Utils.normalRelativeAngleDegrees(e.getBearing() + getHeading() - getGunHeading());
		turnGunRight(turnGunAmt);
		fire(3);
		back(50);
		turnLeft(45);
	}
	
	public boolean pertoParede() {
		return (getX() < 50 || getX() > getBattleFieldWidth() - 50 || getY() < 50 || getY() > getBattleFieldHeight() - 50);
	}
	
	public void onHitWall(HitWallEvent e){
		turnRight(45);
		ahead(60);
	}

}
