package jp.co.dk.neo4jsample;

import java.util.Iterator;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class Neo4jSample {
	
	public static void main(String[] args) {
		Neo4jSample.delete();
	}
	
	public static void add() {
		GraphDatabaseService graphDB = new GraphDatabaseFactory().newEmbeddedDatabase("/tmp/neo4j_sample");
		Transaction tx = graphDB.beginTx();
		try {
			Node user = graphDB.createNode();
			user.addLabel(MyLabel.人間);
			user.addLabel(MyLabel.会社員);
			user.setProperty("名前", "菅野大輔");
			user.setProperty("生年月日", "19860604");
			user.setProperty("住所", "神奈川県横浜市");
			
			Node company = graphDB.createNode();
			company.addLabel(MyLabel.会社);
			company.setProperty("名前", "株式会社バルテック");
			company.setProperty("電話番号", "0353256371");
			company.setProperty("住所", "東京都新宿区");
			
			user.createRelationshipTo(company, MyRelationshipTypes.所属);
			tx.success();
			
		} finally {
			tx.finish();
			graphDB.shutdown();
		}
	}
	
	public static void read() {
		GraphDatabaseService graphDB = new GraphDatabaseFactory().newEmbeddedDatabase("/tmp/neo4j_sample");
		Transaction tx = graphDB.beginTx();
		try {
			ResourceIterator<Node> users = graphDB.findNodes(MyLabel.人間);
			while(users.hasNext()){
				Node node = users.next();
				System.out.println("名前:" + node.getProperty("名前"));
				System.out.println("生年月日:" + node.getProperty("生年月日"));
				System.out.println("住所:" + node.getProperty("住所"));
				
				for (Relationship rs : node.getRelationships(MyRelationshipTypes.所属)) {
					Node company = rs.getEndNode();
					System.out.println("==================================");
					System.out.println("名前:" + company.getProperty("名前"));
					System.out.println("電話番号:" + company.getProperty("電話番号"));
					System.out.println("住所:" + company.getProperty("住所"));
				}
			}
			
			tx.success();
			
		} finally {
			tx.finish();
			graphDB.shutdown();
		}
	}
	
	public static void update() {
		GraphDatabaseService graphDB = new GraphDatabaseFactory().newEmbeddedDatabase("/tmp/neo4j_sample");
		Transaction tx = graphDB.beginTx();
		try {
			ResourceIterator<Node> users = graphDB.findNodes(MyLabel.人間);
			while(users.hasNext()){
				Node node = users.next();
				node.setProperty("住所", "東京都新宿区");
				node.setProperty("性別", "男");
			}
			tx.success();
			
		} finally {
			tx.finish();
			graphDB.shutdown();
		}
	}
	
	public static void delete() {
		GraphDatabaseService graphDB = new GraphDatabaseFactory().newEmbeddedDatabase("/tmp/neo4j_sample");
		Transaction tx = graphDB.beginTx();
		try {
			ResourceIterator<Node> users = graphDB.findNodes(MyLabel.人間);
			while(users.hasNext()){
				Node node = users.next();
				for (Relationship rs : node.getRelationships()) {
					rs.delete();
				};
				node.delete();
			}
			tx.success();
			
		} finally {
			tx.finish();
			graphDB.shutdown();
		}
	}
}

enum MyLabel implements Label
{
    人間,
	会社員,
	会社
}

enum MyRelationshipTypes implements RelationshipType
{
    所属
}
