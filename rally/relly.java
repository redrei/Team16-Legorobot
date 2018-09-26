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
import lejos.hardware.sensor.NXTSoundSensor;

public class relly {
	public static void main (String[] args)
	throws Exception
	{
		Motor.A.setSpeed(300);
		Motor.B.setSpeed(300);
//setter motorfart A til 200 og B til 300.

	 	Brick brick = BrickFinder.getDefault();
	 	Port s1 = brick.getPort("S3");
	 	Port s2 = brick.getPort("S2");
//finner hvilke sensorer som er koblet til

		EV3ColorSensor fargesensor = new EV3ColorSensor(s1);
		SampleProvider fargeLeser = fargesensor.getColorIDMode();

		NXTLightSensor lyssensor = new NXTLightSensor(s2);
		SampleProvider lysLeser = lyssensor.getAmbientMode();
		float[] fargeSample = new float[fargeLeser.sampleSize()];
		float[] lysSample = new float[lysLeser.sampleSize()];
//fargesensor med array og sampleprovider

//sammenligner farger, sjekker etter svart

		while(Button.ESCAPE.isUp()) {

	   		fargeLeser.fetchSample(fargeSample, 0);
			lysLeser.fetchSample(lysSample, 0);
			/*if (fargeSample[0] == svart && lysSample[0] < 0.24){
				Motor.A.setSpeed(300);
				Motor.B.setSpeed(300);
				Motor.A.forward();
				Motor.B.forward();
				Thread.sleep(200);
				//System.out.println("0");
			}
       		else*/ if (fargeSample[0] == 7){
				Motor.A.setSpeed(200);
				Motor.B.setSpeed(200);
		 		Motor.A.forward();
				Motor.B.backward();
				//System.out.println("1");
				//File au = new File("./au.wav");
		 		//lejos.hardware.Sound.playSample(au);
			}else if(lysSample[0] < 0.24){
				Motor.A.setSpeed(300);
				Motor.B.setSpeed(400);
				//System.out.println(lysSample[0]);
				//Thread.sleep(200);
			}
			else  {
				Motor.A.setSpeed(350);
				Motor.B.setSpeed(350);
				Motor.A.forward();
				Motor.B.forward();
				//Thread.sleep(200);
				//System.out.println("0");
			}
		}
	}
}
