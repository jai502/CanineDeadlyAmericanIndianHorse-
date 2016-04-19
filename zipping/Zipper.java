package zipping;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Zipper{

	private static final int BUFFER_SIZE = 4096;

	public void unzip(String input, String destination)	throws IOException 
	{
		File output = new File(destination);
		if (!output.exists()) 
			output.mkdir();
		
		ZipInputStream inputStream = new ZipInputStream(new FileInputStream(input));
		ZipEntry currentFile = inputStream.getNextEntry();
		
		while (currentFile != null)
		{
			String filePath = destination + File.separator + currentFile.getName();
			if (!currentFile.isDirectory()) 
			{
				extractFile(inputStream, filePath);
				System.out.println("Extracted file to: " + filePath);
			} 
			else
			{
				File file = new File(filePath);
				file.mkdir();
			}
			inputStream.closeEntry();
			currentFile = inputStream.getNextEntry();
		}
		inputStream.close();
	}

	private void extractFile(ZipInputStream zis, String input)throws IOException 
	{
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(input));
		byte[] bytesIn = new byte[BUFFER_SIZE];
		int read = 0;
		while ((read = zis.read(bytesIn)) != -1) 
			bos.write(bytesIn, 0, read);
		bos.close();
	}
}