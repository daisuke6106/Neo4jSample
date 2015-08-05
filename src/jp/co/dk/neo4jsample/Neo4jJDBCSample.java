package jp.co.dk.neo4jsample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class Neo4jJDBCSample {
	
	public static void main(String[] args) {
		// Make sure Neo4j Driver is registered
		try {
			Class.forName("org.neo4j.jdbc.Driver");
			// Connect
			Connection con = DriverManager.getConnection("jdbc:neo4j://localhost:7474/");
			// Querying
			con.setAutoCommit(false);
			con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			
			PreparedStatement statement = con.prepareStatement("CREATE (n:User{name:{1}}) RETURN n.name");
			statement.setString(1, "aaa");
			statement.setInt(2, 1);
			statement.setBoolean(2, true);
			ResultSet result = statement.executeQuery();
			
			while(result.next())
		    {
		        System.out.println(result.getString("n.name"));
		    }
			con.commit();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
