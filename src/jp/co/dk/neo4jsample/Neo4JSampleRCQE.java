package jp.co.dk.neo4jsample;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.rest.graphdb.RestAPI;
import org.neo4j.rest.graphdb.RestAPIFacade;
import org.neo4j.rest.graphdb.RestGraphDatabase;

public class Neo4JSampleRCQE {

	public static void main(String[] args) {
		RestAPI api = null;
		GraphDatabaseService graphDB = null;
		Transaction tx = null;
		try {
			api = new RestAPIFacade("http://localhost:7474/db/data");
			graphDB = new RestGraphDatabase(api);
			tx = graphDB.beginTx();
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
			
		} catch(Throwable e) {
			e.printStackTrace();
		} finally {
			tx.finish();
			graphDB.shutdown();
		}
	}

}
