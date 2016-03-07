/*
 * (C) Stammtisch
 * First version created by: Callum Silver
 * Date of first version: 26th February 2016
 * 
 * Last version by: Callum Silver
 * Date of last update: 28th February 2016
 * Version number: 0.1
 * 
 * Commit date: 28th February 2016
 * Description: This class prints a parsed xml file
 */
package Parsers;

import Objects.*;

public class XMLPrint {

	public Presentation presentation;
	
	public static void main (String args[]){
		XMLPrint xPrint = new XMLPrint();
		xPrint.print();
	}

	public XMLPrint() {
		super();
	}
	
	public void print()
	{
		XMLParser parser = new XMLParser();
		parser.parseXML("PWS/pwsTest.xml");
		presentation = parser.getPresentation();
			System.out.println("------------------------");
			System.out.println("Presentation Document Info:");
            System.out.println("Presentation Title : " + presentation.getDocInfo().getTitle());
            System.out.println("Presentation Author : " + presentation.getDocInfo().getAuthor());
            System.out.println("Presentation Version : " + presentation.getDocInfo().getVersion());
            System.out.println("Presentation Comment : " + presentation.getDocInfo().getComment());
            System.out.println("------------------------");
            System.out.println("Presentation Defaults:");
            System.out.println("Default Background Colour : " + presentation.getDefaults().getBackground());
            System.out.println("Default Font: " + presentation.getDefaults().getFont());
            System.out.println("Default Font Size: " + presentation.getDefaults().getFontSize());
            System.out.println("Default Font Colour: " + presentation.getDefaults().getFontColour());
            System.out.println("Default Line Colour: " + presentation.getDefaults().getLineColour());
            System.out.println("Default Fill Colour: " + presentation.getDefaults().getFillColour());
            System.out.println("------------------------");
            System.out.println("Presentation Slides:");
            System.out.println("Number of Slides: " + presentation.getSlides().size());
	            for (int i = 0; i < (presentation.getSlides().size()); i++)
	            {
	            	System.out.println("\n\nPresentation Slide: " + (i+1));
	            	System.out.println("Contains:");
	            	for (int x = 0; x < presentation.getSlides().get(i).getTextList().size(); x++)
	            	{	
	            		System.out.println("--------Text-------");
	            		System.out.println("Texts in slide: " + presentation.getSlides().get(i).getTextList().size());
	            		System.out.println("Text " + (x+1) + " Duration: " + presentation.getSlides().get(i).getTextList().get(x).getDuration());
	            		System.out.println("Text " + (x+1) + " Start Time: " + presentation.getSlides().get(i).getTextList().get(x).getStartTime());
	            		System.out.println("Text " + (x+1) + " Font Size: " + presentation.getSlides().get(i).getTextList().get(x).getFontSize());
	            		System.out.println("Text " + (x+1) + " X Start: " + presentation.getSlides().get(i).getTextList().get(x).getxStart());
	            		System.out.println("Text " + (x+1) + " Y Start: " + presentation.getSlides().get(i).getTextList().get(x).getyStart());
	            		System.out.println("Text " + (x+1) + " Font: " + presentation.getSlides().get(i).getTextList().get(x).getFont());
	            		System.out.println("Text " + (x+1) + " Font Colour: " + presentation.getSlides().get(i).getTextList().get(x).getFontColour());
	            		System.out.println("Text " + (x+1) + " Reads: " + presentation.getSlides().get(i).getTextList().get(x).getText());
	            		System.out.println("Text " + (x+1) + " Source Reads: " + presentation.getSlides().get(i).getTextList().get(x).getSourceText());
	            		System.out.println("Text " + (x+1) + " Width: " + presentation.getSlides().get(i).getTextList().get(x).getWidth());
	            		System.out.println("Text " + (x+1) + " Height: " + presentation.getSlides().get(i).getTextList().get(x).getHeight());
	            		System.out.println("--------Text-------");
	            	}
	            	for (int x = 0; x < presentation.getSlides().get(i).getShapeList().size(); x++)
	            	{	
	            		System.out.println("--------Shape------");
	            		System.out.println("Shapes in slide: " + presentation.getSlides().get(i).getShapeList().size());
	            		System.out.println("Shape " + (x+1) + " Duration: " + presentation.getSlides().get(i).getShapeList().get(x).getDuration());
	            		System.out.println("Shape " + (x+1) + " Start Time: " + presentation.getSlides().get(i).getShapeList().get(x).getStartTime());
	            		System.out.println("Shape " + (x+1) + " Type: " + presentation.getSlides().get(i).getShapeList().get(x).getType());
	            		System.out.println("Shape " + (x+1) + " X Start: " + presentation.getSlides().get(i).getShapeList().get(x).getxStart());
	            		System.out.println("Shape " + (x+1) + " Y Start: " + presentation.getSlides().get(i).getShapeList().get(x).getyStart());
	            		System.out.println("Shape " + (x+1) + " Width: " + presentation.getSlides().get(i).getShapeList().get(x).getWidth());
	            		System.out.println("Shape " + (x+1) + " Height: " + presentation.getSlides().get(i).getShapeList().get(x).getHeight());
	            		System.out.println("Shape " + (x+1) + " Line Colour: " + presentation.getSlides().get(i).getShapeList().get(x).getLineColour());
	            		System.out.println("Shape " + (x+1) + " Fill Colour: " + presentation.getSlides().get(i).getShapeList().get(x).getLineColour());
	            		System.out.println("Shape " + (x+1) + " Shading: " + presentation.getSlides().get(i).getShapeList().get(x).getShading());
	            		System.out.println("--------Shape------");
	            	}
	            	for (int x = 0; x < presentation.getSlides().get(i).getPolygonList().size(); x++)
	            	{	
	            		System.out.println("------Polygon------");
	            		System.out.println("Polygons in slide: " + presentation.getSlides().get(i).getShapeList().size());
	            		System.out.println("Polygon " + (x+1) + " Duration: " + presentation.getSlides().get(i).getPolygonList().get(x).getDuration());
	            		System.out.println("Polygon " + (x+1) + " Start Time: " + presentation.getSlides().get(i).getPolygonList().get(x).getStartTime());
	            		System.out.println("Polygon " + (x+1) + " Source File: " + presentation.getSlides().get(i).getPolygonList().get(x).getSourceFile());
	            		System.out.println("Polygon " + (x+1) + " Line Colour: " + presentation.getSlides().get(i).getPolygonList().get(x).getLineColour());
	            		System.out.println("Polygon " + (x+1) + " Fill Colour: " + presentation.getSlides().get(i).getPolygonList().get(x).getLineColour());
	            		System.out.println("Polygon " + (x+1) + " Shading: " + presentation.getSlides().get(i).getPolygonList().get(x).getShading());
	            		System.out.println("------Polygon------");
	            	}
	            	for (int x = 0; x < presentation.getSlides().get(i).getImageList().size(); x++)
	            	{	
	            		System.out.println("-------Image-------");
	            		System.out.println("Images in slide: " + presentation.getSlides().get(i).getImageList().size());
	            		System.out.println("Image " + (x+1) + " Duration: " + presentation.getSlides().get(i).getImageList().get(x).getDuration());
	            		System.out.println("Image " + (x+1) + " Start Time: " + presentation.getSlides().get(i).getImageList().get(x).getStartTime());
	            		System.out.println("Image " + (x+1) + " Source File: " + presentation.getSlides().get(i).getImageList().get(x).getSourceFile());
	            		System.out.println("Image " + (x+1) + " X Start: " + presentation.getSlides().get(i).getImageList().get(x).getxStart());
	            		System.out.println("Image " + (x+1) + " Y Start: " + presentation.getSlides().get(i).getImageList().get(x).getyStart());
	            		System.out.println("Image " + (x+1) + " Width: " + presentation.getSlides().get(i).getImageList().get(x).getWidth());
	            		System.out.println("Image " + (x+1) + " Height: " + presentation.getSlides().get(i).getImageList().get(x).getHeight());
	            		System.out.println("-------Image-------");
	            	}
	            	for (int x = 0; x < presentation.getSlides().get(i).getAudioList().size(); x++)
	            	{	
	            		System.out.println("-------Audio-------");
	            		System.out.println("Audios in slide: " + presentation.getSlides().get(i).getAudioList().size());
	            		System.out.println("Audio " + (x+1) + " Duration: " + presentation.getSlides().get(i).getAudioList().get(x).getDuration());
	            		System.out.println("Audio " + (x+1) + " Start Time: " + presentation.getSlides().get(i).getAudioList().get(x).getStartTime());
	            		System.out.println("Audio " + (x+1) + " Source File: " + presentation.getSlides().get(i).getAudioList().get(x).getSourceFile());
	            		System.out.println("Audio " + (x+1) + " Loop: " + presentation.getSlides().get(i).getAudioList().get(x).isLoop());
	            		System.out.println("-------Audio-------");
	            	}
	            	for (int x = 0; x < presentation.getSlides().get(i).getVideoList().size(); x++)
	            	{	
	            		System.out.println("-------Video-------");
	            		System.out.println("Videos in slide: " + presentation.getSlides().get(i).getVideoList().size());
	            		System.out.println("Video " + (x+1) + " Duration: " + presentation.getSlides().get(i).getVideoList().get(x).getDuration());
	            		System.out.println("Video " + (x+1) + " Start Time: " + presentation.getSlides().get(i).getVideoList().get(x).getStartTime());
	            		System.out.println("Video " + (x+1) + " X Start: " + presentation.getSlides().get(i).getVideoList().get(x).getxStart());
	            		System.out.println("Video " + (x+1) + " Y Start: " + presentation.getSlides().get(i).getVideoList().get(x).getyStart());
	            		System.out.println("Video " + (x+1) + " Source File: " + presentation.getSlides().get(i).getVideoList().get(x).getSourceFile());
	            		System.out.println("Video " + (x+1) + " Loop: " + presentation.getSlides().get(i).getVideoList().get(x).isLoop());
	            		System.out.println("-------Video-------");
	            	}
	            	for (int x = 0; x < presentation.getSlides().get(i).getInteractableList().size(); x++)
	            	{
	            		System.out.println("-------Interactable-------");
	            		System.out.println("Interactables in slide: " + presentation.getSlides().get(i).getInteractableList().size());
	            		System.out.println("Interactable " + (x+1) + " slide click ID: " + presentation.getSlides().get(i).getInteractableList().get(x).getTargetSlide());           		
	            		System.out.println("-------Interactable-------");
	            	}
	            }
            }
	}
	



