package BC;
import robocode.*;
import java.awt.Color;
import java.util.Random;

public class Blitzcrank extends Robot {
    Random rand = new Random();
    double moveAmount;
    boolean invertido = false;

    public void run() {
        setColors(Color.yellow,Color.darkGray,Color.gray);
        setBulletColor(Color.yellow);
        setScanColor(Color.gray);

        moveAmount = Math.max(getBattleFieldWidth(), getBattleFieldHeight());

        turnLeft(getHeading() % 90);
        ahead(moveAmount);
        turnGunRight(90);
        turnRight(90);

        while (true) {
            ahead(moveAmount);
            turnRight(90);

            if (invertido) {
                invertido = false; 
            }
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        // Sistema de tiro adaptativo:
        double firePower;
        
        if (e.getDistance() < 150) { // Curta distância
            firePower = 3; // Máximo dano
        } 
        else if (e.getDistance() < 400) { // Média distância
            firePower = 2;
        }
        else { // Longa distância
            firePower = 1;
        }
        
        // Precisão extra se o inimigo estiver parado
        if (e.getVelocity() == 0) {
            firePower = Math.min(firePower + 0.5, 3);
        }
        
        fire(firePower);
    }

    public void onHitRobot(HitRobotEvent e) {
        // Tiro extra ao colidir
        fire(3);
        
        if (e.getBearing() > -90 && e.getBearing() < 90) {
            back(100);
        } else {
            ahead(100);
        }
    }

    public void onHitByBullet(HitByBulletEvent e) {
        if (!invertido) {
            invertido = true;
            stop();
            turnRight(180);
        }
    }
}