import static java.nio.file.StandardOpenOption.READ;
import static java.nio.file.StandardOpenOption.WRITE;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.stage.Stage;

public class AddressBook extends Application {

	private TextField tfName = new TextField();
	private TextField tfStreet = new TextField();
	private TextField tfCity = new TextField();
	private TextField tfState = new TextField();
	private TextField tfZip = new TextField();
	private Button btAdd = new Button("Add");
	private Button btFirst = new Button("First");
	private Button btNext = new Button("Next");
	private Button btPrev = new Button("Previous");
	private Button btLast = new Button("Last");
	private Button btUpdt = new Button("Update");
	
	public static String temp = null; 

	@Override // Override the start method in the Application class
	public void start(Stage primaryStage) {
		// Create UI
		HBox hBox1 = new HBox(5);
		HBox hBox2 = new HBox(5);
		HBox hBox3 = new HBox(5);
		HBox hBox4 = new HBox(5);
		VBox vBox = new VBox(15);
		
		hBox1.getChildren().addAll(new Label("Name "), tfName);
		hBox2.getChildren().addAll(new Label("Street "), tfStreet);
		hBox3.getChildren().addAll(new Label("City "), tfCity, new Label("State "), tfState, new Label("Zip "), tfZip);
		hBox4.getChildren().addAll(btAdd, btFirst, btNext, btPrev, btLast, btUpdt);
		vBox.getChildren().addAll(hBox1, hBox2, hBox3, hBox4);
		
		// Set UI properties
		hBox1.setAlignment(Pos.BASELINE_LEFT);
		hBox2.setAlignment(Pos.BASELINE_LEFT);
		hBox3.setAlignment(Pos.BASELINE_LEFT);
		hBox4.setAlignment(Pos.CENTER);
		tfName.setPrefColumnCount(35);
		tfStreet.setPrefColumnCount(35);
		tfCity.setPrefColumnCount(20);
		tfState.setPrefColumnCount(2);
		tfZip.setPrefColumnCount(5);
		vBox.setPadding(new Insets(2, 5, 0, 0));
		
		// Process events
		btAdd.setOnAction(e -> add());
		btFirst.setOnAction(e -> firstone());
		btNext.setOnAction(e -> nextone());
		btPrev.setOnAction(e -> prevone());
		btLast.setOnAction(e -> lastone());
		btUpdt.setOnAction(e -> update());
	
		// Create a scene and place it in the stage
		Scene scene = new Scene(vBox);
		primaryStage.setTitle("Address Book"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage
		
		
		/////////////////////////////////////////////////////////////////
		//Initialize with Data inside File
		
		Path file = Paths.get("src/Address.dat");

		try (FileChannel fc = (FileChannel.open(file, READ, WRITE))) {
			
			int nread;
		    ByteBuffer first_one = ByteBuffer.allocate(92);
		    do {
		        nread = fc.read(first_one);
		        //System.out.println(new String(copy.array()));
		        
		    } while (nread != -1 && first_one.hasRemaining());
		    
		 String initialize = new String(first_one.array());
		 
		temp = initialize;
		
		 tfName.setText(initialize.substring(0, 32).trim());
		 tfStreet.setText(initialize.substring(32, 64).trim());		 
		 tfCity.setText(initialize.substring(64, 84).trim());
		 tfState.setText(initialize.substring(84, 86).trim());
		 tfZip.setText(initialize.substring(86, 91).trim());		 
		 
		} catch (IOException x) {
		    System.out.println("I/O Exception: " + x);
		}
		
		///////////////////////////////////////////////////////////////////
		
	}
	
	//Support Methods////////////////////////////////////////////////////////////////
	public static int file_counter(String filename) {
		
        FileReader fileReader = null;
        int counter = 0;

        try {
            // FileReader reads text files in the default encoding.
            fileReader = new FileReader(filename);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while(bufferedReader.readLine() != null) {
                counter++;
            }  
            
            bufferedReader.close();  

        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                filename + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + filename + "'");                  
        }

    
        return counter;				
	}
	
	
	public static int find_line(String filename, String add) {
		
		String file_line= null;
        FileReader fileReader = null;
        int counter = 0, line = 0;

        try {
            // FileReader reads text files in the default encoding.
            fileReader = new FileReader(filename);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((file_line = bufferedReader.readLine()) != null) {
                counter++;
                	if (add.substring(0, 91).equals(file_line))
                	{
                		line = counter;
                	}
                		
            }  
            
            bufferedReader.close();  

        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                filename + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + filename + "'");                  
        }

    
        return line;				
	}
	
	
	public static boolean find_addr(String filename, String add) {
		
		String file_line= null;
        FileReader fileReader = null;
        boolean flag = false;

        try {
            // FileReader reads text files in the default encoding.
            fileReader = new FileReader(filename);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((file_line = bufferedReader.readLine()) != null) {
                	if (add.substring(0, 91).equals(file_line))
                	{
                		//System.out.println(flag);
                		flag = true;
                		
                	}
            }  
            
            bufferedReader.close();  

        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                filename + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + filename + "'");                  
        }

    
        return flag;				
	}	
	
	public static String find_nextone(String filename, int line) {
		
		String nextone= null;
		String file_line= null;
        FileReader fileReader = null;
        int counter = 0;

        try {
            // FileReader reads text files in the default encoding.
            fileReader = new FileReader(filename);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((file_line = bufferedReader.readLine()) != null) {
                counter++;
                	if (counter == line + 1)
                	{
                		nextone = file_line;
                	}
                		
            }  
            
            bufferedReader.close();  

        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                filename + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + filename + "'");                  
        }   
        return nextone;		
		
	}
		
	public static String find_prevone(String filename, int line) {
		
		String prevone= null;
		String file_line= null;
        FileReader fileReader = null;
        int counter = 0;

        try {
            // FileReader reads text files in the default encoding.
            fileReader = new FileReader(filename);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((file_line = bufferedReader.readLine()) != null) {
                counter++;
                	if (counter == line - 1)
                	{
                		prevone = file_line;
                	}
                		
            }  
            
            bufferedReader.close();  

        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                filename + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + filename + "'");                  
        }
   
        return prevone;				
	}
	
	private String getData() {
		
		String s = tfName.getText();		
		String s1 = tfStreet.getText();			
		String s2 = tfCity.getText();			
		String s3 = tfState.getText();			 			
		String s4 = tfZip.getText();
		 
		String sf_n = null;
		String sf_a = null;
		String sf_c = null;
		String sf_s = null;
		String sf_z = null;
							
		//Fix Name string
		if (s.length() < 32)
			sf_n = String.format("%-32s", s);
		
		else 
			sf_n = s.substring(0, 32);
		
		//Fix Addres string
		if (s1.length() < 32)
			sf_a = String.format("%-32s", s1);
		
		else 
			sf_a = s1.substring(0, 32);
		
		//Fix City string
		if (s2.length() < 20)
			sf_c = String.format("%-20s", s2);
		
		else 
			sf_c = s2.substring(0, 20);
		
		//Fix State string
		if (s3.length() < 2)
			sf_s = String.format("%-2s", s3);
		
		else 
			sf_s = s3.substring(0, 2);
		
		//Fix Zip string
		if (s4.length() < 5)
			sf_z = String.format("%-5s", s4);
		
		else 
			sf_z = s4.substring(0, 5);
		
		String sf = sf_n + sf_a + sf_c + sf_s + sf_z + System.lineSeparator();
		
		return sf;
		
	}
	
	private void inputData (String addr) {
	    
			tfName.setText(addr.substring(0, 32).trim());
			tfStreet.setText(addr.substring(32, 64).trim());		 
			tfCity.setText(addr.substring(64, 84).trim());
			tfState.setText(addr.substring(84, 86).trim());
			tfZip.setText(addr.substring(86, 91).trim());		 
	}
	
	public boolean validate() {
		
		String s = tfName.getText();		
		String s1 = tfStreet.getText();			
		String s2 = tfCity.getText();			
		String s3 = tfState.getText();			 			
		String s4 = tfZip.getText();
		
		if (s.length() > 32 )
		{
				JOptionPane.showMessageDialog(null, "Only 32 Characters for Name");
				return false;
		}
		else
		{
			if (s1.length() > 32 )
			{
					JOptionPane.showMessageDialog(null, "Only 32 Characters for Street");
					return false;
			}
			
			else
			{
				if (s2.length() > 20 )
				{
						JOptionPane.showMessageDialog(null, "Only 20 Characters for City");
						return false;
				}
				
				else
				{
					if (s3.length() > 2 )
					{
							JOptionPane.showMessageDialog(null, "Only 2 Characters for State");
							return false;
					}
					
					else
					{
						if (s4.length() > 5 )
						{
								JOptionPane.showMessageDialog(null, "Only 5 Characters for Zip");
								return false;
						}
						
						else return true;
												
					}
					
				}
			}
		}
			
	
	}
	
	// Add //////////////////////////////////////////////////////////////////////////////
		private void add() {
			
			
			if(validate())
			{
			
			String sf = getData();	

			if (!(find_addr("src/Address.dat", sf.substring(0, 93)) || sf.substring(0, 92).trim().equals("")))
			{
			 
				byte dataf[] = sf.getBytes();
				ByteBuffer outf = ByteBuffer.wrap(dataf);
			
				Path file = Paths.get("src/Address.dat");

				try (FileChannel fc = (FileChannel.open(file, READ, WRITE))) {
				
					long length = fc.size();
					fc.position(length);
					while (outf.hasRemaining())
						fc.write(outf);
				} catch (IOException x) {
					System.out.println("I/O Exception: " + x);
				}
			}
			
			temp = sf;
			
			System.out.println("Add");
			}
		}
		
	//First One //////////////////////////////////////////////////
		private void firstone() {
			
				
			ByteBuffer first = ByteBuffer.allocate(92);
			int nread;
			
			Path file = Paths.get("src/Address.dat");

			try (FileChannel fc = (FileChannel.open(file, READ, WRITE))) {
				
				do {
			        nread = fc.read(first);
			        //System.out.println(new String(copy.array()));
			        
			    } while (nread != -1 && first.hasRemaining());
				
				inputData(new String(first.array()));
				
				temp = new String(first.array());
				
			} catch (IOException x) {
				System.out.println("I/O Exception: " + x);
			}
			
			System.out.println("First One");
		}
						

	// NextOne /////////////////////////////////////////////////////////////////////////
		private void nextone() {
			
			String sf = getData();
			int line_teste = find_line("src/Address.dat", sf);
			int totalfile = file_counter("src/Address.dat");
			
			if (line_teste != totalfile)
			{
				inputData(find_nextone("src/Address.dat", line_teste));
				temp = new String(find_nextone("src/Address.dat", line_teste));
			}
			
			System.out.println("Next One");
		}
		
	// PrevOne /////////////////////////////////////////////////////////////////////////////
		private void prevone() {
			
			String sf = getData();
			int line_teste = find_line("src/Address.dat", sf);
			
			if (line_teste != 1)
			{
				inputData(find_prevone("src/Address.dat", line_teste));
				temp = new String(find_prevone("src/Address.dat", line_teste));
			}
			System.out.println("Prev One");
		}
	// Last One /////////////////////////////////////////////////////////////////
		private void lastone() {
			
			ByteBuffer last = ByteBuffer.allocate(92);
			int nread;
			
			Path file = Paths.get("src/Address.dat");

			try (FileChannel fc = (FileChannel.open(file, READ, WRITE))) {
				
				long length = fc.size();	    
			    fc.position(length-93);
				
				do {
			        nread = fc.read(last);
			        //System.out.println(new String(copy.array()));
			        
			    } while (nread != -1 && last.hasRemaining());
				
				
				inputData(new String(last.array()));
				temp = new String(last.array());
				
			} catch (IOException x) {
				System.out.println("I/O Exception: " + x);
			}
			
			System.out.println("Last One");
		}
	// Update /////////////////////////////////////
		private void update() {
			
			if(validate())
			{
			
			String sf = getData();
				
			int line_teste = find_line("src/Address.dat", temp);
				 
			byte dataf[] = sf.getBytes();
			ByteBuffer outf = ByteBuffer.wrap(dataf);
			
			Path file = Paths.get("src/Address.dat");

			try (FileChannel fc = (FileChannel.open(file, READ, WRITE))) {
				
				fc.position((line_teste-1)*93);
				while (outf.hasRemaining())
					fc.write(outf);
			} catch (IOException x) {
				System.out.println("I/O Exception: " + x);
			}
							
			temp = sf;
						
			System.out.println("Update");
			}
		}
		
	
	public static void main(String[] args)
	{

	Application.launch(args);
	}
	
}
