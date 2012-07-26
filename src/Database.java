import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;






/**
 * TODO Put here a description of what this class does.
 *
 * @author rangesh.
 *         Created Jul 25, 2012.
 */
public class Database {

	/**
	 * TODO Put here a description of what this method does.
	 *
	 * @param args
	 */
	 Multimap<String, Integer> map = LinkedListMultimap.create();
	 Multimap<String, Integer> dmap = LinkedListMultimap.create();
	 HashMap<String,Integer> lmap=new HashMap<String,Integer>();
	 ArrayList<String>rollbacks=new ArrayList<String>();
	 
	public static void main(String[] args) {
		// TODO Auto-generated method stub.
		Database db=new Database();
	db.read();
		
	}

	private void read() {
		// TODO Auto-generated method stub.
		InputStreamReader input=new InputStreamReader(System.in);
		BufferedReader read=new BufferedReader(input);
		int end=0;
		try {
			while(end!=1)
			{
				System.out.println(">");
				String readers=read.readLine();
				String commands[]=readers.split(" ");				
				if(commands[0].equalsIgnoreCase("BEGIN"))
					
				{	while(end!=1)
				{
					System.out.println(">");
					String reader=read.readLine();
					String command[]=reader.split(" ");	
				if(command[0].equalsIgnoreCase("set"))
				{
					rollbacks.add(reader);	
				set(command[1],Integer.parseInt(command[2]));
				}
				else if(command[0].equalsIgnoreCase("unset"))
				{
				rollbacks.add(reader);
				unset(command[1]);
				}
				else if(command[0].equalsIgnoreCase("get"))
				{
				String show=get(command[1]);	
				try
				{
				int flag=0;
					if((show==null)&&(rollbacks.size()==0))
				{
					
                Set loop = lmap.keySet();
		                
		                Iterator it=loop.iterator();
		                while(it.hasNext())
		                {
		                	String key=(String) it.next();
		               if(key.equalsIgnoreCase(command[1]))
		               {
		           	System.out.println(lmap.get(key));
		            	flag=1;
		               }
		                
		                }
		if(flag==0)
		{
					System.out.println("null");	
		}
					
				}
				else
				{
					System.out.println(show);	
				}
				}
				catch (NullPointerException e) {
				System.out.println("this null");
				}
				}
				else if(command[0].equalsIgnoreCase("rollback"))
				{
					
				try{
					
					String remove=rollbacks.get(rollbacks.size()-1);	
				rollback(remove);	
				}
				catch (ArrayIndexOutOfBoundsException e) {
					// TODO: handle exception
					System.out.println("Invalid RollBack");
				}
				}
				else if(command[0].equalsIgnoreCase("commit"))
				{
					String commits=rollbacks.get(rollbacks.size()-1);
					String Variable[]=commits.split(" ");
				commit(Variable[1]);	
				}
				else if(command[0].equalsIgnoreCase("numequalto"))
				{
					 int count=0;
		        
	                   count=search(Integer.parseInt(command[1]));
	                   if((count==0)&&(rollbacks.size()==0))
	                   {
	                       Set loop = lmap.keySet();
			                
			                Iterator it=loop.iterator();
			                while(it.hasNext())
			                {
			                	String key=(String) it.next();
			               if(Integer.parseInt(command[1])==lmap.get(key))
			               {
			            	count++;   
			               }
			                
			                }
			                   
	                   }
				System.out.println(count);
				}
				else
				{
			 if(command[0].equalsIgnoreCase("end"))
				end=1;	
				}
				}
				if(end==1)
				{
				end=1;
				}
			}
				else
				{
				if(commands[0].equalsIgnoreCase("set"))
				{
				lmap.put(commands[1],Integer.parseInt(commands[2]));}	
				 if(commands[0].equalsIgnoreCase("unset")){
				lmap.remove(commands[1]);	}
				 if(commands[0].equalsIgnoreCase("get")){
                 System.out.println(lmap.get(commands[1]));}
				 if(commands[0].equalsIgnoreCase("numequalto"))
				 {
					 int count=0;
		                Set loop = lmap.keySet();
		                
		                Iterator it=loop.iterator();
		                while(it.hasNext())
		                {
		                	String key=(String) it.next();
		               if(Integer.parseInt(commands[1])==lmap.get(key))
		               {
		            	count++;   
		               }
		                
		                }
		                System.out.println(count);

				 }
				 if(commands[0].equalsIgnoreCase("end"))
				 {
				end=1;	 
				 }

					
				}
		}
			
			
			}catch (IOException exception) {
			// TODO Auto-generated catch-block stub.
			exception.printStackTrace();
		}
		
	}

