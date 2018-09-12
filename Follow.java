

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

/**
 * Requires a wheeled vehicle with two independently controlled
 * motors connected to motor ports B and C, and
 * an EV3 IR sensor connected to port 4.
 *
 * @author Lawrie Griffiths
 */
public class Follow {


	public static void main(String[] args) {

		EV3ColorSensor ir = new EV3ColorSensor(SensorPort.S4);
		SampleProvider lyd = new NXTSoundSensor(SensorPort.S3);
		float[] sample = new float[lyd.sampleSize()];



		while(Button.ESCAPE.isUp()) {
			 lyd.fetchSample(sample, 0);
				try
				{
				    Thread.sleep(100);
				}
				catch(InterruptedException ex)
				{
				    Thread.currentThread().interrupt();
}
				System.out.println(ir.getColorID() + " lyd: " + sample);
		}
		ir.close();
	}
}
