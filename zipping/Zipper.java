package zipping;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Zipper {
	static final int BUFFER = 2048;

	public static void main(String args[]) 
	{
		try 
		{
			BufferedOutputStream dest = null;
			BufferedInputStream is = null;
			ZipEntry entry;
			ZipFile zipfile = new ZipFile("PWS/presentation.zip");
			Enumeration e = zipfile.entries();
			while (e.hasMoreElements()) 
			{
				entry = (ZipEntry) e.nextElement();
				System.out.println("Extracting: " + entry);
				is = new BufferedInputStream(zipfile.getInputStream(entry));
				int count;
				byte data[] = new byte[BUFFER];
				FileOutputStream fos = new FileOutputStream(entry.getName());
				dest = new BufferedOutputStream(fos, BUFFER);
				while ((count = is.read(data, 0, BUFFER)) != -1) 
				{
					dest.write(data, 0, count);
				}
				dest.flush();
				dest.close();
				is.close();
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
