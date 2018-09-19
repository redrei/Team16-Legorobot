import lejos.hardware.lcd.*;
import lejos.hardware.motor.*;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.port.Port;
import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.robotics.SampleProvider;
import lejos.hardware.sensor.NXTSoundSensor;
import lejos.hardware.Button;
import java.io.*;
import lejos.hardware.Sound.*;

public class HitratunnelBedre{
	public static void main (String[] args)  throws Exception{
		Motor.A.setSpeed(200);
	 	Motor.C.setSpeed(200);
	 	Motor.B.setSpeed(200);


	 	Brick brick = BrickFinder.getDefault();
	 	Port s1 = brick.getPort("S1");
		Port s2 = brick.getPort("S2");
		Port s3 = brick.getPort("S3");

		SampleProvider trykksensor = new EV3TouchSensor(s1);
		float[] trykkSample = new float[trykksensor.sampleSize()];

		EV3ColorSensor fargesensor = new EV3ColorSensor(s2);
		SampleProvider fargeLeser = fargesensor.getColorIDMode();


		NXTSoundSensor lydsensor = new NXTSoundSensor(s3);
		SampleProvider lyd = lydsensor.getDBAMode();
		float[] lydSample = new float[lydsensor.sampleSize()];





	 	boolean fortsett = true;

		while(Button.ESCAPE.isUp()) {
		float[] fargeSample = new float[fargeLeser.sampleSize()];
	   		fargeLeser.fetchSample(fargeSample, 0);
			Motor.B.forward();
       		if (fargeSample[0] == 7){
		 		LCD.clear();
		 		System.out.println("Svart: ");
				Motor.A.stop(true);
				Motor.C.stop(true);
				Thread.sleep(200);

				Motor.A.backward();
				Motor.C.backward();
				Thread.sleep(500);


				Motor.A.rotate(230);
				Motor.C.rotate(-230);
				while(Motor.A.isMoving())Thread.yield();
			}
			trykksensor.fetchSample(trykkSample, 0);

    	 	if (trykkSample[0] > 0){
		 		System.out.println("Svinger");
		 		LCD.clear();
		 		File au = new File("./au.wav");
		 		lejos.hardware.Sound.playSample(au);
				Motor.A.stop(true);
				Motor.C.stop(true);
				Thread.sleep(500);

				Motor.A.backward();
				Motor.C.backward();
				Thread.sleep(500);

				Motor.A.rotate(270);
				Motor.C.stop(true);

				while (Motor.A.isMoving()) Thread.yield();

			}
			lydsensor.fetchSample(lydSample, 0);
			if (lydSample[0] > 0.75){
				System.out.println("Hoerte en lyd og stopper.");
				Motor.A.stop(true);
				Motor.C.stop(true);
				Thread.sleep(5000);
			}
			else{
				Motor.A.forward();
				Motor.C.forward();
			}
		}
	}
}
