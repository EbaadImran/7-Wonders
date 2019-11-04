import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Wonder {
	private String[] attributes;
	private Card[] stages;
	private ArrayList<String> usableRes;
	private ArrayList<String> tradableRes;
	private HashMap<String, Set<Card>> structures;
	public Wonder(String att) {
		//name|res|cost1|cost2|cost3|stage1|stage2|stage3
		attributes = att.split("|");
		stages = new Card[3];
		usableRes = new ArrayList<>();
		tradableRes = new ArrayList<>();
		structures = new HashMap<>();
		usableRes.add(attributes[1]);
		tradableRes.add(attributes[1]);
	}
	public void addUsable(String res)
	{
		usableRes.add(res);
	}
	public void addTradable(String res)
	{
		tradableRes.add(res);
	}
	public ArrayList<String> getUsableRes()
	{
		return usableRes;
	}
	public ArrayList<String> getTradableRes()
	{
		return tradableRes;
	}
	public String getCost1()
	{
		return attributes[2];
	}
	public String getCost2()
	{
		return attributes[3];
	}
	public String getCost3()
	{
		return attributes[4];
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
		//ebaads mom btw LOL!
		Set<Card> yourmom = structures.get(c.getColor());
		yourmom.add(c);
	}
}
