package cn.musicspider.dao.sequence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.locks.ReentrantLock;

import javax.sql.DataSource;

import cn.person.musicspider.core.utils.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SequenceUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(SequenceUtil.class);
	private static ReentrantLock lock = new ReentrantLock();
	
	private static DataSource dataSource;
	
	static{
		dataSource=(DataSource) SpringContextUtil.getBean("dataSource");
	}
	public static Long getSequence(Sequence sequence){
		Connection conn=null;
		Statement st=null;
		Long id=null;
		try {
			lock.lock();
			conn = dataSource.getConnection();
			String sql="INSERT INTO `cs_sequence`(`name`) VALUES ('#')";
			sql = sql.replace("#", sequence.getText());
			st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			st.execute(sql,Statement.RETURN_GENERATED_KEYS);
			ResultSet gKeys = st.getGeneratedKeys();
			if (gKeys.next()) {
				id = gKeys.getLong(1);
				LOGGER.debug("squence ID:{}", id);
			}
			st.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("获取序列出错");
		}finally {
			lock.unlock();
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
		return id;
	}

}
