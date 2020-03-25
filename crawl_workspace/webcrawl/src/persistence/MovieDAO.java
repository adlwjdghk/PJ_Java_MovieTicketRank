package persistence;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import domain.MovieDTO;
import mybatis.SqlMapConfig;

public class MovieDAO {
	SqlSessionFactory sqlSessionFactory = SqlMapConfig.getSqlSession();
	SqlSession sqlSession;
	int result;
	
	// Oracle DB에 크롤링 데이터를 저장하는 기능
	// mDto : 평점 1건
	public void addMovie(MovieDTO mDto) {
		// sql select문을 제외한 commit이 필요한 sql문이 올때 true를 적어 commit해준다
		sqlSession = sqlSessionFactory.openSession(true);
		try {
			// movie라는 이름의 mapper에 가서 addMovie의 이름을 가진 sql문을 실행시켜라
			// 실행시킬때 mDto를 가져가라
			// , 뒤에는 하나의 값만 들어갈수있다
			 result = sqlSession.insert("movie.addMovie", mDto);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
	}
}
