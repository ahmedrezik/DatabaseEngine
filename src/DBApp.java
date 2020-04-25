import java.io.IOException;
import java.util.*;

public class DBApp {
	
	public static Hashtable<String,Table> tables = new Hashtable<String,Table>();
	
	
public void init() throws ClassNotFoundException, IOException {
	
	AppSettings.readConfig();
	AppSettings.importTables();
}

//TODO: M1

/** @param TableName
 * @param ClusteringKEy index
 * @param Table 
 * @throws IOException 
 */
public void createTable(
String strTableName,
String strClusteringKeyColumn,
Hashtable<String,String> htblColNameType ) throws IOException {
	Table newTable = new Table(strTableName, strClusteringKeyColumn, htblColNameType, AppSettings.MaxPageRows);
	tables.put(strTableName, newTable);
	for(String table: tables.keySet()) {
		System.out.println("Tables created: "+ table+",");
	}
	newTable.writeObject();
	
	
	
	
}

public void createBTreeIndex(String strTableName,
String strColName) {
	
}


public void createRTreeIndex(String strTableName,
String strColName) {
	
}

//TODO: M1
public void insertIntoTable(String strTableName, Hashtable<String,Object> htblColNameValue) throws IOException, DBAppException {
	 
	try{
		Table target = searchforTable(strTableName);
		
		target.insertRow(htblColNameValue);
		target.writeObject();
	}
	catch(Exception e){
		e.printStackTrace();
		throw new DBAppException("Table Not Found");
	}
	
	
	
}


//TODO: M1
public void updateTable(String strTableName,
String strClusteringKey, Hashtable<String,Object> htblColNameValue ) {
	try {
		Table target = searchforTable(strTableName);
		target.updateRow(strClusteringKey, htblColNameValue);
		target.writeObject();
	}
	catch(Exception e) {
		
	}
}

//TODO: M1
public void deleteFromTable(String strTableName,
Hashtable<String,Object> htblColNameValue) throws IOException, DBAppException, ClassNotFoundException {
	try {
	Table target = searchforTable(strTableName);
	
	target.DeleteRow(htblColNameValue);
	target.writeObject();
	}
	catch(Exception e) {
		e.printStackTrace();
	}
}


public Iterator selectFromTable(SQLTerm[] arrSQLTerms, String[] strarrOperators) {
	return null;
}


private Table searchforTable(String TableName) {
	for(String tableName:tables.keySet()) {
		if(tableName.equals(TableName)) {
			return tables.get(tableName);
		}
	}
	return null;
}

public static void main(String [] args) throws IOException, DBAppException, ClassNotFoundException {
	DBApp app = new DBApp();
	app.init();
//	Hashtable htblColNameType = new Hashtable( ); htblColNameType.put("id", "java.lang.Integer"); htblColNameType.put("name", "java.lang.String"); htblColNameType.put("gpa", "java.lang.Double");
//	app.createTable("Student", "id", htblColNameType);
//	//app.tables.get("Student").GenerateMetaData();
//	
//	
//	Hashtable htblColNameValue = new Hashtable( ); htblColNameValue.put("id", 11); htblColNameValue.put("name", new String("Ahmed Noor" ) ); htblColNameValue.put("gpa", 0.95); 
//	Hashtable htblColNameValu = new Hashtable( ); htblColNameValu.put("id", 46); htblColNameValu.put("name", new String("Ahmed Nour" ) ); htblColNameValu.put("gpa", 0.75); 
//	Hashtable htblColNameVal = new Hashtable( ); htblColNameVal.put("id", 14); htblColNameVal.put("name", new String("Ahmed R" ) ); htblColNameVal.put("gpa", 0.75);
	Hashtable htblColNameVa = new Hashtable( ); htblColNameVa.put("id", 12); htblColNameVa.put("name", new String("Ahmed R" ) ); htblColNameVa.put("gpa", 0.75);
//
//	
//	Hashtable htblColNameV = new Hashtable( ); htblColNameV.put("id", 100); htblColNameV.put("name", new String("Ahmed R" ) ); htblColNameV.put("gpa", 0.75);
//
//	
//	
//
//	app.insertIntoTable("Student", htblColNameV);
//	app.insertIntoTable( "Student" , htblColNameValue );
//	app.insertIntoTable("Student", htblColNameValu);
//
//
////	app.updateTable("Student","100", up);
//	//app.deleteFromTable("Student", htblColNameVa);
//	app.insertIntoTable("Student", htblColNameVal);
	app.insertIntoTable("Student", htblColNameVa);
	app.tables.get("Student").viewTable();
}

}

