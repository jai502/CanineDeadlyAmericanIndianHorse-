package email;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class EmailInit
{
 
    Label mail_label;
    TextField mail_text;
    
    Label sub_label;
    TextField sub_text;
    
    Label msg_label;
    TextArea msg_text;
    
    Button send_button;

    public void sneakyAids(VBox bp) {
        
        mail_label = new Label("Send to : ");
        
        mail_text= new TextField("...");
        
        sub_label= new Label("Subject : ");
        
        sub_text= new TextField("...");
        
        msg_label= new Label("Message : ");
          
        msg_text= new TextArea();

        send_button= new Button("Send");
        
        bp.getChildren().add(mail_label);
        bp.getChildren().add(mail_text);
        bp.getChildren().add(sub_label);
        bp.getChildren().add(sub_text);
        bp.getChildren().add(msg_label);
        bp.getChildren().add(msg_text);
        bp.getChildren().add(send_button);
        
        send_button.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent e) {
        		
        		String to = mail_text.getText();
                String subject = sub_text.getText();
                String message =  msg_text.getText();
                
                String user_email = "swengstammtisch@gmail.com";
                String password = "SWengTeam1";
                
                SendEmail.send(to,subject, message, user_email, password);
        		
        	}
        });
        
    }
      
}
    