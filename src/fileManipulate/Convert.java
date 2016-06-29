package fileManipulate;

class Convert {
	/*
	 * Convert from bytes to a legible format.
	 */
	public static String convertBytes(long bytes, boolean sim) {
	    int unit = sim ? 1000 : 1024;
	    if (bytes < unit) return bytes + " B";
	    int exp = (int) (Math.log(bytes) / Math.log(unit));
	    String pre = (sim ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (sim ? "" : "i");
	    return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}
}
