/*
* (C) Stammtisch
* First version created by: Joseph Ingleby
* Date of first version: 2nd May 2016
* 
* Last version by: Joseph Ingleby
* Date of last update: 27th May 2016
* Version number: 2.0
* 
* Commit date: 27thMay 2016
* Description: This class has functions to compress, decompress,delete  and copy files.
*/

package zipping;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

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
	
	//a method to compress a folder to a destination folder
	public void zip(String sourceFolder, String destinationFile) throws Exception
	{
		//two streams for reading the files and compressing
		ZipOutputStream zipStream = null;
		FileOutputStream fileStream = null;

		//assign values
		fileStream = new FileOutputStream(destinationFile);
		zipStream = new ZipOutputStream(fileStream);

		//call the zipFolder function
		zipFolder("", sourceFolder, zipStream);
		zipStream.flush();
		zipStream.close();
	}

	//a method to zip individual files
	public void zipFile(String filePath, String sourceFile, ZipOutputStream zipStream) throws Exception 
	{
		//create a file to manipulate and assign it
		File folder = new File(sourceFile);
		
		//if the file is a folder, use the zipfolder method
		if (folder.isDirectory()) 
		{
			zipFolder(filePath, sourceFile, zipStream);
		}
		
		//else create a buffer, fill it from the filestream and write it to the zip file
		else 
		{
			byte[] buffer = new byte[1024];
			int write;
			FileInputStream fileStream = new FileInputStream(sourceFile);
			zipStream.putNextEntry(new ZipEntry(filePath + File.separator + folder.getName()));
			
			while ((write = fileStream.read(buffer)) > 0)
			{
				zipStream.write(buffer, 0, write);
			}
		}
	}

	//a method to zip a folder
	private void zipFolder(String filePath, String sourceFolder,ZipOutputStream zipStream) throws Exception 
	{
		File folder = new File(sourceFolder);
		//loop through the folder and call the zip file method for all files found. This will run recursively
		for (String fileName : folder.list()) 
		{
			if (filePath.equals("")) 
			{
				zipFile(folder.getName(), sourceFolder + File.separator + fileName, zipStream);
			}
			else
			{
				zipFile(filePath + File.separator + folder.getName(), sourceFolder + File.separator
						+ fileName, zipStream);
			}
		}
	}

	//a method to copy a file using two streams.
	public static void copyFile(String source, String dest) throws IOException 
	{
		File sourceFile = new File(source);
		File destFile = new File(dest);
		
		InputStream inputStream = null;
		OutputStream outputStream = null;
		
		//fill a buffer from the input stream and write it to the output file 
		try
		{
			inputStream = new FileInputStream(sourceFile);
			outputStream = new FileOutputStream(destFile);

			byte[] buffer = new byte[1024];
			int bytesToRead;

			while ((bytesToRead = inputStream.read(buffer)) > 0) 
			{
				outputStream.write(buffer, 0, bytesToRead);
			}
		} 
		finally 
		{
			inputStream.close();
			outputStream.close();
		}
	}
	
	//a simple method to create a folder
	public void makeFolder(String folderName)
	{
		File folder = new File(folderName);
		folder.mkdir();
	}
}