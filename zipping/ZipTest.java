package zipping;

public class ZipTest{
    public static void main(String[] args) {
        String zipFile= "zipping/presentation.pws";
        String destination= "temp/";
        Zipper unzipper = new Zipper();
        unzipper.deleteFolder(destination);
        try {
            unzipper.unzip(zipFile, destination);
            unzipper.makeFolder("zipping/foldertest");
            unzipper.zip("temp/", "zipping/test.pws");
            unzipper.copyFile("zipping/test.pws", "zipping/test2.pws");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}