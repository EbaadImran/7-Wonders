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
	
	public Board(int amt) throws IOException
	{
		deck = new Deck();
		
		ArrayList<String> wonds = new ArrayList<>();
		Scanner scan = new Scanner(new File("wonders.txt"));
		while(scan.hasNext())
			wonds.add(scan.nextLine());
		
		players = new Player[amt];
		for(int i = 0; i < players.length; i++)
			players[i] = new Player(wonds.remove((int) (Math.random()*wonds.size())), i);
	}
	public void setAge1()
	{
		age = 1;
		deal();
	}
	public void setAge2()
	{
		age = 2;
		deal();
	}
	public void setAge3()
	{
		age = 3;
		deal();
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
}
