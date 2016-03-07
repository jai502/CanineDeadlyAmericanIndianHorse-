package graphics;

import javafx.scene.paint.Color;

/**
 * Simple class to create a Gradient object for shape or polygon element on canvas  
 * <p>
 * <STRONG> Software Developers: </STRONG> <br>
 * Jake Apsey
 * <p>
 * <STRONG> Tested by: </STRONG> <br>
 * William Ravenscroft, David Snowden, Mark Woodward
 * <p>
 * <STRONG> Quality Assured by: </STRONG> <br>
 * Dyfan Davies - 04/03/16
 * 
 * @author Feature Teacher Ltd.
 * @version 1.0
 * @see Color
 */

public final class Gradient {
	private float x1;
	private float y1;
	private Color colour1;
	private float x2;
	private float y2;
	private Color colour2;
	
	/**
	 * Method returns first x value of Gradient
	 * @return x1
	 */
	public float getX1() {
		return x1;
	}
	
	/**
	 * Method sets first x value of Gradient
	 * @param x1
	 */
	public void setX1(float x1) {
		this.x1 = x1;
	}

	/**
	 * Method returns y value of Gradient
	 * @return y1
	 */
	public float getY1() {
		return y1;
	}

	/**
	 * Method sets x value of Gradient
	 * @param y1
	 */ 
	public void setY1(float y1) {
		this.y1 = y1;
	}

	/**
	 * Method returns first colour of Gradient
	 * @return colour1
	 */
	public Color getColour1() {
		return colour1;
	}

	/**
	 * Method sets first colour of Gradient
	 * @param colour1
	 */
	public void setColour1(String colour1) {
		this.colour1 = Color.web(colour1);
	}

	/**
	 * Method returns second x value of Gradient
	 * @return x2
	 */
	public float getX2() {
		return x2;
	}

	/**
	 * Method sets second x value of Gradient
	 * @param x2
	 */
	public void setX2(float x2) {
		this.x2 = x2;
	}

	/**
	 * Method returns second y value of Gradient
	 * @return y2
	 */
	public float getY2() {
		return y2;
	}

	/**
	 * Method sets second y value of Gradient
	 * @param y2
	 */	
	public void setY2(float y2) {
		this.y2 = y2;
	}

	/**
	 * Method returns second colour of Gradient
	 * @param colour2
	 */
	public Color getColour2() {
		return colour2;
	}
	
	/**
	 * Method sets second colour of Gradient
	 * @param colour2
	 */
	public void setColour2(String colour2) {
		this.colour2 = Color.web(colour2);
	}
}
