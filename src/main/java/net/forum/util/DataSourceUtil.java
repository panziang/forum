package net.forum.util;

import org.apache.commons.dbcp2.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Properties;

/**
 * @program: forum
 * @description:
 * @author: LQY
 * @create: 2022-04-08 16:36
 **/
public class DataSourceUtil {
	
	private static DataSource ds;
	
	static {
		try {
			InputStream is=DataSourceUtil.class.getClassLoader().getResourceAsStream("database.properties");
			
			Properties properties = new Properties();
			properties.load(is);
			
			// 创建datasource
			ds = BasicDataSourceFactory.createDataSource(properties);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	public static DataSource getDataSource(){
		return ds;
	}
}
