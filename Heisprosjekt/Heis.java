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
import lejos.hardware.sensor.EV3UltrasonicSensor;


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

		EV3UltrasonicSensor ultrasonisksensor = new EV3UltrasonicSensor(s3);
		SampleProvider lengdeLeser = ultrasonisksensor.getDistanceMode();
		float[]	lengdeSample = new float[lengdeLeser.sampleSize()];

		Motor.A.setSpeed(150);

		boolean fortsett = true;

		while(fortsett) {

			trykksensor1.fetchSample(trykkSample1, 0);
			trykksensor2.fetchSample(trykkSample2, 0);
			lengdeLeser.fetchSample(lengdeSample, 0);

			if (trykkSample1[0] > 0){
				File opp = new File("./opp.wav");
				lejos.hardware.Sound.playSample(opp);
				while(lengdeSample[0] < 0.14) {
					lengdeLeser.fetchSample(lengdeSample, 0);
					Motor.A.forward();
					System.out.println(lengdeSample[0]);
					File andre = new File("./2etasje.wav");
					if (lengdeSample[0] >= 0.14) {
						Motor.A.stop();
						lejos.hardware.Sound.playSample(andre);
						break;
					}
				}
			}

			if (trykkSample2[0] > 0){
				File ned = new File("./ned.wav");
				lejos.hardware.Sound.playSample(ned);
				while(lengdeSample[0] > 0.03) {
					lengdeLeser.fetchSample(lengdeSample, 0);
					Motor.A.backward();
					System.out.println(lengdeSample[0]);
					File forste = new File("./1etasje.wav");
					if (lengdeSample[0] <= 0.04) {
						Motor.A.stop();
						lejos.hardware.Sound.playSample(forste);
						break;
					}
				}
			}
		}
	}
}