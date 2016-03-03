package Parsers;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

public class ReadTextFile {
	
	private String sourceFile;
	
	
	
	public ReadTextFile() {
		super();
	}

	public String ReadFile(String sourceFile) throws IOException
	{
		this.sourceFile = sourceFile;
		FileReader reader = new FileReader(sourceFile);
		BufferedReader textReader = new BufferedReader(reader);
		
		int numberOfLines = this.getLines();
		String lineText = new String();
		String text = new String();
		text = "";
		
		for ( int i=0; i < numberOfLines; i++)
		{
			lineText = textReader.readLine();
			text = (text + lineText + " ");
		}
		
		
		textReader.close();
		return text;
		
	}

	private int getLines() throws IOException {
		FileReader fileToRead = new FileReader(sourceFile);
		BufferedReader reader = new BufferedReader(fileToRead);
		
		String currentLine;
		int lines = 0;
		
		while ((currentLine = reader.readLine()) != null) 
		{
			lines++;
		}
		reader.close();
		
		return lines;
	}

}
