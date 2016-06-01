package zipping;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;


public class Zipper 
{
	static int BUFFER = 2048;
	
	public static void unzip(String sourceFile, String destination)
	{
		
		makeFolder(destination);
		try 
		{
			BufferedOutputStream dest = null;
			FileInputStream fis = new FileInputStream(sourceFile);
			ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
			ZipEntry entry;
			while((entry = zis.getNextEntry()) != null) 
			{
				String filePath = destination + File.separator + entry.getName();
				makeFolder(destination+File.separator+entry.getName().substring(0,entry.getName().lastIndexOf(File.separator)+1));
				System.out.println("woolloongabba:" + destination+File.separator+entry.getName().substring(0,entry.getName().lastIndexOf(File.separator)+1));
				
				System.out.println("Extracting: " +entry);
				int count;
				byte data[] = new byte[BUFFER];
				
				
				// write the files to the disk
				if(!new File(filePath).isDirectory())
				{
					System.out.println("filepath = "+ filePath);
					FileOutputStream fos = new FileOutputStream(filePath);
					dest = new BufferedOutputStream(fos, BUFFER);
					while ((count = zis.read(data, 0, BUFFER)) > -1) 
					{
						dest.write(data, 0, count);
					}
					dest.flush();
					dest.close();	
				}
				else
				{
					File file = new File(filePath);
					file.mkdirs();
				}
				
			}
			zis.close();
			System.out.println("Extraction finished");
		}
		
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}

	}

	public static void zip(String toZip, String destination)
	{
		FileOutputStream dest;
		try {
			dest = new FileOutputStream(destination);
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
			File fileToZip = new File(toZip);
			addToZip(out,fileToZip,null);
			out.flush();
			out.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
	
	private static void addToZip(ZipOutputStream zos, File toZip, String folderName)
	{
		try {
			if(toZip == null || !toZip.exists())
			{
				return;
			}
			String entry  = toZip.getName();

			if(folderName!=null && !folderName.isEmpty())
			{
				entry = folderName + File.separator + toZip.getName();
			}

			if(toZip.isDirectory())
			{
				File[] listFiles = toZip.listFiles();
				for (int i = 0; i < listFiles.length; i++) {
					File file = listFiles[i];
					addToZip(zos, file, entry);
					System.out.println(entry);
				}
			}
			else
			{
				System.out.println("Adding to zip:" + entry);
				byte[] buffer = new byte[BUFFER];

				FileInputStream fis = new FileInputStream(toZip);
				zos.putNextEntry(new ZipEntry(entry));
				int length;
				
				while((length = fis.read(buffer))> -1)
				{
					zos.write(buffer,0,length);
				}
				zos.closeEntry();
				fis.close();		
			}
		}
		
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}

	public static void copyFile( String source, String destination) throws IOException 
	{
		File sourceFile = new File(source);
		File destFile = new File(destination);
		Files.copy( sourceFile.toPath(), destFile.toPath() );
	}
	
	//a method to delete a folder and all its entries
		public static void deleteFolder(String folderToDelete) throws IOException
		{
			//read in the file to delete 
			File toDelete = new File(folderToDelete);
			
			//if the file being deleted exists, delete it
			if(toDelete.exists())
			{
				//create an array of files to contain all of the files within the directory
				File[] files = toDelete.listFiles();
				//if the array is not empty
				if(files!=null)
				{
					//loop through every entry in the array and delete it in turn
					for(int i=0; i<files.length; i++)
					{
		               //if the folder is a directory, delete sub entries, else delete individual files
						if(files[i].isDirectory()) 
		                {
		                    //use recursion to delete items in the sub folders
		                	deleteFolder(files[i].getPath());
		                    //delete the parent folder
		                    files[i].delete();
		                    System.out.println("Deleting: "+files[i].getPath());
		                }
		                else 
		                {
		                    //delete the individual file
		                	files[i].delete();
		                    System.out.println("Deleting: "+files[i].getPath());
		                }
		            }
				}
				//delete the parent folder
				toDelete.delete();
			}
		}
	
//	public static void deleteFolder(String outputLocation)
//    {
//        //check if the location to delete exists and if it does, delete it
//        File toDelete = new File(outputLocation);
//        if(!toDelete.exists())
//        {
//            System.out.println("Nothing to delete");
//        }
//        else
//        {
//            try{
//                delete(toDelete);
//            }
//            catch(IOException e)
//            {
//                e.printStackTrace();
//                System.out.println("An error occured while deleting");
//            }
//        }
//    }
//	
//	private static void delete(File toDelete) throws IOException
//    {
//        //check if the file to delete is a directory or a file
//        if(toDelete.isDirectory())
//        {
//            //if the directory is empty, delete it 
//            if(toDelete.list().length==0)
//            {
//                toDelete.delete();
//                System.out.println("Deleted directory:         "+toDelete.getAbsolutePath());
//            }
//            else
//            {
//                //if the directory is not empty, recursively loop through and delete everything in the folder until it is empty
//                String filesToDelete[] = toDelete.list();
//                for (String temp: filesToDelete)
//                {
//                    File deleteThis = new File(toDelete, temp);
//                    delete(deleteThis);
//                }
//                if(toDelete.list().length==0)
//                {
//                    toDelete.delete();
//                    System.out.println("Deleted directory:         "+toDelete.getAbsolutePath());
//                }
//            }    
//        }
//        //if only a single file exists, delete it
//        else
//        {
//            toDelete.delete();
//            System.out.println("Deleted file:             "+toDelete.getAbsolutePath());
//        }
//    }
	
	public static String scan(String filepath)
	{
		String extension;
		extension = filepath.substring(filepath.lastIndexOf(".")+1,filepath.length());
		return extension;
		
	}

	public static void makeFolder(String toMake)
	{
		File folderMaker = new File(toMake);
		if(!folderMaker.exists())
			folderMaker.mkdirs();
	}

}
