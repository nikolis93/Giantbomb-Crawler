package nikos.giantbombcrawler;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetAllGamesInfos {

    final static String apiKey = "insert your API key here";

    private static void getAllGamesInfos() {
        Document doc = null;
        String finalString = "";
        for (int guid = 1; guid <= 70000; guid++) {
            if (guid < 116) {
                continue;
            }
            System.out.println("at: " + guid);
            String url = "https://www.giantbomb.com/api/game/3030-" + guid + "/?api_key=" + apiKey + "&field_list=name,aliases"
                    + ",deck,genres,image,original_release_date,platforms,publishers,site_detail_url,developers";
            System.out.println(url);
            doc = Connect.connect(url);
            if (doc == null) {
                System.out.println("ERROR: null doc at giantbomb getAllGames");
            }
            Game game = parseGame(doc);
            if (game.getGameName().equalsIgnoreCase("Unknown") || game.getGameUrl().equalsIgnoreCase("Unknown")) { //if there is a problem with the game infos we don't insert it into the DB
                System.out.println("Something is wrong at: " + url + ", continuing");
                System.out.println("\n###########################################NEXT GAME####################################");
                continue;
            }
            finalString = game.getGameUrl() + "\", \"" + game.getGameName().replaceAll("\"", "'") + "\", \"" + game.getAliases().replaceAll("\"", "'")
                    + "\", \"" + game.getGameDescription().replaceAll("\"", "'") + "\", \"" + game.getPlatforms()
                    + "\", \"" + game.getGenres() + "\", \"" + game.getGamePublishers().replaceAll("\"", "'") + "\", \"" + game.getDevelopers().replaceAll("\"", "'")
                    + "\", \"" + game.getImageUrl() + "\", \"" + game.getReleaseDate();

            System.out.println(game.toString());
            System.out.println(finalString);
            DBTools.insertToTable(finalString, "gameinfos");
            System.out.println("\n###########################################NEXT GAME####################################");
           

        }
    }

    private static String getAliases(Document doc) {
        String aliases = "Unknown";
        try {
            aliases = doc.getElementsByTag("aliases").text();
        } catch (Exception e) {
            System.out.println("problem at getAliases: " + e.getMessage());
        }
        if (aliases.isEmpty()) {
            return "Unknown";
        }
        return aliases;
    }

    private static String getGameBriefSummary(Document doc) {
        String description = "Unknown";
        try {
            description = doc.getElementsByTag("deck").text();
        } catch (Exception e) {
            System.out.println("problem at getGameBriefSummary: " + e.getMessage());
        }
        if (description.isEmpty()) {
            return "Unknown";
        }
        return description;
    }

    private static String getImageUrl(Document doc) {
        Elements thumbImage = null;
        Elements superImage = null;
        try {
            thumbImage = doc.getElementsByTag("thumb_url");
            superImage = doc.getElementsByTag("super_url");
        } catch (Exception e) {
            System.out.println("problem at getImageUrl: " + e.getMessage());
            return "Unknown";
        }
        if (superImage != null) {
            if (superImage.size() >= 1) {
                return superImage.text();
            }
        }
        if (thumbImage != null) {
            if (thumbImage.size() >= 1) {
                return thumbImage.text();
            }
        }
        return "Unknown";

    }

    private static String getGameName(Document doc) {
        String gameName = "Unknown";
        try {
            gameName = doc.getElementsByTag("name").first().text();
        } catch (Exception e) {
            System.out.println("problem at getName: " + e.getMessage());
        }
        if (gameName.isEmpty()) {
            return "Unknown";
        }
        return gameName;
    }

    private static String getGamePlatforms(Document doc) {
        Element platforms = null;
        Elements platformNames = null;
        try {
            platforms = doc.getElementsByTag("platforms").first();
            platformNames = platforms.getElementsByTag("name");
        } catch (Exception e) {
            System.out.println("problem at getGamePlatforms: " + e.getMessage());
            return "Unknown";
        }
        String platformsFinal = "";
        if (platformNames == null) {
            return "Unknown";
        }
        for (int i = 0; i < platformNames.size(); i++) {
            if (i == platformNames.size() - 1) {
                platformsFinal += platformNames.get(i).text();
            } else {
                platformsFinal += platformNames.get(i).text() + "-@-";
            }
        }
        if (platformsFinal.isEmpty()) {
            return "Unknown";
        }

        return platformsFinal;
    }

    private static String getGameGenres(Document doc) {
        Element genres = null;
        Elements genreNames = null;
        try {
            genres = doc.getElementsByTag("genres").first();
            genreNames = genres.getElementsByTag("name");
        } catch (Exception e) {
            System.out.println("problem at getGameGenres: " + e.getMessage());
            return "Unknown";
        }
        String genresFinal = "";
        if (genreNames == null) {
            return "Unknown";
        }
        for (int i = 0; i < genreNames.size(); i++) {
            if (i == genreNames.size() - 1) {
                genresFinal += genreNames.get(i).text();
            } else {
                genresFinal += genreNames.get(i).text() + "-@-";
            }
        }
        if (genresFinal.isEmpty()) {
            genresFinal = "Unknown";
        }
        return genresFinal;
    }

    private static String getGamePublishers(Document doc) {
        Element publishers = null;
        Elements publisherNames = null;
        try {
            publishers = doc.getElementsByTag("publishers").first();
            publisherNames = publishers.getElementsByTag("name");
        } catch (Exception e) {
            System.out.println("problem at getGamePublishers: " + e.getMessage());
            return "Unknown";
        }
        String publishersFinal = "";
        if (publisherNames == null) {
            return "Unknown";
        }
        for (int i = 0; i < publisherNames.size(); i++) {
            if (i == publisherNames.size() - 1) {
                publishersFinal += publisherNames.get(i).text();
            } else {
                publishersFinal += publisherNames.get(i).text() + "-@-";
            }
        }
        if (publishersFinal.isEmpty()) {
            publishersFinal = "Unknown";
        }
        return publishersFinal;
    }

    private static String getGameUrl(Document doc) {
        String gameUrl = "Unknown";
        try {
            Elements els = doc.getElementsByTag("site_detail_url");
            for (Element e : els) {
                if (e.text().contains("3030-")) {
                    System.out.println("FOUND: " + e.text());
                    gameUrl = e.text();
                }
            }
        } catch (Exception e) {
            System.out.println("problem at getGameUrl: " + e.getMessage());
            return "Unknown";
        }
        if (gameUrl.isEmpty()) {
            gameUrl = "Unknown";
        }

        return gameUrl;
    }

    private static String getGameDescription(Document doc) {
        String description = "Unknown";
        try {
            description = doc.getElementsByTag("description").text();
        } catch (Exception e) {
            System.out.println("problem at getGameDescription: " + e.getMessage());
            return "Unknown";
        }
        if (description.isEmpty()) {
            description = "Unknown";
        }
        return description;
    }

    private static String getDevelopers(Document doc) {

        Element developers = null;
        Elements developerNames = null;
        try {
            developers = doc.getElementsByTag("developers").first();
            developerNames = developers.getElementsByTag("name");

        } catch (Exception e) {
            System.out.println("problem at getGameGenres: " + e.getMessage());
            return "Unknown";
        }
        String developersFinal = "";
        if (developerNames == null) {
            return "Unknown";
        }
        for (int i = 0; i < developerNames.size(); i++) {
            if (i == developerNames.size() - 1) {
                developersFinal += developerNames.get(i).text();
            } else {
                developersFinal += developerNames.get(i).text() + "-@-";
            }
        }
        if (developersFinal.isEmpty()) {
            developersFinal = "Unknown";
        }
        return developersFinal;
    }

    private static String getReleaseDate(Document doc) {
        String releaseDate = "Unknown";
        try {
            releaseDate = doc.getElementsByTag("original_release_date").text().split(" ")[0];
        } catch (Exception e) {
            System.out.println("problem at getGameDescription: " + e.getMessage());
            return "Unknown";
        }
        if (releaseDate.isEmpty()) {
            releaseDate = "Unknown";
        }
        return releaseDate;
    }

    private static Game parseGame(Document doc) {
        String aliases = getAliases(doc);
        String gameDescription = getGameBriefSummary(doc);
        String imageUrl = getImageUrl(doc);
        String gameName = getGameName(doc);
        String platforms = getGamePlatforms(doc);
        String genres = getGameGenres(doc);
        String gamePublishers = getGamePublishers(doc);
        String gameUrl = getGameUrl(doc);
        String developers = getDevelopers(doc);
        String releaseDate = getReleaseDate(doc);
        System.out.println("Name: " + gameName);
        System.out.println("Aliases: " + aliases);
        System.out.println("Description: " + gameDescription);
        System.out.println("Platforms: " + platforms);
        System.out.println("Genres: " + genres);
        System.out.println("Publishers: " + gamePublishers);
        System.out.println("Developers:" + developers);
        System.out.println("gameUrl: " + gameUrl);
        System.out.println("imageUrl: " + imageUrl);
        System.out.println("Release date: " + releaseDate);
        Game game = new Game(gameUrl, gameName, aliases, gameDescription, genres, imageUrl, releaseDate, platforms, gamePublishers, developers);

        return game;
    }

    public static void main(String[] args) {
        getAllGamesInfos();
    }
}
