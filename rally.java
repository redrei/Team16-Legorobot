import lejos.hardware.lcd.*;
import lejos.hardware.motor.*;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.port.Port;
import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.robotics.SampleProvider;

public class rally {
	public static void main (String[] args)
	throws Exception
	{
	//sett motor i A og B. Fargesensor i S1.

		String left = "Turn left";
		String right = "Turn right";

		Motor.A.setSpeed(300);
		Motor.B.setSpeed(200);
	//setter motorfart A til 200 og B til 300.

	 	Brick brick = BrickFinder.getDefault();
	 	Port s1 = brick.getPort("S1");
		//finner hvilke sensorer som er koblet til

		EV3ColorSensor fargesensor = new EV3ColorSensor(s1);
		SampleProvider fargeLeser = fargesensor.getMode("RGB");
		float[] fargeSample = new float[fargeLeser.sampleSize()];
		//fargesensor med array og sampleprovider

		int svart = 0;
		for (int i = 0; i<100; i++){
			fargeLeser.fetchSample(fargeSample, 0);
			svart += fargeSample[0]* 100;
		}
		svart = svart / 100 + 5;

		//sammenligner farger, sjekker etter svart

	 	boolean fortsett = true;

		while(fortsett) {

	   		fargeLeser.fetchSample(fargeSample, 0);

			System.out.println("Svart: " + svart);

			System.out.println("Farge: " + svart);

       		if (fargeSample[0]*100 == svart){
		 		System.out.println("Svart");
		 		Thread.sleep(200);
			}
			else  {
				System.out.println("Ikke svart");
				Motor.A.setSpeed(200);
				Motor.B.setSpeed(300);
				Thread.sleep(200);
			}
		}
	}
}