package zipping;

import java.io.IOException;

public class NewZipTest {
	// main method
	public static void main(String[] args) {
		// Directory deletion test
		String path = "ziptestdir/cake";
		String deleteFailureReason = NewZip.deleteDirFromPath(path);
		System.out.println(deleteFailureReason);
		

		// ZIP archive extraction test
		String zipPath = "ziptestdir/thiszip.zip";
		String zipDestPath = "ziptestdir/thiszip";
		try {
			NewZip.extract(zipPath, zipDestPath);
		} catch (IOException e) {
			System.out.printf("Exception on zip extractionL %s", zipPath);
			e.printStackTrace();
		}
	}
}
