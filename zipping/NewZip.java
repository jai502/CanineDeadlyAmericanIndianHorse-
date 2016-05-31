package zipping;


import java.io.BufferedOutputStream;
// java imports
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class NewZip {
	// size of buffer for zip reading
	private static int bufferSize = 8192;
	
	
	// method deletes a directory
	// this method was sourced online. Used as it was much mroe elegent than out implementation
	public static void deleteDir(File dir) {
	    File[] files = dir.listFiles();
	    if(files != null) { 				// empty directory
	        for(File f: files) {			// loop through all entries in the directory
	            if(f.isDirectory()) {		// if file is a directory, call deleteDir on it
	                deleteDir(f);
	            } else {
	                f.delete();
	            }
	        }
	    }
	    dir.delete();
	}
	
	// encapsulating method for the deleteDir, checks that target is a directory
	// returns null on success
	public static String deleteDirFromPath(String path){
		File dir = new File(path);
		if(!dir.exists()){
			return String.format("[ZIP] %s - no such file or directory", path);
		} else if (dir.isDirectory()) {
			deleteDir(dir);
			return null;
		} else {
			return String.format("[ZIP] %s is not a directory", path);
		}
	}
	
	// extracts a zip to a folder, maintaining directory structure
	// code base on an example on the tutorial website "codejava.net"
	public static void extract(String zipPath, String destPath) throws IOException {
		// open the destination directory, if it doesn't exist create it
		File destDir =  new File(destPath);
		if (!destDir.exists()) {
			System.out.printf("[ZIP] Created destination dir for unzipping: %s", destPath);
			destDir.mkdir();
		}
		
		// create Zip input stream
		ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipPath));
		
		// get first entry in the zip
		ZipEntry zipEntry = zipIn.getNextEntry();
		
		// for all entries in the zip
		while (zipEntry != null) {
			// get path to this zip entry
			String entryPath = destPath + File.separator + zipEntry.getName();
			if (zipEntry.isDirectory()) {	// zip entry is a directory
				// create the directory
				File zipEntryDir = new File(entryPath);
				zipEntryDir.mkdir();
			} else {						// zip entry is a file 
				// extract the file
				extractFile(zipIn, entryPath);
            }
			// close the current entry, and get the next one
            zipIn.closeEntry();
            zipEntry = zipIn.getNextEntry();
        }
		// close the zip stream
        zipIn.close();
	}
	
	// method for extracting a file from a zip stream (cleans up the above method)
	// again, derived from a we-tutorial
    private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
    	// create a buffered output stream for writing the file
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        
        // create a buffer for reading from the zip stream/writing to the file output stream,
        byte[] bytesIn = new byte[bufferSize];
        
        // number of bytes read
        int read = 0;
        
        // keep reading (while stream has not ended)
        while ((read = zipIn.read(bytesIn)) > 0) {
            bos.write(bytesIn, 0, read);
        }
        
        // close the buffered file output stream
        bos.close();
    }
    
    // method to zip a directory
    public static void zipDirectory(String zipPath) {
    	
    }
}
