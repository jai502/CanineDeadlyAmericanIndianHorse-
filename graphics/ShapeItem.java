package graphics;

import javafx.scene.paint.Color;

/** 
 * <tt>ShapeItem</tt> gets and set shape information
 * <p>
 * @see Color
 * @see Gradient
 * @see Item
 * <p>
 * <STRONG> Software Developers: </STRONG> <br>
 * Jake Apsey, William Ravenscroft
 * <p>
 * <STRONG> Tested by: </STRONG> <br>
 * Mark Woodward, David Snowden
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

public class ShapeItem extends Item {
	
	// Class variables
	private String shapeType;
	private float width;
	private float height;
	private Color lineColour;
	private Color fillColour;
	private Gradient shading;
	private float positionX;
	private float positionY;
	
	/**
	 * Method sets x coordinate position of item
	 * @param positionX float value
	 */
	public void setX(float x){
		positionX = x;
	}
	
	/**
	 * Method returns x coordinate position of item
	 * @return positionX float value
	 */
	public float getX(){
		return positionX;
	}
	
	/**
	 * Method sets y coordinate position of item
	 * @param positionY float value
	 */
	public void setY(float y){
		positionY = y;
	}
	
	/**
	 * Method returns y coordinate position of item
	 * @return positionY float value
	 */
	public float getY() {
		return positionY;
	}
	
	/**
	 * Returns shape type
	 * @return shapeType Type of shape string: "circle", "rectangle" or "rounded rectangle"
	 */
	public String getShapeType() {
		return shapeType;
	}
	
	/**
	 * Sets shape type
	 * @param shapeType Type of shape string: "circle", "rectangle" or "rounded rectangle"
	 */
	public void setShapeType(String shapeType) {
		this.shapeType = shapeType;
	}
	
	/**
	 * Returns width of shape
	 * @return width float value of width
	 */
	public float getWidth() {
		return width;
	}
	
	/**
	 * Sets width of shape
	 * @param width float value of width
	 */
	public void setWidth(float width) {
		this.width = width;
	}

	/**
	 * Returns height of shape
	 * @return height float value of height
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * Sets height of shape
	 * @param height float value of height
	 */
	public void setHeight(float height) {
		this.height = height;
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
