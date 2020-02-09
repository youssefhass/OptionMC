package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Course;
import model.Delivery;
import model.Module;
import model.StudentProfile;
import view.ModuleChooserRootPane;
import view.OverviewSelectionPane;
import view.SelectModulesPane;
import view.StudentProfilePane;
import view.MyMenuBar;;

public class OptionsModuleChooserController {

	// fields to be used throughout the class
	private ModuleChooserRootPane view;
	private StudentProfilePane spp;
	private SelectModulesPane smp;
	private OverviewSelectionPane osp;
	private MyMenuBar mmb;
	private StudentProfile model;

	private int credits_t1, credits_t2;

	public OptionsModuleChooserController(ModuleChooserRootPane view, StudentProfile model) {
		// initialise model and view fields
		this.model = model;
		this.view = view;

		spp = view.getStudentProfilePane();
		smp = view.getSelectModulesPane();
		osp = view.getOverviewSelectionPane();
		mmb = view.getMyMenuBar();

		// populate combobox in create profile pane, e.g. if cpp represented
		// your create profile pane you could invoke the line below
		try {
			spp.populateComboBox(setupAndGetCourses());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// attach event handlers to view using private helper method
		this.attachEventHandlers();
		this.attachBindings();

	}

	private void attachEventHandlers() {
		spp.addSubmitProfileHandler(new SubmitStudentProfileHandler());
		smp.addAddHandler(new AddHandler());
		smp.removeRemoveHandler(new RemoveHandler());
		smp.addReserveT1AddHandler(new AddReserveT1Handler());
		smp.removeReserveT1RemoveHandler(new RemoveReserveT1Handler());
		smp.addReserveT2AddHandler(new AddReserveT2Handler());
		smp.removeReserveT2RemoveHandler(new RemoveReserveT2Handler());
		smp.addt2AddHandler(new t2AddHandler());
		smp.removet2RemoveHandler(new t2RemoveHandler());
		smp.submitSelectionHandler(new SubmitSelectionHandler());
		smp.ResetSelectionHandler(new ResetSelectionHandler());
		mmb.SaveHandler(new SaveProfileHandler());
		mmb.addLoadHandler(new LoadHandler());
		osp.addSaveProfileHandler(new SaveOverviewHandler());
		
		mmb.addExitHandler(e -> System.exit(0));
		mmb.addAboutHandler(e ->alertDialogBuilder(AlertType.CONFIRMATION, "About", "Final Year Module Chooser - [By  Youssef Hassan | P17229387]", null));
	}

	/*
	 * this method attaches bindings in the view, e.g. for validation, and to the
	 * model to ensure synchronisation between the data model and view
	 */
	private void attachBindings() {
		// attaches a binding such that the add button in the view will be disabled
		// whenever either of the text fields in the NamePane are empty
		spp.createProfileBtnDisableBind(spp.isEitherFieldEmpty());
		smp.submitSelectionBtnDisableBind(smp.isMaxCredits());
		smp.term1AddBtnEnabledBind(smp.isMaxCreditsT1());
		smp.term2AddBtnEnabledBind(smp.isMaxCreditsT2());
		smp.term1RemoveBtnDisabledBind(smp.isModuleSelectedT1());
		smp.term2RemoveBtnDisabledBind(smp.isModuleSelectedT2());
		
		smp.term1ReserveRemoveBtnDisabledBind(smp.isModuleSelectedReserveT1());
		smp.term2ReserveRemoveBtnDisabledBind(smp.isModuleSelectedReserveT2());
		
	
		
		smp.term1ReserveAddBtnDisabledBind(smp.isModuleReserveFull());
		smp.term2ReserveAddBtnDisabledBind(smp.isModuleReserveT2Full());

	}

	private class SubmitStudentProfileHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {

			if (!isPNumberValid()) {
				spp.setlblInvalidPnumber("Invalid P Number");
			}
			if (!isNameValid()) {
				spp.setlblInvalidFName("Invalid Characters");
			}
			if (!isFamilyNameValid()) {
				spp.setlblInvalidSName("Invalid Characters");
			}

			if (!isEmailValid()) {
				spp.setlblInvalidEmail("Invalid Email Format");

			}

			if (isNameValid() && isFamilyNameValid() && isPNumberValid() && isEmailValid()) {
				smp.clearViews();
				smp.setTerm1Credits(0);
				smp.setTerm2Credits(0);

				model.setCourse(spp.getCourse());
				model.setpNumber(spp.getTxtPnumber());
				model.setStudentName(spp.getStudentName());
				model.setEmail(spp.getTxtEmail());
				model.setDate(spp.getCurrentDatePicker());

				
				populateListViews();


				view.changeTab(1);

			}

		}
	}

