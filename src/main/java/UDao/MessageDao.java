package UDao;
import modal.Message;
import Dao.BaseDao;
import modal.Message;
import modal.UserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MessageDao {

    public static int insert(Message msg) throws SQLException {
        String sql = "insert into message values(?,?,?,?,?,?,?,?)";
        Object[] params = {
                0,
                msg.getContent(),
                msg.getFromId(),
                msg.getToId(),
                msg.getHasRead(),
                msg.getCreateDate(),
                msg.getCommentNum(),
                msg.getAggreeNum()
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
            String sql = "select count(*) from userInfo.message";
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

    public static ArrayList<Message> selectAll(String method, int cpage,int count,int tsum){
        ArrayList<Message> result = new ArrayList<Message>();

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

            String sql =  "select * from userInfo.message order by "+method+" desc limit "+ String.valueOf(t1)+','+String.valueOf(t2);
            ps = conn.prepareStatement(sql);

//            System.out.println(t1);
//            System.out.println(t2);
            System.out.println(sql);

            //从数据库中获取数据
            rs = ps.executeQuery(sql);
            while (rs.next()) {

                Message msg = new Message(
                        rs.getInt("id"),
                        rs.getString("content"),
                        rs.getString("fromId"),
                        rs.getString("toId"),
                        rs.getInt("hasRead"),
                        rs.getDate("createDate"),
                        rs.getInt("commentNum"),
                        rs.getInt("aggreeNUm")
                );
                result.add(msg);
//                System.out.println("1");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }


        return result;
    }
}
