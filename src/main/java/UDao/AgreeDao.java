package UDao;

import Dao.BaseDao;
import modal.Agree;

import java.sql.*;
import java.util.ArrayList;

public class AgreeDao {
    //插入一个点赞
    public static int insert(Agree agree) throws SQLException {
//        System.out.println(sql)；


        String sql = "insert into agree values(?,?,?)";
        Object[] params = {
                0,
                agree.getMessage_id(),
                agree.getAgreeperson_id(),
        };
        System.out.println(agree.getMessage_id());
        System.out.println(agree.getAgreeperson_id());
        return BaseDao.exectuIUD(sql,params);
    }

    public static void delete(Integer id)
    {

        int count = 0;
        //1.连接到数据库
        Connection conn = BaseDao.getconn();
        ResultSet rs = null;
        PreparedStatement ps = null;
        Statement stmt = null;
        //2.拼接sql语句
        String sql = "delete from userInfo.agree where id="+id;
        try {
            System.out.println("准备删除id为："+id+" 的点赞记录...");

            //3.执行发送到数据库的sql语句
            stmt = conn.createStatement();
            count = stmt.executeUpdate(sql);//count接收方法的返回值，为0 表示删除失败

            if (count != 0) {
                System.out.print("删除成功！！！ ");
            } else {
                System.out.print("delete error!!! ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //4.关闭数据库连接，释放资源
            BaseDao.closeAll(rs,ps,conn,stmt);
        }
    }

    public static ArrayList<Agree> select_MId_PId(Integer message_id, Integer aggPer_id) throws SQLException {
        ArrayList<Agree> result = new ArrayList<Agree>();

        Connection conn = BaseDao.getconn();
        ResultSet rs = null;
        PreparedStatement ps = null;


        try {
            String sql = "select * from userInfo.agree where message_id=? and agreeperson_id=?";
            ps = conn.prepareStatement(sql);


            ps.setString(1,String.valueOf(message_id));
            ps.setString(2,String.valueOf(aggPer_id));

            rs = ps.executeQuery();

            while (rs.next()){
                Agree agree = new Agree(
                        rs.getInt("id"),
                        rs.getInt("message_id"),
                        rs.getInt("agreeperson_id"));

                result.add(agree);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }finally {
            BaseDao.closeAll(rs,ps,conn,null);
        }

        return result;
    }

}
