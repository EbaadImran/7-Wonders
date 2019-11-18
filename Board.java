import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;

public class SevenWonders extends JFrame
{
	public static void main(String[] args) throws IOException
	{
		new SevenWonders();
	}
	private Board b;
	private WonderGraphics panel;
	private int currAction;
	private int cardIndex;
	
	public SevenWonders() throws IOException
	{
		b = new Board();
		setUpGraphics();
		startAge();
	}
	public void startAge()
	{
		b.nextAge();
		b.deal();
		updateGame();
		startTurn();
	}
	public void startTurn()
	{
		System.out.println("Age: " + b.getAge() + " Round: " + b.getRound());
		System.out.println("Player " + (b.getTurn()+1));
		System.out.println(b.getPlayers()[b.getTurn()]);
		System.out.println(b.getPlayers()[b.getTurn()].getWonder());
		System.out.println("Choose Resources: " + b.getPlayers()[b.getTurn()].getWonder().getChoose());
		Scanner scan = new Scanner(System.in);
		while(!b.getPlayers()[b.getTurn()].getWonder().getChoose().isEmpty())
		{
			selectResource(scan.nextInt(), scan.nextInt());
		}
		System.out.println("Trade? (Y / N): ");
		String trade = scan.next();
		while(trade.equals("Y"))
		{
			System.out.println("With Whom and What: ");
			if(!trade(scan.nextInt(), scan.next()));
				System.out.println("Unable to Trade!");
			System.out.println("Trade? (Y / N): ");
			trade = scan.next();
		}
		System.out.println("Select Card: ");
		cardIndex = scan.nextInt();
		System.out.println("Options: " + options(cardIndex));
		currAction = scan.nextInt();
		doMove();
		endTurn();
	}
	public ArrayList<String> options(int pos)
	{
		boolean[] arr = b.getPlayers()[b.getTurn()].getPosActions(b.getPlayers()[b.getTurn()].getCard(pos));
		ArrayList<String> temp = new ArrayList<>();
		if(arr[0])
			temp.add("Play Card [0]");
		else if(arr[1])
			temp.add("Build Wonder [1]");
		temp.add("Discard [2]");
		return temp;
	}
	public boolean trade(int oth, String res)
	{
		if(b.tradable(oth, res))
		{
			b.trade(oth, res);
			return true;
		}
		return false;
	}
	public void doMove()
	{
		b.doAction(currAction, cardIndex);
	}
	public void selectResource(int i, int choice)
	{
		b.getPlayers()[b.getTurn()].getWonder().removeRes(i, choice);
	}
	public void endTurn()
	{
		b.getPlayers()[b.getTurn()].getWonder().resetUsable();
		if(b.getRound() < 7)
		{
			if(b.nextTurn() == 0)
				b.nextRound();
			startTurn();
		}
		else if(b.getAge() < 3)
			startAge();
		else
			win();
	}
	public void win()
	{
		panel.win();
	}
	public void setUpGraphics()
	{
		setSize(1920, 1080);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new WonderGraphics();
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
		//setUndecorated(true);
		setVisible(true);
	}
	public void updateGame()
	{
		panel.setGS(b.getGameState());
	}
}
}
