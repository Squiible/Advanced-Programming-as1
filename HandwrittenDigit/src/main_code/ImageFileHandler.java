package main_code;

import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

public class ImageFileHandler extends MainFileHandler {
//declares variables 
	int width;
	int height;
	String image_type;
	BufferedImage img;
	
	public ImageFileHandler() {}
	
	public ImageFileHandler(String file_name) {
		super(file_name);
		img = null;
	}
	
	@Override
	public void readFile() throws IOException {
		if (fp.isFile() && fp.exists()) {
			img = ImageIO.read(fp);
		}
	}

	@Override
	public void writeFile(String file_name) throws IOException {
		// TODO Auto-generated method stub

	}
	public BufferedImage readFile(String file_name) {
		
		try {
			img = ImageIO.read(new File(file_name));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			img = null;
		}
		return img;
	}
	
	public BufferedImage getImage() {
		return this.img;
	}
	

}
