/**
* (C) Stammtisch
* First version created by: J.Bones
* Date of first version: 25.02.2016
* 
* Last version by: J.Bones
* Date of last update: 
* Version number: 1.0
* 
* Commit date:
* Description: Simple text handler to allow for dispaly of text from PWS
 */
package handlers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;

public class TextHandler extends Application{
	Stage window;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
  public void start(Stage primaryStage) throws Exception
  {
      window = primaryStage;
      window.setTitle("Text viewer");
      window.setHeight(300);
      window.setWidth(400);

      //Add some text to the screen
      String message = "I am some <b>bold</b> and <i>italic</i> text and " +
              "some more <b>of that bold</b> and <i>of that italic</i> text. "+
              "This is a test for <b>PWS</b> testing <i>Good job</i>.";

      System.out.println("Length of string is: "+message.length()+" characters");

      FlowPane flow = new FlowPane();
      flow.setVgap(5);
      flow.setHgap(0);
      flow.setPrefWrapLength(300);

      int boldCount = 0;
      int italCount = 0;
      int endOfLastTag = 0;
      int numberOfTextElements = 0;

      //cycle through each character of the string
      for(int i=0;i<message.length();i++){

          if(message.charAt(i)=='<'){
              numberOfTextElements +=1;
              ArrayList normalChars = new ArrayList(message.length());
              String normalText = null;

              //Print out the text from the end of the last tag to the start of the current tag
              for(int j=endOfLastTag;j<i;j++){
                  normalChars.add(message.charAt(j));
              }
              StringBuilder normal = new StringBuilder();
              for (Object index : normalChars){
                  normal.append(index);
              }
              normalChars.clear();
              normalText = normal.toString();
              System.out.println("default format string: "+normalText);

              //Append bold text to screen
              Text defaultText = new Text(normalText);
              defaultText.getStyleClass().add("text-default");
              flow.getChildren().add(defaultText);

              switch (message.charAt(i+1)){
                  case'b':
                      numberOfTextElements +=1;
                      boldCount +=1;
                      //Create an array list of characters to store bold content
                      ArrayList boldChars = new ArrayList(message.length()-i);
                      String boldString = null;

                      for(int j=i+3;j<message.length();j++){
                          //append characters to an arraylist
                          boldChars.add(message.charAt(j));
                          //break out of for loop if end tag identifier found
                          if(message.charAt(j)=='/'){
                              //Remove the final two characters in order to remove the closing tags
                              //Remove the final two characters in order to remove the closing tags
                              boldChars.remove(j-i-3);
                              boldChars.remove(j-i-4);

                              //Update the end of tag counter
                              endOfLastTag = j+3;

                              //Iterate through list and format to a string
                              StringBuilder output = new StringBuilder();
                              for(Object character : boldChars){
                                  output.append(character);
                              }
                              //flush out the array list
                              boldChars.clear();
                              boldString = output.toString();
                              System.out.println("String to be bold: "+output);
                              break;
                          }

                      }

                      //Append bold text to screen
                      Text bold = new Text(boldString);
                      bold.getStyleClass().add("text-bold");
                      flow.getChildren().add(bold);
                      break;

                  case 'i':
                      numberOfTextElements +=1;
                      italCount +=1;
                      //Create an array list of characters to store bold content
                      ArrayList italChars = new ArrayList(message.length()-i);
                      String italicString = null;

                      for(int j=i+3;j<message.length();j++){

                          //append characters to an arraylist
                          italChars.add(message.charAt(j));

                          //break out of for loop if end tag identifier found
                          if(message.charAt(j)=='/'){
                              //Remove the final two characters in order to remove the tag
                              italChars.remove(j-i-3);
                              italChars.remove(j-i-4);

                              //Update the end of tag counter
                              endOfLastTag = j+3;

                              //Iterate through list and format to a string
                              StringBuilder output = new StringBuilder();
                              for(Object character : italChars){
                                  output.append(character);
                              }
                              italicString = output.toString();
                              System.out.println("String to be italic: "+italicString);
                              italChars.clear();
                              break;
                          }
                      }

                      //Append italic text to screen
                      Text italic = new Text(italicString);
                      italic.getStyleClass().add("text-italic");
                      flow.getChildren().add(italic);
                      break;

                  default:
                      break;

              }

          }
      }
      System.out.println("Number of bold tag pair occurrences: "+boldCount);
      System.out.println("Number of italic tag pair occurences: "+italCount);
      System.out.println("Number of text elements is: "+numberOfTextElements);



      Scene scene = new Scene(flow);
      scene.getStylesheets().add("handlers/Style.css");
      window.setScene(scene);
      window.show();

  }
}

