package Client.src;
import javax.swing.*;

class Display extends JFrame {
  public Display() {
	setTitle("My Empty Frame");
	setSize(300,200); // default size is 0,0
	setLocation(10,200); // default is 0,0 (top left corner)
	
  }

  public static void main(String[] args) {
    JFrame f = new Display();
  }
}