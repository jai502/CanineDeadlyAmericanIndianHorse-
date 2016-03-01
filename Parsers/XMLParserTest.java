/*
 * (C) Stammtisch
 * First version created by: Callum Silver
 * Date of first version: 29th February 2016
 * 
 * Last version by: Callum Silver
 * Date of last update: 29th February 2016
 * Version number: 0.1
 * 
 * Commit date: 29th February 2016
 * Description: This class is a test for a parsed XML file
 */
package Parsers;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.*;

import Objects.*;

public class XMLParserTest {
	
	private XMLParser parser;
	private Presentation presentation;

	@Before
	public void setUp() throws Exception 
	{
		XMLParser parser = new XMLParser();
		parser.parseXML("PWS/PWSTest.xml");
		presentation = parser.getPresentation();
	}

	@Test
	public void createPresentation()
	{
		assertTrue(presentation instanceof Presentation);
	}
	
	@Test
	public void presContainsDocInfo() 
	{
		assertTrue(presentation.getDocInfo() instanceof DocumentInfo);
	}
	
	@Test
	public void presContainsDefaults() 
	{
		assertTrue(presentation.getDefaults() instanceof Defaults);
	}
	
	@Test
	public void presContainsSlides() 
	{
		assertTrue(presentation.getSlides().get(0) instanceof Slide);
	}
	
	@Test
	public void DocInfoReturnsCorrectFields()
	{
		assertNotNull(presentation.getDocInfo().getTitle());
		assertNotNull(presentation.getDocInfo().getAuthor());
		assertNotNull(presentation.getDocInfo().getVersion());
		assertNotNull(presentation.getDocInfo().getComment());
	}
	
	@Test
	public void DefaultsReturnCorrectFields() 
	{
		assertNotNull(presentation.getDefaults().getBackground());
		assertNotNull(presentation.getDefaults().getLineColour());
		assertNotNull(presentation.getDefaults().getFillColour());
		assertNotNull(presentation.getDefaults().getFont());
		assertNotNull(presentation.getDefaults().getFontSize());
		assertNotNull(presentation.getDefaults().getFontColour());
	}
	
	@Test
	public void SlidesReturnCorrectFields() 
	{
		assertNotNull(presentation.getSlides().get(0));
		assertNotNull(presentation.getSlides().get(0).getBackgroundColour());
		assertNotNull(presentation.getSlides().get(0).getNextSlide());
		assertNotNull(presentation.getSlides().get(0).getSlideId());
		assertNotNull(presentation.getSlides().get(0).getDuration());
		assertNotNull(presentation.getSlides().get(0).getImageList());
		assertNotNull(presentation.getSlides().get(1).getShapeList());
		assertNotNull(presentation.getSlides().get(1).getPolygonList());
		assertNotNull(presentation.getSlides().get(2).getAudioList());
		assertNotNull(presentation.getSlides().get(3).getVideoList());
		assertNotNull(presentation.getSlides().get(4).getTextList());
		assertNotNull(presentation.getSlides().get(5).getInteractableList());
	}
	
	@Test
	public void TextReadsIntoList() {
		assertNotNull(presentation.getSlides().get(4).getTextList().get(0).getDuration());
		assertNotNull(presentation.getSlides().get(4).getTextList().get(0).getFont());
		assertNotNull(presentation.getSlides().get(4).getTextList().get(0).getFontColour());
		assertNotNull(presentation.getSlides().get(4).getTextList().get(0).getFontSize());
		assertNotNull(presentation.getSlides().get(4).getTextList().get(0).getStartTime());
		assertNotNull(presentation.getSlides().get(4).getTextList().get(0).getxStart());
		assertNotNull(presentation.getSlides().get(4).getTextList().get(0).getyStart());
		assertNotNull(presentation.getSlides().get(4).getTextList().get(0).getText());
	}
	
	@Test
	public void ShapeReadsIntoList() {
		assertNotNull(presentation.getSlides().get(1).getShapeList().get(1).getDuration());
		assertNotNull(presentation.getSlides().get(1).getShapeList().get(1).getStartTime());
		assertNotNull(presentation.getSlides().get(1).getShapeList().get(1).getxStart());
		assertNotNull(presentation.getSlides().get(1).getShapeList().get(1).getyStart());
		assertNotNull(presentation.getSlides().get(1).getShapeList().get(1).getType());
		assertNotNull(presentation.getSlides().get(1).getShapeList().get(1).getWidth());
		assertNotNull(presentation.getSlides().get(1).getShapeList().get(1).getHeight());
		assertNotNull(presentation.getSlides().get(1).getShapeList().get(1).getLineColour());
		assertNotNull(presentation.getSlides().get(1).getShapeList().get(1).getFillColour());
		assertNotNull(presentation.getSlides().get(1).getShapeList().get(0).getShading());
	}
	
	@Test
	public void PolygonReadsIntoList() {
		assertNotNull(presentation.getSlides().get(1).getPolygonList().get(0).getDuration());
		assertNotNull(presentation.getSlides().get(1).getPolygonList().get(0).getStartTime());
		assertNotNull(presentation.getSlides().get(1).getPolygonList().get(0).getLineColour());
		assertNotNull(presentation.getSlides().get(1).getPolygonList().get(0).getFillColour());
		assertNotNull(presentation.getSlides().get(1).getPolygonList().get(0).getShading());
		assertNotNull(presentation.getSlides().get(1).getPolygonList().get(0).getSourceFile());
	}
	
