package view;

import java.util.Collection;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import javafx.scene.control.SelectionMode;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Border;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import model.Course;
import model.Module;
import model.Name;
import model.Delivery;

public class SelectModulesPane extends GridPane {

	private ListView<Module> listyearLong_modules,listView_T1,listView_T1_Selected,listView_T1_Reserve,listView_T2_Reserve,listView_T2,listView_T2_Selected;
	private ObservableList<Module> yearLong_modules,t1_modules,t1_modules_Selected,listView_T1_Reserve_Modules, listView_T2_Reserve_Modules,t2_modules,t2_modules_Selected;
	private Button t1_addBtn, t1_removeBtn,t1_addBtn_Reserve, t1_removeBtn_Reserve,t2_addBtn_Reserve, t2_removeBtn_Reserve, t2_addBtn, t2_removeBtn, submit,reset;
	private Label yearLong, unselectedTerm1, unselectedTerm2, selectedTerm1, selectedTerm2, term1,term2,currentTerm1Credits,currentTerm2Credits, term1_Reserve, term2_Reserve, term1_Reserve_Btn, term2_Reserve_Btn;

	private TextField term1Credits, term2Credits;

	public SelectModulesPane() {

		GridPane grid = new GridPane();
		grid.setHgap(20);
		grid.setVgap(20);
		this.setAlignment(Pos.CENTER);
		this.setPadding(new Insets(40, 40, 40, 40));
		this.getChildren().addAll(grid);
		
		term1 = new Label("Term 1");
		term2 = new Label("Term 2");
		currentTerm1Credits = new Label("Current Term 1 Credits");
		currentTerm2Credits = new Label("Current Term 2 Credits");

		yearLong = new Label("YearLong Modules");
		unselectedTerm1 = new Label("Unselected Term 1");
		unselectedTerm2 = new Label("Unselected Term 2");
		selectedTerm1 = new Label("Selected Term 1");
		selectedTerm2 = new Label("Selected Term 2");
		term1_Reserve = new Label("Term 1 Reserve");
		term2_Reserve = new Label("Term 2 Reserve");
		term1_Reserve_Btn = new Label("Term 1 Reserve");
		term2_Reserve_Btn = new Label("Term 2 Reserve");


	
		  ColumnConstraints column1 = new ColumnConstraints();
	        column1.setHalignment(HPos.CENTER);
	        grid.getColumnConstraints().add(column1);

		/*ColumnConstraints column1 = new ColumnConstraints();
		 column1.setHalignment(HPos.RIGHT);
		  grid.getColumnConstraints().add(column1);
		  
		  ColumnConstraints column2 = new ColumnConstraints();
			 column2.setHalignment(HPos.LEFT);
			  grid.getColumnConstraints().add(column2);
*/
		yearLong_modules = FXCollections.observableArrayList();
		listyearLong_modules = new ListView<>(yearLong_modules);

		listyearLong_modules.setPrefSize(1, 100);

		t1_modules = FXCollections.observableArrayList();
		listView_T1 = new ListView<>(t1_modules);
		listView_T1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		listView_T1.setPrefSize(400, 200);

		t1_modules_Selected = FXCollections.observableArrayList();
		listView_T1_Selected = new ListView<>(t1_modules_Selected);
		listView_T1_Selected.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		listView_T1_Selected.setPrefSize(400, 200);
		
		listView_T1_Reserve_Modules = FXCollections.observableArrayList();
		listView_T1_Reserve = new ListView<>(listView_T1_Reserve_Modules);

		listView_T1_Reserve.setPrefSize(1, 100);

		t2_modules = FXCollections.observableArrayList();
		listView_T2 = new ListView<>(t2_modules);
		listView_T2.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		listView_T2.setPrefSize(400, 200);
		
		

		t2_modules_Selected = FXCollections.observableArrayList();
		listView_T2_Selected = new ListView<>(t2_modules_Selected);
		listView_T2_Selected.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		listView_T2_Selected.setPrefSize(400, 200);
		
		listView_T2_Reserve_Modules = FXCollections.observableArrayList();
		listView_T2_Reserve = new ListView<>(listView_T2_Reserve_Modules);

		listView_T2_Reserve.setPrefSize(1, 100);

		

		t1_addBtn = new Button(" Add ");
		t1_removeBtn = new Button(" Remove ");
		
		t1_addBtn_Reserve = new Button(" Add Reserve ");
		t1_removeBtn_Reserve = new Button(" Remove Reserve ");
		
		
		t2_addBtn = new Button(" Add ");
		t2_removeBtn = new Button(" Remove ");
		
		t2_addBtn_Reserve = new Button(" Add Reserve ");
		t2_removeBtn_Reserve = new Button(" Remove Reserve ");
		
		HBox submitReset = new HBox();
		HBox unselectedTerm1Buttons = new HBox();
		HBox reserveTerm1Buttons = new HBox();
		HBox unselectedTerm2Buttons = new HBox();
		HBox reserveTerm2Buttons = new HBox();

		unselectedTerm1Buttons.setSpacing(15);
		unselectedTerm2Buttons.setSpacing(15);
		reserveTerm1Buttons.setSpacing(15);
		reserveTerm2Buttons.setSpacing(15);
		submitReset.setSpacing(15);

		submit = new Button(" Submit ");
		reset = new Button(" Reset ");
		
		
		term1Credits = new TextField("");
		term1Credits.setEditable(false);
		term1Credits.setPrefWidth(50);

		term2Credits = new TextField("");
		term2Credits.setEditable(false);
		term2Credits.setPrefWidth(50);
		// Setting the margin to the nodes
		// unselectedTerm1VBox.setPadding(new Insets(10, 50, 50, 50));

		VBox unselectedTerm1VBox = new VBox(unselectedTerm1, listView_T1);
		unselectedTerm1VBox.setPadding(new Insets(10, 50, 50, 50));
		VBox containerUT1 = new VBox(unselectedTerm1VBox, unselectedTerm1Buttons, reserveTerm1Buttons);

		VBox unselectedTerm2VBox = new VBox(unselectedTerm2, listView_T2);
		unselectedTerm2VBox.setPadding(new Insets(10, 50, 50, 50));
		VBox containerUT2 = new VBox(unselectedTerm2VBox, unselectedTerm2Buttons,reserveTerm2Buttons);

		unselectedTerm1Buttons.setPadding(new Insets(10, 50, 50, 50));
		unselectedTerm2Buttons.setPadding(new Insets(10, 50, 50, 50));
		reserveTerm1Buttons.setPadding(new Insets(10, 50, 50, 50));
		reserveTerm2Buttons.setPadding(new Insets(10, 50, 50, 50));
		submitReset.setPadding(new Insets(10, 50, 50, 50));
		// unselectedTerm1VBox.getChildren().addAll(unselectedTerm1,listView_T1);
		unselectedTerm1Buttons.getChildren().addAll(term1, t1_addBtn, t1_removeBtn, currentTerm1Credits,term1Credits);
		
		reserveTerm1Buttons.getChildren().addAll(term1_Reserve_Btn,t1_addBtn_Reserve,t1_removeBtn_Reserve);
		
		unselectedTerm2Buttons.getChildren().addAll(term2, t2_addBtn, t2_removeBtn, currentTerm2Credits, term2Credits);
		
		reserveTerm2Buttons.getChildren().addAll(term2_Reserve_Btn,t2_addBtn_Reserve,t2_removeBtn_Reserve);
		
		submitReset.getChildren().addAll(submit,reset);
		submitReset.setAlignment(Pos.CENTER_RIGHT);

		VBox selectedTerm1VBox = new VBox(yearLong, listyearLong_modules, selectedTerm1, listView_T1_Selected,term1_Reserve,listView_T1_Reserve,selectedTerm2, listView_T2_Selected,term2_Reserve, listView_T2_Reserve);
		selectedTerm1VBox.setPadding(new Insets(10, 50, 50, 50));
		VBox containerST1 = new VBox(selectedTerm1VBox);



		VBox leftVBox = new VBox(containerUT1, containerUT2, submitReset );
		VBox rightVBox = new VBox(containerST1);
		
		grid.add(leftVBox, 0, 0);
		grid.add(rightVBox, 1, 0);

	


	}

