package UI;
import javax.swing.*;

import main_code.MNIST_reader;

import java.io.IOException;
import java.util.concurrent.Executors;

//main class to run UI from
public class Run {

    private static JFrame mainFrame = new JFrame();

    public static void main(String[] args) throws Exception {

    	MNIST_reader filereader = new MNIST_reader();
    	
        UI ui = new UI();
        Executors.newCachedThreadPool().submit(()->{
                try {
                	filereader.Read();
                    ui.initUI();
                } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
                    mainFrame.dispose();
                }
         });
    }
}