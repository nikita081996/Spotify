package nikitaverma.example.com.spotify.model.search_api;

import nikitaverma.example.com.spotify.model.search_api.artists.Artists;

public class Search {

    private Artists artists;

    public Artists getArtists ()
    {
        return artists;
    }

    public void setArtists (Artists artists)
    {
        this.artists = artists;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [artists = "+artists+"]";
    }
}
