import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Board 
{
	private int age;
	private int turn;
	private Deck deck;
	private Player[] players;
	
	public Board() throws IOException
	{
		age = 1;
		deck = new Deck();
		
		ArrayList<String> wonds = new ArrayList<>();
		Scanner scan = new Scanner(new File("wonders.txt"));
		while(scan.hasNext())
			wonds.add(scan.nextLine());
		
		players = new Player[3];
		for(int i = 0; i < players.length; i++)
			players[i] = new Player(wonds.remove((int) (Math.random()*wonds.size())), i);
	}
	public int nextTurn()
	{
		turn = (turn+1) % 4;
		return turn;
	}
	public void nextAge()
	{
		age = age + 1;
	}
	public void doAction(String a, int i)
	{
		if(a.equals("build"))
			players[turn].playCard(i);
		else if(a.equals("wonder"))
			players[turn].buildWonder(i);
		else if(a.equals("discard"))
			deck.discard(players[turn].discard(i));
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
	public int[][] getPoints()
	{
		int[][] points = new int[3][];
		for(int i = 0; i < 3; i++)
			points[i] = players[i].getScore();
		return points;
	}
	public ArrayList<Object> getGameState()
	{
		ArrayList<Object> gs = new ArrayList<Object>();
		gs.add(players);
		gs.add((Integer) age);
		gs.add((Integer) turn);
		
		return gs;
	}
}
