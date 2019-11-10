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
		/*try {
			b = new Board();
		}
		catch(IOException e)
		{
			System.out.println("Error");
		}*/
		setUpGraphics();
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
				if(panel.isTitleScreen())
				{
					panel.turnOffTitleScreen();
					repaint();
				}
				//g.drawImage(arrow, 512/4, 1080 - (512/3), -1*(512/4), 512/4, null);
				//g.drawImage(arrow, 129, 1080 - (512/3), 512/4, 512/4, null);
				else if(panel.isPlayScreen() && x>= 30 && x<=512/4-30 && y>=1080 - 512/3 && y<=1080 - 512/3 + 512/4)
				{
					int t = panel.getTurn()-1;
					if(t==0)
						t = 3;
					panel.setTurn(t);
				}
				else if(panel.isPlayScreen() && x>=512/4+1 && x<=512/4+512/4-30 && y>=1080 - 512/3 && y<=1080 - 512/3 + 512/4)
				{
					int t = panel.getTurn() + 1;
					if(t==4)
						t = 1;
					panel.setTurn(t);
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
