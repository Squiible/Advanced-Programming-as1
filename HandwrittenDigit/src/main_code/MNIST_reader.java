package main_code;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
public class MNIST_reader {
	
	String train_label_filename = "C:\\Users\\Jake\\OneDrive\\Documents\\UNI\\Year 3 - Computer Science\\Advanced Programming\\DigitRecognize\\src\\backend\\train-labels.idx1-ubyte";
	String train_image_filename = "C:\\Users\\Jake\\OneDrive\\Documents\\UNI\\Year 3 - Computer Science\\Advanced Programming\\DigitRecognize\\src\\backend\\train-images.idx3-ubyte";
	
	FileInputStream in_stream_labels = null;
	FileInputStream in_stream_images = null;
	BufferedImage currentImg;
	
	//creates arrays
	int[] label_list;
	BufferedImage[] image_list;
	//creates arrays
	public int[] getLabels(){
		return label_list;
	}
	
	public BufferedImage[] getImage(){
		return image_list;
	}
	
	public void load_MNIST() {
		
	try {
		in_stream_labels = new FileInputStream(new File(train_label_filename));
		in_stream_images = new FileInputStream(new File(train_image_filename));

		int labels_start_code = (in_stream_labels.read() << 24) |
				(in_stream_labels.read() << 16) |
				(in_stream_labels.read() << 8) |
				(in_stream_labels.read());
		
		System.out.println("Label start code" + labels_start_code);
		
		int images_start_code = (in_stream_labels.read() << 24) |
				(in_stream_images.read() << 16) |
				(in_stream_images.read() << 8) |
				(in_stream_images.read());
		
		System.out.println("images start code" + images_start_code);
		
		int number_of_labels = (in_stream_labels.read() << 24) |
				(in_stream_labels.read() << 16) |
				(in_stream_labels.read() << 8) |
				(in_stream_labels.read());
		
		int number_of_images = (in_stream_images.read() << 24) |
				(in_stream_images.read() << 16) |
				(in_stream_images.read() << 8) |
				(in_stream_images.read());
		
		System.out.println("number of lables and images" + number_of_labels + "and" + number_of_images);

		
		int image_height = (in_stream_images.read() << 24) |
				(in_stream_images.read() << 16) |
				(in_stream_images.read() << 8) |
				(in_stream_images.read());
		
		int image_width = (in_stream_images.read() << 24) |
				(in_stream_images.read() << 16) |
				(in_stream_images.read() << 8) |
				(in_stream_images.read());
		
		System.out.println("Image size: " + image_width + " x " + image_height);
		int image_size = image_width * image_height;
		
		label_list = new int[number_of_labels];
		image_list = new BufferedImage[number_of_labels];
		//creates array
		int[] image_data;
		for(int record = 0; record < number_of_images; record++){
			
			int label = in_stream_labels.read();
			label_list[record] = label;
			System.out.println(label);
			
			image_data = new int[image_width * image_height];
			
			for (int pixel = 0; pixel <image_size; pixel++){
				
				int grey_value= in_stream_images.read();
				int rgb_value = 0xFF000000 | (grey_value << 16 ) |
						(grey_value << 8) |
						(grey_value);
				image_data[pixel] = rgb_value;
				
			}
			currentImg.setRGB(0, 0, image_width, image_height, image_data, 0, image_width);
			image_list[record] = currentImg;	
		}
		
	   } catch (IOException e){
		   
		   e.printStackTrace();
	   } 
	}
	
	
}
