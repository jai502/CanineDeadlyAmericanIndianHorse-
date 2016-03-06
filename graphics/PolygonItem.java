package graphics;

import javafx.scene.paint.Color;

/** 
 * <tt>PolygonItem</tt> gets and set shape information
 * <p>
 * <STRONG> Software Developers: </STRONG> <br>
 * Jake Apsey
 * <p>
 * <STRONG> Tested by: </STRONG> <br>
 * William Ravenscroft, Mark Woodward, David Snowden
 * <p>
 * <STRONG> Quality Assured by: </STRONG> <br>
 * Dyfan Davies - 04/03/16
 * <p>
 * @author Feature Teacher Ltd.
 * @version 1.0
 * @see Color
 * @see Gradient
 * @see Item
 */

public class PolygonItem extends Item {
	private String sourceFile;
	private Color lineColour;
	private Color fillColour;
	private Gradient shading;
	
	/**
	 * Method gets source file including path for CSV file with polygon coordinates
	 * @return sourceFile String source path of .csv file
	 */
	public String getSourceFile() {
		return sourceFile;
	}
	
	/**
	 * Method sets source file including path for CSV file with polygon coordinates
	 * @param sourceFile String source path of .csv file
	 */
	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}

	/**
	 * Returns outline colour of shape
	 * @return lineColour Color value of shape
	 */
	public Color getLineColour(){
		return lineColour;
	}

	/**
	 * Sets outline colour of shape
	 * @param lineColour Color value of shape
	 */
	public void setLineColour(String lineColour){
		this.lineColour = Color.web(lineColour);
	}

	/**
	 * Returns fill colour of shape
	 * @return fillColour Color value of shape
	 */
	public Color getFillColour(){
		return fillColour;
	}

	/**
	 * Sets fill colour of shape
	 * @param fillColour Color value of shape
	 */
	public void setFillColour(String fillColour){
		this.fillColour = Color.web(fillColour);
	}
	
	/**
	 * Returns colour gradient of shape
	 * @return shading Gradient of shape item
	 */
	public Gradient getShading() {
		return shading;
	}

	/**
	 * Sets colour gradient of shape
	 * @param shading Gradient of shape item
	 */
	public void setShading(Gradient shading) {
		this.shading = shading;
	}
}
