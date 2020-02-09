package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import model.StudentProfile;

public class OverviewSelectionPane extends GridPane {
	
	private TextArea results;
	private Button btnSaveoverview;
	
	public OverviewSelectionPane() {
		
	    GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(20, 20, 20, 20));
        this.getChildren().addAll(grid);
        
		results = new TextArea("Results will appear here...");
		results.setMinSize(700, 700);
		results.setEditable(false);
		
		this.setPadding(new Insets(40, 40, 40, 40));
		btnSaveoverview = new Button("Save Overview");

		grid.add(results, 0, 0);
		grid.add(btnSaveoverview, 0, 1);

		this.getChildren().addAll(results,btnSaveoverview);
	}
	
	//methods to update the content of this pane
	public void setResults(String overview) {
		results.setText(overview);
	}
	



public void addSaveProfileHandler(EventHandler<ActionEvent> handler) {
	btnSaveoverview.setOnAction(handler);
}


	


}