	public void addAddHandler(EventHandler<ActionEvent> handler) {
		t1_addBtn.setOnAction(handler);
	}

	public void removeRemoveHandler(EventHandler<ActionEvent> handler) {
		t1_removeBtn.setOnAction(handler);
	}
	
	public void addReserveT1AddHandler(EventHandler<ActionEvent> handler) {
		t1_addBtn_Reserve.setOnAction(handler);
	}

	public void removeReserveT1RemoveHandler(EventHandler<ActionEvent> handler) {
		t1_removeBtn_Reserve.setOnAction(handler);
	}
	
	
	public void addReserveT2AddHandler(EventHandler<ActionEvent> handler) {
		t2_addBtn_Reserve.setOnAction(handler);
	}

	public void removeReserveT2RemoveHandler(EventHandler<ActionEvent> handler) {
		t2_removeBtn_Reserve.setOnAction(handler);
	}


	public void addt2AddHandler(EventHandler<ActionEvent> handler) {
		t2_addBtn.setOnAction(handler);
	}

	public void removet2RemoveHandler(EventHandler<ActionEvent> handler) {
		t2_removeBtn.setOnAction(handler);
	}

	public void submitSelectionHandler(EventHandler<ActionEvent> handler) {
		submit.setOnAction(handler);
	}
	

