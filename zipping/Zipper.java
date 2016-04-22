package zipping;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Zipper{

	//create a buffer for the input stream data to be temporarily stored
	private static final int BUFFER_SIZE = 4096;

	public void unzip(String input, String outputLocation)	throws IOException 
	{
		//create the output file
		File output = new File(outputLocation);
		//if the output file/directory does not already exist, make the output
		if (!output.exists()) 
			output.mkdir();
		
		//create an input stream from to read the zip file
		ZipInputStream inputStream = new ZipInputStream(new FileInputStream(input));
		//create an object to store the zip item for extraction
		ZipEntry currentFile = inputStream.getNextEntry();
		
		//loop through until the end of the zip file is reached
		while (currentFile != null)
		{
			//configure output location
			String filePath = outputLocation + File.separator + currentFile.getName();
			//check if the current file is a file or a directory
			if (!currentFile.isDirectory()) 
			{
				extract(inputStream, filePath);
				System.out.println("Extracted file to:		 " + filePath);
			} 
			else
			{
				File file = new File(filePath);
				file.mkdir();
			}
			//once extracted, move to the next file
			inputStream.closeEntry();
			currentFile = inputStream.getNextEntry();
		}
		//stop extracting
		inputStream.close();
	}

	private void extract(ZipInputStream inputStream, String input)throws IOException
	{
		//create an outputstream and add a buffer to improve performance and reduce overhead during extraction
		BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(input));
		//create an array to store the buffered data
		byte[] bytesToRead = new byte[BUFFER_SIZE];
		
		//extract the file by reading a buffer from the input stream and writing to new files 
		int beingRead = 0;
		while((beingRead=inputStream.read(bytesToRead))>0)
		{
			outputStream.write(bytesToRead,0,beingRead);
		}
		
		outputStream.close();
	}
	
	public void deleteFolder(String outputLocation)
	{
		//check if the location to delete exists and if it does, delete it
		File toDelete = new File(outputLocation);
		if(!toDelete.exists())
		{
			System.out.println("Nothing to delete");
		}
		else
		{
			try{
				delete(toDelete);
			}
			catch(IOException e)
			{
				e.printStackTrace();
				System.out.println("An error occured while deleting");
			}
		}
	}
	
	private void delete(File toDelete) throws IOException
	{
		//check if the file to delete is a directory or a file
		if(toDelete.isDirectory())
		{
			//if the directory is empty, delete it 
			if(toDelete.list().length==0)
			{
				toDelete.delete();
				System.out.println("Deleted directory: 		"+toDelete.getAbsolutePath());
			}
			else
			{
				//if the directory is not empty, recursively loop through and delete everything in the folder until it is empty
				String filesToDelete[] = toDelete.list();
				for (String temp: filesToDelete)
				{
					File deleteThis = new File(toDelete, temp);
					delete(deleteThis);
				}
				if(toDelete.list().length==0)
				{
					toDelete.delete();
					System.out.println("Deleted directory: 		"+toDelete.getAbsolutePath());
				}
			}	
		}
		//if only a single file exists, delete it
		else
		{
			toDelete.delete();
			System.out.println("Deleted file: 			"+toDelete.getAbsolutePath());
		}
	}
}