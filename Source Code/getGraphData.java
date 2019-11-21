import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class getGraphData {
	private Scanner input;
	
	private File file;
	double rEndTime;
	static int slot;
	static Double[] intervalArray;
	static Double[] byteArray;
	double interval;
	
	private String regex;
	private Pattern pattern;
	private String currentLine;
	private Matcher regexMatcher;
	static boolean hasTime;
	static boolean hasSrcIP;
	static boolean hasDestIP;
	private int byteIndex;
	private int newByteIndex;
	static int ipPacketSizeMax;

	getGraphData(File f) {
		this.file = f;
		
		input = null;
		try{
			input = new Scanner(file);
		} catch(IOException e) {
			return;
		}
		ipPacketSizeMax = 0;
		rEndTime = Math.ceil(fileReadLine.timestampMax);
	    slot = (int) Math.ceil(rEndTime / 2);
	    intervalArray = new Double[slot];
	    byteArray = new Double[slot];
	    interval = 2;
	    for(int i = 0; i < slot; i++) {
	        intervalArray[i] = interval;
	        interval += 2.0;
	    }
	    for(int i = 0; i < slot; i++) {
	        byteArray[i] = 0.0;
	    }
	    
	    regex = "^(?:[^\\t]*)\\t([^\\t]*)\\t([^\\t]*)\\t(?:[^\\t]*)\\t([^\\t]*)\\t(?:[^\\t]*)\\t(?:[^\\t]*)\\t([^\\t]*)";
        pattern = Pattern.compile(regex);
		while(input.hasNext()) {
			currentLine = input.nextLine();
			regexMatcher = pattern.matcher(currentLine);
	        if (regexMatcher.find()) {
				if(regexMatcher.group(2).equals(WindowGUI.currentIP) | regexMatcher.group(3).equals(WindowGUI.currentIP)) {
		            byteIndex = Arrays.binarySearch(intervalArray, Math.floor(Double.parseDouble(regexMatcher.group(1))));
		            ipPacketSizeMax += Integer.parseInt(regexMatcher.group(4));
		            
		            if(byteIndex >= 0) {
		                byteArray[byteIndex + 1] += Double.parseDouble(regexMatcher.group(4));
		            } else {
		                newByteIndex = -byteIndex - 1;
		                byteArray[newByteIndex] += Double.parseDouble(regexMatcher.group(4));
		            }
		        }
	        }
		}
		input.close();
	}
}