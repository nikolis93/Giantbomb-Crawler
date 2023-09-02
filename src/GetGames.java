package nikos.giantbombcrawler;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetGames {

    final static String apiKey = "insert your API key here";

    private static void phase1() {//we get all the game ID's first (phase 1) and then we get the info for each game (phase 2)
        String url = "https://www.giantbomb.com/api/games/?api_key=" + apiKey + "&field_list=name,guid,id";
        Document doc = null;
        doc = Connect.connect(url);
        if (doc == null) {
            System.out.println("ERROR: null doc at CrawlGames");
        }

        Elements games = doc.getElementsByTag("game");
        String gameName = "";
        String guid = "";
        String id = "";
        String insertString = "";
        for (Element g : games) {
            gameName = g.getElementsByTag("name").text();
            guid = g.getElementsByTag("guid").text();
            id = g.getElementsByTag("id").text();
            System.out.println("name: " + gameName);
            System.out.println("guid: " + guid);
            System.out.println("id: " + id);
            insertString = "'" + gameName.replaceAll("'", "") + "', '" + guid + "', '" + id + "'";
            System.out.println("insert String: " + insertString);
            DBTools.insertToTable(insertString, "gameIds");
            System.out.println("--------------------------------------------");
        }
        System.out.println(games.size());
    }

    private static void parseGame(Element game) {
        String name = game.getElementsByTag("name").text();
        String aliases = game.getElementsByTag("aliases").text();
        String apiDetailUrl = game.getElementsByTag("api_detail_url").text();
        String gameDescription = game.getElementsByTag("deck").text();
        String guid = game.getElementsByTag("guid").text();
        String id = game.getElementsByTag("id").text();
        String genres = game.getElementsByTag("").text();
        String image = game.getElementsByTag("image").text();
        String releaseDate = game.getElementsByTag("").text();
        String platforms = game.getElementsByTag("platforms").text();
        String siteDetailUrl = game.getElementsByTag("site_detail_url").text();
        String franchises = game.getElementsByTag("").text();
        String rating = game.getElementsByTag("original_game_rating").text();
        String publishers = game.getElementsByTag("").text();

    }

    public static void main(String[] args) {
        phase1();
    }
}
