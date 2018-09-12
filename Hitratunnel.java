
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

public class Hitratunnel{
	public static void main (String[] args)  throws Exception{
		Motor.A.setSpeed(450);
		Motor.B.setSpeed(900);
	 	Motor.C.setSpeed(450);
		//setter motorfart til 450

	 	Brick brick = BrickFinder.getDefault();
	 	Port s1 = brick.getPort("S1");
		Port s2 = brick.getPort("S2");
		Port s3 = brick.getPort("S3");
		//finner hvilke sensorer som er koblet til

		SampleProvider trykksensor = new EV3TouchSensor(s1);
		float[] trykkSample = new float[trykksensor.sampleSize()];
		//registrerer trykksensor og lager en array for samples

		EV3ColorSensor fargesensor = new EV3ColorSensor(s2);
		SampleProvider fargeLeser = fargesensor.getMode("RGB");
		float[] fargeSample = new float[fargeLeser.sampleSize()];
		//fargesensor med array og sampleprovider

		NXTSoundSensor lydsensor = new NXTSoundSensor(s3);
		SampleProvider lyd = lydsensor.getDBAMode();
		float[] lydSample = new float[lydsensor.sampleSize()];
		//lydsensor med array og sampleprovider


		int svart = 0;
		for (int i = 0; i<100; i++){
			fargeLeser.fetchSample(fargeSample, 0);
			svart += fargeSample[0]* 100;
		}
		svart = svart / 100 + 5;

		//sammenligner farger, sjekker etter svart



		while(Button.ESCAPE.isUp()) {

	   		fargeLeser.fetchSample(fargeSample, 0);

	   		System.out.println("farge: " + fargesensor.getColorID());

			System.out.println("Farge: " + svart);


       		if (fargesensor.getColorID()==7){
		 		LCD.clear();
				Motor.A.stop();
				Motor.C.stop();
				Thread.sleep(500);
				System.out.println("svart");

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
			if (lydSample[0] > 0.4){
				System.out.println("Hørte en lyd og stopper.");

					Motor.A.stop();

				Motor.B.stop();

    	 			Motor.C.stop();
				Thread.sleep(2000);
			}
			else{
				Motor.A.forward();
				Motor.C.forward();
			}
		}
	}
}
