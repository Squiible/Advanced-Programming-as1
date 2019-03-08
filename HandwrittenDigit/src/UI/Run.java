package UI;



import javax.swing.*;
import java.util.concurrent.Executors;


public class Run {

    private static JFrame mainFrame = new JFrame();

    public static void main(String[] args) throws Exception {


        UI ui = new UI();
        Executors.newCachedThreadPool().submit(()->{
                try {
                    ui.initUI();
                } finally {
                    mainFrame.dispose();
                }
            });
    }

  
}
