package nikitaverma.example.com.spotify.model.search_api.playlist;

public class Playlists
{
    private String next;

    private String total;

    private String offset;

    private String previous;

    private String limit;

    private String href;

    private String[] items;

    public String getNext ()
{
    return next;
}

    public void setNext (String next)
    {
        this.next = next;
    }

    public String getTotal ()
    {
        return total;
    }

    public void setTotal (String total)
    {
        this.total = total;
    }

    public String getOffset ()
    {
        return offset;
    }

    public void setOffset (String offset)
    {
        this.offset = offset;
    }

    public String getPrevious ()
{
    return previous;
}

    public void setPrevious (String previous)
    {
        this.previous = previous;
    }

    public String getLimit ()
    {
        return limit;
    }

    public void setLimit (String limit)
    {
        this.limit = limit;
    }

    public String getHref ()
    {
        return href;
    }

    public void setHref (String href)
    {
        this.href = href;
    }

    public String[] getItems ()
    {
        return items;
    }

    public void setItems (String[] items)
    {
        this.items = items;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [next = "+next+", total = "+total+", offset = "+offset+", previous = "+previous+", limit = "+limit+", href = "+href+", items = "+items+"]";
    }
}
