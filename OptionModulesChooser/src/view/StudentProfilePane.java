package view;

import java.time.LocalDate;

import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import model.Course;
import model.Name;

public class StudentProfilePane extends GridPane {
	// courses, 5 text fields- {p#, firstname,lastname,email}
	// submissiondate, create profile button

	private ComboBox<Course> cboCourses;
	private TextField txtPnumber, txtFirstName, txtSurName, txtEmail;
	private DatePicker currentDatePicker;
	private Button createProfileBtn;
	private Name studentName;
	private Label lblInvalidPnumber, lblInvalidFname,lblInvalidSname,lblInvalidEmail;

	public StudentProfilePane() {

		// GridPane

		GridPane grid = new GridPane();
		grid.setHgap(20);
		grid.setVgap(20);
		this.setAlignment(Pos.CENTER);
		this.setPadding(new Insets(40, 40, 40, 40));
		this.getChildren().addAll(grid);

		ColumnConstraints column1 = new ColumnConstraints();
		column1.setHalignment(HPos.RIGHT);
		grid.getColumnConstraints().add(column1);

		// create labels
		Label lblCourse = new Label("Select Course: ");
		Label lblPnumber = new Label("Enter P number");
		Label lblFirstName = new Label("First name: ");
		Label lblSurname = new Label("Surname: ");
		Label lblEmail = new Label("Email");
		Label lblDate = new Label("Date");
		
		lblInvalidPnumber = new Label();
		lblInvalidFname = new Label();
		lblInvalidSname = new Label();
		lblInvalidEmail = new Label();
		
		

		// setup combobox
		cboCourses = new ComboBox<Course>(); // will be populated via method towards end of class
		// setup text fields
		txtPnumber = new TextField();
		txtFirstName = new TextField();
		txtSurName = new TextField();
		txtEmail = new TextField();
		currentDatePicker = new DatePicker();

		// initialise create button
		createProfileBtn = new Button("Create Profile");

		// adding children to grid

		grid.add(lblCourse, 0, 0);
		grid.add(cboCourses, 1, 0);
		grid.add(lblPnumber, 0, 1);
		grid.add(txtPnumber, 1, 1);
		grid.add(lblInvalidPnumber, 2, 1);
		grid.add(lblFirstName, 0, 2);
		grid.add(txtFirstName, 1, 2);
		grid.add(lblInvalidFname, 2, 2);
		grid.add(lblSurname, 0, 3);
		grid.add(txtSurName, 1, 3);
		grid.add(lblInvalidSname, 2, 3);
		grid.add(lblEmail, 0, 4);
		grid.add(txtEmail, 1, 4);
		grid.add(lblInvalidEmail, 2, 4);
		grid.add(lblDate, 0, 5);
		grid.add(currentDatePicker, 1, 5);
		grid.add(createProfileBtn, 1, 6);
	}

	public void populateComboBox(Course[] courses) {
		cboCourses.getItems().addAll(courses);
		cboCourses.getSelectionModel().select(0);
	}

	public Course getCourse() {
		return cboCourses.getSelectionModel().getSelectedItem();
	}

	public void setCourse(Course course) {
		cboCourses.setValue(course);
	}

	public void setPnumber(String pNumber) {
		txtPnumber.setText(pNumber);
	}
	public String getTxtPnumber() {
		return txtPnumber.getText();
	}

	public void setFirstName(String fname) {
		txtFirstName.setText(fname);
	}
	public String getTxtFirstName() {
		return txtFirstName.getText();
	}

	public void setSurname(String sname) {
		txtSurName.setText(sname);
	}
	public String getTxtSurName() {
		return txtSurName.getText();
	}
	
	public void setEmail(String email) {
		txtEmail.setText(email);
	}

	public String getTxtEmail() {
		return txtEmail.getText();
	}
	
	public void setDate(LocalDate date) {
		currentDatePicker.setValue(date);
	}

	public LocalDate getCurrentDatePicker() {
		return currentDatePicker.getValue();
	}

	public Name getStudentName() {
		studentName = new Name();
		studentName.setFirstName(txtFirstName.getText());
		studentName.setFamilyName(txtSurName.getText());
		return studentName;
	}

	public void addSubmitProfileHandler(EventHandler<ActionEvent> handler) {
		createProfileBtn.setOnAction(handler);
	}
	
	public void setlblInvalidPnumber(String error) {
		lblInvalidPnumber.setText(error);		
	}
	public void setlblInvalidFName(String error) {
		lblInvalidFname.setText(error);		
	}
	public void setlblInvalidSName(String error) {
		lblInvalidSname.setText(error);		
	}
	public void setlblInvalidEmail(String error) {
		lblInvalidEmail.setText(error);		
	}
	
	public BooleanBinding isEitherFieldEmpty() {
		return txtFirstName.textProperty().isEmpty().or(txtSurName.textProperty().isEmpty()).or(txtPnumber.textProperty().isEmpty()).or(txtEmail.textProperty().isEmpty()).or(currentDatePicker.dayCellFactoryProperty().isNotNull());
	}
	public void createProfileBtnDisableBind(BooleanBinding property) {
		createProfileBtn.disableProperty().bind(property);
	}

}
