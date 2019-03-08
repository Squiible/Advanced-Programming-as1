package UI;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;

import main_code.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.awt.event.*;
public class UI {

   //set JFrame width/height to integer
    private static final int FRAME_WIDTH = 810;
    private static final int FRAME_HEIGHT = 510;

   //Initialise variables
    private DrawCanvas drawArea;
    
    private JFrame mainFrame;
    
    private JPanel mainPanel;
    private JPanel drawDigit_predictionPanel;  
    private JPanel outputPanel;
    
    private JFileChooser fileChooser;
    
    private final Font sansSerifBold = new Font("SansSerif", Font.BOLD, 10);

    private JButton openFileBtn;
	private JTextField fileNameTxt;
	private JLabel fileNameLbl;
	private JLabel image;
	private String selectFile = "";
	private JProgressBar progressBar;

	//ImageModel imgModel;
	
	//set up exceptions
    public UI() throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        UIManager.put("Button.font", new FontUIResource(new Font("Dialog", Font.BOLD, 18)));
    }

    //Initialize GUI class
    public void initUI() {
        //create main frame
        mainFrame = createMainFrame();
        //create main panel
        mainPanel = new JPanel();
        //set panel to border layout
        mainPanel.setLayout(new BorderLayout());
        //call addTopPanel function
        addTopPanel();
        //create drawAndDigitPredictionPanel panel and give it a grid layout
        drawDigit_predictionPanel = new JPanel(new GridLayout());
        //call addActionPanel function
        addActionPanel();
        //call addDrawAreaAndPredictionArea function
        addDrawAreaAndPredictionPanel();
        //add addDrawAreaAndPredictionArea to the center of the main panel
        mainPanel.add(drawDigit_predictionPanel, BorderLayout.CENTER);
        //add mainpanel to the center of the mainFrame
        mainFrame.add(mainPanel, BorderLayout.CENTER);
        //make sure the mainframe is visible when Initialized
        mainFrame.setVisible(true);

    }

    private void addActionPanel() {
    	//create button to recognize digit
        JButton recognize = new JButton("Compare Images");
        //add actionlistener to recognize digit button
        recognize.addActionListener(e -> {        	 
            });
        
        //create clear button
        JButton clear = new JButton("Clear Drawing");
        //actionlistener to repaint drawarea white 
        clear.addActionListener(e -> {
                drawArea.setImage(null);
                drawArea.repaint();
                drawDigit_predictionPanel.updateUI();
                image.setIcon( null );    
		        outputPanel.removeAll();
		        fileNameTxt.setText("");
		        progressBar.setString("0%"); 
            });
        
      //create cheat button to cheat
        JButton cheat = new JButton("Cheat");
        //actionlistener to compare 
        cheat.addActionListener(e -> {
              // String d_img = ("E:\\advanced\\AP\\7");
               try {
				BufferedImage d_img = ImageIO.read(new File("E:\\advanced\\AP\\7"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            });
        
        
        JPanel layer1Panel = new JPanel();
        layer1Panel.setLayout(new BoxLayout(layer1Panel, BoxLayout.Y_AXIS));
        layer1Panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                "",
                TitledBorder.LEFT,
                TitledBorder.TOP, sansSerifBold, Color.BLACK));
        
        JPanel actionPanel = new JPanel(new GridLayout(4, 1));
        
        JPanel ImagePanel = new JPanel();
        ImagePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                "Selected Image",
                TitledBorder.LEFT,
                TitledBorder.TOP, sansSerifBold, Color.BLACK));
        image = new JLabel(" ");
        ImagePanel.add(image);
        
     
        
        actionPanel.add(recognize);
        actionPanel.add(clear);
        actionPanel.add(cheat);
        actionPanel.add(ImagePanel);
        
        layer1Panel.add(actionPanel);
        layer1Panel.add(ImagePanel);
        
        mainPanel.add(layer1Panel, BorderLayout.EAST);
    }

    private void addDrawAreaAndPredictionPanel() {

        drawArea = new DrawCanvas();
        
        drawDigit_predictionPanel.add(drawArea);

    	outputPanel = new JPanel();
    	
    	   	

    	outputPanel.  setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                "Predicted Number",
                TitledBorder.LEFT,
                TitledBorder.TOP, sansSerifBold, Color.red));
    }
   
    
    private void addTopPanel() {
  
        JPanel fileSelectPanel = new JPanel();
        
        fileNameLbl = new JLabel ("The File name: ");
        fileNameTxt = new JTextField(50);
        fileNameTxt.setEnabled(false);        
        openFileBtn = new JButton("Browse Files");
        
        progressBar = new JProgressBar(0, 100);        
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setIndeterminate(true); // Set the indeterminate property to true
//        progressBar.setString(); 
		
        // Add components to the JPanel
        fileSelectPanel.add(fileNameLbl);
        fileSelectPanel.add(fileNameTxt);
        fileSelectPanel.add(openFileBtn);
        fileSelectPanel.add(progressBar);
        
		openFileBtn.addActionListener(new ActionListener() {
			
			
			@Override
			public void actionPerformed(ActionEvent e) {
				File selectedFile = showFileChooserDialog();
				
				if(selectedFile != null) {
					fileNameTxt.setText(selectedFile.getAbsolutePath());
					selectFile = selectedFile.getAbsolutePath();
					try {
	    				
	    				if (selectFile != null)  {
	    					
	    					ImageFileHandler currentfileHandler = new ImageFileHandler(selectFile); 
	    			        currentfileHandler.readFile(); 
	    			        ImageProcessing ICObject = new ImageProcessing();
	    			        ICObject.setImage(currentfileHandler.getImage());
	    			        ICObject.convertRGBToGrayscale(ICObject.getImage());
	    			        ICObject.resizeGreyScaleImage(28, 28);
	    			        
	    			        MNISTDataItemLoader DILoader = new MNISTDataItemLoader();
	    			        DILoader.setImageController(ICObject);
	    			        DILoader.loadItemArray();		     
	    			        DILoader.computingEcludianDidst(); 
	    			        progressBar.setString("100%"); 
	    	                drawArea.setImage(null);
	    	                drawArea.repaint();
	    	                drawDigit_predictionPanel.updateUI();
	    	                	    	                			             			     
	    			        outputPanel.removeAll();             	               
	    	                outputPanel.updateUI();
	    	                
	    	            
	    					
	    				}
	    			} catch (Exception ex) {
	    				ex.printStackTrace();
	    			}

				}
				else
					fileNameTxt.setText("No file has been selected");	
			}
		});	
        

        mainPanel.add(fileSelectPanel, BorderLayout.SOUTH);
        mainPanel.add(progressBar,BorderLayout.WEST);
    }
    
    //file chooser function
	private File showFileChooserDialog() {
		//set up file chooser
		fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new 
				File(System.getProperty("user.home")));
	    int status = fileChooser.showOpenDialog(this.mainFrame);
	    File selected_file = null;
	    if (status == JFileChooser.APPROVE_OPTION) {
	        selected_file = fileChooser.getSelectedFile();
	        try {
	        	//get image of selected file and add it to JLabel(image) to display the chosen image on the JPanel, resize image to 200 by 200
                image.setIcon(new ImageIcon(ImageIO.read(selected_file).getScaledInstance(150, 150, Image.SCALE_DEFAULT)));
            } catch (IOException e) {
                e.printStackTrace();
            }     
	    }
	    return selected_file;
	}
  
    

    public static BufferedImage toBufferedImage(Image img) {
        // Create a buffered image
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }

    private static double[] transformImageToOneDimensionalVector(BufferedImage img) {
        double[] imageGray = new double[28 * 28];
        int w = img.getWidth();
        int h = img.getHeight();
        int index = 0;
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                Color color = new Color(img.getRGB(j, i), true);
                int red = (color.getRed());
                int green = (color.getGreen());
                int blue = (color.getBlue());
                double v = 255 - (red + green + blue) / 3d;
                imageGray[index] = v;
                index++;
            }
        }
        return imageGray;
    }

    
    //method to create and display the main Frame
    private JFrame createMainFrame() {
        JFrame mainFrame = new JFrame();
        mainFrame.setTitle("Hand Written Digit Recognizer");
        mainFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        //give mainframe height and width 
        mainFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        mainFrame.setLocationRelativeTo(null);
        
        mainFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    System.exit(0);
                }
            });

        return mainFrame;
    }


}