import lejos.hardware.lcd.*;
import lejos.hardware.motor.*;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.port.Port;
import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.robotics.SampleProvider;
import lejos.hardware.Button;
import java.io.*;
import lejos.hardware.Sound.*;
import lejos.hardware.sensor.NXTLightSensor;

public class relly {
	public static void main (String[] args)
	throws Exception
	{
		Motor.A.setSpeed(100);
		Motor.B.setSpeed(100);
//setter motorfart A til 200 og B til 300.

	 	Brick brick = BrickFinder.getDefault();
	 	Port s1 = brick.getPort("S1");
	 	Port s2 = brick.getPort("S2");
//finner hvilke sensorer som er koblet til

		EV3ColorSensor fargesensor = new EV3ColorSensor(s1);
		SampleProvider fargeLeser = fargesensor.getColorIDMode();

		NXTLightSensor lyssensor = new NXTLightSensor(s2);
		SampleProvider lysLeser = lyssensor.getAmbientMode();
//fargesensor med array og sampleprovider

		int svart = 7;
//sammenligner farger, sjekker etter svart

	 	boolean fortsett = true;

		while(fortsett && Button.ESCAPE.isUp()) {
			float[] fargeSample = new float[fargeLeser.sampleSize()];
			float[] lysSample = new float[lysLeser.sampleSize()];

	   		fargeLeser.fetchSample(fargeSample, 0);
			lysLeser.fetchSample(lysSample, 0);
       		if (fargeSample[0] == svart){
				Motor.A.setSpeed(100);
				Motor.B.setSpeed(100);
		 		Motor.A.forward();
				Motor.B.forward();
				Thread.sleep(500);
				System.out.println("1");
				File au = new File("./au.wav");
		 		lejos.hardware.Sound.playSample(au);
			}else if(lysSample[0] < 0.2){
				Motor.A.setSpeed(100);
				Motor.B.setSpeed(100);
			}
			else  {
				Motor.A.setSpeed(150);
				Motor.B.setSpeed(100);
				Motor.A.forward();
				Motor.B.forward();
				Thread.sleep(500);
				System.out.println("0");
			}
		}
	}
}
