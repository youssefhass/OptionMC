package view;

import javafx.scene.layout.BorderPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;

//You may change this class to extend another type if you wish
public class ModuleChooserRootPane extends BorderPane {

	private StudentProfilePane spp;
	private SelectModulesPane smp;
	private OverviewSelectionPane osp;
	private MyMenuBar mmb;
	private TabPane tp;
	
	public ModuleChooserRootPane() {
		
		tp = new TabPane();
		
		tp.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE); //don't allow tabs to be closed
		
		mmb = new MyMenuBar();
		BorderPane borderPane  = new BorderPane();
		
		spp = new StudentProfilePane();
		smp = new SelectModulesPane();
		osp = new OverviewSelectionPane();
		
		Tab t1 = new Tab("CreateProfile",spp);
		Tab t2 = new Tab("Select Modules", smp);
		Tab t3 = new Tab("Overview", osp);
		
		//add tabs to tab pane
		tp.getTabs().addAll(t1, t2, t3);
		
		this.setTop(mmb);
		this.setCenter(tp);
		 this.getChildren().addAll(borderPane);
		
	}
	
	/* These methods provide a public interface for the root pane and allow
	 * each of the sub-containers to be accessed by the controller. */
	public StudentProfilePane getStudentProfilePane() {
		return spp;
	}

	public SelectModulesPane getSelectModulesPane() {
		return smp;
	}
	
	public OverviewSelectionPane getOverviewSelectionPane() {
		return osp;
	}
	
	public MyMenuBar getMyMenuBar(){
		return mmb;
	}
	//method to allow the controller to change tabs
	public void changeTab(int index) {
		tp.getSelectionModel().select(index);
	}
	
	public TabPane getTabPane() {
		return tp;
	}
	

}
