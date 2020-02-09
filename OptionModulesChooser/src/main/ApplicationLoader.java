package main;

import controller.OptionsModuleChooserController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.StudentProfile;
import view.ModuleChooserRootPane;

public class ApplicationLoader extends Application {

	private ModuleChooserRootPane view;
	
	@Override
	public void init() {
		//create model and view and pass their references to the controller
		StudentProfile model = new StudentProfile();
		view = new ModuleChooserRootPane();
		new OptionsModuleChooserController(view, model);	
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		//whilst you can set a min width and height (example shown below) for the stage window,
		//you should not set a max width or height and the application should
		//be able to be maximised to fill the screen and ideally behave sensibly when resized
		stage.setMinWidth(1200); 
		stage.setMinHeight(900);
		stage.setWidth(1200);
		stage.setHeight(900);
		
		
		
		stage.setTitle("Final Year Module Chooser Tool");
		stage.setScene(new Scene(view));
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}