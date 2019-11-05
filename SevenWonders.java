import java.io.IOException;

import javax.swing.JFrame;

public class SevenWonders extends JFrame
{
	
	public static void main(String[] args)
	{
		new SevenWonders();
	}
	private Board b;
	public SevenWonders()
	{
		/*try {
			b = new Board(3);
		}
		catch(IOException e) {
			System.out.println("Error");
		}*/
		setSize(1920, 1080);
		WondersGraphics panel = new WondersGraphics();
		add(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		setVisible(true);
	}
}
