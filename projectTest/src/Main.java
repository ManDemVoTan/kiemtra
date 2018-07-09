import javafx.geometry.Pos;
import netscape.javascript.JSObject;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

public class Main {
    String fileName = "filename.txt";
    public static void main(String[] args) {
        //Tạo Obect Post và bỏ dữ liệu vào cho nó
         Post p1 = new Post ();
         p1.setId (1);
         p1.setPostId ("1");
         p1.setName ("thor");
         p1.setAction ("post");
         p1.setContent ("Hello everyone");
         Post p2 = new Post ();
         p2.setId (6);
         p2.setPostId ("1");
         p2.setName ("thor");
         p2.setAction ("like");
        Post p3 = new Post ();
        p3.setId (7);
        p3.setPostId ("1");
        p3.setName ("Zera8");
        p3.setAction ("comment");
        p3.setContent ("This is content");
        Post p4= new Post ();
        p4.setId (6);
        p4.setPostId ("1");
        p4.setName ("thor");
        p4.setAction ("like");
        Post p5 = new Post ();
        p5.setId (9);
        p5.setPostId ("1");
        p5.setName ("Linh");
        p5.setAction ("comment");
        p5.setContent ("Hi");
        Post p6 = new Post ();
        p6.setId (10);
        p6.setPostId ("2");
        p6.setName ("Linh");
        p6.setAction ("post");
        p6.setContent ("Post second");
        Post p7 = new Post ();
        p7.setId (12);
        p7.setPostId ("2");
        p7.setName ("Linh");
        p7.setAction ("comment");
        p7.setContent ("Hi");
        ////Tạo ra Danh sách Post rỗng à bỏ các Post mình đã khai báo ở trên vào
        List<Post> posts = new ArrayList<> ();
        posts.add (p1);
        posts.add (p2);
        posts.add (p3);
        posts.add (p4);
        posts.add (p5);
        posts.add (p6);
        posts.add (p7);
//        để viết danh sách trên vào file
        Main main = new Main ();

        main.writeFile (posts);

        //Đọc file
                main.readFile ();
    }
    public void writeFile(List<Post> posts){
        try (PrintStream out = new PrintStream(new FileOutputStream(fileName))) {
//            vòng for để đọc hết các post trong danh sách
            for (int i = 0; i < posts.size (); i++) {
                out.print (posts.get (i).inPost ());
                if ( i<= posts.size ()) out.print ('\n');
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace ();
        }
    }
    public void readFile(){
//        tạo Danh sách post mới
        List<Post> posts = new ArrayList<> ();

        //tạo ra đối tượng line để lưu dữ liệu đổ về từ file
        String line = null;

        try {
            //đọc file
            FileReader fileReader =
                    new FileReader(fileName);

            // sử dụng bufferedReader
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
               String strPost[] = line.split (",");
                 Post post = new Post ();
                //Chuyển từ định dạng văn bản sang định dạng của Obect mình
               if ( ( strPost[0])!="null"){
                    String postId =  strPost[0].substring (3);
                    post.setId (Integer.valueOf (postId));
               };
                if ( (strPost[1])!="null") post.setPostId (strPost[1].substring (7));
                if ( ( strPost[2])!="null") post.setName (strPost[2].substring (5));
                if ( ( strPost[3])!="null")  post.setAction (strPost[3].substring (7));
                if (strPost.length>=5)  post.setContent (strPost[4].substring (8));
                posts.add (post);
            }
            posts.forEach (post -> System.out.println (post));

            //câu 2
            //Danh sách các post
            List<Post> listPost = new ArrayList<> ();
            posts.forEach (post -> {
                if ( post.getAction ().equals ("post") ) listPost.add (post);
            });

            //Hiển thị các post
            Map<Post,Map<String,List<Post>>> postMapMap = new HashMap<> ();
            listPost.forEach (post -> {
                Map<String,List<Post>> stringListMap = new HashMap<> ();

                List<Post> listLike = new ArrayList<> ();
                posts.forEach (post1 -> {
                    if ( post1.getAction ().equals ("like") && post1.getPostId ().equals (post.getPostId ())) listLike.add (post1);
                });

                stringListMap.put ("LIKE",listLike);
                List<Post> listComment = new ArrayList<> ();
                //Thêm Các comment
                posts.forEach (post1 -> {
                    if ( post1.getAction ().equals ("comment") && post1.getPostId ().equals (post.getPostId ())) listComment.add (post1);
                });
//                Hiển thị các comment
                stringListMap.put ("COMMENT",listComment);

                postMapMap.put (post,stringListMap);
            });
            count c = new count ();
            
            postMapMap.forEach ((post, stringListMap) -> {
                System.out.println ("----------POST----------");
                System.out.println (post.getName ()+" : "+post.getContent ());
                System.out.println ("LIKE : "+stringListMap.get ("LIKE").size ());

                stringListMap.get ("LIKE").forEach (post1 -> {

                });
                //Kiem tra max like cua cmt
                  if ( c.maxLike< stringListMap.get ("LIKE").size ()){
                      c.maxLike =stringListMap.get ("LIKE").size ();
                      c.idMaxLike=post.getId ();
                  }
                 //post nhieu cmt nhat
                if ( c.maxCmtInPost < stringListMap.get ("COMMENT").size ()){
                    c.maxCmtInPost = stringListMap.get ("COMMENT").size ();
                    c.idmaxCmtInPost = post.getId ();
                }
                System.out.println ("------comment------");
                 stringListMap.get ("COMMENT").forEach (post1 -> {
                    System.out.println ("  | "+post1.getName ()+" : "+post1.getContent ());
                });
            });
            posts.forEach (post -> {
                if ( post.getId ()==c.idMaxLike ){
                    System.out.println ("Post dk nhieu like nhat la post cua "+post.getName ()+" voi id "+post.getId ()+" duoc "+c.maxLike+" like");
                }
                if ( post.getId ()==c.idmaxCmtInPost ){
                    System.out.println ("Post dk nhieu cmt nhat la post cua "+post.getName ()+" voi id "+post.getId ()+" duoc "+c.maxLike+" cmt");
                }
            });

            // đóng
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            //k tìm thấy file
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        }
        catch(IOException ex) {
            //Lỗi IO
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }
    }

    }
class count{
    int maxLike = 0;
    int idMaxLike = 0;
    int maxCmtInPost = 0;
    int idmaxCmtInPost = 0;

}
class countDAO{
    int idUser = 0;
    int maxLike = 0;
    int maxCmt = 0;
}


