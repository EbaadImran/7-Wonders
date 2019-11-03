import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Wonder {
	private String[] attributes;
	private Card[] stages = new Card[3];
	private ArrayList<String> res = new ArrayList<String>();
	private Map<String, Set<Card>> structures = new HashMap<>();
	public Wonder(String att) {
		//name|res|cost1|cost2|cost3|stage1|stage2|stage3
		attributes = att.split("|");
		res.add(attributes[1]);
	}
	public ArrayList<String> getRes()
	{
		return res;
	}
	public String getStage1()
	{
		return attributes[5];
	}
	public String getStage2()
	{
		return attributes[6];
	}
	public String getStage3()
	{
		return attributes[7];
	}
	public Set<Card> getStructure(String color)
	{
		return structures.get(color);
	}
	public void activate(Card oth)
	{
		for(int i=0;i<stages.length;i++)
		{
			if(stages[i] == null)
			{
				stages[i] = oth;
				return;
			}
		}
	}
	public void build(Card c) {
		Set<Card> yourmom = structures.get(c.getColor());
		yourmom.add(c);
	}
}
