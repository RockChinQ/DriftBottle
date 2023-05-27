package data.models;

public class Comment {
    public int id;
    public int user_id;
    public User user;
    public int bottle_id;
    public String message;
    public long created_at;
}
