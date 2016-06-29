package fileManipulate;

class SizeCalculator {
	static void totalSize(long size) {
		System.out.println("The total size of the directory is: " + Convert.convertBytes(size, true)); 
	}
	
	static void totalDeletedSize(long erasedSize) {
		System.out.println("Total erased size: " + Convert.convertBytes(erasedSize, true));
	}
	
	static void actualSize(long size, long erasedSize) {
		System.out.println("Total erased size: " + Convert.convertBytes((size - erasedSize), true));
	}
}