	/**
	 * TODO Put here a description of what this method does.
	 *
	 */
	private int search(int compare) {
		// TODO Auto-generated method stub.
	int count=0;	
	Collection<String> keys=map.keySet();
	Iterator i=keys.iterator();
	while(i.hasNext())
	{
	String key=(String) i.next();	
	Collection<Integer> values=map.values();
	Iterator it=values.iterator();
	int value = 0;
	while(it.hasNext())
	{
	 value=(Integer) it.next();	
	}
	if(value==compare)
	{
	count++;	
	}
	}
	return count;
	}

	/**
	 * TODO Put here a description of what this method does.
	 *
	 */
	private void commit(String com) {
		// TODO Auto-generated method stub.
		System.out.println("key"+com);
		Collection<Integer> values=map.get(com);
		Iterator<Integer> i=values.iterator();
		int value = 0;
		while(i.hasNext())
		{
		value=i.next();	
		}
		map.removeAll(com);
		dmap.removeAll(com);
		rollbacks.remove(rollbacks.size()-1);
	System.out.println(rollbacks.size());
		map.put(com,value);
		
	}

	/**
	 * TODO Put here a description of what this method does.
	 *
	 */
	private void rollback(String Variable) {
		// TODO Auto-generated method stub.
		String varible[]=Variable.split(" ");
		String variable=varible[0];
		String Val=varible[1];
		if(variable.equalsIgnoreCase("set"))
		{
		Collection<Integer> values=map.get(Val);
		Iterator<Integer> i=values.iterator();
		Integer get = null;
		while(i.hasNext())
		{
		 get=i.next();	
		}
			if(get!=null)
		{
		map.remove(Val,get);
		rollbacks.remove(rollbacks.size()-1);
	
			}
		}
		if(variable.equalsIgnoreCase("unset"))
		{
		Collection<Integer> values=dmap.get(Val);
		Iterator<Integer> i=values.iterator();
		Integer get = null;
		while(i.hasNext())
		{
		 get=i.next();	
		}
		if(get!=null)
		{
map.put(Val,get);
dmap.remove(Val,get);
rollbacks.remove(rollbacks.size()-1);
			}
		}
	}

	/**
	 * TODO Put here a description of what this method does.
	 *
	 */
	private void unset(String Variable) {
		// TODO Auto-generated method stub.
		Collection<Integer> values=map.get(Variable);
		Iterator<Integer> i=values.iterator();
		Integer del = null;
		while(i.hasNext())
		{
		 del=i.next();	
		}
if(del!=null)
{
	this.dmap.put(Variable,del);
		this.map.remove(Variable,del);
}		
	}

	/**
	 * TODO Put here a description of what this method does.
	 *
	 */
	private String get(String Variable) {
		// TODO Auto-generated method stub.
		Collection<Integer> values=map.get(Variable);
		Iterator<Integer> i=values.iterator();
		String value = null;
		while(i.hasNext())
		{
		value=String.valueOf(i.next());
		}
		return value;
		
	}

	/**
	 * TODO Put here a description of what this method does.
	 *
	 */
	private void set(String variable,Integer value) {
		  
		map.put(variable,value);
	}



}
