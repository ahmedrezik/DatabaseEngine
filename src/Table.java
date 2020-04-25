import java.util.*;
import java.awt.Polygon;
import java.io.*;
public class Table  implements Serializable{

	
	/**
	 * 
	 */
	public static final long serialVersionUID = 2764068054682856661L;
	public String name;
	public String StringClusteringColumn;
	public Hashtable<String,String> TableData;
	public static int MaxRowsPerPage;
	public ArrayList<page> pages;
	public int rowCount;
	
	

	
	/**
	 * @author ahmed
	 * Constructor
	 */
	public Table(String name,String ClusteringColumn,Hashtable<String,String> table, int MaxRows) {
		this.name = name;
		this.StringClusteringColumn = ClusteringColumn;
		this.TableData = table;
		pages = new ArrayList<page>();
		this.MaxRowsPerPage = MaxRows;
		try {
			this.GenerateMetaData();
		} catch (IOException e) {
			System.out.println("Failed to Generate MetaData try creating the file and calling Table.GenerateMetaData() before inserting");
		}
		
	}
	
	
//Binary Search	
public page PageSearch(Tuple t) throws ClassNotFoundException, IOException {
	for(page Page :this.pages) {
		//Page = page.readObject(Page.getindex());
		Tuple first = (Tuple) Page.tuples.firstElement();
		Tuple last = (Tuple) Page.tuples.lastElement();
		if(this.pages.get(0).tuples.isEmpty()) {
			return this.pages.get(0);
		}
		 if(t.compareTo(last)<0 || Page.tuples.size() < page.MaxRows) {
			return Page;
		}
		
		else if( t.compareTo(last) < 0 && t.compareTo(first)> 0 ) {
			return Page;
		}
		
		
	}
	createPage();
	return this.pages.get(this.pages.size()-1);
	
}
//private page search(Tuple t, int l, int r) {
//	if(l>r) {
//		createPage();
//		return this.pages.get(this.pages.size()-1);
//	}
//	int mid = l + (r-l)/2;
//
//	page currentPage = this.pages.get(mid);
//	Tuple first = (Tuple) currentPage.tuples.firstElement();
//	Tuple last = (Tuple) currentPage.tuples.lastElement();
//	if(t.compareTo(first) > 0  && currentPage.tuples.size() < currentPage.MaxRows ) {
//		
//		return currentPage;
//		
//	}
////	else if(t.compareTo(first) > 0) {
////		return currentPage;
////	}
//	else if( t.compareTo(last) < 0 && t.compareTo(first)> 0 ) {
//		
//		return currentPage;
//	}
//	else if((t.compareTo(first)<0 || t.compareTo(last) < 0) && currentPage.getindex() == 0) {
//		
//		return currentPage;
//	}
//	
//	else {
//
//		if(t.compareTo(first) < 0 ) {
//			
//		return search(t,l,mid);
//		}
//		else {
//		return	search(t,mid,r);
//		}
//	}
//}




	@SuppressWarnings("unchecked")
	public void insertRow(Hashtable<String,Object> ColumnAndValue) throws IOException, DBAppException, ClassNotFoundException {
		
	
		Tuple toBeAdded = new Tuple(ColumnAndValue,this.StringClusteringColumn);
		
		// Insert into Vector
	if(checkTypeMatching(ColumnAndValue)) {
		if(pages.size() == 0 ) {
		createPage();
		page first = this.pages.get(0) ;
		//first = page.readObject(0);
		first.tuples.add(toBeAdded);
		first.max = (Tuple) first.tuples.lastElement();
		
		return;
		
		}
		
		page toAdd = PageSearch(toBeAdded);
		
		if(toAdd.tuples.size() < page.MaxRows) {
			//toAdd = page.readObject(toAdd.getindex());
			toAdd.tuples.add(toBeAdded);
			Collections.sort(toAdd.tuples,page.tupleComparator);
			//toAdd.writeObject();
			
			}
		else {
			//toAdd = page.readObject(toAdd.getindex());
			Tuple tobeShifted = (Tuple) toAdd.tuples.lastElement();
			toAdd.tuples.removeElementAt(toAdd.tuples.size()-1);
			toAdd.tuples.add(toBeAdded);
			Collections.sort(toAdd.tuples,page.tupleComparator);
			
			insertRow(convertToHashtable(tobeShifted));
			//toAdd.writeObject();
		}
		
		
		
		
		//toAdd.writeObject(toAdd);
		

		
		
	
		}
	else{
		throw new DBAppException("Wrong types");
	}
		
	}
	
	private Hashtable<String,Object> convertToHashtable(Tuple t) {
		Hashtable<String,Object> table = new Hashtable<String,Object>();
		for(Value value: t.values) {
			table.put(value.key, value.value);
		}
		return table;
	}
	
	
	
//TODO:
	public void updateRow(String ClusteringKey, Hashtable<String,Object> ColumnAndValue) throws DBAppException, IOException, ClassNotFoundException  {
		if(checkTypeMatching(ColumnAndValue)) {
			HashMap<String, String> types = AppSettings.getMetaData();
		String type = types.get(this.StringClusteringColumn);
		
		Object value = null;
		switch(type) {
		case "java.lang.Integer": value = Integer.parseInt(ClusteringKey);break;
		case "java.lang.String": value = ClusteringKey;break;
		case "java.lang.Double": value = Double.parseDouble(ClusteringKey);break;
		case "java.lang.Boolean": value = Boolean.parseBoolean(ClusteringKey);break;
		case "java.lang.Date": value =  Date.parse(ClusteringKey);break;
		case "java.awt.Polygon": value = new Polygon();break;
		}
		
			
			Value searchValue = new Value(this.StringClusteringColumn,value);
			Tuple toBeUpdated = findTuple(searchValue);
			
			if(toBeUpdated != null ) {
				Hashtable tuple = convertToHashtable(toBeUpdated);
				for(String key : ColumnAndValue.keySet()) {
					tuple.put(key, ColumnAndValue.get(key));
				}
				insertRow(tuple);
			}
			else{
				System.out.println("Tuple doesn't exist");
			}
			
		}}
	
	
	
