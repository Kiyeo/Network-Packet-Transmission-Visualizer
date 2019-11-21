class Host implements Comparable<Host> {
    private String ip;
    public Host(String ipA) {
        this.ip = ipA;
    }
    public int compareTo(Host other) {
    	 if(this.ip.length() < other.ip.length()) {
    		return -1;
    	} else if(this.ip.length() == other.ip.length()) {
    		return this.ip.compareTo(other.ip);
    	} else {
    		return 1;
    	}
    }
    
    public String toString() {
        return this.ip;
    }
}