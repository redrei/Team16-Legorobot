/*    MinBil.java G.S 2011 - 08 - 24
* Program som styrer en bil med 2 motorer. Bilen oppfører seg slik:
* 1. kjør framover
* 2. Rygg
* 3. Sving høyre
* 4. Endre hastighet på motorene
* 5. Kjør framover igjen
*/


import lejos.hardware.lcd.*;
import lejos.hardware.motor.*;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.port.Port;
import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.robotics.SampleProvider;



public class MinBil
{
  public static void main (String[] args)  throws Exception
  {
     Motor.A.setSpeed(100);  // sett hastighet (toppfart = 900)
	 Motor.C.setSpeed(100);

	    	Brick brick = BrickFinder.getDefault();
			Port s1 = brick.getPort("S1"); // fargesensor

		SampleProvider trykksensor = new EV3TouchSensor(s1);
	float[] trykkSample = new float[trykksensor.sampleSize()]; // tabell som inneholder avlest verdi


	 boolean fortsett = true;
		while(fortsett) {

     // Kjør framover
     	System.out.println("Fram 2000 ms");
    	 Motor.A.forward();  // Start motor A - kjør framover
    	 Motor.C.forward();  // Start motor C - kjør framover
    	 Thread.sleep(200); // Vent i 1000 ms før vi går videre i koden

     // Sving

		trykksensor.fetchSample(trykkSample, 0);


    	 if (trykkSample[0] > 0){
		 			System.out.println("Svinger");
		 			 LCD.clear();
							Motor.A.setSpeed(900);
							Motor.C.setSpeed(900);
							Motor.A.backward();
							Motor.C.backward();
							Thread.sleep(1000);

					    	 Motor.A.rotate(45);  // roter 180 gr med motor A
					    	 Motor.C.stop();

					    	 while (Motor.A.isMoving()) Thread.yield();  // vent til rotasjon er ferdig

					    	 Thread.sleep(1000);
					    	 Motor.A.stop();
    	 					Motor.C.stop();
		}
	  }
	}
}
