public class Main {

	public static void main(String[] args) throws IOException {
		URL input = new URL("https://pl.wikipedia.org/");
		InputStream connectStream = input.openConnection().getInputStream();
		
		try (Scanner sc = new Scanner(connectStream); MongoClient mongoClient = new MongoClient("localhost", 27017)) {
			MongoDatabase db = mongoClient.getDatabase("wikisriki");
			printIntoDB(sc, db);
		}

	}

	private static void printIntoDB(Scanner sc, MongoDatabase db) {
		Pattern p = Pattern.compile("\\/\\/[a-zA-Z0-9/.?=&#%_-]+");
		String url;
		MongoCollection<Document> collection = db.getCollection("test");
		
		DBObject dbopcjons = new BasicDBObject();
		dbopcjons.put("autoIndexID", "true");
		
		while ((url = sc.findWithinHorizon(p, 0)) != null ) {
			Document doc = new Document("url", url);
			collection.insertOne(doc);
		}
	}

}