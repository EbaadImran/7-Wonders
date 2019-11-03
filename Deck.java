import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Deck 
{
	private HashMap<Integer, ArrayList<Card>> ages;
	private HashMap<Integer, ArrayList<Card>> discard;
	
	public Deck()
	{
		ages = new HashMap<>();
		discard = new HashMap<>();
		for(int i = 1; i <= 3; i++)
		{
			ages.put(i, new ArrayList<>());
			discard.put(i, new ArrayList<>());
		}
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
