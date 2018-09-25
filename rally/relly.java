import lejos.hardware.lcd.*;
import lejos.hardware.motor.*;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.port.Port;
import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.robotics.SampleProvider;

public class relly {
	public static void main (String[] args)
	throws Exception
	{
		Motor.A.setSpeed(100);
		Motor.B.setSpeed(100); //setter motorfart A til 200 og B til 300.

	 	Brick brick = BrickFinder.getDefault();
	 	Port s1 = brick.getPort("S1"); //finner hvilke sensorer som er koblet til

		EV3ColorSensor fargesensor = new EV3ColorSensor(s1);
		SampleProvider fargeLeser = fargesensor.getMode("RGB");
		float[] fargeSample = new float[fargeLeser.sampleSize()]; //fargesensor med array og sampleprovider

		int svart = 0;
		for (int i = 0; i<100; i++){
			fargeLeser.fetchSample(fargeSample, 0);
			svart += fargeSample[0]* 100;
		}
		svart = svart / 100 + 5; //sammenligner farger, sjekker etter svart

	 	boolean fortsett = true;

		while(fortsett) {

	   		fargeLeser.fetchSample(fargeSample, 0);

       		if (fargeSample[0]*100 == svart){
		 		Motor.A.forward(true);
				Motor.B.forward(true);
			}
			else  {
				Motor.A.setSpeed(150);
				Motor.B.setSpeed(100);
				Motor.A.forward(true);
				Motor.B.forward(true);
			}
		}
	}
}
