package maquinacombate;

import robocode.*;
import java.awt.Color;
import robocode.util.*;
/**
 * MaquinaDeCombate - a robot by (Lincoln Silva de Oliveira)
 */
public class MaquinaDeCombate extends AdvancedRobot {

	double potenciaDoTiro, distancia, inimigoX, inimigoY, posicaoDoInimigo, velocidadeDoInimigo, altDoCampDeBatalha,
	larDoCampDeBatalha, previsaoX, previsaoY, anguloAbsoluto, giroDoRadar, giroDoCanhao;

	public void run() {
		// Define as cores
		setBodyColor(Color.blue);
		setGunColor(Color.black);
		setRadarColor(Color.red);
		setScanColor(Color.red);
		setBulletColor(Color.red);
		
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);
		setAdjustRadarForRobotTurn(true);
		
		while (true) {
			
			ahead(40);
			setTurnRight(45);
			execute();
			turnRadarRight(360);
			
			if(pertoParede()) {
				back(50);
			} else {
				ahead(50);
			}			
		}
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		
		potenciaDoTiro = Math.min(2.0, getEnergy());
		distancia = getHeadingRadians() + e.getBearingRadians();
		inimigoX = getX() + e.getDistance() * Math.sin(distancia);
		inimigoY = getY() + e.getDistance() * Math.cos(distancia);
		posicaoDoInimigo = e.getHeadingRadians();
		velocidadeDoInimigo = e.getVelocity();
		altDoCampDeBatalha = getBattleFieldHeight();
		larDoCampDeBatalha = getBattleFieldWidth();
		previsaoX = inimigoX;
		previsaoY = inimigoY;

		previsaoX += Math.sin(posicaoDoInimigo) * velocidadeDoInimigo;
		previsaoY += Math.cos(posicaoDoInimigo) * velocidadeDoInimigo;
		if (previsaoX < 18.0 || previsaoY < 18.0 || previsaoX > larDoCampDeBatalha - 18.0
				|| previsaoY > altDoCampDeBatalha - 18.0) {
			previsaoX = Math.min(Math.max(18.0, previsaoX), larDoCampDeBatalha - 18.0);
			previsaoY = Math.min(Math.max(18.0, previsaoY), altDoCampDeBatalha - 18.0);
		}

		anguloAbsoluto = Utils.normalAbsoluteAngle(Math.atan2(previsaoX - getX(), previsaoY - getY()));

		setTurnRightRadians(distancia / 2 * -1 - getRadarHeadingRadians());
		setTurnRadarRightRadians(Utils.normalRelativeAngle(distancia - getRadarHeadingRadians()));
		setTurnGunRightRadians(Utils.normalRelativeAngle(anguloAbsoluto - getGunHeadingRadians()));
		fogo(distancia);
	}
	
	public void fogo(double distancia) {
		if (distancia > 100 || getEnergy() < 30) {
			fire(1);
		} else if (distancia > 50) {
			fire(2);
		} else {
			fire(3);
	    }
	}
	
	public double anguloRelativo(double ANG) {
		if (ANG> -180 && ANG<= 180) {
			return ANG;
		}
		double REL = ANG;
		while (REL<= -180) {
			REL += 360;
		}
		while (ANG> 180) {
			REL -= 360;
		}
		return REL;
	}	

	public void onHitByBullet(HitByBulletEvent e) {
		
		back(100);
		turnRight(anguloRelativo(e.getBearing()));
		turnGunRight(anguloRelativo(e.getBearingRadians()));
		fire(2);
		execute();
	}

	
	public void onHitRobot(HitRobotEvent e) {
		
		setAhead(e.getBearing());
		turnGunRight(anguloRelativo(e.getBearing()));
		fire(3);
	}
	
	public boolean pertoParede() {
		return (getX() < 50 || getX() > getBattleFieldWidth() - 50 || getY() < 50 || getY() > getBattleFieldHeight() - 50);
	}
	
	public void onHitWall(HitWallEvent e){
		back(10);
		turnRight(45);
	}

}