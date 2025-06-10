package BC;
import robocode.*;
import java.awt.Color;
import java.util.Random;

public class Blitzcrank extends Robot {
    Random rand = new Random();
    boolean peek;
    double moveAmount;
    boolean invertido = false; // controla se já inverteu direção

    public void run() {
        setColors(Color.yellow,Color.darkGray,Color.gray);
        setBulletColor(Color.yellow);
        setScanColor(Color.gray);

        moveAmount = Math.max(getBattleFieldWidth(), getBattleFieldHeight());
        peek = false;

        // Alinha com a parede
        turnLeft(getHeading() % 90);
        ahead(moveAmount);
        peek = true;
        turnGunRight(90);
        turnRight(90);

        while (true) {
            peek = true;
            ahead(moveAmount);
            peek = false;

            turnRight(90);

            // Se estiver invertido, já que deu uma volta, volta a zerar o estado
            if (invertido) {
                invertido = false; 
            }
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        fire(1);
    }
	// Quando atingir robo com robo
    public void onHitRobot(HitRobotEvent e) {
        if (e.getBearing() > -90 && e.getBearing() < 90) {
            back(100);
        } else {
            ahead(100);
        }
    }

    public void onHitByBullet(HitByBulletEvent e) {
        if (!invertido) {
            invertido = true;

            // Para o movimento atual
            stop();

            // Vira 180 graus para ir na direção oposta
            turnRight(180);
			turnGunRight(180);

            // Corre para a parede oposta
            ahead(moveAmount);
			turnGunRight(180);
        }
    }
}