/**
 * (C) Stammtisch
 * First version created by: J.Bones
 * Date of first version: 06.03.2016
 * 
 * Last version by: J.Bones
 * Date of last update: 
 * Version number: 1.1
 * 
 * Commit date: 06.03.2016
 * Description: Simple text handler to allow for display of text according to the PWS
 */
package handlers;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import java.util.ArrayList;
import Objects.TextItem;

public class TextHandler
{
	String textToHandle = null; //String to store input text
	int windowHeight;
	int windowWidth;


	public TextFlow setText(TextItem textItem)
	{
		//Declare an empty text flow object to be returned
		TextFlow textFlow = null;

		textFlow = new TextFlow();

		//Set the starting co-ordinates of the textFlow object
		textFlow.setLayoutX(textItem.getxStart()*windowWidth);
		textFlow.setLayoutY(textItem.getyStart()*windowHeight);
		textFlow.setPrefHeight(textItem.getHeight()*windowHeight);
		textFlow.setPrefWidth(textItem.getHeight()*windowWidth);

		//Notify the user if the string is empty
		if(textItem.getSourceText() != null)
		{
			textToHandle = textItem.getSourceText();
		}
		else if(textItem.getText() != null)
		{
			textToHandle = textItem.getText();
		}
		else{
			System.out.println("String sources are null!");
			System.out.println("Exiting program");
			System.exit(1);
		}


		//Declare counter to keep track of HTML tag indexes
		int endOfLastTag = 0;


		//Declare an ArrayList to store characters which are not bold or italic
		ArrayList<Character> normalChars = new ArrayList<Character>(textItem.getText().length());

		if(textToHandle.contains("<"))
		{
			//cycle through each character of the message to be displayed
			for(int i=0;i<textToHandle.length();i++)
			{
				//If
				if(textToHandle.charAt(i)=='<')
				{
					//Store the characters from the end of the last tag to the start of the current tag
					for(int j=endOfLastTag;j<i;j++)
					{
						normalChars.add(textToHandle.charAt(j));
					}
					//Build a string from the characters stored in the character array list
					StringBuilder normal = new StringBuilder();
					for (Object index : normalChars)
					{
						normal.append(index);
					}
					normalChars.clear(); //Clear the array list ready for the next characters

					//Store the string of non-formatted characters
					String normalText = null;
					normalText = normal.toString();

					//Add the string of non-formatted characters to the text flow object
					Text defaultText = new Text(normalText);
					defaultText.setFont(Font.font(textItem.getFont(), textItem.getFontSize())); //Set the font of the non-formatted string
					defaultText.setFill(textItem.getFontColour()); //Set the font colour of the non-formatted string
					textFlow.getChildren().add(defaultText);

					//Apply font formatting based on the respective HTML tag
					switch (textToHandle.charAt(i+1))
					{
					case'b':
						//Create an array list of characters to store bold characters
						ArrayList<Character> boldChars = new ArrayList<Character>(textToHandle.length()-i);
						String boldString = null;

						for(int j=i+3;j<textToHandle.length();j++){
							//append characters to the bold array list
							boldChars.add(textToHandle.charAt(j));
							//break out of for loop if end tag identifier found
							if(textToHandle.charAt(j)=='/'){
								//Remove the final two characters in order to remove the closing tags
								boldChars.remove(j-i-3);
								boldChars.remove(j-i-4);

								//Update the end of tag counter with end of tag index
								endOfLastTag = j+3;

								//Build a string from the characters stored in the character array list
								StringBuilder output = new StringBuilder();
								for(Object character : boldChars)
								{
									output.append(character);
								}
								boldChars.clear(); //Clear the array list ready for the next bold characters
								boldString = output.toString();
								break;
							}
						}
						//Add the bold text to the textflow object
						Text bold = new Text(boldString);
						bold.setFont(Font.font(textItem.getFont(), FontWeight.BOLD, textItem.getFontSize()));
						bold.setFill(textItem.getFontColour());
						textFlow.getChildren().add(bold);
						break;

					case 'i':
						//Create an array list of characters to store italic content
						ArrayList<Character> italChars = new ArrayList<Character>(textToHandle.length()-i);
						String italicString = null;

						for(int j=i+3;j<textToHandle.length();j++)
						{
							//append characters to an arraylist
							italChars.add(textToHandle.charAt(j));

							//break out of for loop if an end tag identifier is found
							if(textToHandle.charAt(j)=='/')
							{
								//Remove the final two characters in order to remove the tag
								italChars.remove(j-i-3);
								italChars.remove(j-i-4);

								//Update the end of tag counter
								endOfLastTag = j+3;

								//Iterate through array list and build a string from the characters
								StringBuilder output = new StringBuilder();
								for(Object character : italChars)
								{
									output.append(character);
								}
								italicString = output.toString();
								italChars.clear();
								break;
							}
						}

						//Append italic text to screen
						Text italic = new Text(italicString);
						italic.setFont(Font.font(textItem.getFont(), FontPosture.ITALIC, textItem.getFontSize()));
						italic.setFill(textItem.getFontColour());
						textFlow.getChildren().add(italic);
						break;

						//default case statement
					default:
						break;
					}
				}

			}

		}
		else
		{
			//Add the string of non-formatted characters to the text flow object
			Text defaultText = new Text(textToHandle);
			defaultText.setFont(Font.font(textItem.getFont(), textItem.getFontSize())); //Set the font of the non-formatted string
			defaultText.setFill(textItem.getFontColour()); //Set the font colour of the non-formatted string
			textFlow.getChildren().add(defaultText);
		}

		//Return the text flow object to the superclass
		return textFlow;
	}

	public void setWindowSize(int width, int height)
	{
		windowHeight = height;
		windowWidth = width;
	}

}



