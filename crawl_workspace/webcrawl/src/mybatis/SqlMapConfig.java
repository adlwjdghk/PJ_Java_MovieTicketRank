package mybatis;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


public class SqlMapConfig {
	private static SqlSessionFactory sqlSessionFactory;
	
	static { // 정적 블럭 클래스 로딩시 1회만 실행되는 코드
		String resource = "mybatis/Configuration.xml";
		
		try {
			
			Reader reader = Resources.getResourceAsReader(resource);
			if(sqlSessionFactory == null) {
				sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
				// build 인 빌드전문가에게 객체생성을 맡기는 것 : 빌드패턴 : 빌드생성해달라고
				// 객체 생성만 딱 하기에는 공장이 너무 복잡해서 맡기는 것 
			}
			// builder가 factory를 하나 만들어주는 과정
		} catch (IOException e ) {
			e.printStackTrace();
		}
				
	} // end static  
	public static SqlSessionFactory getSqlSession() {
		return sqlSessionFactory;
	}
}
