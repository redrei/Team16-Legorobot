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
	static File LYD_OPP = new File("./opp.wav");
	static File LYD_NED = new File("./ned.wav");
	static File LYD_FORSTE = new File("./forste.wav");
	static File LYD_ANDRE = new File("./andre.wav");

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
				//hvis trykksensor 1 blir trykket går heisen opp
				heisOpp(lengdeSample, lengdeLeser);
			}

			if (trykkSample2[0] > 0){
				//hvis trykksensor 2 blir trykket går heisen ned
				heisNed(lengdeSample, lengdeLeser);

			}
		}
	}

	public static void heisNed(float[] lengde, SampleProvider lengdeLeser){
		//denne metoden kjører heisen ned
		Musikk sangRunnable = new Musikk(new File("./heismusikk.wav"),LYD_NED,LYD_FORSTE);
		//lager et objekt av klassen Musikk for å lage en thread av senere
		Motor.B.forward();
		try{
			Thread.sleep(2100);
		}catch(Exception e){
			System.out.println(e);
		}
		Motor.B.stop();
		Thread vakkerSang = new Thread(sangRunnable);
		//lager thread med objektet sangRunnable av klassen Musikk som runnable
		vakkerSang.start();
		//dette starter Musikk.run() som en ny thread
		try{
			Thread.sleep(3500);
		}catch(Exception e){
			System.out.println(e);
		}
		while(lengde[0] > 0.036) {
			lengdeLeser.fetchSample(lengde, 0);
			Motor.A.backward();
			//System.out.println(lengde[0]);
			if (lengde[0] <= 0.042) {//
				Motor.A.stop();
				//motorstopp NB! må være før vakkerSang.join()
				sangRunnable.doStop();
				//stopp den vakre sangen via runtimen
				try{
					vakkerSang.join();
					//venter på at den vakre sangen stopper
				}catch(Exception e){
					System.out.println(e);
				}
				//sier at vakkerSang threaden må bli ferdig
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
		//Denne metoden kjører heisen opp
		Musikk sangRunnable = new Musikk(new File("./heismusikk.wav"),LYD_OPP,LYD_ANDRE);
		//lager et objekt av klassen Musikk for å lage en thread av senere
		Motor.B.forward();
		try{
			Thread.sleep(2100);
		}catch(Exception e){
			System.out.println(e);
		}
		Motor.B.stop();
		Thread vakkerSang = new Thread(sangRunnable);
		//lager thread med objektet sangRunnable av klassen Musikk som runnable
		vakkerSang.start();
		//dette starter Musikk.run() som en ny thread
		try{
		Thread.sleep(3500);
		}catch(Exception e){
			System.out.println(e);
		}
		while(lengde[0] < 0.134) {
			lengdeLeser.fetchSample(lengde, 0);
			Motor.A.forward();
			//System.out.println(lengde[0]);
			File andre = new File("./2etasje.wav");
			if (lengde[0] >= 0.126) {
				Motor.A.stop();
				//motorstopp NB! må være før vakkerSang.join()
				sangRunnable.doStop();
				//stopp den vakre sangen via runtimen
				try{
					vakkerSang.join();
					//venter på at den vakre sangen stopper
				}catch(Exception e){
					System.out.println(e);
				}
				//sier at vakkerSang threaden må bli ferdig
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