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
* Description: This class tests functions to compress, decompress, delete  and copy files.
*/


package zipping;

public class ZipTest
{
    public static void main(String[] args) 
    {
        //define variables
    	String zipFile= "zipping/presentation.pws";
        String destination= "temp/";
        Zipper unzipper = new Zipper();
        unzipper.deleteFolder(destination);
        
        //run functions
        try 
        {
            unzipper.unzip(zipFile, destination);
            unzipper.makeFolder("zipping/foldertest");
            unzipper.zip("temp/", "zipping/test.pws");
            unzipper.copyFile("zipping/test.pws", "zipping/test2.pws");
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }

    }
}