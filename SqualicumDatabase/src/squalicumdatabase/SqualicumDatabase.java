/*
Jacob Gervais
FBLA Coding and Programing

This program generates a user interface that is connected to a database. 
This program can: create books, students and teachers; edit names of students
and teachers; check out books to a student; manage settings for book limit and 
time limit; and generate reports.
*/


package squalicumdatabase;


import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;




 

public class SqualicumDatabase {

   public static ArrayList<String> student = new ArrayList<String>();//models and arrays of database and tables
   public static ArrayList<String> teacher = new ArrayList<String>();
   public static ArrayList<String> books = new ArrayList<String>();
   public static ArrayList<String> author = new ArrayList<String>();
   public static ArrayList<String> bid = new ArrayList<String>();  
   
   public static ArrayList<String> checkedName = new ArrayList<String>();
   public static ArrayList<String> checkedTitle = new ArrayList<String>();
   public static ArrayList<String> checkedSID = new ArrayList<String>();
   public static ArrayList<String> checkedBID = new ArrayList<String>();
   public static ArrayList<String> dueDate = new ArrayList<String>();
   public static ArrayList<String> isReturned = new ArrayList<String>();
   
   public static ArrayList<String> checkedNameTeacher = new ArrayList<String>();
   public static ArrayList<String> checkedTitleTeacher = new ArrayList<String>();
   public static ArrayList<String> checkedTID = new ArrayList<String>();
   public static ArrayList<String> checkedBIDTeacher = new ArrayList<String>();
   public static ArrayList<String> dueDateTeacher = new ArrayList<String>();
   public static ArrayList<String> isReturnedTeacher = new ArrayList<String>();
   
   
   public static int[] settings = new int[4];
   public static DefaultTableModel studentModel = new DefaultTableModel(0,1);
   public static DefaultTableModel teacherModel = new DefaultTableModel(0,1);
   public static DefaultTableModel bookModel = new DefaultTableModel(0,3);
   public static DefaultTableModel checkedOutModel = new DefaultTableModel(0,4);
   public static DefaultTableModel checkedOutTeacherModel = new DefaultTableModel(0,4);


