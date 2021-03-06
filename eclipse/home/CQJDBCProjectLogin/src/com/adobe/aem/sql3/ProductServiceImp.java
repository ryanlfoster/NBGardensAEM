package com.adobe.aem.sql3;
  
  
import com.adobe.aem.sql3.ConnectionHelper;
import com.adobe.aem.sql3.Product ; 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
  
import java.util.List;
import java.util.ArrayList;
  
import org.w3c.dom.Document;
import org.w3c.dom.Element;
   
import java.io.StringWriter;
import java.util.Iterator;
  
  
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
  
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
   
 
public class ProductServiceImp implements ProductService {
  
    //Adds a new Product record in the Product table
    @Override
    public int injestProdData(String id,String name, String type, String qty, String desc, String price) {
        Connection c = null;
          
        int rowCount= 0; 
        try {
                                     
              // Create a Connection object
              c =  ConnectionHelper.getConnection();
            
               ResultSet rs = null;
               Statement s = c.createStatement();
               Statement scount = c.createStatement();
                  
               //Use prepared statements to protected against SQL injection attacks
               PreparedStatement pstmt = null;
               PreparedStatement ps = null; 
                            
               //Set the query and use a preparedStatement
               String query = "Select * FROM Product";
               pstmt = c.prepareStatement(query); 
               rs = pstmt.executeQuery();
                  
               while (rs.next()) 
                       rowCount++;
                               
               //Set the PK value
             //Set the PK value
               int pkVal = Integer.parseInt(id); 
                  
               String insert = "INSERT INTO Product(prodId,prodName,prodType,prodDescription,prodQty,prodPrice) VALUES(?, ?, ?, ?, ?, ?);";
               ps = c.prepareStatement(insert);
               ps.setInt(1, pkVal);
               ps.setString(2, name);
               ps.setString(3, type);
               ps.setString(4, desc);
               ps.setString(5, qty);
               ps.setString(6, price);
               ps.execute();
               return pkVal;
        }
        catch (Exception e) {
          e.printStackTrace();
         }
        finally {
          ConnectionHelper.close(c);
    }
        return 0; 
 }
}