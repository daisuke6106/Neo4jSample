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
			Node firstNode = graphDB.createNode();
			Node secondNode = graphDB.createNode();
			Relationship relationship = firstNode.createRelationshipTo( secondNode, MyRelationshipTypes.KNOWS );
			firstNode.setProperty( "message", "Hello, " );
			secondNode.setProperty( "message", "world!" );
			relationship.setProperty( "message", "brave Neo4j " );
			tx.success();
			System.out.print( firstNode.getProperty( "message" ) );
			System.out.print( relationship.getProperty( "message" ) );
			System.out.print( secondNode.getProperty( "message" ) );

		} finally {
			tx.finish();
			graphDB.shutdown();
		}
	}

}

enum MyRelationshipTypes implements RelationshipType
{
    KNOWS
}