    public static void connect() { //creates a connection to the database
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:UIBuild.db";
            conn = DriverManager.getConnection(url); 
            System.out.println("Connection to SQLite has been established."); 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    SqualicumDatabase(){
      JFrame frame = new JFrame("SQHS Library Database"); 
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      
      JPanel p1 = new JPanel(new GridLayout(3,2)); //panels for my user interface
      JPanel p2 = new JPanel(new GridLayout(3,2));
      JPanel p3 = new JPanel(new GridLayout(4,2));
      JPanel p4 = new JPanel(new GridLayout(2,3));
      JPanel p5 = new JPanel(new GridLayout(4,2));
      JPanel p6 = new JPanel(new GridLayout(2,1));
      JPanel p7 = new JPanel(new GridLayout(3,2));
      
      
      JPanel lp1 = new JPanel(new GridLayout(1,1)); //panels for viewing the data
      JPanel lp2 = new JPanel(new GridLayout(1,1));
      JPanel lp3 = new JPanel(new GridLayout(1,1));
      JPanel lp4 = new JPanel(new GridLayout(1,1));
      JPanel lp5 = new JPanel(new GridLayout(1,1));
      
      
      JMenuBar menuBar = new JMenuBar(); //adds a menu bar that includes a tutorial for the database
      JMenu menu = new JMenu("Help");
      menuBar.add(menu);
      JMenuItem menuItem = new JMenuItem("A Brief Tutorial", KeyEvent.VK_T);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("A Brief Tutorial");
        menu.add(menuItem);
        menuItem.addActionListener((ActionEvent ae) -> {
            File file = new File("tutorial.rtf");
            try{
                Desktop desktop = Desktop.getDesktop();
                if(file.exists()) desktop.open(file);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(SqualicumDatabase.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(SqualicumDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }
      });
        frame.setJMenuBar(menuBar);
      
      JTabbedPane tp = new JTabbedPane();
      JTabbedPane tp2= new JTabbedPane();
      
      JTable studentTable = new JTable(); //students table that displays the names of all students in database
      studentModel.setRowCount(student.size());
      studentTable.setModel(studentModel);
      studentTable.getColumnModel().getColumn(0).setHeaderValue("Name");
      int count = 0;
      for(String i : student){
         add(i, studentTable, count,0);
         count++;   
      }
      count = 0;
 
      JTable teacherTable = new JTable(0,1); //teacher table that displays the names of all teachers in database
      teacherTable.setModel(teacherModel);
      teacherModel.setRowCount(teacher.size());
      teacherTable.getColumnModel().getColumn(0).setHeaderValue("Name");
      count = 0;
      for(String i : teacher){
         add(i, teacherTable, count,0);
         count++;      
      }
      count = 0;
    
      
      JTable bookTable = new JTable(0,3); //book table that displays the books in library
      bookModel.setRowCount(books.size());
      bookTable.setModel(bookModel);
      bookTable.getColumnModel().getColumn(0).setHeaderValue("Title");
      bookTable.getColumnModel().getColumn(1).setHeaderValue("Author");
      bookTable.getColumnModel().getColumn(2).setHeaderValue("ID");
      count = 0;
      for(String i : books){
         add(i, bookTable, count, 0);
         count++;      
      }
      count = 0;
      for(String i : author){
         add(i, bookTable, count,1);
         count++;      
      }
      count = 0;
      for(String i : bid){
         add(i, bookTable, count,2);
         count++;      
      }
      
      JTable checkedOut = new JTable(0,4); //Table that displays all books that have been checked out
      checkedOut.setModel(checkedOutModel);
      checkedOutModel.setRowCount(checkedName.size());
      checkedOut.getColumnModel().getColumn(0).setHeaderValue("Name");
      checkedOut.getColumnModel().getColumn(1).setHeaderValue("Book Title");
      checkedOut.getColumnModel().getColumn(2).setHeaderValue("Book ID");
      checkedOut.getColumnModel().getColumn(3).setHeaderValue("Due Date");
      count = 0;
      for(String i : checkedName){
         add(i, checkedOut, count,0);
         count++;      
      }
      count = 0;
      for(String i : checkedTitle){
         add(i, checkedOut, count,1);
         count++;      
      }
      count = 0;
      for(String i : checkedBID){
         add(i, checkedOut, count,2);
         count++;      
      }
      count = 0;
      for(String i : dueDate){
          add(i, checkedOut, count,3);
          count++;
      }
      
      JTable checkedOutTeacher = new JTable(0,4); //Table that displays all books that have been checked out
      checkedOutTeacher.setModel(checkedOutTeacherModel);
      checkedOutTeacherModel.setRowCount(checkedNameTeacher.size());
      checkedOutTeacher.getColumnModel().getColumn(0).setHeaderValue("Name");
      checkedOutTeacher.getColumnModel().getColumn(1).setHeaderValue("Book Title");
      checkedOutTeacher.getColumnModel().getColumn(2).setHeaderValue("Book ID");
      checkedOutTeacher.getColumnModel().getColumn(3).setHeaderValue("Due Date");
      count = 0;
      for(String i : checkedNameTeacher){
         add(i, checkedOutTeacher, count,0);
         count++;      
      }
      count = 0;
      for(String i : checkedTitleTeacher){
         add(i, checkedOutTeacher, count,1);
         count++;      
      }
      count = 0;
      for(String i : checkedBIDTeacher){
         add(i, checkedOutTeacher, count,2);
         count++;      
      }
      count = 0;
      for(String i : dueDateTeacher){
          add(i, checkedOutTeacher, count,3);
          count++;
      }
      
      JScrollPane stu = new JScrollPane(studentTable);
      JScrollPane tea = new JScrollPane(teacherTable);
      JScrollPane boo = new JScrollPane(bookTable);
      JScrollPane check = new JScrollPane(checkedOut);
      JScrollPane checkTeach = new JScrollPane(checkedOutTeacher);

      lp1.add(stu);
      lp2.add(tea);
      lp3.add(boo);
      lp4.add(check);
      lp5.add(checkTeach);
       
      JTextField tf1 = new JTextField(12); //Add book
      JTextField tf2 = new JTextField(12);
      JButton addBook = new JButton(" Add ");
      JButton clear = new JButton(" Reset");      
      p1.add(new JLabel("Title:"));
      p1.add(tf1);
      p1.add(new JLabel("Author:"));
      p1.add(tf2);
      p1.add(addBook);
      p1.add(clear);
      clear.addActionListener( //code that makes the clear button clear everything
      (ActionEvent ae) -> {
          tf1.setText("");
          tf2.setText("");
      });
      addBook.addActionListener( //button that adds a book to the library
      (ActionEvent ae) -> {
          if(tf1.getText().length() != 0 && tf2.getText().length() != 0){
              JOptionPane.showMessageDialog(p1, tf1.getText()+ " by " + tf2.getText() + " was successfully entered into database");
              books.add(tf1.getText());
              author.add(tf2.getText());
              bid.add(Integer.toString(Integer.parseInt((bid.get(bid.size()-1)))+1));
              bookModel.setRowCount(books.size());
              add(tf1.getText(),bookTable, books.size()-1,0);
              add(tf2.getText(),bookTable, author.size()-1,1);
              add(bid.get(bid.size()-1), bookTable, books.size()-1,2);
              Insert app = new Insert();
              app.insertBook(tf1.getText(), tf2.getText());
              tf1.setText("");
              tf2.setText("");
              
          }else{
              JOptionPane.showMessageDialog(p1,"Error inserting into database, please re-enter.");
          }
      });
      
      
      JTextField tf4 = new JTextField(12); //Add person
      String[] optionStrings = {"Student","Teacher"};
      JComboBox tOrS = new JComboBox(optionStrings);
      JButton addName = new JButton(" Add ");
      JButton clear1 = new JButton(" Reset");
      p2.add(new JLabel("Name:"));
      p2.add(tf4);
      p2.add(new JLabel("Teacher or Student?"));
      p2.add(tOrS);
      p2.add(addName);
      p2.add(clear1);
      addName.addActionListener( //when button is clicked this is how the name is added
      (ActionEvent ae) -> {
          if(tf4.getText().length() != 0 && "Student".equals(tOrS.getSelectedItem())){ //if student this code is exectued
              JOptionPane.showMessageDialog(p2, tf4.getText() + " has been added as a " + tOrS.getSelectedItem());
              student.add(tf4.getText());
              studentModel.setRowCount(student.size());
              add(tf4.getText(), studentTable, student.size()-1,0);
              add("?", studentTable, student.size()-1, 1);
              Insert app = new Insert();
              app.insertStudent(tf4.getText());
              tf4.setText("");
              
              
          }else if(tf4.getText().length() != 0 && "Teacher".equals(tOrS.getSelectedItem())){ //if teacher this code is executed
              JOptionPane.showMessageDialog(p2, tf4.getText() + " has been added as a " + tOrS.getSelectedItem());
              teacher.add(tf4.getText());
              teacherModel.setRowCount(teacher.size());
              add(tf4.getText(), teacherTable, teacher.size()-1,0);
              Insert app = new Insert();
              app.insertTeacher(tf4.getText());
              tf4.setText("");
              
          }else{
              JOptionPane.showMessageDialog(p2,"Error inserting into database, please re-enter.");
          }
      });
      clear1.addActionListener( //clears text box
      (ActionEvent ae) -> {
          tf4.setText("");
      });
      
      
      JTextField tf6 = new JTextField(12); //Edit Name
      JTextField tf7 = new JTextField(12);
      String[] ops = {"Student","Teacher"};
      JComboBox jcb = new JComboBox(ops);
      JButton edit = new JButton("Edit");
      JButton clearText = new JButton("Reset");
      p3.add(new JLabel("Name you wish to edit:"));
      p3.add(tf6);
      p3.add(new JLabel(""));
      p3.add(jcb);
      p3.add(new JLabel("New Name:"));
      p3.add(tf7);
      p3.add(edit);
      p3.add(clearText);
      edit.addActionListener( //edits the names of teachers or students
      (ActionEvent ae) -> {
          if(tf6.getText().length() != 0 && tf7.getText().length() != 0){
              Insert app = new Insert();
              if("Student".equals(jcb.getSelectedItem()) && student.contains(tf6.getText())){ //if student
                  app.editStudent(tf6.getText(), tf7.getText());
                  removeStudent(tf6.getText(), studentTable);
                  student.remove(tf6.getText());
                  student.add(tf7.getText());
                  studentModel.setRowCount(student.size());
                  add(tf7.getText(), studentTable, student.size()-1,0);
                  JOptionPane.showMessageDialog(p3, "Edit was made.");
              }else if("Teacher".equals(jcb.getSelectedItem()) && teacher.contains(tf6.getText())){ //if teacher
                  app.editTeacher(tf6.getText(), tf7.getText());
                  removeStudent(tf6.getText(), teacherTable);
                  teacher.remove(tf6.getText());
                  teacher.add(tf7.getText());
                  teacherModel.setRowCount(teacher.size());
                  add(tf7.getText(), teacherTable, teacher.size()-1,0);
                  JOptionPane.showMessageDialog(p3, "Edit was made.");
              }
              tf6.setText("");
              tf7.setText("");
          }else{
              JOptionPane.showMessageDialog(p3, "Please re-enter.");
          }
      });
      clearText.addActionListener( //clears the text boxes
      (ActionEvent ae) -> {
          tf6.setText("");
          tf7.setText("");
      });
                  
      String[] options = {"Student","Teacher"};//Checkout a book
      JComboBox sOrT = new JComboBox(options);
      JTextField tf8 = new JTextField(12);
      JTextField tf9 = new JTextField(12);
      JButton checkout = new JButton("Checkout");
      JButton ret = new JButton("Return");
      p5.add(new JLabel("Name:"));
      p5.add(tf8);
      p5.add(new JLabel(""));
      p5.add(sOrT);
      p5.add(new JLabel("Book ID:"));
      p5.add(tf9);
      p5.add(checkout);
      p5.add(ret);
      checkout.addActionListener( //checkout a book
      (ActionEvent ae) -> {
          Insert app = new Insert();
          if("Student".equals(sOrT.getSelectedItem()) && student.contains(tf8.getText())
                  && bid.contains(tf9.getText()) && !checkedBID.contains(tf9.getText())
                  && !checkedBIDTeacher.contains(tf9.getText()) && 
                  getNumOfBooks(tf8.getText()) <= settings[0]){
              
              String date = getDueDate(1,0) + "/" + getDueDate(2,0) + "/" +getDueDate(0,0); 
                      
              app.checkoutStudent(tf8.getText(), tf9.getText(), date);
              checkedName.add(tf8.getText());
              checkedBID.add(tf9.getText());
              checkedTitle.add(app.getTitle(tf9.getText()));
              dueDate.add(date);
              checkedOutModel.setRowCount(checkedName.size());
              add(tf8.getText(), checkedOut, checkedName.size()-1,0);
              add(app.getTitle(tf9.getText()), checkedOut, checkedName.size()-1,1);
              add(tf9.getText(), checkedOut, checkedName.size()-1, 2);
              add(date, checkedOut, checkedName.size()-1,3);
              
          }else if("Teacher".equals(sOrT.getSelectedItem()) &&
                  teacher.contains(tf8.getText())
                  && bid.contains(tf9.getText()) && !checkedBID.contains(tf9.getText()) &&
                  !checkedBIDTeacher.contains(tf9.getText()) &&
                  getNumOfBooks(tf8.getText()) <= settings[2]){
              
              String date = getDueDate(1,1) + "/" + getDueDate(2,1) + "/" +getDueDate(0,1);
              app.checkoutTeacher(tf8.getText(), tf9.getText(), date);
              checkedNameTeacher.add(tf8.getText());
              checkedBIDTeacher.add(tf9.getText());
              checkedTitleTeacher.add(app.getTitle(tf9.getText()));
              dueDateTeacher.add(date);
              checkedOutTeacherModel.setRowCount(checkedNameTeacher.size());
              add(tf8.getText(), checkedOutTeacher, checkedNameTeacher.size()-1,0);
              add(app.getTitle(tf9.getText()), checkedOutTeacher, checkedNameTeacher.size()-1,1);
              add(tf9.getText(), checkedOutTeacher, checkedNameTeacher.size()-1, 2);
              add(date, checkedOutTeacher, checkedNameTeacher.size()-1,3);
          }else if (getNumOfBooks(tf8.getText()) >= settings[0]){
              JOptionPane.showMessageDialog(p4, "This person has checked out too many books!"); 
          }else{
              JOptionPane.showMessageDialog(p4, "Please re-enter. Something is messed up");
          }
          tf8.setText("");
          tf9.setText("");
      });
      ret.addActionListener( //return a book
      (ActionEvent ae) -> {
          Insert app = new Insert();
          if("Student".equals(sOrT.getSelectedItem()) && checkedName.contains(tf8.getText()) && checkedBID.contains(tf9.getText())){
              
              app.returnStudent(tf9.getText());
              int removeIndex = checkedName.indexOf(tf8.getText());
              checkedName.remove(checkedName.get(removeIndex));
              checkedSID.remove(checkedSID.get(removeIndex));
              checkedTitle.remove(checkedTitle.get(removeIndex));
              checkedBID.remove(checkedBID.get(removeIndex));
              dueDate.remove(dueDate.get(removeIndex));
              removeCheckout(tf9.getText(), checkedOut);
              
          }else if("Teacher".equals(sOrT.getSelectedItem()) &&
                  checkedNameTeacher.contains(tf8.getText()) && checkedBIDTeacher.contains(tf9.getText())){
              
              app.returnTeacher(tf9.getText());
              int removeIndex = checkedBIDTeacher.indexOf(tf9.getText());
              checkedNameTeacher.remove(checkedNameTeacher.get(removeIndex));
              checkedTID.remove(checkedTID.get(removeIndex));
              checkedTitleTeacher.remove(checkedTitleTeacher.get(removeIndex));
              checkedBIDTeacher.remove(checkedBIDTeacher.get(removeIndex));
              dueDateTeacher.remove(dueDateTeacher.get(removeIndex));
              removeCheckout(tf9.getText(), checkedOutTeacher);
              
          }else{
              JOptionPane.showMessageDialog(p4, "Please re-enter. Something is messed up");
          }
          tf8.setText("");
          tf9.setText("");
      });


      
      JButton overdue = new JButton("Print weekly report of overdue books"); //print reports
      JButton weekcheck = new JButton("Print list of books checked out this week");
      p6.add(overdue);
      p6.add(weekcheck);
      overdue.addActionListener( //generate overdue book report
      (ActionEvent ae) -> {
          overdueReport();
      });
      weekcheck.addActionListener( //generate book report of books checked out from the week
      (ActionEvent ae) -> {
          weeklyReport();
      });


      
      JButton current = new JButton("View Settings"); //settings
      String[] limits = {"Change Student Book limit", "Change Student Time limit",
      "Change Teacher Book limit","Change Teacher Time limit"};
      JComboBox setting = new JComboBox(limits);
      JTextField tf10 = new JTextField(12);
      JButton change = new JButton("Change Setting");
      p7.add(new JLabel("Change Setting:"));
      p7.add(tf10);
      p7.add(new JLabel(""));
      p7.add(setting);
      p7.add(change);
      p7.add(current);
      current.addActionListener( //veiw current settings
      (ActionEvent ae) -> {
          JOptionPane.showMessageDialog(p7, "Student Book Lim: " + settings[0] + "\n"
                  + "Student Time Lim: " + settings[1] + "\n" +"Teacher Book Lim: " + settings[2]
                  + "\n" + "Teacher Time Lim: " + settings[3]);
      });
      change.addActionListener( //change settings
      (ActionEvent ae) -> {
          Insert app = new Insert();
          String newSet = tf10.getText();
          if(!"".equals(newSet)){
              if("Change Student Book limit".equals(setting.getSelectedItem())){
                  app.insertsbl(newSet, settings[0]);
                  settings[0] = Integer.parseInt(newSet);
              }else if("Change Student Time limit".equals(setting.getSelectedItem())){
                  app.insertstl(newSet, settings[1]);
                  settings[1] = Integer.parseInt(newSet);
              }else if("Change Teacher Book limit".equals(setting.getSelectedItem())){
                  app.inserttbl(newSet, settings[2]);
                  settings[2] = Integer.parseInt(newSet);
              }else if("Change Teacher Time limit".equals(setting.getSelectedItem())){
                  app.insertttl(newSet, settings[3]);
                  settings[3] = Integer.parseInt(newSet);
              }
          }else{
              JOptionPane.showMessageDialog(p7, "you messed something up. pls fix");
              
          }
          tf10.setText("");
      });
     
      
      frame.getContentPane().add(tp); //TabbedPane
      tp.addTab("Add Book",p1);
      tp.addTab("Add Account", p2);
      tp.addTab("Edit Accounts", p3);
      tp.addTab("Veiw lists", tp2);
      tp.addTab("Checkout",p5);
      tp.addTab("Print reports",p6);
      tp.addTab("Settings",p7);
      
      tp2.addTab("Students", lp1);
      tp2.addTab("Teacher", lp2);
      tp2.addTab("Library", lp3);
      tp2.addTab("Student Check Outs" , lp4);
      tp2.addTab("Teacher Check Outs", lp5);
               
      frame.setSize(1000,333);
      frame.setVisible(true);
      frame.setResizable(true);
   
   }
   
   public static void add(String name, JTable table, int row, int col){ //adds stuff to a givin column
      table.setValueAt(name,row,col);        
      table.repaint();
   }
   
   public static void removeStudent(String name, JTable table){ //removes a student from a table
        int index = 0;
        for(int i = 0; i < student.size(); i++){
            String rowName = (String)table.getValueAt(i,0);
            if(rowName.equals(name)){
                index = i;
                ((DefaultTableModel)table.getModel()).removeRow(index);
                break;
            }
            
        }
   }
   
   public static void removeTeacher(String name, JTable table){ //removes a teacher from a table
        int index = 0;
        for(int i = 0; i < teacher.size(); i++){
            String rowName = (String) table.getValueAt(i,0);
            if(rowName.equals(name)){
                index = i;
                ((DefaultTableModel)table.getModel()).removeRow(index);
                break;
            }
            
        }
   }
   
   public static void removeCheckout(String bid, JTable table){ //removes a checkout when book is returned
       int index = 0;
       for(int i = 0; i < checkedBID.size(); i++){
           String rowName = (String) table.getValueAt(i,2);
            if(rowName.equals(bid)){
                index = i;
                ((DefaultTableModel)table.getModel()).removeRow(index);
                break;
            }
           
       }
       
       
       
   }

   public static void insertStudent(){ //inserts students from the database into the set
       Insert app = new Insert();
       String[] nameList = app.getStudents();
       for(int i = 0; i < nameList.length; i++){
           student.add(nameList[i]);
           
       }
       
       
   }
   
   public static void insertTeacher(){ //inserts teachers from the database into the set
       Insert app = new Insert();
       String[] nameList = app.getTeachers();
       for(int i = 0; i < nameList.length; i++){
           teacher.add(nameList[i]);
 
       }    
   }
   
   public static void insertBooks(){ //inserts books from the database into the set
       Insert app = new Insert();
       String[][] nameList = app.getBooks();
       for(int i = 0; i < nameList.length; i++){
           books.add(nameList[i][0]);
           author.add(nameList[i][1]);
           bid.add(nameList[i][2]);
       } 
       
   
   }
   
   public static void insertSettings() throws SQLException{ //inserts settings from the database into the set
       Insert app = new Insert();
       int data[] = new int[4];
       data = app.getSettings(); 
       for(int i = 0; i < 4; i++){
           settings[i] = data[i];          
       }    
  
       
   }
   
   public static void checkedOut(){ //adds checked out books to the user interface
       Insert app = new Insert();
       String[][] nameList = app.getStudentCheckouts();
       for(int i = 0; i < nameList.length; i++){
           isReturned.add(nameList[i][3]);   
       }
       int j = 0;
       for(int i = 0; i < nameList.length; i++){
            if("0".equals(isReturned.get(i))){
                checkedSID.add(nameList[i][0]);
                checkedBID.add(nameList[i][1]);
                dueDate.add(nameList[i][2]);
                checkedName.add(app.getName(checkedSID.get(j)));
                checkedTitle.add(app.getTitle(checkedBID.get(j)));
                j++;
            }
         
       }
   
   } 
   
   public static void checkedOutTeacher(){ //adds books checked out by teachers to the user interface
       Insert app = new Insert();
       String[][] nameList = app.getTeacherCheckouts();
       for(int i = 0; i < nameList.length; i++){
           isReturnedTeacher.add(nameList[i][3]);  
       }
       int j = 0;
       for(int i = 0; i < nameList.length; i++){
            if("0".equals(isReturnedTeacher.get(i))){
                checkedTID.add(nameList[i][0]);
                checkedBIDTeacher.add(nameList[i][1]);
                dueDateTeacher.add(nameList[i][2]);
                checkedNameTeacher.add(app.getTeacherName(checkedTID.get(j)));
                checkedTitleTeacher.add(app.getTitle(checkedBIDTeacher.get(j)));
                j++;
            }
         
       }
   
   } 
     
   public static void overdueReport(){ //generates an overdue report
        File file = new File("Overdue_Book_Report.txt");
        ArrayList<String> people = new ArrayList<String>();
        ArrayList<String> overdue = new ArrayList<String>();
        
        ArrayList<String> peopleTeacher = new ArrayList<String>();
        ArrayList<String> overdueTeacher = new ArrayList<String>();
        try {
            PrintStream ps = new PrintStream(file);
            ps.println("Overdue Book Report");
            for(int i = 0; i < 55; i++){
                ps.print("=");
                
            }
            ps.println();
            ps.print("|");
            ps.printf("%11s","Name");
            ps.printf("%16s","|");
            ps.printf("%20s", "Due Date");
            ps.printf("%8s", "|\n");
            for(int i = 0; i < 55; i++){
                ps.print("=");
                
            } 
            ps.println();
            int index = 0;
            for(String i : dueDate){
                if(getDay(i) < getDay("current")){
                    index = dueDate.indexOf(i);
                    overdue.add(i);
                    people.add(checkedName.get(index));
                } 
            }
            index = 0;
            for(String i : dueDateTeacher){
                if(getDay(i) < getDay("current")){
                    index = dueDateTeacher.indexOf(i);
                    overdueTeacher.add(i);
                    peopleTeacher.add(checkedNameTeacher.get(index));
                } 
            }
            
            
            for(int i = 0; i < people.size(); i++){
                ps.print("| " + people.get(i));
                for(int j = 0; j < 25 - people.get(i).length(); j++){
                    ps.print(" ");
                }
                ps.print("|"+ overdue.get(i));
                for(int j = 0; j < 25 - overdue.get(i).length(); j++){
                    ps.print(" ");
                }
                ps.print("|");
                ps.println();
                
            }
            for(int i = 0; i < 55; i++){
                ps.print("=");
                
            } 
            ps.println();
            for(int i = 0; i < peopleTeacher.size(); i++){
                ps.print("| " + peopleTeacher.get(i));
                for(int j = 0; j < 25 - peopleTeacher.get(i).length(); j++){
                    ps.print(" ");
                }
                ps.print("|" + overdue.get(i));
                for(int j = 0; j < 25 - overdue.get(i).length(); j++){
                    ps.print(" ");
                }
            ps.print("|");
            ps.println();
                
            }
            for(int i = 0; i < 55; i++){
                ps.print("=");
                
            } 
            Desktop desktop = Desktop.getDesktop();
            if(file.exists()) desktop.open(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SqualicumDatabase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SqualicumDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
       
   }
   
   public static void weeklyReport(){ //generates a report of books checked out this week
        File file = new File("Weekly_Report.txt");
        try {
            PrintStream ps = new PrintStream(file);
            ps.println("Books Checked Out This Week That Haven't been returned yet");
            for(int i = 0; i < 55; i++){
                ps.print("=");
                
            }
            ps.println();
            ps.print("|");
            ps.printf("%11s","Name");
            ps.printf("%16s","|");
            ps.printf("%14s", "Book ID");
            ps.printf("%14s", "|\n");
            for(int i = 0; i < 55; i++){
                ps.print("=");
                
            } 
            ps.println();
            for(int i = 0; i < checkedName.size(); i++){
                if(getDay(dueDate.get(i))-settings[1] >= getDay("current")-7 ){
                    ps.print("| " + checkedName.get(i));
                    for(int j = 0; j < 25 - checkedName.get(i).length(); j++){
                        ps.print(" ");
                    }
                    ps.print("| " + checkedBID.get(i));
                    for(int j = 0; j < 25 - checkedBID.get(i).length(); j++){
                        ps.print(" ");
                    }
                    ps.print("|");
                    ps.println();
                }
                
            }
            for(int i = 0; i < 55; i++){
                ps.print("=");
                
            } 
            ps.println();
            for(int i = 0; i < checkedNameTeacher.size(); i++){
                if(getDay(dueDateTeacher.get(i))-settings[3] >= getDay("current")-7){
                    ps.print("| " + checkedNameTeacher.get(i));
                    for(int j = 0; j < 25 - checkedNameTeacher.get(i).length(); j++){
                        ps.print(" ");
                    }
                    ps.print("| " + checkedBIDTeacher.get(i));
                    for(int j = 0; j < 25 - checkedBIDTeacher.get(i).length(); j++){
                        ps.print(" ");
                    }
                    ps.print("|");
                    ps.println();
                }
                
            }
            for(int i = 0; i < 55; i++){
                ps.print("=");
                
            }
            Desktop desktop = Desktop.getDesktop();
            if(file.exists()) desktop.open(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SqualicumDatabase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SqualicumDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
       
   }
   
   public static int getDueDate(int monthDayYear, int SorT) { //gets the due date of a book

    Calendar date = Calendar.getInstance();
    
    if(monthDayYear == 0){
    
        return date.get(Calendar.YEAR);
    
    }else if(monthDayYear == 1 ){

        return date.get(Calendar.MONTH)+1;
    
    }else if(monthDayYear == 2 && SorT == 0){

        return date.get(Calendar.DAY_OF_MONTH) +settings[1];
    }else if(SorT == 1){
        return date.get(Calendar.DAY_OF_MONTH) + settings[3];
    }

    return -1;
}
   
   public static int getNumOfBooks(String person){ //gets the number of books a student or teacher has checked out
       int count = 0;
       for(String i : checkedName){
           if(i.equals(person)){
               count++;
           }
           
       }
       for(String i : checkedNameTeacher){
           if(i.equals(person)){
               count++;
           }
           
       }
       
       return count;
   }
    
   public static int getDay(String date){ //gets the day 
       Calendar cal = Calendar.getInstance();
       if("current".equals(date)){
           return cal.get(Calendar.DAY_OF_MONTH);
           
       }else{
           String[] parts = date.split("/");
           
           return Integer.parseInt(parts[1]);
           
       }
       
   }
   
   public static void main(String[] args) throws SQLException {
        connect(); 
        insertStudent();
        insertTeacher();
        insertBooks();
        insertSettings();
        checkedOut();
        checkedOutTeacher();
        new SqualicumDatabase();
    }
        
}

 
   