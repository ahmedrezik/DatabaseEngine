import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;
import java.nio.*;
import java.nio.file.Files;
import java.nio.file.Paths;
public class AppSettings {
	
	public static int MaxPageRows;
	

	public static void readConfig() {
		try {
//			File config = new File("eclipse-workspace/euler/data/metadata.csv");
//			
//			config.createNewFile();
			
			FileReader reader=new FileReader("/Users/ahmed/eclipse-workspace/euler/database/config/DBApp.config");  
		
		    Properties properties = new Properties();  
		    properties.load(reader);  
		      
		 MaxPageRows = Integer.parseInt(properties.getProperty("MaximumRowsCountinPage"));
		   
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("Error Loading startup data");
				Scanner sc = new Scanner(System.in);
				System.out.println("Enter maximum  number of rows manually");
				MaxPageRows = Integer.parseInt(sc.nextLine());
			}
		
	}
	
	public static HashMap<String,String> getMetaData() throws IOException {
		//TODO: Add path for csv file
		BufferedReader br = Files.newBufferedReader(Paths.get("/Users/ahmed/eclipse-workspace/euler/database/data/metadata.csv"));
		String[] headers = br.readLine().split(",");
		HashMap<String,String> columns = new HashMap<String,String>();
		String line = "";
		while((line = br.readLine()) != null) {
			String [] tuple = line.split(",");
			columns.put(tuple[1], tuple[2]);
			
		}
			return columns;	
		
	}
	
	public static void importTables() throws IOException, ClassNotFoundException {
		int filecount = Filecount();
		File directory = new File("/Users/ahmed/eclipse-workspace/euler/database/data/tables/");
		for(int i = 0 ; i < filecount; i++) {
			Table retrieved = (Table) Table.readObject(directory.list()[i]);
			
			
			DBApp.tables.put(retrieved.name, retrieved);
		}
		

		
	}
	
	private static int Filecount() {
		File directory = new File("/Users/ahmed/eclipse-workspace/euler/database/data/tables/");
		try {
		int filecount = directory.list().length;
		
		return filecount;}
		catch(Exception e) {
			System.out.println(0);
			return 0;
		}
		
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
