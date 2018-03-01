
package squalicumdatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



public class Insert {
    
    private Connection connect() { //connects to database
        // SQLite connection string
        String url = "jdbc:sqlite:UIbuild.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
 
    
    public void insertStudent(String name) { //inserts student into the database
        String sql = "INSERT INTO Students(name) VALUES(?)";
         
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void insertTeacher(String name) { //inserts teacher into the database
        String sql = "INSERT INTO Teachers(name) VALUES(?)";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void insertBook(String title, String author){ //inserts books into the database
        String sql = "INSERT INTO Books(title, author) VALUES(?,?)";
        
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        
    }
    
    public void insertsbl(String setting, int oldSetting){ //inserts student book limit into the database
        String sql = "UPDATE settings SET studentBookLim = ? WHERE studentBookLim = ?";
        
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, setting);  
            pstmt.setString(2, String.valueOf(oldSetting));
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        
    }
    
    public void insertstl(String setting, int oldSetting){ //inserts student time limit into the database
        String sql = "UPDATE settings SET studentTimeLim = ? WHERE studentTimeLim = ?";
        
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {       
            pstmt.setString(1, setting);
            pstmt.setString(2, String.valueOf(oldSetting));
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        
    }
    
    public void inserttbl(String setting, int oldSetting){ //inserts teacher book limit into the database
        String sql = "UPDATE settings SET teacherBookLim = ? WHERE teacherBookLim = ?";
        
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, setting);
            pstmt.setString(2, String.valueOf(oldSetting));
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        
    }
    
    public void insertttl(String setting, int oldSetting){ //inserts teacher time limit into the database
        String sql = "UPDATE settings SET teacherTimeLim = ? WHERE teacherTimeLim = ?";
        
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, setting);
            pstmt.setString(2, String.valueOf(oldSetting));
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        
    }
    
    public String[] getStudents(){ //gets student from database
        String sql = "SELECT name, sid FROM students";
        int count = 0;
        ArrayList<String> students = new ArrayList<String>();
        try (Connection conn = this.connect()){
                Statement sqlState = conn.createStatement();
                ResultSet row = sqlState.executeQuery(sql);
                while(row.next()){
                    students.add(row.getString("name"));
     
                }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } 
        String[] nameList = new String[students.size()];
        int j = 0;
        for(String i : students){
            nameList[j] = i;
            j++;
        }
        j = 0;
        
        return nameList;
    }
    
    public String[] getTeachers(){ //gets teachers from database
        String sql = "SELECT name,tid FROM teachers"; 
        ArrayList<String> teachers = new ArrayList<String>();

        try (Connection conn = this.connect()){
                Statement sqlState = conn.createStatement();
                ResultSet row = sqlState.executeQuery(sql);
                while(row.next()){
                    teachers.add(row.getString("name"));
              
                }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } 
        String[] nameList = new String[teachers.size()];
        int j = 0;
        for(String i : teachers){
            nameList[j] = i;
            j++;
        }
        j = 0;
       
        return nameList;
        
    }
        
    public String[][] getBooks(){ //gets books from database
        String sql = "SELECT bid,title,author FROM books"; 
        ArrayList<String> books = new ArrayList<String>();
        ArrayList<String> author = new ArrayList<String>();
        ArrayList<String> bid = new ArrayList<String>();
        
        try (Connection conn = this.connect()){
                Statement sqlState = conn.createStatement();
                ResultSet row = sqlState.executeQuery(sql);
                while(row.next()){
                    books.add(row.getString("title"));
                    author.add(row.getString("author"));
                    bid.add(row.getString("bid"));
                }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } 
        String[][] nameList = new String[books.size()][3];
        int j = 0;
        for(String i : books){
            nameList[j][0] = i;
            j++;
        }
        j = 0;
        for(String i : author){
           nameList[j][1] = i;
            j++; 
        }
        j = 0;
        for(String i : bid){
           nameList[j][2] = i;
            j++; 
        }
        return nameList;
         
        
        
    }
    
    public String[][] getStudentCheckouts(){ //gets student checkouts from database
        String sql = "SELECT bid,sid,DueDate,IsReturned FROM studentCheckouts"; 
        ArrayList<String> sid = new ArrayList<String>();
        ArrayList<String> bid = new ArrayList<String>();
        ArrayList<String> dueDate = new ArrayList<String>();
        ArrayList<String> isReturned = new ArrayList<String>();
        
        try (Connection conn = this.connect()){
                Statement sqlState = conn.createStatement();
                ResultSet row = sqlState.executeQuery(sql);
                while(row.next()){
                    sid.add(row.getString("SID"));
                    bid.add(row.getString("BID"));
                    dueDate.add(row.getString("DueDate"));
                    isReturned.add(row.getString("IsReturned"));
                }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
                 
        String[][] nameList = new String[sid.size()][4];
        int j = 0;
        for(String i : sid){
            nameList[j][0] = i;
            j++;
        }
        j = 0;
        for(String i : bid){
           nameList[j][1] = i;
            j++; 
        }
        j = 0;
        for(String i : dueDate){
           nameList[j][2] = i;
            j++; 
        }
        j = 0;
        for(String i : isReturned){
            nameList[j][3] = i;
            j++;
        }
        return nameList; 
        
        
    }
    
   public String[][] getTeacherCheckouts(){ //gets teacher checkouts from database
        String sql = "SELECT tid,bid,DueDate,IsReturned FROM teacherCheckouts"; 
        ArrayList<String> tid = new ArrayList<String>();
        ArrayList<String> bid = new ArrayList<String>();
        ArrayList<String> dueDate = new ArrayList<String>();
        ArrayList<String> isReturned = new ArrayList<String>();
        
        try (Connection conn = this.connect()){
                Statement sqlState = conn.createStatement();
                ResultSet row = sqlState.executeQuery(sql);
                while(row.next()){
                    tid.add(row.getString("TID"));
                    bid.add(row.getString("BID"));
                    dueDate.add(row.getString("DueDate"));
                    isReturned.add(row.getString("IsReturned"));
                }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
                 
        String[][] nameList = new String[tid.size()][4];
        int j = 0;
        for(String i : tid){
            nameList[j][0] = i;
            j++;
        }
        j = 0;
        for(String i : bid){
           nameList[j][1] = i;
            j++; 
        }
        j = 0;
        for(String i : dueDate){
           nameList[j][2] = i;
            j++; 
        }
        j = 0;
        for(String i : isReturned){
            nameList[j][3] = i;
            j++;
        }
        return nameList; 
        
        
    }
    
    public int[] getSettings() throws SQLException{ //gets settings from database
        String sql = "SELECT studentBookLim,studentTimeLim,teacherBookLim, teacherTimeLim FROM settings";
        int[] data = new int[4];
        
         try (Connection conn = this.connect()){
                Statement sqlState = conn.createStatement();
                ResultSet row = sqlState.executeQuery(sql);
                
                data[0] = Integer.parseInt(row.getString("studentBookLim"));
                data[1] = Integer.parseInt(row.getString("studentTimeLim"));
                data[2] = Integer.parseInt(row.getString("teacherBookLim"));
                data[3] = Integer.parseInt(row.getString("teacherTimeLim"));
                
            return data;
         }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } 
         
        return data;
        
    }
    
    public void editStudent(String name, String newName){ //edits student name
        String sql = "UPDATE Students SET name = ? WHERE name = ?";
        
         try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newName);
            pstmt.setString(2, name);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    public void editTeacher(String name, String newName){ //edits teacher name
        String sql = "UPDATE Teachers SET name = ? WHERE name = ?";
        
         try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newName);
            pstmt.setString(2, name);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    
    public void checkoutStudent(String name, String BID, String date){ //checks out a student
        String sql = "INSERT INTO studentCheckouts(BID, SID, DueDate, IsReturned ) VALUES(?, ?, ?, 0)";
        String sql1 = "SELECT SID, name FROM students";
        String sql2 = "SELECT studentTimeLim FROM settings";
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, BID);
            String SID = "";   
            try (Connection con = this.connect()){
                Statement sqlState = con.createStatement();
                ResultSet row = sqlState.executeQuery(sql1);
                while(row.next()){
                    if(row.getString("name").equals(name)){
                        SID = row.getString("SID");
                        break;
                    }
                }
                con.close();
                
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }    
            pstmt.setString(2, SID);
            pstmt.setString(3,date);
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    public void checkoutTeacher(String name, String BID, String date){ //checks out a teacher
        String sql = "INSERT INTO teacherCheckouts(BID, TID, DueDate, IsReturned ) VALUES(?, ?, ?, 0)";
        String sql1 = "SELECT TID, name FROM teachers";
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, BID);
            String TID = "";
            try (Connection con = this.connect()){
                Statement sqlState = con.createStatement();
                ResultSet row = sqlState.executeQuery(sql1);
                while(row.next()){
                    if(row.getString("name").equals(name)){
                        TID = row.getString("TID");
                        break;
                    }
                }
                con.close();
                
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }    
            pstmt.setString(2, TID);
            pstmt.setString(3, date);
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
 
    public void returnStudent(String BID){ //returns a book from a student
        String sql = "UPDATE studentCheckouts SET IsReturned = 1 WHERE BID = ?";
        
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, BID);  
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    public void returnTeacher(String BID){ //returns a book from the teacher
        String sql = "UPDATE teacherCheckouts SET IsReturned = 1 WHERE BID = ?";
        
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, BID);  
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    public String getName(String sid){ //gets student name from a given id
        String sql = "SELECT name, sid FROM Students"; 
        String name = "";
        try (Connection conn = this.connect()){
                Statement sqlState = conn.createStatement();
                ResultSet row = sqlState.executeQuery(sql);
                while(row.next()){
                    if(sid.equals(row.getString("SID"))){
                        name = row.getString("name");
                        break;
                        
                    }
                    
                 
              
                }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } 
        return name;
        
        
    }
    
    public String getTeacherName(String tid){ //gets teacher name from a given id
        String sql = "SELECT name, tid FROM Teachers"; 
        String name = "";
        try (Connection conn = this.connect()){
                Statement sqlState = conn.createStatement();
                ResultSet row = sqlState.executeQuery(sql);
                while(row.next()){
                    if(tid.equals(row.getString("TID"))){
                        name = row.getString("name");
                        break;
                        
                    }
                    
                 
              
                }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } 
        return name;
        
        
    }
    
    public String getTitle(String bid){ //gets title from a given id
        String sql = "SELECT title, bid FROM Books"; 
        String name = "";
        try (Connection conn = this.connect()){
                Statement sqlState = conn.createStatement();
                ResultSet row = sqlState.executeQuery(sql);
                while(row.next()){
                    if(bid.equals(row.getString("BID"))){
                        name = row.getString("title");
                        break;
                        
                    }

                }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } 
        return name;
        
        }
    
}