	private Tuple findTuple(Value v) {
	for(page Page: this.pages) {
		for(int i =0; i < Page.tuples.size();i++) {
			Tuple t = (Tuple) Page.tuples.get(i);
			for(Value val : t.values) {
				
				if(val.compareTo(v) == 0) {
				
					Page.tuples.remove(t);
					return t;
					
				}
			}
		}
		
	}
	System.out.println("Tuple not found");
	return null;
	}
	
	private page removeTuple(Tuple t) {
	for(page Page: this.pages) {
		for(int i =0; i < Page.tuples.size();i++) {
			Tuple tuple = (Tuple) Page.tuples.get(i);
		if(tuple.compareTo(t) == 0) {
			Page.tuples.remove(tuple);
			return Page;
		}
		}
		
	}
	System.out.println("The Tuple does not exist");
	return null;
	}
	public void DeleteRow(
			Hashtable<String,Object> htblColNameValue) throws IOException, DBAppException, ClassNotFoundException {
		Tuple toBeDeleted = new Tuple(htblColNameValue, this.StringClusteringColumn);
		
		page removedFrom = removeTuple(toBeDeleted);
		
		if(removedFrom.getindex() < this.pages.size()-1 && removedFrom.tuples.size() < page.MaxRows) {
			
			Tuple t = (Tuple) this.pages.get(removedFrom.getindex()+1).tuples.get(0);
			this.pages.get(removedFrom.getindex()+1).tuples.remove(0);
			
			insertRow(convertToHashtable(t));
		}
		
		
		
	}
	
	

	private boolean checkTypeMatching(Hashtable<String,Object> ColumnAndValue) throws DBAppException, IOException {
		HashMap<String, String> tableTypes = AppSettings.getMetaData();
		
		for(String key: ColumnAndValue.keySet()) {
			
			if(!tableTypes.get(key).equals(ColumnAndValue.get(key).getClass().getCanonicalName())){
				throw new DBAppException("Type mismatch in input data" + tableTypes.get(key) + " " +tableTypes.get(key).getClass().toString() + ColumnAndValue.get(key).getClass().toString());
				
			}
		
		}
		return true;
	}
	
private boolean checkValue(String columnType, Object value) {
		if(value.getClass().getCanonicalName().equals(columnType)) {
			return true;
		}
	
		return false;
	}

public void viewTable() throws ClassNotFoundException, IOException {
	for(page Page: this.pages) {
		//Page =  page.readObject(Page.getindex());
		System.out.println(Page.tuples.toString());
	}

}
	
	
 private void createPage() throws IOException {
	 page Page = new page(MaxRowsPerPage);
	 //Page.writeObject();
		pages.add(Page);
	}

	public void GenerateMetaData() throws IOException {
		File file = new File("/Users/ahmed/eclipse-workspace/euler/database/data/metadata.csv");
		boolean fileExists = false;
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			fileExists = true;
			System.out.println("File exists in" + file.getAbsolutePath());
		}
		FileWriter fr = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fr);
		if(!fileExists) {
			bw.write("Table Name, Column Name, Column Type, ClusteringKey, Indexed\n"); //only on initial creation
		}
		
		for(String key:this.TableData.keySet()) {
			String pair = key + "," + this.TableData.get(key);
			bw.write(this.name + "," + pair + ",");
			if(key.equals(this.StringClusteringColumn)) {
				bw.write("True,False \n");
			}
			else {
				bw.write("False,False \n");
			}
		}
		bw.close();
		
		
	}
	
	public final void writeObject()
            throws IOException{
		String filename =  this.name+"table" +".ser"; 
        FileOutputStream file = new FileOutputStream("/Users/ahmed/eclipse-workspace/euler/database/data/tables/"+ filename); 
        ObjectOutputStream out = new ObjectOutputStream(file); 
          
        
        out.writeObject(this); 
          
        out.close(); 
        file.close(); 
          
        

		
	}
	
	
	public static final Object readObject(String name)
            throws IOException,
         ClassNotFoundException{
		Table currentTable = null;
		String filename = name ; 
		 FileInputStream file = new FileInputStream("/Users/ahmed/eclipse-workspace/euler/database/data/tables/"+ filename); 
         ObjectInputStream in = new ObjectInputStream(file); 
           
         // Method for deserialization of object 
         currentTable = (Table)in.readObject(); 
           
         in.close(); 
         file.close(); 
         //Collections.sort(tuples,tupleComparator);
         return currentTable;
		
	}
	
	
	public static void main(String [] args) throws Exception {
//		FileInputStream fileIn = new FileInputStream("/Users/ahmed/eclipse-workspace/euler/database/data/pages/page0.ser");
//        ObjectInputStream in = new ObjectInputStream(fileIn);
//      // page e = (page) in.readObject();
//        in.close();
//        fileIn.close();
        //System.out.println(e.tuples);
	}

	
	
	
	
}
