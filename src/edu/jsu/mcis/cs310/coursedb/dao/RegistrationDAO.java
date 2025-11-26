package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import com.github.cliftonlabs.json_simple.*;



public class RegistrationDAO {
    
    private final DAOFactory daoFactory;   
    private final String QUERYINSERT = "INSERT INTO registration (studentid, termid, crn) VALUES (?, ?, ?)";
    private final String QUERYDELETE = "DELETE FROM registration WHERE studentid = ? AND termid = ? AND crn = ?";
    private final String QUERYDELETEALL = "DELETE FROM registration WHERE termid = ? AND studentid = ?";    
    private final String QUERYSELECT = "SELECT * FROM registration WHERE studentid = ? AND termid = ? ORDER BY crn";
    
    RegistrationDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    public boolean create(int studentid, int termid, int crn) {
        
        boolean result = false;
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                // INSERT YOUR CODE HERE
                ps = conn.prepareStatement(QUERYINSERT);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                ps.setInt(3, crn);

                //executing the update
                int rowsAffected = ps.executeUpdate();
                
                result = (rowsAffected > 0);
                
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {
            
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }

    public boolean delete(int studentid, int termid, int crn) {
        
        boolean result = false;
        
        PreparedStatement ps = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                // INSERT YOUR CODE HERE
                 ps = conn.prepareStatement(QUERYDELETE);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                ps.setInt(3, crn);

                //execute the update
                int rowsAffected = ps.executeUpdate();
                
                result = (rowsAffected > 0);
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {

            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }
    
    public boolean delete(int studentid, int termid) {
        
        boolean result = false;
        
        PreparedStatement ps = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                // INSERT YOUR CODE HERE
                ps = conn.prepareStatement(QUERYDELETEALL);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                
                int rowsAffected = ps.executeUpdate();
                
                result = (rowsAffected > 0);
                
                
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {

            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }

    public String list(int studentid, int termid) {
        
        String result = null;
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        
        try {
            Connection conn = daoFactory.getConnection(); 
            if (conn.isValid(0)) {
                //prepare statement
                ps = conn.prepareStatement(QUERYSELECT);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                Boolean gotResult = ps.execute();
                
                if(gotResult){
                    rs = ps.getResultSet();
                    JsonArray resultArray = new JsonArray();
                    JsonObject stuID = new JsonObject();
                    JsonObject termID = new JsonObject();     
                    while(rs.next()){
                        stuID.put("studentid", rs.getInt(studentid));
                        termID.put("termid", rs.getInt(termid));
                        resultArray.add(stuID);
                        resultArray.add(termID);
                        result = resultArray.toString();
                    }
                }
            }      
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {
            
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            
            
        }
        
        return result;
        
    }
}