	@Test
	public void ShadingReadsIntoList() {
		assertNotNull(presentation.getSlides().get(1).getPolygonList().get(0).getShading().getColourOne());
		assertNotNull(presentation.getSlides().get(1).getPolygonList().get(0).getShading().getColourTwo());
		assertNotNull(presentation.getSlides().get(1).getPolygonList().get(0).getShading().getxOne());
		assertNotNull(presentation.getSlides().get(1).getPolygonList().get(0).getShading().getxTwo());
		assertNotNull(presentation.getSlides().get(1).getPolygonList().get(0).getShading().getyOne());
		assertNotNull(presentation.getSlides().get(1).getPolygonList().get(0).getShading().getyTwo());
	}
	
	@Test
	public void ImageReadsIntoList() 
	{
		assertNotNull(presentation.getSlides().get(0).getImageList().get(0).getDuration());
		assertNotNull(presentation.getSlides().get(0).getImageList().get(0).getStartTime());
		assertNotNull(presentation.getSlides().get(0).getImageList().get(0).getxStart());
		assertNotNull(presentation.getSlides().get(0).getImageList().get(0).getyStart());
		assertNotNull(presentation.getSlides().get(0).getImageList().get(0).getSourceFile());
		assertNotNull(presentation.getSlides().get(0).getImageList().get(0).getWidth());
		assertNotNull(presentation.getSlides().get(0).getImageList().get(0).getHeight());
	}
	
	@Test
	public void AudioReadsIntoList() 
	{
		assertNotNull(presentation.getSlides().get(2).getAudioList().get(0).getDuration());
		assertNotNull(presentation.getSlides().get(2).getAudioList().get(0).getStartTime());
		assertNotNull(presentation.getSlides().get(2).getAudioList().get(0).getSourceFile());
		assertNotNull(presentation.getSlides().get(2).getAudioList().get(0).isLoop());
	}
	
	@Test
	public void VideoReadsIntoList() 
	{
		assertNotNull(presentation.getSlides().get(3).getVideoList().get(0).getDuration());
		assertNotNull(presentation.getSlides().get(3).getVideoList().get(0).getStartTime());
		assertNotNull(presentation.getSlides().get(3).getVideoList().get(0).getxStart());
		assertNotNull(presentation.getSlides().get(3).getVideoList().get(0).getyStart());
		assertNotNull(presentation.getSlides().get(3).getVideoList().get(0).getSourceFile());
		assertNotNull(presentation.getSlides().get(3).getVideoList().get(0).isLoop());
	}
	
	public void InteractableReadsIntoList() 
	{
		assertNotNull(presentation.getSlides().get(5).getInteractableList().get(0).getTargetSlide());
		assertNotNull(presentation.getSlides().get(5).getInteractableList().get(0).getTextList());
		// double check some interactable text aspects have been stored.
		assertNotNull(presentation.getSlides().get(5).getInteractableList().get(0).getTextList().get(0).getDuration());
		assertNotNull(presentation.getSlides().get(5).getInteractableList().get(0).getTextList().get(0).getStartTime());
	}
	// TODO Add in values for the assertEquals
	//Test some random values, as testing all aspects of the pres file would be too extensive
	@Test
	public void randomValueTests() {
		// Doc info
		assertEquals("Callum Silver", presentation.getDocInfo().getAuthor());
		assertEquals("1.0", presentation.getDocInfo().getVersion());
		// Defaults
		assertEquals(12, presentation.getDefaults().getFontSize());
		assertEquals("serif", presentation.getDefaults().getFont());
		// Slide
		assertEquals(2, presentation.getSlides().get(0).getSlideId());
		assertEquals(3, presentation.getSlides().get(0).getNextSlide());
		// Text
		assertEquals("This is <b>bold</b> a <i>italics</i> test for testing.", presentation.getSlides().get(4).getTextList().get(0).getText());
		assertEquals(0.5, presentation.getSlides().get(4).getTextList().get(0).getxStart(), 0); // extra term is for asserting equals double and is the discrepancy
		// Images
		assertEquals("InsertPicture.jpg", presentation.getSlides().get(0).getImageList().get(0).getSourceFile());
		assertEquals(0.2, presentation.getSlides().get(0).getImageList().get(0).getHeight(), 0);
		// Shape
		// TODO find way of testing colour
		//assertEquals(,presentation.getSlides().get(1).getShapeList().get(1).getLineColour());
		assertEquals("rectangle", presentation.getSlides().get(1).getShapeList().get(1).getType());
		// Polygon
		//assertEquals(2, presentation.getSlides().get(1).getPolygonList().get(0).getFillColour());
		assertEquals(1 , presentation.getSlides().get(1).getPolygonList().get(0).getStartTime());
		// Audio
		assertEquals(true, presentation.getSlides().get(2).getAudioList().get(0).isLoop());
		assertEquals("Audio.aac", presentation.getSlides().get(2).getAudioList().get(0).getSourceFile());
		// Video
		assertEquals(0.1, presentation.getSlides().get(3).getVideoList().get(0).getyStart(), 0);
		assertEquals("Video.mp4", presentation.getSlides().get(3).getVideoList().get(0).getSourceFile());
		// Interactable
		assertEquals(3, presentation.getSlides().get(5).getInteractableList().get(0).getTargetSlide());
		assertEquals(0, presentation.getSlides().get(5).getInteractableList().get(0).getTextList().get(0).getDuration());
		// Shading
		//assertEquals(2, presentation.getSlides().get(1).getPolygonList().get(0).getShading().getColourOne());
		assertEquals(0.1 ,presentation.getSlides().get(1).getPolygonList().get(0).getShading().getxTwo(), 0);
	}
	
	@Test
	public void allSlidesRead() 
	{	
		assertEquals(presentation.getSlides().size(), 6);
	}

}
