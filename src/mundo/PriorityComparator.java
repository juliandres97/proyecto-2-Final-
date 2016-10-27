package mundo;

import java.util.Comparator;

public class PriorityComparator implements Comparator<LocalAuthority>
{
	@Override
	public int compare(LocalAuthority lA1, LocalAuthority lA2)
	{
		// TODO Auto-generated method stub
		if (lA1.getPrioridad() < lA2.getPrioridad())
			return -1;
		
		else if (lA1.getPrioridad() > lA2.getPrioridad())
			return 1;
		
		else 
			return 0;
	}
}