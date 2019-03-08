package main_code;
import java.awt.image.*;
import java.io.*;

public class MNISTItem {
	//declares Variables 
	private int MNIST_label; 
	private BufferedImage MNISTbuffimage;
	private double KnnDistanceValue; 
	
	
	public MNISTItem() {}
	 
	public int getMNIST_label() {
		return this.MNIST_label;
	}
	
	public void setMNIST_label(int givenLabel) {
		this.MNIST_label = givenLabel;
	}
	
	public BufferedImage getMNISTbuffimage()  {
		return this.MNISTbuffimage;
	}

	public void setMNISTbuffimage(BufferedImage tempimage) {
		this.MNISTbuffimage = tempimage;
	}
	
	public double getKnnDistanceValue() {
		return KnnDistanceValue;
	} 
	
	public void setKnnDistanceValue(double tempValue) {
		this.KnnDistanceValue = tempValue;
	}
}