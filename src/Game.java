package nikos.giantbombcrawler;

public class Game {

    private String aliases;
    private String gameDescription;
    private String imageUrl;
    private String gameName;
    private String platforms;
    private String genres;
    private String gamePublishers;
    private String gameUrl;
    private String developers;
    private String releaseDate;

    public Game(String gameUrl, String name, String aliases, String gameDescription, String genres, String imageUrl,
            String releaseDate, String platforms, String publishers, String developers ) {
        this.aliases = aliases;
        this.gameDescription = gameDescription;
        this.imageUrl = imageUrl;
        this.gameName = name;
        this.platforms = platforms;
        this.genres= genres;
        this.gamePublishers = publishers;
        this.gameUrl = gameUrl;
        this.developers = developers;
        this.releaseDate = releaseDate;       
    }

    public String getAliases() {
        return aliases;
    }

    public String getGameDescription() {
        return gameDescription;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getGameName() {
        return gameName;
    }

    public String getPlatforms() {
        return platforms;
    }

    public String getGenres() {
        return genres;
    }

    public String getGamePublishers() {
        return gamePublishers;
    }

    public String getGameUrl() {
        return gameUrl;
    }

    public String getDevelopers() {
        return developers;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    @Override
    public String toString() {
        return "Game{\n" + "  gameName=" + gameName +  "\n  aliases=" + aliases + "\n  gameDescription=" + gameDescription  + "\n  platforms=" + platforms + "\n  genres=" + genres + "\n  gamePublishers=" + gamePublishers +  "\n  developers=" + developers + "\n  releaseDate=" + releaseDate + "\n  gameUrl=" + gameUrl + "\n  imageUrl=" + imageUrl+ "\n}";
    }
    
    
}
