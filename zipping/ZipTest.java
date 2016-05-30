/*
* (C) Stammtisch
* First version created by: Joseph Ingleby
* Date of first version: 2nd May 2016
* 
* Last version by: Joseph Ingleby
* Date of last update: 28th May 2016
* Version number: 2.1
* 
* Commit date: 28thMay 2016
* Description: This class tests functions to compress, decompress, delete  and copy files.
*/


package zipping;

public class ZipTest
{
    public static void main(String[] args) 
    {
    	//delete existing folders
        Zipper.deleteFolder("temp/");
        Zipper.deleteFolder("zipping/foldertest");
        
        //run file operations
        try 
        {
            Zipper.unzip("zipping/presentation.pws", "temp/");
            Zipper.makeFolder("zipping/temp");
            Zipper.zip("temp/", "zipping/test.pws");
            Zipper.copyFile("C:/Users/Callum/Desktop/test.pws", "zipping/temp/test3.pws" );
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }

    }
}