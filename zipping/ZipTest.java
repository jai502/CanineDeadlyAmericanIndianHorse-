package zipping;

public class ZipTest{
    public static void main(String[] args) {
        String zipFile= "PWS/presentation.zip";
        String destination= "Unzipped Files/";
        Zipper unzipper = new Zipper();
        try {
            unzipper.unzip(zipFile, destination);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}