	public void ResetSelectionHandler(EventHandler<ActionEvent> handler) {
		reset.setOnAction(handler);
	}

	public void populateYearLong(Module m) {
		yearLong_modules.add(m);
	}

	public void populateT1Listview(Module m) {
		t1_modules.add(m);
	}

	public void populateT1Compulsary(Module m) {
		t1_modules_Selected.add(m);
	}

	public void populateT2Listview(Module m) {
		t2_modules.add(m);
	}

	public void populateT2Compulsary(Module m) {
		t2_modules_Selected.add(m);

	}

	public Module getSelectedItem_T1() {
		return listView_T1.getSelectionModel().getSelectedItem();
	}

	public Module getSelectedItem_T2() {
		return listView_T2.getSelectionModel().getSelectedItem();
	}

	public void addSelectedItem_T1(Module m) {
		listView_T1_Selected.getSelectionModel().clearSelection();
		t1_modules.remove(m);
		t1_modules_Selected.add(m);
	}

	public Module getRemoveSelectedItem_T1() {
		return listView_T1_Selected.getSelectionModel().getSelectedItem();
	}

	public int getSelectedItem_T1Index() {
		return listView_T1_Selected.getSelectionModel().getSelectedIndex();
	}
	public int getSelectedItem_T2Index() {
		return listView_T2_Selected.getSelectionModel().getSelectedIndex();
	}
	public void removeSelectedItem_T1(Module m) {
		listView_T1_Selected.getSelectionModel().clearSelection();
		t1_modules_Selected.remove(m);
		t1_modules.add(m);

	}

	public void addSelectedItem_T2(Module m) {
		listView_T2_Selected.getSelectionModel().clearSelection();
		t2_modules.remove(m);
		t2_modules_Selected.add(m);
	}

	public Module getRemoveSelectedItem_T2() {
		return listView_T2_Selected.getSelectionModel().getSelectedItem();
	}

	public void removeSelectedItem_T2(Module m) {
		listView_T2_Selected.getSelectionModel().clearSelection();
		t2_modules_Selected.remove(m);
		t2_modules.add(m);

	}

	public void clearSelectionT1() {

		listView_T1.getSelectionModel().clearSelection();
	}
	
	public void clearSelectionT2() {

		listView_T2.getSelectionModel().clearSelection();
	}
	
	////////Reserve Modules /////////
	public void addSelectedItemT1Reserve(Module m) {
		listView_T1.getSelectionModel().clearSelection();
		t1_modules.remove(m);
		listView_T1_Reserve_Modules.add(m);
	}

	public Module getRemoveSelectedItemT1Reserve() {
		return listView_T1_Reserve.getSelectionModel().getSelectedItem();
	}

	public int getSelectedItemT1IndexReserve() {
		return listView_T1_Reserve.getSelectionModel().getSelectedIndex();
	}

	public void removeSelectedItemT1Reserve(Module m) {
		listView_T1_Reserve.getSelectionModel().clearSelection();
		listView_T1_Reserve_Modules.remove(m);
		t1_modules.add(m);

	}
	
	
	public void addSelectedItemT2Reserve(Module m) {
		listView_T2.getSelectionModel().clearSelection();
		t2_modules.remove(m);
		listView_T2_Reserve_Modules.add(m);
	}

	public Module getRemoveSelectedItemT2Reserve() {
		return listView_T2_Reserve.getSelectionModel().getSelectedItem();
	}

	public int getSelectedItemT2IndexReserve() {
		return listView_T2_Reserve.getSelectionModel().getSelectedIndex();
	}

