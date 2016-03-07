package graphics;

import java.io.FileNotFoundException;

import java.io.FileReader;
import java.io.IOException;

import com.opencsv.CSVReader;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/** 
 * CSVHandler class reads a CSV file consisting of two columns
 * <p>
 * e.g. <p> 
 * (1, 2)
 * <p>
 * (3, 4)
 * <p>
 * Parses column data into separate x point & y point arrays.
 * <p>
 * Returns the number of lines as a number of points in an array.
 * <p>
 * This class requires the opencsv library that can be downloaded here: 
 * https://sourceforge.net/projects/opencsv/
 *
 * <p>
 * <STRONG> Software Developers: </STRONG> <br>
 * Alex Lambert, William Ravenscroft
 * <p>
 * <STRONG> Tested by: </STRONG> <br>
 * Mark Woodward, David Snowden
 * <p>
 * <STRONG> Quality Assured by: </STRONG> <br>
 * Dyfan Davies - 04/03/16
 * <p>
 * 
 * @author Feature Teacher Ltd.
 * @version 1.0
 * @see Alert
 * @see CSVreader
 */
public class CSVHandler {
	
	// Declare variables
	private String[] nextLine;
	private double[] xPoints;
	private double[] yPoints;
	private int nPoints;
	private int point;
	private boolean error;
	
	/** 
	 * CSVHandler creates a new <tt> CSVReader </tt> that takes an input file (String)
	 * that points to a file location 
	 * The file data is parsed to find the number of lines and separate out column data into double[]
	 *  
	 * @param inputFile - String e.g. "/Users/path to..../sourceFile.csv"
	 * @see FileReader
	 * @exception FileNotFoundException CSV file has not been found, change path of inputFile
	 * @exception IOException CSV file was read incorrectly
	 */	
	
	public CSVHandler(String inputFile) {
		
		// Reset the error variable
		error = false;
	
		// Read in CSV file to CSV Reader to find number of lines
		CSVReader count;
		try {
			// Instantiate new CSVReader and read the file stored at 'inputFile'
			count = new CSVReader(new FileReader(inputFile));
			
			// Reset variables nPoints and point between calls of CSVReader
			nPoints = 0;
			point = 0;
			
			// Count the number of lines (points) in source file
			while ((nextLine = count.readNext()) != null) {
				nPoints++;
			}	
			
			// Close the CSVReader
			count.close();
			
		} catch (FileNotFoundException e) {					
			// Alert if file can't be found
			Alert fileLoadAlert = new Alert(AlertType.ERROR);
			fileLoadAlert.setTitle("Error");
			fileLoadAlert.setContentText("File Not Found");
			fileLoadAlert.showAndWait();
			e.printStackTrace();
			
			// Set error to be true
			error = true;
			
		} catch (IOException e) {	
			// Alert if file can't be read
			Alert ioCSVAlert = new Alert(AlertType.ERROR);
			ioCSVAlert.setTitle("Error");
			ioCSVAlert.setContentText("Failure Reading CSV File");
			ioCSVAlert.showAndWait();
			e.printStackTrace();
		}		
		
		// Create double arrays to store x & y variables
		xPoints = new double[nPoints];
		yPoints = new double[nPoints];
		
		// Read in CSV file to CSV Reader to separate x and y coordinate data
		CSVReader reader;
		try {
			// Instantiate new CSVReader to read 'inputFile'
			reader = new CSVReader(new FileReader(inputFile));
			
			// Parse line data into x and y arrays
			while ((nextLine = reader.readNext()) != null) {			
				xPoints[point] = Double.parseDouble(nextLine[0]);
				yPoints[point] = Double.parseDouble(nextLine[1]);
				point++;
			}			
			
			// Close the CSVReader
			reader.close();
			
		} catch (FileNotFoundException e) {		
			// Alert if file can't be found
			Alert fileLoadAlert = new Alert(AlertType.ERROR);
			fileLoadAlert.setTitle("Error");
			fileLoadAlert.setContentText("File Not Found");
			fileLoadAlert.showAndWait();
			e.printStackTrace();	
			
			// Set error to be true
			error = true;
			
		} catch (NumberFormatException e) {			
			// Alert if CSV format is incorrect
			Alert numberFormatAlert = new Alert(AlertType.ERROR);
			numberFormatAlert.setTitle("Error");
			numberFormatAlert.setContentText("Number Format Error");
			numberFormatAlert.showAndWait();
			e.printStackTrace();
			
		} catch (IOException e) {
			// Alert if file cannot be read 
			Alert ioCSVAlert = new Alert(AlertType.ERROR);
			ioCSVAlert.setTitle("Error");
			ioCSVAlert.setContentText("Failure Reading CSV File");
			ioCSVAlert.showAndWait();
			e.printStackTrace();
		}		
	}
	
	/** 
	 * Method returns x column from CSV file 
	 * 
	 * @return xPoints[]
	 */
	public double[] getXPoints() {
		return xPoints;
	}
	
	/** 
	 * Method returns y column from CSV file 
	 * 
	 * @return yPoints[]
	 */
	public double[] getYPoints() {
		return yPoints;
	}
	
	/** 
	 * Method returns number of points in source file
	 *  
	 * @return nPoints
	 */
	public int getNPoints() {
		return nPoints;
	}
	
	/** 
	 * Method returns error boolean
	 *  
	 * @return error
	 */
	public boolean getError() {
		return error;
	}
}
