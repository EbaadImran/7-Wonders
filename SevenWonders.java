import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import javax.swing.JFrame;

public class SevenWonders extends JFrame
{
	public static void main(String[] args)
	{
		new SevenWonders();
	}
	private Board b;
	WondersGraphics panel;
	public SevenWonders()
	{
		try {
			b = new Board();
		}
		catch(IOException e)
		{
			System.out.println("Error");
		}
	}
	public void setUpGraphics()
	{
		setSize(1920, 1080);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new WondersGraphics();
		add(panel);
		this.addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
				int x = e.getX(), y = e.getY();
				if(panel.getIsTitleScreen()==true)
				{
					panel.turnOffTitleScreen();
					repaint();
				}
			}
		});
		setUndecorated(true);
		setVisible(true);
	}
	public void updateGame()
	{
		panel.setGS(b.getGameState());
	}
}
