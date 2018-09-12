

import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.RegulatedMotor;
import lejos.hardware.sensor.NXTSoundSensor;
import java.util.concurrent.TimeUnit;
import lejos.robotics.SampleProvider;
//hehehehehehehehe
public class Follow {


	public static void main(String[] args) {

		EV3ColorSensor ir = new EV3ColorSensor(SensorPort.S4);
		//henter fargesensor
		SampleProvider lyd = new NXTSoundSensor(SensorPort.S3);
		//henter lydsensor som en sampleprovider



		while(Button.ESCAPE.isUp()) { 
			//while til escape blir trykket
			 float[] sample = new float[lyd.sampleSize()];
			//lager en float-array med med lengden antall sampler som blir målt av lydsensor 
			 lyd.fetchSample(sample, 0);
			//henter sampler til samplearrayen fra lydsensor
				try
				{
				    Thread.sleep(100);
				}
				catch(InterruptedException ex)
					//sleep krevde e try catch
				{
				    Thread.currentThread().interrupt();
}
				System.out.println(ir.getColorID() + " lyd: " + sample[0]); 
			//henter farge id-en som er en int, og det første elementet i arrayen "sample"
		}
		ir.close();
		//slår av fargesensor
	}
}
