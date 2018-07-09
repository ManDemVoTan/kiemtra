import java.io.Serializable;
public class Post implements Serializable {

    private  int id;
    private String postId;
    private String name;
    private String action;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String inPost(){
       String result = "id="+ id+",postId="+postId+",name="+name+",action="+action;
       if ( content!=null )result+=",content="+content;
       return result;
    }
// để hiển thị lúc in ra

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", postId='" + postId + '\'' +
                ", name='" + name + '\'' +
                ", action='" + action + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}