	public void removeSelectedItemT2Reserve(Module m) {
		listView_T2_Reserve.getSelectionModel().clearSelection();
		listView_T2_Reserve_Modules.remove(m);
		t2_modules.add(m);

	}
	
	
	
	public ObservableList<Module> getT1ReserveSelected() {
		return listView_T1_Reserve.getItems();
	}

	public ObservableList<Module> getT2ReserveSelected() {
		return listView_T2_Reserve.getItems();
	}
	
	
	
////////
	public void setTerm1Credits(int c) {
		term1Credits.setText(String.valueOf(c));

	}

	public int getTerm1credits() {
		if ((term1Credits.getText()).equals("")) {
			return 0;
		} else
			return Integer.parseInt((term1Credits.getText()));
	}

	public void setTerm2Credits(int c) {
		term2Credits.setText(String.valueOf(c));

	}

	public int getTerm2credits() {
		if ((term2Credits.getText()).equals("")) {
			return 0;
		} else
			return Integer.parseInt((term2Credits.getText()));
	}

	public ObservableList<Module> getYearLong() {
		return listyearLong_modules.getItems();
	}

	public ObservableList<Module> getT1Selected() {
		return listView_T1_Selected.getItems();
	}

	public ObservableList<Module> getT2Selected() {
		return listView_T2_Selected.getItems();
	}

	// button bindings
	public void submitSelectionBtnDisableBind(BooleanBinding property) {
		submit.disableProperty().bind(property);
	}

	public BooleanBinding isMaxCredits() {
		return term1Credits.textProperty().isNotEqualTo("60").or(term2Credits.textProperty().isNotEqualTo("60")).or(Bindings.isEmpty(listView_T1_Reserve.getItems())).or(Bindings.isEmpty(listView_T2_Reserve.getItems()));
	}

	public void term1AddBtnEnabledBind(BooleanBinding property) {
		t1_addBtn.disableProperty().bind(property);
	}

	public BooleanBinding isMaxCreditsT1() {
		return term1Credits.textProperty().isEqualTo("60");
	}

	public void term2AddBtnEnabledBind(BooleanBinding property) {
		t2_addBtn.disableProperty().bind(property);
	}

	public BooleanBinding isMaxCreditsT2() {
		return term2Credits.textProperty().isEqualTo("60");
	}

	public void term1RemoveBtnDisabledBind(BooleanBinding property) {
		t1_removeBtn.disableProperty().bind(property);
	}

	public BooleanBinding isModuleSelectedT1() {
		return listView_T1_Selected.getSelectionModel().selectedItemProperty().isNull();
	}
	
	
	
	public void term1ReserveAddBtnDisabledBind(BooleanBinding property) {
		t1_addBtn_Reserve.disableProperty().bind(property);
	}
	
	public void term2ReserveAddBtnDisabledBind(BooleanBinding property) {
		t2_addBtn_Reserve.disableProperty().bind(property);
	}
	
	
	
	public void term2RemoveBtnDisabledBind(BooleanBinding property) {
		t2_removeBtn.disableProperty().bind(property);
	}

	public BooleanBinding isModuleSelectedT2() {
		return listView_T2_Selected.getSelectionModel().selectedItemProperty().isNull();
	}

	
	public void term1ReserveRemoveBtnDisabledBind(BooleanBinding property) {
		t1_removeBtn_Reserve.disableProperty().bind(property);
	}

	public BooleanBinding isModuleSelectedReserveT1() {
		return listView_T1_Reserve.getSelectionModel().selectedItemProperty().isNull();
	}
	
	public void term2ReserveRemoveBtnDisabledBind(BooleanBinding property) {
		t2_removeBtn_Reserve.disableProperty().bind(property);
	}

	public BooleanBinding isModuleSelectedReserveT2() {
		return listView_T2_Reserve.getSelectionModel().selectedItemProperty().isNull();
	}
	
	public BooleanBinding isModuleReserveFull() {
		return Bindings.isNotEmpty(listView_T1_Reserve.getItems());
	}
	public BooleanBinding isModuleReserveT2Full() {
		return Bindings.isNotEmpty(listView_T2_Reserve.getItems());
	}
	

	
	public void clearViews() {
		listyearLong_modules.getItems().clear();
		listView_T1.getItems().clear();
		listView_T2.getItems().clear();
		listView_T2_Selected.getItems().clear();
		listView_T1_Selected.getItems().clear();
		listView_T1_Reserve.getItems().clear();
		listView_T2_Reserve.getItems().clear();
	}
	


}