	// add module
	private class AddHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			Module m = smp.getSelectedItem_T1();

			if (credits_t1 != 60 && m != null) {

				smp.addSelectedItem_T1(m);
				smp.clearSelectionT1();
				credits_t1 = smp.getTerm1credits();
				credits_t1 += m.getCredits();
				smp.setTerm1Credits(credits_t1);

			}
		}
	}

	private class RemoveHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {

			Module m = smp.getRemoveSelectedItem_T1();
			int i = smp.getSelectedItem_T1Index();
			if (!m.isMandatory() && i != -1) {

				smp.removeSelectedItem_T1(m);
				credits_t1 = smp.getTerm1credits();
				credits_t1 -= m.getCredits();
				smp.setTerm1Credits(credits_t1);

			} else {
				alertDialogBuilder(AlertType.ERROR, "Error", "Selection Error]", "Please select an item to remove");
			

		}
		}
	}

	private class AddReserveT1Handler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			Module m = smp.getSelectedItem_T1();

			if (m != null) {
				smp.addSelectedItemT1Reserve(m);
			
				smp.clearSelectionT1();
			

			}
		}
	}
	
	private class RemoveReserveT1Handler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
///////////FIX//////////
			Module m = smp.getRemoveSelectedItemT1Reserve();
		//	int i = smp.getSelectedItem_T1Index();
			if (!m.isMandatory()) {
				
				smp.removeSelectedItemT1Reserve(m);
			

			} else {
				alertDialogBuilder(AlertType.ERROR, "Error", "Selection Error]", "Please select an item to remove");
		}
		}
	}
	
	private class AddReserveT2Handler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			Module m = smp.getSelectedItem_T2();

			if (m != null) {
				smp.addSelectedItemT2Reserve(m);
			
				smp.clearSelectionT2();
			

			}
		}
	}
	
	private class RemoveReserveT2Handler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
