package cn.person.musicspider.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;

import javax.sql.DataSource;

import cn.person.musicspider.web.vo.SongVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SongDAO {

	@Autowired
	private DataSource dataSource;

	public void addBatch(List<SongVo> songs) {
		Connection conn = null;
		PreparedStatement st = null;
		Savepoint savepoint = null;
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			String sql = "INSERT INTO `cloud_spider`.`cs_song` (`song_id`, `song_name`, `title`, `lyric`, `songURL`, `comment_count`, `duration`, `commentURL`, `score`, `alias`) VALUES (?,?,?,?,?,?,?,?,?,?);";
			st = conn.prepareStatement(sql);
			int i=0;
			for (SongVo s : songs) {
				st.setLong(1, s.getSongId());
				st.setString(2, s.getSongName());
				st.setString(3, s.getTitle());
				st.setString(4, s.getLyric());
				st.setInt(6, s.getCommentCount()==null?0:s.getCommentCount());
				st.setInt(7, s.getDuration());
				st.setInt(9, s.getScore());
				st.setString(10, s.getAlias());
				st.addBatch();
				if(i%5==0){
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
		st=null;
		conn=null;
	}
}
