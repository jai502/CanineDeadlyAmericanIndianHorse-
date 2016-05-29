/*
 * (C) Stammtisch
 * First version created by: Callum Silver
 * Date of first version: 26th May 2016
 * 
 * Last version by: Callum Silver
 * Date of last update: 26th May 2016
 * Version number: 1.0
 * 
 * Commit date: 26th May 2016
 * Description: This class creates a presentation xml file
 * from user inputs
 */

package Parsers;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import Objects.Presentation;

public class XMLCreator {

	public XMLCreator() {
	}

	public void createXML(Presentation presentation, boolean containsDefaults ,boolean containsImage, 
			boolean containsShape,boolean shapeShade, boolean containsPoly,
			boolean containsText, boolean containsVideo, boolean containsAudio) 
	{
		try {

			// Create document builder
			DocumentBuilderFactory builderFac = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFac.newDocumentBuilder();

			// Presentation root

			Document presentationDoc = builder.newDocument();
			Element presElement = presentationDoc.createElement("Presentation");
			presentationDoc.appendChild(presElement);

			// DocInfo
			Element documentInfo = presentationDoc.createElement("documentInfo");
			presElement.appendChild(documentInfo);

			// DocInfo elements (Title, Author, Version, Comment)
			Element title = presentationDoc.createElement("Title");
			Element author = presentationDoc.createElement("Author");
			Element version = presentationDoc.createElement("Version");
			Element comment = presentationDoc.createElement("Comment");

			title.appendChild(presentationDoc.createTextNode(presentation.getDocInfo().getTitle()));
			author.appendChild(presentationDoc.createTextNode(presentation.getDocInfo().getAuthor()));
			version.appendChild(presentationDoc.createTextNode(presentation.getDocInfo().getVersion()));
			comment.appendChild(presentationDoc.createTextNode(presentation.getDocInfo().getComment()));

			documentInfo.appendChild(title);
			documentInfo.appendChild(author);
			documentInfo.appendChild(version);
			documentInfo.appendChild(comment);

			// Defaults
			if (containsDefaults == true)
			{

				Element defaults = presentationDoc.createElement("defaults");
				presElement.appendChild(defaults);

				// Default elements (background colour, font, font size, font
				// colour, line colour, fill colour)

				Element backgroundColour = presentationDoc.createElement("backgroundColour");
				Element font = presentationDoc.createElement("font");
				Element fontSize = presentationDoc.createElement("fontSize");
				Element fontColour = presentationDoc.createElement("fontColour");
				Element lineColour = presentationDoc.createElement("lineColour");
				Element fillColour = presentationDoc.createElement("fillColour");

				backgroundColour.appendChild(
						presentationDoc.createTextNode(presentation.getDefaults().getBackground().toString()));
				font.appendChild(
						presentationDoc.createTextNode(presentation.getDefaults().getFont().toString()));
				fontSize.appendChild(
						presentationDoc.createTextNode(String.valueOf(presentation.getDefaults().getFontSize())));
				fontColour.appendChild(
						presentationDoc.createTextNode(presentation.getDefaults().getFontColour().toString()));
				lineColour.appendChild(
						presentationDoc.createTextNode(presentation.getDefaults().getLineColour().toString()));
				fillColour.appendChild(
						presentationDoc.createTextNode(presentation.getDefaults().getFillColour().toString()));

				defaults.appendChild(backgroundColour);
				defaults.appendChild(font);
				defaults.appendChild(fontSize);
				defaults.appendChild(fontColour);
				defaults.appendChild(lineColour);
				defaults.appendChild(fillColour);
			}
			// Slides
			for (int i = 0; i < presentation.getSlides().size(); i++) 
			{
				Element slide = presentationDoc.createElement("slide");
				presElement.appendChild(slide);
				// slide attributes
				Attr slideID = presentationDoc.createAttribute("slideID");
				Attr nextSlide = presentationDoc.createAttribute("nextSlide");
				Attr duration = presentationDoc.createAttribute("duration");

				slideID.setValue(String.valueOf(presentation.getSlides().get(i).getSlideId()));
				nextSlide.setValue(String.valueOf(presentation.getSlides().get(i).getNextSlide()));
				duration.setValue(String.valueOf(presentation.getSlides().get(i).getDuration()));

				slide.setAttributeNode(slideID);
				slide.setAttributeNode(nextSlide);
				slide.setAttributeNode(duration);



				// slide elements
				// Images

				if (containsImage == true) 
				{
					for (int x = 0; x < presentation.getSlides().get(i).getImageList().size(); x++) 
					{
						Element image = presentationDoc.createElement("image");

						// Image Attributes (height, ystart, xstart, sourcefile,
						// width, startTime, Duration)
						Attr imageHeight = presentationDoc.createAttribute("height");
						Attr imageYstart = presentationDoc.createAttribute("ystart");
						Attr imageXstart = presentationDoc.createAttribute("xstart");
						Attr imageSource = presentationDoc.createAttribute("sourceFile");
						Attr imageWidth = presentationDoc.createAttribute("width");
						Attr imageStart = presentationDoc.createAttribute("starttime");
						Attr imageDuration = presentationDoc.createAttribute("duration");

						// Set image attributes
						imageHeight.setValue(
								String.valueOf(presentation.getSlides().get(i).getImageList().get(x).getHeight()));
						imageYstart.setValue(
								String.valueOf(presentation.getSlides().get(i).getImageList().get(x).getyStart()));
						imageXstart.setValue(
								String.valueOf(presentation.getSlides().get(i).getImageList().get(x).getxStart()));
						imageSource.setValue(presentation.getSlides().get(i).getImageList().get(x).getSourceFile());
						imageWidth.setValue(
								String.valueOf(presentation.getSlides().get(i).getImageList().get(x).getWidth()));
						imageStart.setValue(
								String.valueOf(presentation.getSlides().get(i).getImageList().get(x).getStartTime()));
						imageDuration.setValue(
								String.valueOf(presentation.getSlides().get(i).getImageList().get(x).getDuration()));

						image.setAttributeNode(imageYstart);
						image.setAttributeNode(imageXstart);
						image.setAttributeNode(imageSource);
						image.setAttributeNode(imageStart);
						image.setAttributeNode(imageDuration);
						image.setAttributeNode(imageHeight);
						image.setAttributeNode(imageWidth);
						
						slide.appendChild(image);
						
						

					}
				}

				// Shape
				if (containsShape == true) 
				{
					for (int x = 0; x < presentation.getSlides().get(i).getShapeList().size(); x++) 
					{
						Element shape = presentationDoc.createElement("shape");

						// Shape Attributes (width, ystart, xstart, type,
						// height, startTime, Duration, fillcolour, linecolour)
						Attr shapeWidth = presentationDoc.createAttribute("width");
						Attr shapeYstart = presentationDoc.createAttribute("ystart");
						Attr shapeXstart = presentationDoc.createAttribute("xstart");
						Attr shapeType = presentationDoc.createAttribute("type");
						Attr shapeHeight = presentationDoc.createAttribute("height");
						Attr shapeStart = presentationDoc.createAttribute("starttime");
						Attr shapeDuration = presentationDoc.createAttribute("duration");
						Attr shapeFill = presentationDoc.createAttribute("fillColour");
						Attr shapeLine = presentationDoc.createAttribute("lineColour");

						

						// Set shape attributes
						shapeWidth.setValue(
								String.valueOf(presentation.getSlides().get(i).getShapeList().get(x).getWidth()));
						shapeYstart.setValue(
								String.valueOf(presentation.getSlides().get(i).getShapeList().get(x).getyStart()));
						shapeXstart.setValue(
								String.valueOf(presentation.getSlides().get(i).getShapeList().get(x).getxStart()));
						shapeType.setValue(
								presentation.getSlides().get(i).getShapeList().get(x).getType());
						shapeHeight.setValue(
								String.valueOf(presentation.getSlides().get(i).getShapeList().get(x).getHeight()));
						shapeStart.setValue(
								String.valueOf(presentation.getSlides().get(i).getShapeList().get(x).getStartTime()));
						shapeDuration.setValue(
								String.valueOf(presentation.getSlides().get(i).getShapeList().get(x).getDuration()));
						shapeFill.setValue(
								String.valueOf(presentation.getSlides().get(i).getShapeList().get(x).getFillColour()));
						shapeLine.setValue(
								String.valueOf(presentation.getSlides().get(i).getShapeList().get(x).getLineColour()));
						
						shape.setAttributeNode(shapeYstart);
						shape.setAttributeNode(shapeXstart);
						shape.setAttributeNode(shapeStart);
						shape.setAttributeNode(shapeDuration);
						shape.setAttributeNode(shapeType);
						shape.setAttributeNode(shapeWidth);
						shape.setAttributeNode(shapeHeight);
						shape.setAttributeNode(shapeFill);
						shape.setAttributeNode(shapeLine);
						
						// Shading element for shape 
						Element shapeShading = presentationDoc.createElement("Shading");

						Attr shapeShadeXOne = presentationDoc.createAttribute("X1");
						Attr shapeShadeXTwo = presentationDoc.createAttribute("X2");
						Attr shapeShadeYOne = presentationDoc.createAttribute("Y1");
						Attr shapeShadeYTwo = presentationDoc.createAttribute("Y2");
						Attr shapeShadeColourOne = presentationDoc.createAttribute("colour1");
						Attr shapeShadeColourTwo = presentationDoc.createAttribute("colour2");				

						if (shapeShade == true){
						shapeShadeXOne.setValue(String
								.valueOf(presentation.getSlides().get(i).getShapeList().get(x).getShading().getxOne()));
						shapeShadeXTwo.setValue(String
								.valueOf(presentation.getSlides().get(i).getShapeList().get(x).getShading().getxTwo()));
						shapeShadeYOne.setValue(String
								.valueOf(presentation.getSlides().get(i).getShapeList().get(x).getShading().getyOne()));
						shapeShadeYTwo.setValue(String
								.valueOf(presentation.getSlides().get(i).getShapeList().get(x).getShading().getyTwo()));
						shapeShadeColourOne.setValue(String.valueOf(
								presentation.getSlides().get(i).getShapeList().get(x).getShading().getColourOne()));
						shapeShadeColourTwo.setValue(String.valueOf(
								presentation.getSlides().get(i).getShapeList().get(x).getShading().getColourTwo()));				

						shapeShading.setAttributeNode(shapeShadeXOne);
						shapeShading.setAttributeNode(shapeShadeXTwo);
						shapeShading.setAttributeNode(shapeShadeYOne);
						shapeShading.setAttributeNode(shapeShadeYTwo);
						shapeShading.setAttributeNode(shapeShadeColourOne);
						shapeShading.setAttributeNode(shapeShadeColourTwo);


						shape.appendChild(shapeShading);
						}
						slide.appendChild(shape);
					}
				}
				// Polygon
				if (containsPoly == true)
				{
					for (int x = 0; x < presentation.getSlides().get(i).getShapeList().size(); x++) 
					{
						Element polygon = presentationDoc.createElement("polygon");

						// polygon Attributes (Source, start time, duration,
						// fill colour, line colour)
						Attr polySource = presentationDoc.createAttribute("sourceFile");
						Attr polyStart = presentationDoc.createAttribute("starttime");
						Attr polyDuration = presentationDoc.createAttribute("duration");
						Attr polyFill = presentationDoc.createAttribute("fillColour");
						Attr polyLine = presentationDoc.createAttribute("lineColour");

						// Set polygon attributes
						polySource.setValue(String
								.valueOf(presentation.getSlides().get(i).getPolygonList().get(x).getSourceFile()));
						polyStart.setValue(
								String.valueOf(presentation.getSlides().get(i).getPolygonList().get(x).getStartTime()));
						polyDuration.setValue(
								String.valueOf(presentation.getSlides().get(i).getPolygonList().get(x).getDuration()));
						polyFill.setValue(String
								.valueOf(presentation.getSlides().get(i).getPolygonList().get(x).getFillColour()));
						polyLine.setValue(String
								.valueOf(presentation.getSlides().get(i).getPolygonList().get(x).getLineColour()));

						polygon.setAttributeNode(polySource);
						polygon.setAttributeNode(polyStart);
						polygon.setAttributeNode(polyDuration);
						polygon.setAttributeNode(polyFill);
						polygon.setAttributeNode(polyLine);

						// Shading element for polygon
						Element polyShading = presentationDoc.createElement("Shading");

						Attr polyShadeXOne = presentationDoc.createAttribute("X1");
						Attr polyShadeXTwo = presentationDoc.createAttribute("X2");
						Attr polyShadeYOne = presentationDoc.createAttribute("Y1");
						Attr polyShadeYTwo = presentationDoc.createAttribute("Y2");
						Attr polyShadeColourOne = presentationDoc.createAttribute("colour1");
						Attr polyShadeColourTwo = presentationDoc.createAttribute("colour2");

						polyShadeXOne.setValue(String
								.valueOf(presentation.getSlides().get(i).getShapeList().get(x).getShading().getxOne()));
						polyShadeXTwo.setValue(String
								.valueOf(presentation.getSlides().get(i).getShapeList().get(x).getShading().getxTwo()));
						polyShadeYOne.setValue(String
								.valueOf(presentation.getSlides().get(i).getShapeList().get(x).getShading().getyOne()));
						polyShadeYTwo.setValue(String
								.valueOf(presentation.getSlides().get(i).getShapeList().get(x).getShading().getyTwo()));
						polyShadeColourOne.setValue(String.valueOf(
								presentation.getSlides().get(i).getShapeList().get(x).getShading().getColourOne()));
						polyShadeColourTwo.setValue(String.valueOf(
								presentation.getSlides().get(i).getShapeList().get(x).getShading().getColourTwo()));


						polyShading.setAttributeNode(polyShadeXOne);
						polyShading.setAttributeNode(polyShadeXTwo);
						polyShading.setAttributeNode(polyShadeYOne);
						polyShading.setAttributeNode(polyShadeYTwo);
						polyShading.setAttributeNode(polyShadeColourOne);
						polyShading.setAttributeNode(polyShadeColourTwo);

						polygon.appendChild(polyShading);
						slide.appendChild(polygon);

					}
				}

				// Text
				if (containsText == true) 
				{
					for (int x = 0; x < presentation.getSlides().get(i).getTextList().size(); x++) 
					{
						Element text = presentationDoc.createElement("text");

						// text Attributes (ystart, xstart, startTime, Duration, font, fontsize, font colour)
						Attr textYstart = presentationDoc.createAttribute("ystart");
						Attr textXstart = presentationDoc.createAttribute("xstart");
						Attr textStart = presentationDoc.createAttribute("starttime");
						Attr textDuration = presentationDoc.createAttribute("duration");
						Attr textFont = presentationDoc.createAttribute("font");
						Attr textFontSize = presentationDoc.createAttribute("fontsize");
						Attr textFontColour = presentationDoc.createAttribute("fontcolour");

						// Set text attributes
						textYstart.setValue(
								String.valueOf(presentation.getSlides().get(i).getTextList().get(x).getyStart()));
						textXstart.setValue(
								String.valueOf(presentation.getSlides().get(i).getTextList().get(x).getxStart()));
						textStart.setValue(
								String.valueOf(presentation.getSlides().get(i).getTextList().get(x).getStartTime()));
						textDuration.setValue(
								String.valueOf(presentation.getSlides().get(i).getTextList().get(x).getDuration()));
						textFont.setValue(
								String.valueOf(presentation.getSlides().get(i).getTextList().get(x).getFont()));
						textFontSize.setValue(
								String.valueOf(presentation.getSlides().get(i).getTextList().get(x).getFontSize()));
						textFontColour.setValue(
								String.valueOf(presentation.getSlides().get(i).getTextList().get(x).getFontColour()));

						// set attributes
						text.setAttributeNode(textYstart);
						text.setAttributeNode(textXstart);
						text.setAttributeNode(textStart);
						text.setAttributeNode(textDuration);
						text.setAttributeNode(textFont);
						text.setAttributeNode(textFontSize);
						text.setAttributeNode(textFontColour);

						// set text
						text.appendChild(presentationDoc
								.createTextNode(presentation.getSlides().get(i).getTextList().get(x).getText()));
						slide.appendChild(text);

					}
				}

				// Video
				if (containsVideo == true)
				{
					for (int x = 0; x < presentation.getSlides().get(i).getVideoList().size(); x++)
					{
						Element video = presentationDoc.createElement("video");

						// video Attributes (ystart, xstart, sourcefile,
						// duration, startTime, loop)

						Attr videoYstart = presentationDoc.createAttribute("ystart");
						Attr videoXstart = presentationDoc.createAttribute("xstart");
						Attr videoSource = presentationDoc.createAttribute("sourceFile");
						Attr videoDuration = presentationDoc.createAttribute("duration");
						Attr videoStart = presentationDoc.createAttribute("starttime");
						Attr videoLoop = presentationDoc.createAttribute("loop");

						// Set video attributes
						videoYstart.setValue(
								String.valueOf(presentation.getSlides().get(i).getVideoList().get(x).getyStart()));
						videoXstart.setValue(
								String.valueOf(presentation.getSlides().get(i).getVideoList().get(x).getxStart()));
						videoSource.setValue(presentation.getSlides().get(i).getVideoList().get(x).getSourceFile());
						videoDuration.setValue(
								String.valueOf(presentation.getSlides().get(i).getVideoList().get(x).getDuration()));
						videoStart.setValue(
								String.valueOf(presentation.getSlides().get(i).getVideoList().get(x).getStartTime()));
						videoLoop.setValue(
								String.valueOf(presentation.getSlides().get(i).getVideoList().get(x).isLoop()));

						video.setAttributeNode(videoYstart);
						video.setAttributeNode(videoXstart);
						video.setAttributeNode(videoSource);
						video.setAttributeNode(videoStart);
						video.setAttributeNode(videoDuration);
						video.setAttributeNode(videoLoop);

						slide.appendChild(video);
					}
				}

				// Audio
				if (containsAudio == true) 
				{
					for (int x = 0; x < presentation.getSlides().get(i).getAudioList().size(); x++)
					{
						Element audio = presentationDoc.createElement("audio");

						// audio Attributes (sourcefile, duration, startTime,
						// loop)
						Attr audioSource = presentationDoc.createAttribute("sourceFile");
						Attr audioDuration = presentationDoc.createAttribute("duration");
						Attr audioStart = presentationDoc.createAttribute("starttime");
						Attr audioLoop = presentationDoc.createAttribute("loop");

						// Set audio attributes
						audioSource.setValue(presentation.getSlides().get(i).getAudioList().get(x).getSourceFile());
						audioLoop.setValue(
								String.valueOf(presentation.getSlides().get(i).getAudioList().get(x).getDuration()));
						audioStart.setValue(
								String.valueOf(presentation.getSlides().get(i).getAudioList().get(x).getStartTime()));
						audioDuration.setValue(
								String.valueOf(presentation.getSlides().get(i).getAudioList().get(x).isLoop()));

						audio.setAttributeNode(audioSource);
						audio.setAttributeNode(audioStart);
						audio.setAttributeNode(audioDuration);
						audio.setAttributeNode(audioLoop);

						slide.appendChild(audio);
					}
				}

			}
			// write the content into xml file
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			DOMSource source = new DOMSource(presentationDoc);
			StreamResult result = new StreamResult(new File("C:/Users/Callum/Desktop/pres.xml"));
			transformer.transform(source, result);

			System.out.println("created XML file");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}

	}
}