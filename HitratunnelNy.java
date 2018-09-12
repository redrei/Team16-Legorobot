import lejos.hardware.lcd.*;
import lejos.hardware.motor.*;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.port.Port;
import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.robotics.SampleProvider;
import lejos.hardware.sensor.NXTSoundSensor;

public class HitratunnelNy{
	public static void main (String[] args)  throws Exception{
		Motor.A.setSpeed(450);
	 	Motor.C.setSpeed(450);

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

		while(fortsett) {
		float[] fargeSample = new float[fargeLeser.sampleSize()];
	   		fargeLeser.fetchSample(fargeSample, 0);
	   		System.out.println(fargeSample[0]);

       		if (fargeSample[0] == 7){
		 		LCD.clear();
		 		System.out.println("Svart: ");
				Motor.A.stop();
				Motor.C.stop();
				Thread.sleep(500);

				Motor.A.backward();
				Motor.C.backward();
				Thread.sleep(500);

				Motor.A.rotate(390);
				Motor.C.stop();

				while (Motor.A.isMoving()) Thread.yield();

					Thread.sleep(1000);
					Motor.A.stop();
    	 			Motor.C.stop();
			}

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

				while (Motor.A.isMoving()) Thread.yield();

					Thread.sleep(1000);
					Motor.A.stop();
    	 			Motor.C.stop();
			}
			lydsensor.fetchSample(lydSample, 0);
			if (lydSample[0] > 0.55){
				System.out.println("Hørte en lyd og stopper.");
				Motor.A.stop();
				Motor.C.stop();
				Thread.sleep(5000);
			}
			else{
				Motor.A.forward();
				Motor.C.forward();
			}
		}
	}
}