///////////FIX//////////
			Module m = smp.getRemoveSelectedItemT2Reserve();
		//	int i = smp.getSelectedItem_T1Index();
			if (!m.isMandatory()) {
				
				smp.removeSelectedItemT2Reserve(m);
			

			} else {
				alertDialogBuilder(AlertType.ERROR, "Error", "Selection Error]", "Please select an item to remove");
			}

		}
	}
	private class t2AddHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {

			if (credits_t2 != 60) {
				Module m = smp.getSelectedItem_T2();
				smp.clearSelectionT2();
				if (m != null) {
					smp.addSelectedItem_T2(m);
					credits_t2 = smp.getTerm2credits();
					credits_t2 += m.getCredits();
					smp.setTerm2Credits(credits_t2);
				}
			}

		}
	}

	private class t2RemoveHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {

			Module m = smp.getRemoveSelectedItem_T2();
			int i = smp.getSelectedItem_T2Index();
			if (!m.isMandatory() && i!=-1) {
				
					smp.removeSelectedItem_T2(m);
					int credits_t2 = smp.getTerm2credits();
					credits_t2 -= m.getCredits();
					smp.setTerm2Credits(credits_t2);
				
			}

		}
	}

	private class SubmitSelectionHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {

			model.clearSelectedModules();
			model.clearSelectedReserveModules();

			saveSelectedModules();
			osp.setResults(getChosenModules());
			view.changeTab(2);

		}
	}
	
	private class ResetSelectionHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {

			

			
			smp.clearViews();
			smp.setTerm1Credits(0);
			smp.setTerm2Credits(0);
			
			populateListViews();

		}
	}

	public String getChosenModules() {
		String results = "overview\n================\n";

		results += "================\n";

		results += "Course: " + model.getCourse().getCourseName();
		results += "\nFull Name: " + model.getStudentName().getFamilyName() + " "
				+ model.getStudentName().getFamilyName();
		results += "\nP Number: " + model.getpNumber();
		results += "\nEmail: " + model.getEmail();
		results += "\nDate: " + model.getDate();

		results += "\n================\n";

		
		for (Module m : model.getSelectedModules()) {

			results += "\n Module code: " + m.getModuleCode() + "\nModule Title: " + m.getModuleName();
			results += "\nCredits: " + m.getCredits() + " \nIs Mandatory: " + (m.isMandatory() ? "Yes " : "No ")
					+ "\nDelivery: " + (m.getRunPlan() == Delivery.YEAR_LONG ? "Year Long "
							: m.getRunPlan() == Delivery.TERM_1 ? "Term 1 " : "Term 2 ");
			results += "\n\n";
		}
		results += "\n================\n";
		results += "\nReserve Modules\n";


		for (Module m : model.getSelectedReserveModules()) {

			results += "\n Module code: " + m.getModuleCode() + "\nModule Title: " + m.getModuleName();
			results += "\nCredits: " + m.getCredits() + " \nIs Mandatory: " + (m.isMandatory() ? "Yes " : "No ")
					+ "\nDelivery: " + (m.getRunPlan() == Delivery.YEAR_LONG ? "Year Long "
							: m.getRunPlan() == Delivery.TERM_1 ? "Term 1 " : "Term 2 ");
			results += "\n\n";
		}
		return results;
	}

	private class SaveProfileHandler implements EventHandler<ActionEvent> {

		public void handle(ActionEvent e) {

			model.clearSelectedModules();
			model.clearSelectedReserveModules();

			saveSelectedModules();
			// save the data model
			try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("StudentProfile.dat"));) {

				oos.writeObject(model);

				oos.flush();

				alertDialogBuilder(AlertType.INFORMATION, "Information Dialog", "Save success",
						"Register saved to StudentProfile.dat");
			} catch (IOException ioExcep) {
				System.out.println("Error saving");
			}

			for (Module m : model.getSelectedModules()) {
				System.out.println("Saveing... " + m.getModuleName().toString());
			}
		}
	}

	private class LoadHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream("StudentProfile.dat"));) {

				model = (StudentProfile) stream.readObject();
				spp.setPnumber(model.getpNumber());
				spp.setFirstName(model.getStudentName().getFirstName());
				spp.setSurname(model.getStudentName().getFamilyName());
				spp.setEmail(model.getEmail());
				spp.setDate(model.getDate());
				
				
				smp.clearViews();
				
				smp.setTerm1Credits(0);
				smp.setTerm2Credits(0);
				
				populateListViews();
					
				for (Module s: model.getSelectedModules()) {
					if(s.getRunPlan() == Delivery.TERM_1 && !s.isMandatory()) {
						smp.addSelectedItem_T1(s);
						
						credits_t1 = smp.getTerm1credits();
						credits_t1 += s.getCredits();
						smp.setTerm1Credits(credits_t1);
						
					}
					else if(s.getRunPlan() == Delivery.TERM_2 && !s.isMandatory()) {
						smp.addSelectedItem_T2(s);
						credits_t2 = smp.getTerm2credits();
						credits_t2 += s.getCredits();
						smp.setTerm2Credits(credits_t2);
						
					}
				}
						//reserve 
				for (Module s: model.getSelectedReserveModules()) {
					if(s.getRunPlan() == Delivery.TERM_1 && !s.isMandatory()) {
						smp.addSelectedItemT1Reserve(s);
						
					}
					else if(s.getRunPlan() == Delivery.TERM_2 && !s.isMandatory()) {
						smp.addSelectedItemT2Reserve(s);
						
					}
				}
					

				

				// alertDialogBuilder("Student Profile", "Student_Profile.dat
				// has been Loaded.");
			} catch (IOException failevent) {
				System.out.println("Exception: Can not be loaded");
			} catch (ClassNotFoundException classnotfound) {
				System.out.println("Error: Class not Found");
			}

		}

	}

		private class SaveOverviewHandler implements EventHandler<ActionEvent> {
			public void handle(ActionEvent e) {

				String fileName = "StudentProfile.txt";
				
				PrintWriter writer = null;

				try {

					writer = new PrintWriter(fileName);

					writer.println("Course" + model.getCourse().toString());
					writer.println("Name" + model.getStudentName().getFirstName().toString() + ""
							+ model.getStudentName().getFamilyName().toString());
					writer.println("P Number" + model.getpNumber().toString());
					writer.println("Email" + model.getEmail().toString());
					writer.println("Date" + model.getDate().toString());
					writer.println("Selected Modules " + model.getSelectedModules());
					writer.println("Reserve Modules " + model.getSelectedReserveModules());


				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} finally {
					if (writer != null) {
						writer.close();
					}
				}

				for (Module m : model.getSelectedModules()) {

					System.out.println(m.getModuleName().toString());
				}

			}
		}

		private void alertDialogBuilder(AlertType type, String title, String header, String content) {
			Alert alert = new Alert(type);
			alert.setTitle(title);
			alert.setHeaderText(header);
			alert.setContentText(content);
			alert.showAndWait();
		}

	/*	private Course[] setupAndGetCourses() {
			Module imat3423 = new Module("IMAT3423", "Systems Building: Methods", 15, true, Delivery.TERM_1);
			Module imat3451 = new Module("IMAT3451", "Computing Project", 30, true, Delivery.YEAR_LONG);
			Module ctec3902_SoftEng = new Module("CTEC3902", "Rigerous Systems", 15, true, Delivery.TERM_2);
			Module ctec3902_CompSci = new Module("CTEC3902", "Rigerous Systems", 15, false, Delivery.TERM_2);
			Module ctec3110 = new Module("CTEC3110", "Secure Web Application Development", 15, false, Delivery.TERM_1);
			Module ctec3426 = new Module("CTEC3426", "Telematics", 15, false, Delivery.TERM_1);
			Module ctec3605 = new Module("CTEC3605", "Multi-service Networks 1", 15, false, Delivery.TERM_1);
			Module ctec3606 = new Module("CTEC3606", "Multi-service Networks 2", 15, false, Delivery.TERM_2);
			Module ctec3410 = new Module("CTEC3410", "Web Application Penetration Testing", 15, false, Delivery.TERM_2);
			Module ctec3904 = new Module("CTEC3904", "Functional Software Development", 15, false, Delivery.TERM_2);
			Module ctec3905 = new Module("CTEC3905", "Front-End Web Development", 15, false, Delivery.TERM_1);
			Module ctec3906 = new Module("CTEC3906", "Interaction Design", 15, false, Delivery.TERM_2);
			Module imat3410 = new Module("IMAT3104", "Database Management and Programming", 15, false, Delivery.TERM_2);
			Module imat3406 = new Module("IMAT3406", "Fuzzy Logic and Knowledge Based Systems", 15, false,
					Delivery.TERM_1);
			Module imat3611 = new Module("IMAT3611", "Computing Ethics and Privacy", 15, false, Delivery.TERM_1);
			Module imat3613 = new Module("IMAT3613", "Data Mining", 15, false, Delivery.TERM_1);
			Module imat3614 = new Module("IMAT3614", "Big Data", 15, false, Delivery.TERM_2);
			Module imat3428_CompSci = new Module("IMAT3426", "Information Techbology Services Practice", 15, false,
					Delivery.TERM_2);

			Course compSci = new Course("Computer Science");
			compSci.addModule(imat3423);
			compSci.addModule(imat3451);
			compSci.addModule(ctec3902_CompSci);
			compSci.addModule(ctec3110);
			compSci.addModule(ctec3426);
			compSci.addModule(ctec3605);
			compSci.addModule(ctec3606);
			compSci.addModule(ctec3410);
			compSci.addModule(ctec3904);
			compSci.addModule(ctec3905);
			compSci.addModule(ctec3906);
			compSci.addModule(imat3410);
			compSci.addModule(imat3406);
			compSci.addModule(imat3611);
			compSci.addModule(imat3613);
			compSci.addModule(imat3614);
			compSci.addModule(imat3428_CompSci);

			Course softEng = new Course("Software Engineering");
			softEng.addModule(imat3423);
			softEng.addModule(imat3451);
			softEng.addModule(ctec3902_SoftEng);
			softEng.addModule(ctec3110);
			softEng.addModule(ctec3426);
			softEng.addModule(ctec3605);
			softEng.addModule(ctec3606);
			softEng.addModule(ctec3410);
			softEng.addModule(ctec3904);
			softEng.addModule(ctec3905);
			softEng.addModule(ctec3906);
			softEng.addModule(imat3410);
			softEng.addModule(imat3406);
			softEng.addModule(imat3611);
			softEng.addModule(imat3613);
			softEng.addModule(imat3614);

			Course[] courses = new Course[2];
			courses[0] = compSci;
			courses[1] = softEng;
			return courses;
		}*/
		

		private Course[] setupAndGetCourses() throws IOException {
			Course compSci = new Course("Computer Science");
			Course softEng = new Course("Software Engineering");
			Course[] courses = new Course[2];
			courses[0] = compSci;
			courses[1] = softEng;

			Scanner in = new Scanner(new File("Courses"));

			String line;
			String arr[];

			while (in.hasNextLine()) {
				line = in.nextLine();
				arr = line.split(",");

				if (arr[5].equals("compscisofteng")) {
					compSci.addModule(new Module(arr[0], arr[1], Integer.parseInt(arr[2]), Boolean.valueOf(arr[3]),
							Delivery.valueOf(arr[4])));
					softEng.addModule(new Module(arr[0], arr[1], Integer.parseInt(arr[2]), Boolean.valueOf(arr[3]),
							Delivery.valueOf(arr[4])));
				} else if(arr[5].equals("compsci")) {
					compSci.addModule(new Module(arr[0], arr[1], Integer.parseInt(arr[2]), Boolean.valueOf(arr[3]),
							Delivery.valueOf(arr[4])));
					
				} else if(arr[5].equals("softeng")) {
					softEng.addModule(new Module(arr[0], arr[1], Integer.parseInt(arr[2]), Boolean.valueOf(arr[3]),
							Delivery.valueOf(arr[4])));
					
				}

			}
			in.close();

			return courses;
		}

		
		private void populateListViews() {
			for (Module m : model.getCourse().getModulesOnCourse()) {

				
				if (m.getRunPlan() == Delivery.YEAR_LONG) {
					view.getSelectModulesPane().populateYearLong(m);
					credits_t1 = smp.getTerm1credits();
					credits_t1 += ((m.getCredits() / 2));
					smp.setTerm1Credits(credits_t1);

					credits_t2 = smp.getTerm2credits();
					credits_t2 += ((m.getCredits() / 2));
					smp.setTerm2Credits(credits_t2);
				}

				else if (m.getRunPlan() == Delivery.TERM_1 && m.isMandatory()) {
					view.getSelectModulesPane().populateT1Compulsary(m);
					credits_t1 = smp.getTerm1credits();

					credits_t1 += m.getCredits();
					smp.setTerm1Credits(credits_t1);

				}

				else if (m.getRunPlan() == Delivery.TERM_1 && !m.isMandatory()) {
					view.getSelectModulesPane().populateT1Listview(m);
				}

				else if (m.getRunPlan() == Delivery.TERM_2 && !m.isMandatory()) {
					view.getSelectModulesPane().populateT2Listview(m);
				}

				else if (m.getRunPlan() == Delivery.TERM_2 && m.isMandatory()) {
					view.getSelectModulesPane().populateT2Compulsary(m);
					credits_t2 = smp.getTerm2credits();
					credits_t2 += m.getCredits();
					smp.setTerm2Credits(credits_t2);
				}

			}
		}
		
		private void saveSelectedModules() {
			for (Module m : smp.getYearLong()) {
				model.addSelectedModule(m);
				System.out.println("YL: " + m.getModuleName());
			}

			for (Module m : smp.getT1Selected()) {
				model.addSelectedModule(m);
				System.out.println("T1 Selected: " + m.getModuleName());
			}
			for (Module m : smp.getT2Selected()) {
				model.addSelectedModule(m);
				System.out.println("T2 Selected: " + m.getModuleName());
			}
			for (Module m : smp.getT1ReserveSelected()){
				model.addSelectedReserveModule(m);
				System.out.println("T2 r Selected: " + m.getModuleName());
			}
			for (Module m : smp.getT2ReserveSelected()) {
				model.addSelectedReserveModule(m);
				System.out.println("T2  rSelected: " + m.getModuleName());
			}

		}
		private boolean isPNumberValid() {

			Pattern p = Pattern.compile("^[Pp][0-9]{8}[a-zA-Z{1}]?$");
			Matcher m = p.matcher(spp.getTxtPnumber());
			if (m.find() && m.group().equals(spp.getTxtPnumber())) {
				spp.setlblInvalidPnumber("");
				return true;
			} else {
				 
				return false;
			}
		}

		private boolean isNameValid() {

			Pattern p = Pattern.compile("[a-zA-Z]+");
			Matcher m = p.matcher(spp.getTxtFirstName());
			if (m.find() && m.group().equals(spp.getTxtFirstName())) {
				spp.setlblInvalidFName("");
				return true;
			} else {
				
				return false;
			}
		}

		private boolean isFamilyNameValid() {

			Pattern p = Pattern.compile("[a-zA-Z]+");
			Matcher m = p.matcher(spp.getTxtSurName());
			if (m.find() && m.group().equals(spp.getTxtSurName())) {
				spp.setlblInvalidSName("");
				return true;
			} else {
				
				return false;
			}
		}

		private boolean isEmailValid() {

			Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
			Matcher m = p.matcher(spp.getTxtEmail());
			if (m.find() && m.group().equals(spp.getTxtEmail())) {
				spp.setlblInvalidEmail("");
				return true;
			} else {
				
				return false;
			}
		}

}
