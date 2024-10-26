/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package generalinventorytracker_v01;

import javax.swing.*;
import java.io.*;

/**
 *General Inventory Tracker v1.0 is a  Desktop application which has the following basic operations :
 * -Loads inventory data from CSV.
 * -Allows you to add a record 
 * -Allows you to delete a record and save the inventory records.
 * 
 * NB: If you want to update records , you can find the CSV that keeps data and edit manually. 
 * @author Dominic Nzundah 
 */
public class GeneralInventoryTracker_v01 {


    /**
     * For running the app.
     * @param args the command line arguments
     * @throws java.io.IOException
     */  
    public static void main(String[] args) throws IOException{
        SwingUtilities.invokeLater(()->{
            RecordManager app=  new RecordManager();
            app.setVisible(true);
        } );    
    }//end main() 


    
}//end class 
