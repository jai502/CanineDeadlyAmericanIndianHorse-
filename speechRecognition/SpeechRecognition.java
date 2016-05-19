package speechRecognition;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import edu.cmu.sphinx.api.*;

public class SpeechRecognition {

	public static void main(String[] args){
		
		//Demo for Speech Recognition
		Configuration configuration = new Configuration();
		configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
		configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
		configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
		
		try {
			StreamSpeechRecognizer recognizer = new StreamSpeechRecognizer(configuration);
			InputStream stream = new FileInputStream("speechRecognition/speechdemo.wav");
			recognizer.startRecognition(stream);
			
			SpeechResult result;
			
			while((result = recognizer.getResult()) != null)
			{
				System.out.format("Speech: %s\n", result.getHypothesis());
			}
			
			recognizer.stopRecognition();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
