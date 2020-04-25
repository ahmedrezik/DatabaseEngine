import java.awt.Polygon;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

public class Tuple implements Comparable, Serializable {
	
public ArrayList<Value> values;
public Date TouchDate;
public String ClusterKey; 
	
	
	public Tuple(Hashtable<String,Object> table,String ClusterKey) {
		values = new ArrayList<Value>();
		for(String key:table.keySet()) {
			values.add(new Value(key, table.get(key)));
		}
		this.TouchDate = new Date();
		this.ClusterKey = ClusterKey;
	}


	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub

		Tuple val2 = (Tuple) o;
		Value val = val2.findValue(ClusterKey);
		Value val1 = this.findValue(ClusterKey);

	return val1.compareTo(val);
	
	
		}
	public String toString() {
		String x = "{";
		for(Value value: values) {
			x +=  value.key + ": " + value.value.toString() + ", " ;
		}
		return x + "touchDate: " + this.TouchDate.toString() + "}\n";
	}

		
		
	
	
	public Value findValue(String Key){
		for(Value value:values) {
			if(value.key.equals(ClusterKey)) {
				
				return value;
			}
	}
		
		return null;
	}
		
		public static void main(String [] args) {
			Hashtable<String,Object> h = new Hashtable();
			Integer y = 3;
			h.put("age", y);
			h.put("name", "c");
			h.put("status", true);
			Hashtable<String,Object> h2 = new Hashtable();
			Integer x = 13;
			h2.put("age", x);
			h2.put("name", "b");
			h2.put("status", true);
			Hashtable<String,Object> h3 = new Hashtable();
			Integer v = 130;
			h3.put("age", v);
			h3.put("name", "a");
			h3.put("status", true);
			Tuple t1 = new Tuple(h,"name");
			Tuple t2 = new Tuple(h2,"name");
			Tuple t3 = new Tuple(h3,"name");
			Vector V = new Vector();
			V.add(t3);V.add(t2);V.add(t1);
			    Comparator<Tuple> tupleComparator = new Comparator<Tuple>() {
			        @Override
			        public int compare(Tuple e1, Tuple e2) {
			            return e1.compareTo(e2);
			        }
			    };
			    Collections.sort(V,tupleComparator);
			System.out.print(V);
		}
	
	
	
	
	

	
}

class Value implements Comparable, Serializable{
	public String key;
	public Object value;
	
	public Value(String key, Object value) {
		this.key = key;
		this.value =  value;
		
	}
	
	public String toString() {
		return key + " :" + value.toString();
	}

	@Override
	public int compareTo(Object o) {
		Value c2 = (Value) o;
		if(this.key.equals(c2.key)) {
		switch(this.value.getClass().getCanonicalName()) {
		case "java.lang.Integer": return compareInt((Integer) this.value,(Integer)(c2.value));
		case "java.lang.String": return compareString((String) this.value, (String) c2.value);
		case "java.lang.Double": return compareDouble((Double)(this.value),(Double) c2.value);
		case "java.lang.Boolean": return compareBool((Boolean)(this.value), (Boolean)c2.value);
		case "java.awt.Polygon": return comparePolygon((Polygon) this.value, (Polygon)c2.value);
		case "java.lang.Date": return compareDate((Date)this.value, (Date)c2.value);
		}}
		return -7; //Invalid operation
	
	}
	
	private int compareInt(Integer i, Integer j) {
		return i.compareTo(j)>0?1:i.equals(j)?0:-1;
	}
	private int compareString(String a, String b) {
		return a.compareTo(b)>0?1:a.equals(b)?0:-1;
	}
	private int compareDouble(Double i, Double j) {
		return i==j?0:i>j?-1:1;
	}
	private int compareBool(boolean a, boolean b) {
		return a==b?0:a?1:-1;
	}
	private int compareDate(Date a, Date b){
		return a.compareTo(b)>0?1:a.compareTo(b)==0?0:-1;
	}
	private int comparePolygon(Polygon a, Polygon b) {
		//TODO: 
		return calculateArea(a)>calculateArea(b)? 1:calculateArea(a)==calculateArea(b)?0:-1;
	}
	private double calculateArea(Polygon a) {
	return a.getBounds().width * a.getBounds().height;
	}
			
	}
	

