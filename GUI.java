	import javax.swing.*;
	import java.awt.event.*;
	import java.io.File;
	import java.util.*;
	import java.sql.Timestamp;

public class GUI extends JFrame {
		private static final long serialVersionUID = 1L;
		//input panel
		private JPanel inputPane;		
		//Text fields to be accessed by GUI methods
		private JTextField openDirTextField;		
		//Buttons to be accessed by GUI methods
		private JButton openDirButton;
		private JButton clearButton;
		private JButton runButton;		
		//Panes
		private JPanel basePane;
		private JTextArea errorDisplay;
		private JTextArea resultsDisplay;
		//Directory Selector
		private JFileChooser dirSelector;
		
		public GUI() {
			super("Ultra Product Parser");
			setResizable(false);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 700, 500);
			
			// Base Pane
			basePane = new JPanel();
			setContentPane(basePane);
			basePane.setLayout(null);
			
			// Input Pane for buttons and text fields
			inputPane = new JPanel();		
			inputPane.setBounds(10, 10, 674, 170);
			basePane.add(inputPane);
			inputPane.setLayout(null);
			
			// Labels
			JLabel openDirLabel = new JLabel("Selected Directory: ");
			openDirLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			openDirLabel.setBounds(16, 11, 140, 14);
			inputPane.add(openDirLabel);
			
			JLabel errorLabel = new JLabel("Error Messages: ");
			errorLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			errorLabel.setBounds(16, 56, 140, 14);
			inputPane.add(errorLabel);
			
			JLabel resultLabel = new JLabel("Results: ");
			resultLabel.setHorizontalAlignment(SwingConstants.LEFT);
			resultLabel.setBounds(0, 155, 140, 14);
			inputPane.add(resultLabel);
			
			//TextField							
			openDirTextField = new JTextField();
			openDirTextField.setBounds(160, 8, 350, 20);
			openDirTextField.setEditable(false);
			inputPane.add(openDirTextField);
			openDirTextField.setColumns(15);	
						
			//Buttons
			openDirButton = new JButton("Open Directory");
			openDirButton.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					selectDirecory();
				}
			});			
			openDirButton.setBounds(540, 11, 125, 28);
			inputPane.add(openDirButton);

			clearButton = new JButton("Clear");
			clearButton.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					clearFields();
				}
			});
			clearButton.setBounds(540, 51, 125, 28);
			inputPane.add(clearButton);
			
			runButton = new JButton("Run");
			runButton.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					run();
				}
			});			
			runButton.setBounds(540, 110, 125, 28);
			inputPane.add(runButton);
			
			//Error Text Area
			JScrollPane errorPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			errorPane.setBounds(160, 50, 350, 90);
			inputPane.add(errorPane);
			errorDisplay = new JTextArea();
			errorPane.setViewportView(errorDisplay);
			
			//Results Text Area
			JScrollPane resultPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			resultPane.setBounds(10, 185, 674, 265);
			basePane.add(resultPane);
			resultsDisplay = new JTextArea();
			resultPane.setViewportView(resultsDisplay);
						
			setLocationRelativeTo(null);
			setVisible(true);
		}
		
		//Select a directory
		public void selectDirecory ()	{
			//Get Time
			Date date= new Date();
		    dirSelector = new JFileChooser(); 
		    dirSelector.setCurrentDirectory(new File("."));
		    dirSelector.setDialogTitle("Select folder with JSON/XML files");
		    //Directories Only
		    dirSelector.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		    dirSelector.setAcceptAllFileFilterUsed(false);
		    //Display selected folder
		    if (dirSelector.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) 
		    	openDirTextField.setText("" + dirSelector.getSelectedFile());
		    else 
		      errorDisplay.append(new Timestamp(date.getTime()) + ": Invalid Selection. See log.\n");
		}
		
		//Run parser
		public void run ()	{
	        ProductParser pp = new ProductParser();
	        File file;
	        Boolean success;
	        String dirPath = openDirTextField.getText();
	        Date date= new Date();

	        //This parses the sample files in the src directory.
	        ArrayList<Product> products = pp.parseDirectory(dirPath);

	        //uncomment the two System.out lines for debugging - delete for final version
	        //System.out.printf("Number of products parsed: %s\n", products.size());

	        for(Product product:products) {
	            resultsDisplay.append("" + product.getName() + " " + product.getId() + "\n");
	        }

	        //This outputs to the root of the project folder (ItemParser/ in my case)
	        System.out.println(dirPath);
	        file = new File(dirPath + "\\output.json");

	        success = pp.writeProductsToFile(products, file);
	        if (success) {
	        	errorDisplay.append(new Timestamp(date.getTime()) + ": Success!\n");
	        }
	        //System.out.printf("Did the writer succeed? %s.\n", (success ? "Yes" : "No"));
		}
		
		//Clear all fields for user convenience
		public void clearFields()	{
			errorDisplay.setText("");
			resultsDisplay.setText("");
		}
	}

