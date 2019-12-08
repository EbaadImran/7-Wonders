import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Board 
{
	private int age;
	private int turn;
	private int round;
	private Deck deck;
	private Player[] players;
	private String[] moves;
	
	public Board() throws IOException
	{
		age = 0;
		round = 0;
		deck = new Deck();
		
		ArrayList<String> wonds = new ArrayList<>();
		Scanner scan = new Scanner(new File("wonders.txt"));
		while(scan.hasNext())
			wonds.add(scan.nextLine());
		
		players = new Player[3];
		for(int i = 0; i < players.length; i++)
			players[i] = new Player(wonds.remove((int) (Math.random()*wonds.size())), i);
	}
	public int getTurn()
	{
		return turn;
	}
	public int getAge()
	{
		return age;
	}
	public int getRound()
	{
		return round;
	}
	public int nextTurn()
	{
		turn = (turn+1) % 3;
		return turn;
	}
	public int nextRound()
	{
		round++;
		passHands();
		return round;
	}
	public void nextAge()
	{
		age = age + 1;
		round = 1;
		turn = 0;
	}
	public void chooseResource(int i, int choice)
	{
		players[turn].getWonder().removeRes(i, choice);
	}
	public void doAction(int a, int i)
	{
		if(a == 0)
		{
			doEffect(players[turn].getCard(i).getEffect());
			players[turn].playCard(i);	
		}
		else if(a == 1)
			players[turn].buildWonder(i);
		else if(a == 2)
			deck.discard(players[turn].discard(i));
		else if(a == 3)
		{
			doEffect(players[turn].getCard(i).getEffect());
			players[turn].playCard(i);
			if(age == 1)
				players[turn].removeHas1();
			else if(age == 2)
				players[turn].removeHas2();
			else if(age == 3)
				players[turn].removeHas3();
		}
	}
	public void haliEffect(int a, int i)
	{
		Card c = deck.getDiscard(a).remove(i);
		doEffect(c.getEffect());
		players[turn].playCard(c);
	}
	public void doEffect(String eff)
	{
		String[] parts = eff.split(" ");
		int total = 0;
		//left and right coins
		if(parts[0].equals("all"))
		{
			if(parts[1].equals("brown"))
				total += players[0].getColorAmt("brown") + players[1].getColorAmt("brown") + players[2].getColorAmt("brown");
			else if(parts[1].equals("silver"))
				total += (players[0].getColorAmt("silver") + players[1].getColorAmt("silver") + players[2].getColorAmt("silver")) * 2;
		}
		else if(parts[0].equals("self"))
		{
			if(parts[1].equals("wonder"))
				total += 3*(players[turn].getWonderAmt());
			else if(parts[1].equals("brown"))
				total += players[turn].getColorAmt("brown");
			else if(parts[1].equals("silver"))
				total += players[turn].getColorAmt("silver") * 2;
			else if(parts[1].equals("gold"))
				total += players[turn].getColorAmt("gold");
		}
		players[turn].addCoins(total);
	}
	public void deal()
	{
		ArrayList<Card> hand1 = new ArrayList<>();
		ArrayList<Card> hand2 = new ArrayList<>();
		ArrayList<Card> hand3 = new ArrayList<>();
		for(int i = 0; i < 7; i++)
		{
			hand1.add(deck.draw(age));
			hand2.add(deck.draw(age));
			hand3.add(deck.draw(age));
		}
		Collections.sort(hand1);
		Collections.sort(hand2);
		Collections.sort(hand3);
		for(Player k : players)
		{
			ArrayList<Card> temp = k.getHand();
			while(!temp.isEmpty())
				deck.discard(temp.remove(0));
		}
		players[0].setHand(hand1);
		players[1].setHand(hand2);
		players[2].setHand(hand3);
	}
	public void passHands()
	{
		if(age % 2 == 1)
		{
			ArrayList<Card> temp = players[2].getHand();
			players[2].setHand(players[1].getHand());
			players[1].setHand(players[0].getHand());
			players[0].setHand(temp);
		}
		else
		{
			ArrayList<Card> temp = players[0].getHand();
			players[0].setHand(players[1].getHand());
			players[1].setHand(players[2].getHand());
			players[2].setHand(temp);
		}
	}
	public boolean tradable(int oth, String res)
	{
		return players[oth].getWonder().hasTradableRes(res) && players[turn].getCoins() >= players[turn].getPrice(oth, res);
	}
	public void trade(int oth, String res)
	{
		if(players[turn].getOneCost().get(oth).contains(res))
		{
			players[turn].addCoins(-1);
			players[oth].addCoins(1);
		}
		else
		{
			players[turn].addCoins(-2);
			players[oth].addCoins(2);
		}
		players[turn].getWonder().addTradeResource(res);
		players[oth].getWonder().removeTrade(res);
	}
	public Player[] getPlayers()
	{
		return players;
	}
	public Player getCurrPlayer()
	{
		return players[turn];
	}
	public Deck getDeck()
	{
		return deck;
	}
	public int[][] getPoints()
	{
		int[][] points = new int[3][];
		for(int i = 0; i < 3; i++)
			points[i] = players[i].getScore(players);
		return points;
	}
	public ArrayList<Object> getGameState()
	{
		ArrayList<Object> gs = new ArrayList<Object>();
		gs.add(players); //0
		gs.add((Integer) age); //1
		gs.add((Integer) turn); //2
		gs.add((Integer) round); //3
		gs.add(getPoints()); //4
		gs.add(podium()); //5
		gs.add(deck); //6
		return gs;
	}
	public void war()
	{
		for(int i = 0; i < 3; i++)
		{
			if(players[i].getMPower() > players[(i+1) % 3].getMPower())
			{
				players[(i+1) % 3].addPoints(-1, 1);
				players[i].addPoints(1 + (age-1)*2, 1);
			}
			if(players[i].getMPower() > players[(i+2) % 3].getMPower())
			{
				players[(i+2) % 3].addPoints(-1, 1);
				players[i].addPoints(1 + (age-1)*2, 1);
			}
		}
	}
	public ArrayList<Player> podium() 
	{
		int tot1 = 0;
		int tot2 = 0;
		int tot3 = 0;
		for(int k : getPoints()[0])
			tot1 += k;
		for(int k : getPoints()[1])
			tot2 += k;
		for(int k : getPoints()[2])
			tot3 += k;
		players[0].setScore(tot1);
		players[1].setScore(tot2);
		players[2].setScore(tot3);
		ArrayList<Player> temp = new ArrayList<>();
		temp.add(players[0]);
		temp.add(players[1]);
		temp.add(players[2]);
		Collections.sort(temp);
		return temp;
	}
}
