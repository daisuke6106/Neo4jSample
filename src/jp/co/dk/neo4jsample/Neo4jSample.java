package jp.co.dk.neo4jsample;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class Neo4jSample {
	
	public static void main(String[] args) {
		
		GraphDatabaseService graphDB = new GraphDatabaseFactory().newEmbeddedDatabase("/tmp/neo4j_sample");
		Transaction tx = graphDB.beginTx();
		
		try {
			Node user_master = createUser(graphDB, "山田太郎", "19851010", "神奈川県横浜市１−１−１");
			
			for (int i=0; i<1000; i++) {
				Node user = createUser(graphDB, "山田太郎" + i, "19851010", "神奈川県横浜市１−１−１");
				Relationship relationship = user_master.createRelationshipTo( user, MyRelationshipTypes.KNOWS );
				relationship.setProperty( "Relationship", "Friend" );
			}
			
			tx.success();
			
		} finally {
			tx.finish();
			graphDB.shutdown();
		}
	}
	
	static Node createUser(GraphDatabaseService graphDB, String name, String birth, String address) {
		Node user = graphDB.createNode();
		user.setProperty("name", name);
		user.setProperty("birth", birth);
		user.setProperty("address", address);
		return user;
	}
	
}

enum MyRelationshipTypes implements RelationshipType
{
    KNOWS
}