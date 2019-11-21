import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DefaultComboBoxModel;

public class fileReadLine {
		private Scanner input;
		private Set<String> IPV4 = new HashSet<String>();
		private Set<String> DEST = new HashSet<String>();
		private File file;
		private ArrayList<Double> timestamp = new ArrayList<>();
		private ArrayList<Integer> ipPacketSize = new ArrayList<>();
		private String regex;
		private Pattern pattern;
		private String currentLine;
		private Matcher regexMatcher;
		static double timestampMax = 600.0;
		private String[] Hosts;
		private String[] DestHost;
		
		fileReadLine(File f) {
			this.file = f;
		
			input = null;
			try{
				input = new Scanner(file);
			} catch(IOException e) {
				return;
			}
			
			regex = "^(?:[^\\t]*)\\t([^\\t]*)\\t([^\\t]*)\\t(?:[^\\t]*)\\t([^\\t]*)\\t(?:[^\\t]*)\\t(?:[^\\t]*)\\t([^\\t]*)";
	        pattern = Pattern.compile(regex);
			while(input.hasNext()) {
				currentLine = input.nextLine();
		        regexMatcher = pattern.matcher(currentLine);
		        if (regexMatcher.find()) {
		        	if(!regexMatcher.group(1).equals("") & !regexMatcher.group(2).equals("") & !regexMatcher.group(3).equals("")) {
		        		timestamp.add(Double.parseDouble(regexMatcher.group(1)));
		        		timestampMax = Double.parseDouble(regexMatcher.group(1));
		        		IPV4.add(regexMatcher.group(2));
		        		DEST.add(regexMatcher.group(3));
		        		if(!regexMatcher.group(4).equals("")) {
			        		ipPacketSize.add(Integer.parseInt(regexMatcher.group(4)));
			        	} else {
			        		ipPacketSize.add(0);
			        	}
		        	}
		        }
			}   
			input.close();		
			Hosts = IPV4.toArray(new String[IPV4.size()]);
			Arrays.sort(Hosts, new ipSort());
			DestHost = DEST.toArray(new String[DEST.size()]);
			Arrays.sort(DestHost, new ipSort());
			WindowGUI.srcModel = new DefaultComboBoxModel<String>(Hosts);
			WindowGUI.destModel = new DefaultComboBoxModel<String>(DestHost);
		}
		
	}