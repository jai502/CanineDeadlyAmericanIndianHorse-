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

public class TextHandler
{

	public TextFlow setText(String text, int xStart, int yStart, String fontFamily, int fontSize, Color fontColour)
	{
		//Declare an empty text flow object to be returned
		TextFlow textFlow = null;

		//Notify the user if the string is empty
		if(text.isEmpty())
		{
			System.out.println("Nothing to handle!");
			System.exit(0);
		}
		else
		{
			//Declare a TextFlow object to append the text objects
			textFlow = new TextFlow();

			//Set the starting co-ordinates of the textFlow object
			textFlow.setLayoutX(xStart);
			textFlow.setLayoutY(yStart);

			//Declare counter to keep track of HTML tag indexes
			int endOfLastTag = 0;

			
		//Declare an ArrayList to store characters which are not bold or italic
			ArrayList<Character> normalChars = new ArrayList<Character>(text.length());
			
			if(text.contains("<"))
			{
				//cycle through each character of the message to be displayed
				for(int i=0;i<text.length();i++)
				{
					//If
					if(text.charAt(i)=='<')
					{
						//Store the characters from the end of the last tag to the start of the current tag
						for(int j=endOfLastTag;j<i;j++)
						{
							normalChars.add(text.charAt(j));
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
						defaultText.setFont(Font.font(fontFamily, fontSize)); //Set the font of the non-formatted string
						defaultText.setFill(fontColour); //Set the font colour of the non-formatted string
						textFlow.getChildren().add(defaultText);

						//Apply font formatting based on the respective HTML tag
						switch (text.charAt(i+1))
						{
						case'b':
							//Create an array list of characters to store bold characters
							ArrayList<Character> boldChars = new ArrayList<Character>(text.length()-i);
							String boldString = null;

							for(int j=i+3;j<text.length();j++){
								//append characters to the bold array list
								boldChars.add(text.charAt(j));
								//break out of for loop if end tag identifier found
								if(text.charAt(j)=='/'){
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
							bold.setFont(Font.font(fontFamily, FontWeight.BOLD, fontSize));
							bold.setFill(fontColour);
							textFlow.getChildren().add(bold);
							break;

						case 'i':
							//Create an array list of characters to store italic content
							ArrayList<Character> italChars = new ArrayList<Character>(text.length()-i);
							String italicString = null;

							for(int j=i+3;j<text.length();j++)
							{
								//append characters to an arraylist
								italChars.add(text.charAt(j));

								//break out of for loop if an end tag identifier is found
								if(text.charAt(j)=='/')
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
							italic.setFont(Font.font(fontFamily, FontPosture.ITALIC, fontSize));
							italic.setFill(fontColour);
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
				Text defaultText = new Text(text);
				defaultText.setFont(Font.font(fontFamily, fontSize)); //Set the font of the non-formatted string
				defaultText.setFill(fontColour); //Set the font colour of the non-formatted string
				textFlow.getChildren().add(defaultText);
			}
		}
		//Return the text flow object to the superclass
		return textFlow;
	}

}

