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


class Heisobje {
	static File lydopp = new File("./opp.wav");
	static File lydned = new File("./ned.wav");
	static File lydforste = new File("./1etasje_1.wav");
	static File lydandre = new File("./2etasje_1.wav");

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
		Motor.B.setSpeed(50);

		boolean fortsett = true;

		while(fortsett) {

			trykksensor1.fetchSample(trykkSample1, 0);
			trykksensor2.fetchSample(trykkSample2, 0);
			lengdeLeser.fetchSample(lengdeSample, 0);

			if (trykkSample1[0] > 0){
				heisOpp(lengdeSample, lengdeLeser);
			}

			if (trykkSample2[0] > 0){
				heisNed(lengdeSample, lengdeLeser);

			}
		}
	}

	public static void heisNed(float[] lengde, SampleProvider lengdeLeser){
		musikk sangrunnable = new musikk(new File("./heismusikk.wav"),lydned,lydforste);
		Motor.B.forward();
		try{
			Thread.sleep(2100);
		}catch(Exception e){
			System.out.println(e);
		}
		Motor.B.stop();
		//File ned = new File("./ned.wav");
		//File forste = new File("./1etasje.wav");
		//lejos.hardware.Sound.playSample(ned);
		Thread vakkersang = new Thread(sangrunnable);
		vakkersang.start();
		try{
			Thread.sleep(3500);
		}catch(Exception e){
			System.out.println(e);
		}
		while(lengde[0] > 0.036) {
			lengdeLeser.fetchSample(lengde, 0);
			Motor.A.backward();
			System.out.println(lengde[0]);
			if (lengde[0] <= 0.042) {
				Motor.A.stop();
				//motorstopp NB! må være før vakkersang.join()
				sangrunnable.doStop();
				//stopp den vakre sangen via runtimen
				try{
					vakkersang.join();
				}catch(Exception e){
					System.out.println(e);
				}
				//sier at vakkersang threaden må bli ferdig
				//lejos.hardware.Sound.playSample(forste);
				Motor.B.backward();
				try{
					Thread.sleep(2100);
				}catch(Exception e){
					System.out.println(e);
				}
				Motor.B.stop();
				break;
			}
		}
	}

	public static void heisOpp(float[] lengde, SampleProvider lengdeLeser){
		musikk sangrunnable = new musikk(new File("./heismusikk.wav"),lydopp,lydandre);
		Motor.B.forward();
		//System.out.println("0");
		try{
			Thread.sleep(2100);
		}catch(Exception e){
			System.out.println(e);
		}
		Motor.B.stop();
	//	System.out.println("1");
	//	File opp = new File("./opp.wav");
	//	System.out.println(2);
		//lejos.hardware.Sound.playSample(opp);
		//System.out.println(3);
		Thread vakkersang = new Thread(sangrunnable);
		vakkersang.start();
		try{
		Thread.sleep(3500);
		}catch(Exception e){
			System.out.println(e);
		}
		while(lengde[0] < 0.134) {
		//	System.out.println(4);
			lengdeLeser.fetchSample(lengde, 0);
		//	System.out.println(5);
			Motor.A.forward();
		//	System.out.println(6);
			System.out.println(lengde[0]);
		//	System.out.println(7);
			File andre = new File("./2etasje.wav");
		//	System.out.println('8');
			if (lengde[0] >= 0.126) {
			//	System.out.println('9');
				Motor.A.stop();
				//motorstopp NB! må være før vakkersang.join()
				sangrunnable.doStop();
				//stopp den vakre sangen via runtimen
				try{
					vakkersang.join();
				}catch(Exception e){
					System.out.println(e);
				}
				//sier at vakkersang threaden må bli ferdig
				//lejos.hardware.Sound.playSample(andre);
				Motor.B.backward();
				try{
					Thread.sleep(2100);
				}catch(Exception e){
					System.out.println(e);
				}
				Motor.B.stop();
				break;
			}
		}
	}
}