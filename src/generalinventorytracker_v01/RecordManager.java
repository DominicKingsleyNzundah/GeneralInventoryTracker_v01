package generalinventorytracker_v01;
    import javax.swing.*;
    import javax.swing.table.DefaultTableModel;
    import java.io.*;
    import java.awt.event.ActionEvent;
    import com.opencsv.CSVReader;// to be used for reading the data from CSV file 
    import com.opencsv.CSVWriter;// for writing data into CSV 
    import com.opencsv.exceptions.CsvException;// gets predefined CSV exceptions 
    import java.awt.BorderLayout;
    import java.util.List;

/**
 *
 * @author Dominic Nzundah
 */
public class RecordManager extends JFrame{
    //fields    
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField itemField,quantityField;
    private static final String CSV_FILE="inventory.csv";  
    
    //methods + constructor(s)
    public RecordManager(){
        setTitle("General Inventory Tracker v1.0");
        setSize(1200,800);    
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
        //make a table 
        String[] columns={"Item","Quantity"};
        tableModel= new DefaultTableModel(columns,0);
        table=new JTable(tableModel); 
        
        JScrollPane scrollPane=new JScrollPane(table);
        
        //instanciating input fields 
        itemField= new JTextField(20);
        quantityField=new JTextField(10);
       
        //buttons  for actions 
        JButton addButton= new JButton("Add Item");
        JButton deleteButton= new JButton("Delete Selected row");
        JButton saveButton= new JButton("Save Data");
        JButton loadButton= new JButton("Load Data");
        
        // action event listeners 
        addButton.addActionListener(this::addItem);
        deleteButton.addActionListener(this::deleteItem);
        saveButton.addActionListener(this::saveToCSV);
        loadButton.addActionListener(this::loadFromCSV);        
        
        //panels 
        JPanel inputPanel=new JPanel();
        inputPanel.add(new JLabel("Item:"));
        inputPanel.add(itemField);
        inputPanel.add(new JLabel("Quantity:"));
        inputPanel.add(quantityField);
        inputPanel.add(addButton);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(deleteButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        
        //adding components to the frame 
        setLayout(new BorderLayout());
        add(scrollPane,BorderLayout.CENTER);
        add(inputPanel,BorderLayout.NORTH);
        add(buttonPanel,BorderLayout.SOUTH); 
    }//end constructor() 
    
    /**
    Adds action event for adding an item 
    @param:e
    @return:none
    */
    private void addItem(ActionEvent e){        
        String item=itemField.getText();
        String quantity=quantityField.getText();      
        
        if(!item.isEmpty()&& !quantity.isEmpty()){
            tableModel.addRow(new Object[]{item,quantity});
            itemField.setText("");
            quantityField.setText("");
        }
    }//end addItem() 
    
    /**
    Adds action event for deleting a row  
    @param:e
    @return:none
    */
    private void deleteItem(ActionEvent e){
        int selectedRow=table.getSelectedRow();        
        if(selectedRow !=-1){
            tableModel.removeRow(selectedRow);
        }
    }//end deleteItem() 
    
    /**
    Adds action event for saving to CSV
    @param:e
    @return:none
    */
    private void saveToCSV(ActionEvent e){
        try(CSVWriter writer = new CSVWriter(new FileWriter(CSV_FILE,false)))
        {
            for(int i=0;i<tableModel.getRowCount();i++){
                String[] row={(String)  tableModel.getValueAt(i,0),
                (String)  tableModel.getValueAt(i,1)} ;  
                writer.writeNext(row);             
            }
            JOptionPane.showMessageDialog(this, "Inventory saved to "+CSV_FILE);
        }
        catch(IOException ex)
        {
            JOptionPane.showMessageDialog(this, "Error while saving to "+CSV_FILE+ex.getMessage());
        }
    }//end saveToCSV() 
    
    /**
    Adds action event for loading from CSV
    @param:e
    @return:none
    */     
        private void loadFromCSV(ActionEvent e){
            File file = new File(CSV_FILE);
            if (!file.exists()) {
                JOptionPane.showMessageDialog(this, 
                    "File not found: " + file.getAbsolutePath(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            try (CSVReader reader = new CSVReader(new FileReader(file))){
                System.out.println("Reading from: " + file.getAbsolutePath());
                List<String[]> rows = reader.readAll();
                System.out.println("Number of rows read: " + rows.size());
                
                tableModel.setRowCount(0);
                for (String[] row : rows) {
                    System.out.println("Adding row: " + String.join(",", row));
                    tableModel.addRow(row);
                }
                JOptionPane.showMessageDialog(this, "Inventory loaded from "+CSV_FILE);
            } catch (IOException ex) {
                ex.printStackTrace(); 
                JOptionPane.showMessageDialog(this, 
                    "Error reading file: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            } catch (CsvException ex) {
                ex.printStackTrace(); 
                JOptionPane.showMessageDialog(this, 
                    "Error parsing CSV: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }//()end new loadFromCSV    
    
}
