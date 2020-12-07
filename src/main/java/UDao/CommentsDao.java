package UDao;

import Dao.BaseDao;
import modal.Comments;
import modal.Message;

import java.sql.*;
import java.util.ArrayList;

public class CommentsDao {

    public static int insert(Comments comment) throws SQLException {
        String sql = "insert into comment values(?,?,?,?)";
        Object[] params = {
                0,
                comment.getName(),
                comment.getComment(),
                comment.getRelate(),
        };
//        System.out.println(sql)；
        return BaseDao.exectuIUD(sql,params);
    }

    public static int[] totalPage(int count) {

        //0为总记录数，1为页数
        int arr[] ={0,1};
        ResultSet rs = null;
        Connection conn = BaseDao.getconn();
        PreparedStatement ps = null;
        Statement stmt = null;

        try {
            String sql = "select count(*) from userInfo.comment";
            ps = conn.prepareStatement(sql);
            stmt =conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery(sql);
            while (rs.next()){
                arr[0] = rs.getInt(1);

            }
            arr[1] = arr[0]/count;
            if (arr[0]%count != 0)
                arr[1] +=1;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }finally {
            BaseDao.closeAll(rs,ps,conn,stmt);
        }


        return arr;

    }

    public static ArrayList<Comments> selectAll(String method, int cpage, int count, int tsum){
        ArrayList<Comments> result = new ArrayList<Comments>();

        //声明结果集
        ResultSet rs = null;
        //获取连接对象
        Connection conn = BaseDao.getconn();

        PreparedStatement ps = null;

        try {
            Integer t2 = count;
            Integer t1 = (cpage-1)*count;
            if(tsum-t1 < count)
                t2 = tsum-t1;

            String sql =  "select * from userInfo.comment order by "+method+" desc limit "+ String.valueOf(t1)+','+String.valueOf(t2);
            ps = conn.prepareStatement(sql);

//            System.out.println(t1);
//            System.out.println(t2);
            System.out.println(sql);

            //从数据库中获取数据
            rs = ps.executeQuery(sql);
            while (rs.next()) {

                Comments com = new Comments(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("comment"),
                        rs.getInt("relate")

                );
                result.add(com);
//                System.out.println("1");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }


        return result;
    }

}
