import java.awt.Polygon;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class page implements Serializable{
	
	

	private static final long serialVersionUID = -4729785009485504523L;
	public static int MaxRows = 3;
	private int index = -1;
	//Vector list is a list of HashTable<String,Object>
	public Vector tuples;
	public Tuple max ;
	
	
	public page(int MaxRows) {
		this.tuples = new Vector(MaxRows);
		this.index  = ++index;
		//index++;
	}

	
   public static Comparator<Tuple> tupleComparator = new Comparator<Tuple>() {
        @Override
        public int compare(Tuple e1, Tuple e2) {
            return e1.compareTo(e2);
        }
    };
	
	public int getindex() {
		return this.index;
	}
	
	// Serialization Methods
//	public final void writeObject()
//            throws IOException{
//		String filename = "page" + index +".ser"; 
//        FileOutputStream file = new FileOutputStream("/Users/ahmed/eclipse-workspace/euler/database/data/pages/"+filename); 
//        ObjectOutputStream out = new ObjectOutputStream(file); 
//          
//        
//        out.writeObject(this); 
//          
//        out.close(); 
//        file.close(); 
//          
//        System.out.println("Object has been serialized"); 
//
//		
//	}
//	
//	
//	public  static final page readObject(int index)
//            throws IOException,
//         ClassNotFoundException{
//		
//		
//		page currentPage = null;
//		 FileInputStream file = new FileInputStream("/Users/ahmed/eclipse-workspace/euler/database/data/pages/page" + index +".ser"); 
//         ObjectInputStream in = new ObjectInputStream(file); 
//           
//         // Method for deserialization of object 
//        // currentPage = (page)in.readObject(); 
//           
//         in.close(); 
//         file.close(); 
//         //Collections.sort(tuples,tupleComparator);
//         return currentPage;
//		
//	}
	
	
	
	public static void main(String [] args) {
		
Value x = new Value("id",100);
Value y = new Value("id",100);
System.out.println(x.compareTo(y));
		
		
	}

}
