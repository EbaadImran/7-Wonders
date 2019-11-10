import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class Deck 
{
	private HashMap<Integer, ArrayList<Card>> ages;
	private HashMap<Integer, ArrayList<Card>> discard;
	
	public Deck() throws IOException
	{
		ages = new HashMap<>();
		discard = new HashMap<>();
		for(int i = 1; i <= 3; i++)
		{
			ages.put(i, new ArrayList<>());
			discard.put(i, new ArrayList<>());
		}
		
		Scanner age1 = new Scanner(new File("age1.txt"));
		Scanner age2 = new Scanner(new File("age2.txt"));
		Scanner age3 = new Scanner(new File("age3.txt"));
		Scanner guilds = new Scanner(new File("guilds.txt"));
		
		while(age1.hasNext())
			ages.get(1).add(new Card(age1.nextLine()));
		while(age2.hasNext())
			ages.get(2).add(new Card(age2.nextLine()));
		while(age3.hasNext())
			ages.get(3).add(new Card(age3.nextLine()));
		
		ArrayList<Card> guild = new ArrayList<>();
		while(guilds.hasNext())
			guild.add(new Card(guilds.nextLine()));
		
		for(int i = 0; i < 5; i++)
			ages.get(3).add(guild.remove((int) (Math.random()*guild.size())));
		
		shuffle();
	}
	public void shuffle()
	{
		Collections.shuffle(ages.get(1));
		Collections.shuffle(ages.get(2));
		Collections.shuffle(ages.get(3));
	}
	public Card draw(int age) {return ages.get(age).remove(0);}
	public void discard(Card c) {discard.get(c.getAge()).add(c);}
	public ArrayList<Card> getDiscard(int age) {return discard.get(age);}
}