import java.io.*;
import lejos.hardware.Sound;

public class musikk extends Thread{
	private File musikk;
	public musikk(File musikk){
	this.musikk = musikk;
	}
	public void run(){
		Sound.playSample(musikk);
	}
}