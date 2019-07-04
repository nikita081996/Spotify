package nikitaverma.example.com.spotify.model.search_api;

public class Items {
    private Images[] images;

    private Followers followers;

    private String[] genres;

    private String popularity;

    private String name;

    private String href;

    private String id;

    private String type;

    private External_urls external_urls;

    private String uri;

    public Images[] getImages ()
    {
        return images;
    }

    public void setImages (Images[] images)
    {
        this.images = images;
    }

    public Followers getFollowers ()
    {
        return followers;
    }

    public void setFollowers (Followers followers)
    {
        this.followers = followers;
    }

    public String[] getGenres ()
    {
        return genres;
    }

    public void setGenres (String[] genres)
    {
        this.genres = genres;
    }

    public String getPopularity ()
    {
        return popularity;
    }

    public void setPopularity (String popularity)
    {
        this.popularity = popularity;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getHref ()
    {
        return href;
    }

    public void setHref (String href)
    {
        this.href = href;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public External_urls getExternal_urls ()
    {
        return external_urls;
    }

    public void setExternal_urls (External_urls external_urls)
    {
        this.external_urls = external_urls;
    }

    public String getUri ()
    {
        return uri;
    }

    public void setUri (String uri)
    {
        this.uri = uri;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [images = "+images+", followers = "+followers+", genres = "+genres+", popularity = "+popularity+", name = "+name+", href = "+href+", id = "+id+", type = "+type+", external_urls = "+external_urls+", uri = "+uri+"]";
    }
}
