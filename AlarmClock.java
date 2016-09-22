package AlarmClock;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * ProjectPortfolio
 * @author Winnie Sep 21, 2016
 * AlarmClock.java
 * The purpose of this program is to accept a command line argument for the time
 * to go off for the alarm. When that happens, it will launch a Youtube video 
 * in their browser and starts playing music. 
 */
public class AlarmClock {
	
	public static void main(String[] args) throws IOException, InterruptedException{
		
		String playlistFile = "../ProjectPortfolio/src/AlarmClock/Playlist.txt";
		BufferedReader br = null;
		ArrayList<String> playlist = new ArrayList<String>();
		try {
			FileReader fr = new FileReader(playlistFile);
			br = new BufferedReader(fr);
			String link = "";
			
			while((link = br.readLine()) != null){
				playlist.add(link);
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("Your youtube playlist cannot be found. :[");
			e.printStackTrace();
		} finally{
			if(br != null){
				br.close();
			}
		}
		
		if(args.length == 0){
			throw new IllegalArgumentException("User did not enter the time to set for your alarm.");
		}
		
		for(String alarm : args){
			
			if(checkValidTime(alarm)){
				DateFormat dateFormat = new SimpleDateFormat("HH:mm");
				GregorianCalendar cal = (GregorianCalendar) Calendar.getInstance();
				String time = dateFormat.format(cal.getTime());
				if(alarm.equals(time)){
					alarmAlert(time, playlist);
				}
				//boolean alreadyExecutedOnce = false;
				while(!alarm.equals(time)){
					GregorianCalendar calendar = (GregorianCalendar) Calendar.getInstance();
					String updatedTime = dateFormat.format(calendar.getTime());
					System.out.println("Current time is " + updatedTime);
    				Thread.sleep(10000);
    				//if(alarm.equals(updatedTime) && alreadyExecutedOnce){
        			if(alarm.equals(updatedTime)){
    					alarmAlert(updatedTime, playlist);
    					//alreadyExecutedOnce = true; 
    				}
				}
			}else{
				System.out.println("User did not enter correct time format.");
			}
		}
	}


	
	private static boolean checkValidTime(String time) {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		df.setLenient(false);
		try{
			df.parse(time.trim());
		} catch(ParseException e){
			e.getStackTrace();
			return false;
		}
		return true;
	}
	
	private static void alarmAlert(String updatedTime, ArrayList<String> playlist){

			System.out.println("Wake up! Wake up! Buzz Buzz");
			int index = new Random().nextInt(playlist.size());
			String url = playlist.get(index); 
			openWebPage(url);
		
	}
	
	public static void openWebPage(String link){
		try{
			Desktop.getDesktop().browse(new URL(link).toURI());
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
