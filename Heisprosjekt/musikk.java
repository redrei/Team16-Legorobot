import java.io.*;
import lejos.hardware.Sound;

public class Musikk implements Runnable{
	private File musikk;
	private File lydEn;
	private File lydTo;
	private volatile boolean exit = false;
	public Musikk(File musikk, File lydEn, File lydTo){
	this.lydEn = lydEn;
	this.lydTo = lydTo;
	this.musikk = musikk;
	}
	public void run(){
		//spiller av heislyden
		try{
			Sound.playSample(lydEn);
			//spiller av opp eller ned lyden
		}catch(Exception e){
			System.out.println(e);
		}

		while(!exit){

			try{
				Sound.playSample(musikk);
				//spiller av den særs vakre heismusikken
			}catch(Exception e){
				System.out.println(e);
			}
		}

		try{
			Sound.playSample(lydTo);
			//spiller av eklæringen av hvilken etasje heisen er i
		}catch(Exception e){
			System.out.println(e);
		}
	}

    public synchronized void doStop() {
		 //får musikken til å stoppe
        exit = true;
    }

    public synchronized boolean keepRunning() {
		//bare bekrefter at prosessen fortsatt kjører, er ikke i bruk og endrer ingenting
        return exit == false;

     }


}