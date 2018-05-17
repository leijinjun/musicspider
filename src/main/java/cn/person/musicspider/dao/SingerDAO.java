package cn.person.musicspider.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.person.musicspider.pojo.Singer;

@Component
public class SingerDAO {

	@Autowired
	private DataSource dataSource;
	
	public void addSinger(List<Singer> singers){
		Connection conn = null;
		PreparedStatement st = null;
		Savepoint savepoint = null;
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			String sql = "insert into cs_singer(singer_id,singer_name,title,singerURL,photoURL,descURL,is_hot)VALUES(?,?,?,?,?,?,?)";
			st = conn.prepareStatement(sql);
			int i=0;
			for (Singer singer : singers) {
				st.setLong(1, singer.getSingerId());
				st.setString(2, singer.getSingerName());
				st.setString(3, singer.getTitle());
				st.setString(5, singer.getPhotoURL());
				st.setBoolean(7, singer.getIsHot());
				st.addBatch();
				if(i%10==0){
					 savepoint = conn.setSavepoint();
				}
			}
			st.executeBatch();
			conn.commit();
			st.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback(savepoint);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			if(st!=null){
				try {
					st.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
