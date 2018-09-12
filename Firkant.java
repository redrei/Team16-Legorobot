
import lejos.hardware.lcd.*;
import lejos.hardware.motor.*;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.port.Port;
import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.robotics.SampleProvider;


public class test{
	public static void main (String[] args)  throws Exception{
		Motor.A.setSpeed(450);
	 	Motor.C.setSpeed(450);

	 	Brick brick = BrickFinder.getDefault();
	 	Port s1 = brick.getPort("S1");

		SampleProvider trykksensor = new EV3TouchSensor(s1);
		float[] trykkSample = new float[trykksensor.sampleSize()];


	 	boolean fortsett = true;
		int i = 0;

		while(fortsett) {

     		System.out.println("Fram 2000 ms");
    		Motor.A.forward();
    	 	Motor.C.forward();

			trykksensor.fetchSample(trykkSample, 0);

    	 	if (trykkSample[0] > 0){
		 		System.out.println("Svinger");
		 		LCD.clear();
				Motor.A.stop();
				Motor.C.stop();
				Thread.sleep(500);

				Motor.A.backward();
				Motor.C.backward();
				Thread.sleep(500);

				Motor.A.rotate(180);
				Motor.C.stop();

				i++;

				while (Motor.A.isMoving()) Thread.yield();

					Thread.sleep(1000);
					Motor.A.stop();
    	 			Motor.C.stop();
			}
			else if ( i >= 4){
				fortsett = false;
				System.out.println("Ferdig");
			}
		}
	}
}
