import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;
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
	}
	public void chooseRes()
	{
		Card c = b.getPlayers()[b.getTurn()].getCard(cardIndex);
		ArrayList<String> missingRes = b.getPlayers()[b.getTurn()].missingRes(c);
		ArrayList<String> chooseRes =  b.getPlayers()[b.getTurn()].getWonder().getChoose();
		
		while(!chooseRes.isEmpty())
		{
			String[] choose = chooseRes.get(0).split("/");
			boolean chosen = false;
			for(int i = 0; i < choose.length; i++)
			{
				if(missingRes.contains(choose[i]))
				{
					selectResource(0, i);
					chosen = true;
				}
			}
			if(!chosen)
				selectResource(0, 0);
		}
	}
	public boolean[] options(int pos)
	{
		boolean[] arr = b.getPlayers()[b.getTurn()].getPosActions(b.getPlayers()[b.getTurn()].getCard(pos));
		boolean[] temp = new boolean[4];
		temp[0] = arr[0];
		temp[1] = arr[1];
		temp[2] = true;
		temp[3] = arr[2];
		return temp;
	}
	public boolean trade(int oth, String res)
	{
		System.out.println(oth + " " + res);
		boolean tradable = b.tradable(oth, res);
		if(tradable)
			b.trade(oth, res);
		System.out.println(tradable);
		return tradable;
	}
	public boolean doMove()
	{
		if(options(cardIndex)[currAction])
		{
			b.doAction(currAction, cardIndex);
			if(currAction == 1 && b.getCurrPlayer().getWonder().getName().equals("halikarnassusA") && b.getCurrPlayer().getWonderAmt() == 3)
				return true;
		}
		else
			b.doAction(2, cardIndex);
		return false;
	}
	public void selectResource(int i, int choice)
	{
		b.getPlayers()[b.getTurn()].getWonder().removeRes(i, choice);
	}
	public void endTurn()
	{
		b.getPlayers()[b.getTurn()].getWonder().resetUsable();
		b.getPlayers()[b.getTurn()].getWonder().resetTradable();
		b.getPlayers()[(b.getTurn()+1) % 3].getWonder().resetTradable();
		b.getPlayers()[(b.getTurn()+2) % 3].getWonder().resetTradable();
		if(b.nextTurn() == 0)
			b.nextRound();
		if(b.getRound() >= 7 && b.getAge() < 3)
		{
			b.war();
			startAge();
		}
		else if(b.getRound() >= 7 && b.getAge() == 3)
		{
			b.war();
			win();
		}
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
                System.out.println(x+" "+y);
                if(panel.isTitleScreen())
                {
                    panel.turnOffTitleScreen();
                    repaint();
                }
                //g.drawImage(arrow, 512/4, 1080 - (512/3), -1(512/4), 512/4, null);
                //g.drawImage(arrow, 129, 1080 - (512/3), 512/4, 512/4, null);
                else if(panel.isPlayScreen() && x>= 30 && x<=512/4-30 && y>=1080 - 512/3 && y<=1080 - 512/3 + 512/4)
                {
                    int t = panel.getTurn()-1;
                    if(t==-1)
                        t = 2;
                    panel.setTurn(t);
                }
                else if(panel.isPlayScreen() && x>=512/4+1 && x<=512/4+512/4-30 && y>=1080 - 512/3 && y<=1080 - 512/3 + 512/4)
                {
                    int t = panel.getTurn() + 1;
                    if(t==3)
                        t = 0;
                    panel.setTurn(t);
                }
                /*int x = 1920/2 - (c.size()/2+1)*160, y = 1080/3;
                 for(Card cd:c) {
                     x = x + 145;
                     g.drawImage(new ImageIcon(cd.getName()+".png").getImage(), x, y, (int)(180.8), (int)(275.8), null);
                 }*/
                else if(panel.getTurn()==b.getTurn() && panel.isPlayScreen()) {
                	int i = checkIndex(e.getX(), e.getY());
                	if(i >= 0 && i < b.getCurrPlayer().getHand().size())
                	{
                		cardIndex = i;
                		chooseRes();
                		panel.setCard(b.getPlayers()[b.getTurn()].getCard(i));
                		panel.setPlayCardScreen();
                	}
                }
                else if(panel.isPlayCardScreen())
                {
                	/*g.drawImage(build.getImage(), 550, 440, 125, 100, null);
            		g.drawImage(wonder.getImage(), 800, 440, 120, 100, null);
            		g.drawImage(discard.getImage(), 1050, 440, 80, 100, null);
            		g.drawImage(buildFree.getImage(), 1300, 440, 100, 110, null);*/
                	if(x >= 550 && x <= 675 && y >= 440 && y <= 540)
                	{
                		currAction = 0;
                		finishSelection();
                		
                	}
                	else if(x >= 800 && x <= 920 && y >= 440 && y <= 540)
                	{
                		currAction = 1;
                		finishSelection();
                	}
                	else if(x >= 1050 && x <= 1130 && y >= 440 && y <= 540)
                	{
                		currAction = 2;
                		finishSelection();
                	}
                	else if(x >= 1300 && x <= 1400 && y >= 440 && y <= 550)
                	{
                		if(b.getAge() == 1 && b.getCurrPlayer().hasFree1() || b.getAge() == 2 && b.getCurrPlayer().hasFree2() || b.getAge() == 3 && b.getCurrPlayer().hasFree3())
                		{
                			currAction = 3;
                			finishSelection();
                		}
                	}
                }
                else if(panel.isTradeScreen())
                {
                	if(x >= 760 && x <= 1160 && y >= 800 && y <= 970)
                	{
                		panel.setTradeScreen();
                		boolean hali = doMove();
                		if(hali)
                		{
                			panel.setHaliScreen();
                		}
                		else
                		{
                			endTurn();
                			updateGame();
                			panel.resetTurn();
                		}
                	}
                	else if(checkPlayerTrade(y) != -1)
                	{
                		int i = checkTradeIndex(x, y, checkPlayerTrade(y));
                		if(i >= 0)
                		{
                			System.out.println(b.getPlayers()[checkPlayerTrade(y)].getWonder().getTradableRes().get(i));
                			trade(checkPlayerTrade(y), b.getPlayers()[checkPlayerTrade(y)].getWonder().getTradableRes().get(i));
                		}
                	}
                }
                else if(panel.isHaliScreen())
                {
                	int a = checkAgeDiscard(y);
                	if(a != -1)
                	{
                		int i = checkDiscIndex(x, y, a);
                		if(i >= 0)
                		{
                			b.haliEffect(a, i);
                			panel.setHaliScreen();
                			endTurn();
                			updateGame();
                			panel.resetTurn();
                		}
                	}
                }
                updateGame();
            }
        });
        setUndecorated(true);
        setVisible(true);
    }
	public void finishSelection()
	{
		panel.setPlayCardScreen();
		if((currAction == 0 || currAction == 1) && !options(cardIndex)[currAction] && b.getCurrPlayer().getCoins() >= 1)
		{
			panel.setAction(currAction);
			panel.setTradeScreen();
		}
		else
		{
			boolean hali = doMove();
    		if(hali)
    		{
    			panel.setHaliScreen();
    		}
    		else
    		{
    			endTurn();
    			updateGame();
    			panel.resetTurn();
    		}
		}
	}
	public int checkDiscIndex(int x, int y, int a)
	{
		ArrayList<Card> disc = b.getDeck().getDiscard(a);
		for(int i = 0; i < disc.size(); i++)
		{
			int loc = i * 155 + 100;
			if(x >= loc && x <= loc + 144)
				return i;
		}
		return -1;
	}
	public int checkAgeDiscard(int y)
	{
		if(y >= 100 && y <= 320)
			return 1;
		else if(y >= 350 && y <= 570)
			return 2;
		else if(y >= 600 && y <= 820)
			return 3;
		return -1;
	}
	public int checkIndex(int x, int y)
	{
		for(int i = 0; i < 7; i++)
		{
			int a = i * 150 + 425;
			if(x >= a && x <= a + 144 && y >= 25 && y <=245)
				return i;
		}
		return -1;
	}
	public int checkTradeIndex(int x, int y, int p)
	{
		ArrayList<String> temp = b.getPlayers()[p].getWonder().getTradableRes();
		for(int i = 0; i < temp.size(); i++)
		{
			int a = i * 55 + 100;
			if(x >= a && x <= a + 50)
				return i;
		}
		return -1;
	}
	public int checkPlayerTrade(int y)
	{
		if(y >= 450 && y <= 500)
			return (b.getTurn()+2) % 3;
		else if(y >= 600 && y <= 650)
			return (b.getTurn()+1) % 3;
		return -1;
	}
	public void updateGame()
	{
		panel.setGS(b.getGameState());
	}
}
