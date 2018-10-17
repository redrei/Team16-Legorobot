import lejos.hardware.lcd.*;
import lejos.hardware.motor.*;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.port.Port;
import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.robotics.SampleProvider;
import lejos.hardware.Button;
import java.io.*;
import lejos.hardware.Sound.*;
import lejos.hardware.sensor.NXTUltrasonicSensor;


class Heis {
	public static void main (String[] args)  throws Exception{

		Brick brick = BrickFinder.getDefault();
		Port s1 = brick.getPort("S1");
		Port s2 = brick.getPort("S2");
		Port s3 = brick.getPort("S3");

		SampleProvider trykksensor1 = new EV3TouchSensor(s1);
		float[] trykkSample1 = new float[trykksensor1.sampleSize()];

		SampleProvider trykksensor2 = new EV3TouchSensor(s2);
		float[] trykkSample2 = new float[trykksensor2.sampleSize()];

		NXTUltrasonicSensor ultrasonisksensor = new NXTUltrasonicSensor(s3);
		SampleProvider lengdeLeser = ultrasonisksensor.getDistanceMode();
		float[]	lengdeSample = new float[lengdeLeser.sampleSize()];

		Motor.A.setSpeed(225);

		boolean fortsett = true;

		while(fortsett) {

			trykksensor1.fetchSample(trykkSample1, 0);
			trykksensor2.fetchSample(trykkSample2, 0);
			lengdeLeser.fetchSample(lengdeSample, 0);

			if (trykkSample1[0] > 0){
				Motor.A.forward();
				Thread.sleep(1250);
				Motor.A.stop();
				System.out.println(lengdeSample[0]);
			}

			if (trykkSample2[0] > 0){
				Motor.A.backward();
				Thread.sleep(1400);
				Motor.A.stop();
				System.out.println(lengdeSample[0]);
			}


		}

	}
}