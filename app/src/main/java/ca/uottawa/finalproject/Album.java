package ca.uottawa.finalproject;

/**
 * Class for an album
 */
public class Album {
    String idAlbum;
    String idArtist;
    String strArtist;
    String strAlbum;
    String strGenre;
    String intYearReleased;

    public Album(String idAlbum, String idArtist, String strArtist, String strAlbum, String strGenre, String intYearReleased) {
        this.idAlbum = idAlbum;
        this.idArtist = idArtist;
        this.strArtist = strArtist;
        this.strAlbum = strAlbum;
        this.strGenre = strGenre;
        this.intYearReleased = intYearReleased;
    }

    public String getIdAlbum() {
        return idAlbum;
    }

    public String getIdArtist() {
        return idArtist;
    }

    public String getStrArtist() {
        return strArtist;
    }

    public String getStrAlbum() {
        return strAlbum;
    }

    public String getStrGenre() {
        return strGenre;
    }

    public String getIntYearReleased() {
        return intYearReleased;
    }
}
