package main_code;
import java.awt.image.*;
import java.util.*;
import java.io.*; 

public class MNISTDataItemLoader {
	
	private ImageProcessing tempImageController = null;
	// import MNIST images
	String train_label_filename = "C:\\Users\\Jake\\OneDrive\\Documents\\UNI\\Year 3 - Computer Science\\Advanced Programming\\DigitRecognize\\src\\backend\\train-labels.idx1-ubyte";
	String train_image_filename = "C:\\Users\\Jake\\OneDrive\\Documents\\UNI\\Year 3 - Computer Science\\Advanced Programming\\DigitRecognize\\src\\backend\\train-images.idx3-ubyte";
	//creates the streams
	FileInputStream in_stream_labels = null;
	FileInputStream in_stream_images = null;
	DataInputStream dataStreamLables = null; 
	DataInputStream dataStreamImages = null; 
	//creates array
	private MNISTItem[] currentDataItems = null;
	private int recognizedDigit;
	
	public ImageProcessing getImageController() {
		return this.tempImageController;
	}
	
	public void setImageController (ImageProcessing providedIC) {
		this.tempImageController = providedIC;
	}
	
	public MNISTItem[] getDIArray() {
		return this.currentDataItems;
	}
	
	public void setDIArray(MNISTItem[] providedDIArray) {
		this.currentDataItems = providedDIArray;
	}	
	
	public void loadItemArray() { 
		
		try {
			in_stream_labels = new FileInputStream(new File(train_label_filename));
			in_stream_images = new FileInputStream(new File(train_image_filename));
	
			dataStreamLables = new DataInputStream(in_stream_labels);
			dataStreamImages = new DataInputStream(in_stream_images); 
			
			int labelStartCode = dataStreamLables.readInt(); 
			int labelCount = dataStreamLables.readInt();
			int imageStartCode = dataStreamImages.readInt();
			int imageCount = dataStreamImages.readInt();
			int imageHeight = dataStreamImages.readInt(); 
			int imageWidth = dataStreamImages.readInt();
			
			currentDataItems = new MNISTItem[labelCount]; 
			
			int imageSize = imageHeight * imageWidth; 
			byte[] labelData = new byte[labelCount];
			byte[] imageData = new byte[imageSize * imageCount];
			BufferedImage tempImage;
			
			dataStreamLables.read(labelData);
			dataStreamImages.read(imageData);
			
			for (int currentRecord = 0; currentRecord < labelCount; currentRecord++) {
				int currentLabel = labelData[currentRecord];				
				MNISTItem newlabeledimage = new MNISTItem();
				newlabeledimage.setMNIST_label(currentLabel);			
				tempImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);				
				int[][] imageDataArray = new int[imageWidth][imageHeight];
				for (int row = 0; row < imageHeight; row++) {
					for(int column = 0; column < imageWidth; column++) { 
						imageDataArray[column][row] = imageData[(currentRecord * imageSize)+((row*imageWidth) + column)] | 0xFF000000;
						tempImage.setRGB(column, row, imageDataArray[column][row]);
					}
					
				}
				newlabeledimage.setMNISTbuffimage(tempImage); 
				currentDataItems[currentRecord] = newlabeledimage;
			}
			if (in_stream_labels != null) {
				in_stream_labels.close();
			}
			if (in_stream_images != null) {
				in_stream_images.close();
			}			
		} catch (FileNotFoundException fn) {
			fn.printStackTrace();	   
		} catch (IOException e)	{
		   
		   e.printStackTrace();
	   }
	
	}
	
	// Adds in an array sort
	public void sortArray() {  
	    int n = currentDataItems.length;  
	    MNISTItem tempDI = null;  
	    for(int i = 0; i < n; i++){  
	    	for(int j = 1; j < (n-i); j++){  
	    		if(currentDataItems[j - 1].getKnnDistanceValue() > currentDataItems[j].getKnnDistanceValue()){                       
                   tempDI = currentDataItems[j - 1];  
                   currentDataItems[j - 1] = currentDataItems[j];  
                   currentDataItems[j] = tempDI;  
	    		}                  
	    	}  
	    }	
	}
	
	public void computingEcludianDidst() throws NullPointerException {
		MNISTItem[] processedMDIArray = new MNISTItem[this.getDIArray().length];
		MNISTItem[]  tempDataArray = this.getDIArray();
		BufferedImage ComparisonImage= tempImageController.getImage();
		if (ComparisonImage != null) {
			int currentImageWidth = ComparisonImage.getWidth();
			int currentImageHeight = ComparisonImage.getHeight();
			double mseValue = 0.0;
			double squareSum = 0.0; 
			for(int i =0; i < tempDataArray.length; i++) {
				MNISTItem currentComparisonMNISTDataItem = tempDataArray[i]; 
				BufferedImage currentComparisonImage = currentComparisonMNISTDataItem.getMNISTbuffimage();
				for (int y = 0; y < currentImageHeight; y++) {
					for(int x = 0; x < currentImageWidth; x++) {
					int imagetoComparePixels = ComparisonImage.getRGB(x, y);
				    int currentComparisonImagePixelValue =  currentComparisonImage.getRGB(x, y); 
				    squareSum += (Math.pow((imagetoComparePixels - currentComparisonImagePixelValue), 2));
				    
					}
				}
				mseValue = squareSum / (currentComparisonImage.getWidth() * currentComparisonImage.getHeight());
				currentComparisonMNISTDataItem.setKnnDistanceValue(mseValue);
				processedMDIArray[i] = currentComparisonMNISTDataItem;
				
			}
			this.setDIArray(processedMDIArray);			
		}
	}
	
	

	public int getRecogniseDidgit() {
		return this.recognizedDigit;
	}
	
	public void setRecogniseDigit( int suppliedDidgit) {
		this.recognizedDigit = suppliedDidgit;
	}
	

}
