import java.util.Comparator;

public class ipSort implements Comparator<String> {
	public int compare(String o1, String o2) {
		if(o1.length() < o2.length()) {
    		return -1;
    	} else if(o1.length() == o2.length()) {
    		return o1.compareTo(o2);
    	} else {
    		return 1;
    	}
	}
}
