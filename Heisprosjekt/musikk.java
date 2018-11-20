import java.io.*;
import lejos.hardware.Sound;

public class musikk implements Runnable{
	private File musikk;
	private File lyden;
	private File lydto;
	private volatile boolean exit = false;
	public musikk(File musikk, File lyden, File lydto){
	this.lyden = lyden;
	this.lydto = lydto;
	this.musikk = musikk;
	}
	public void run(){
		try{
			Sound.playSample(lyden);
		}catch(Exception e){
			System.out.println(e);
		}
		while(!exit){
			try{
				Sound.playSample(musikk);
			}catch(Exception e){
				System.out.println(e);
			}
		}
		try{
			Sound.playSample(lydto);
		}catch(Exception e){
			System.out.println(e);
		}
	}
     public synchronized void doStop() {
        exit = true;
    }

    public synchronized boolean keepRunning() {
        return exit == false;

     }


}