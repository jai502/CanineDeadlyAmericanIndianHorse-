package zipping;

public class ZipTest{
    public static void main(String[] args) {
        String zipFile= "zipping/presentation.zip";
        String destination= "Unzipped Files/";
        Zipper unzipper = new Zipper();
        unzipper.deleteFolder(destination);
        try {
            unzipper.unzip(zipFile, destination);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}