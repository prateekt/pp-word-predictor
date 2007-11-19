package org.ss12.wordprediction;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

/**
 * Allows loading the word-frequency pairs and the dictionary.
 */
public class WordLoader {
	Integer min_freq;
	Set<Entry<String, Integer>> set;
	TreeMap <String, Integer> tm = new TreeMap <String, Integer>();
	
	public WordLoader(Integer minimumFrequency)
	{
		min_freq = minimumFrequency;	// Initialize the frequency to zero
	}
	
	/**
	 * Loads the given file containing word-frequency pairs.
	 * 
	 * @param file the file of word-frequency pairs
	 */
	public void loadFrequenciess(File file) throws IOException 
	{
		FileReader	objReader = new FileReader(file);
		BufferedReader objBuffReader = new BufferedReader (objReader);
		
		while(true)
		{
			String line = objBuffReader.readLine();
			if(line!= null)
			{	

				String[] items = line.split(" ");
				if (items.length != 2) {
					continue;
				}
				
				
				String word="";
				Integer value;
				for(int i=0;i<items.length;i++){
					if(items[i].length()>0)
					{
						if(word.equals(""))
						{
							word=items[i];
						}
						else
						{
							value=Integer.parseInt(items[i]);
							tm.put(word.toLowerCase(), value);
							break;
						}
					}
				}
			}
			else
			{
				break;
			}
			set = tm.entrySet();
		}
	}
	
	/**
	 * Loads the given file containing only words.
	 * 
	 * @param file the file of words
	 * @throws IOException 
	 * @throws IOException 
	 */
	public void loadDictionary(File file) throws IOException  
	{
		FileReader objReader = new FileReader(file);
		BufferedReader objBuffReader = new BufferedReader (objReader);		
		
		while(true)
		{
			String word = objBuffReader.readLine();
			if(word!= null)
			{	
				tm.put(word,new Integer(min_freq));
			}
			else
			{
				break;
			}
			set = tm.entrySet();
		}
	}
	public TreeMap<String, Integer> loadNgram(File file)
	{
		TreeMap<String,Integer> retval = new TreeMap<String,Integer>();
		try{
			FileInputStream objReader = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(objReader);
			retval = (TreeMap<String, Integer>)in.readObject();
			in.close();
		}
		catch(IOException e){
			System.out.println("File not found, creating empty Ngram entry instead");
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		System.out.println("Contains and:"+retval.containsKey("and"));
		System.out.println("Contains and histories:"+retval.containsKey("and histories"));
		System.out.println("Contains about such matters:"+retval.containsKey("about such matters"));
		return retval;
	}
	
	/**
	 * Display entries of the TreeMap.
	 * 
	 * @param 
	 */
	
	public void display()
	{
		for (Map.Entry<String, Integer> me : set) 
		{
		      System.out.print(me.getKey() + ": ");
		      System.out.println(me.getValue());    
		}
		System.out.println();
	}
	
	/**
	 * Returns all loaded words as an array of word-frequency pairs.
	 * 
	 * @return an array of word-frequency pairs
	 */
	public SortedMap<String, Integer> getWords() {
		return tm;
	}
}
