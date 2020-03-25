package naver;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import domain.MovieDTO;
import persistence.MovieDAO;

public class NaverMovie {
	public static void main(String[] args) throws IOException {
		// 네이버 
		// Orecle DB
		
		String base = "https://movie.naver.com/movie/bi/mi/pointWriteFormList.nhn?code=191431&type=after&isActualPointWriteExecute=false&isMileageSubscriptionAlready=false&isMileageSubscriptionReject=false&page=";
		int page = 1;
		String url = base + page;
		
		int score = 0, regdate = 0, count = 0, total = 0, checker;
		String title = "", writer = "", content = "", basedate, subdate;  
		double scoreAvg = 0.0; // 평균평점
		String compare = "";
		
		String titUrl = "https://movie.naver.com/movie/bi/mi/basic.nhn?code=191431";
		Document titDoc = Jsoup.connect(titUrl).get();
		
		MovieDAO mDao = new MovieDAO();
		
		label: while(true) {
			
			Document doc = Jsoup.connect(url).get();
			Elements reply = doc.select("div.score_result li");
			checker = Integer.parseInt(doc.select("div.score_total em").text());
			
			// 제목 (1) 하나만 가져올수있게 해주는 것 하나의 태그만 선택
			title = titDoc.select("h3.h_movie > a:nth-child(1)").text();
			System.out.println(title);

			for(int i = 0; i < reply.size(); i++) {
			// == for(Element one : reply){}
				count++;
				writer = reply.get(i).select("div.score_reple dl span").text();
				score = Integer.parseInt(reply.get(i).select("div.star_score > em").text());
				content = reply.get(i).select("div.score_reple > p").text();
				basedate = reply.get(i).select("div.score_reple em:last-child").text();
				subdate = basedate.substring(0, 10);
				regdate = Integer.parseInt(subdate.replace(".",""));
				
				// 마지막페이지 종료 하는 두번째 방식!! (쌤)
				// 중복안되는 작성자명을 가지고 해당 페이지의 첫번째 작성자를 확인하여
				// 중복 출력되는 페이지가 없도록 하는 것
				if(i == 0) {
					if(compare.equals(writer)) {
						break label;
						// label Java최식버젼에서 나온것
						// label 이란 이름으로 되어있는 곳에 가서 반복문을 깨부숨
						// 다른 명으로도 사용가능
					} else {
						compare = writer;
					}
				}
				MovieDTO mDto = new MovieDTO(title, content, writer, score, "naver", regdate);
				
				// DB에 저장
				mDao.addMovie(mDto);
				total += score;
				System.out.println(count);
				System.out.println("제목: "+title);
				System.out.println("작성자: "+writer);
				System.out.println("평점: "+score);
				System.out.println("내용: "+content);
				System.out.println("작성일자: "+regdate);				
			}
			// 네이버의 경우 마지막페이지 내용을 계속해서 다음페이지에 띄우기 때문에
			// 빈값으로 확인해서 빠져나오게 할수없음
			// 그래서 확인하는 방법을 다르게 생각해야함
			// 마지막페이지 종료 하는 첫번째 방식!! (대군씨)
			if(count == checker) {
				break;
			}
			page++;
			url = base+ page;
		}
		// 평균평점계산
		scoreAvg = (double)total/count;
		// 소수점 첫번째 자리까지 출력(나머지 버림)
		double result = Math.floor(scoreAvg);
		
		System.out.println("▒▒▒ 영화 "+title+" Naver 영화 평점 수집 결과 ▒▒▒▒▒▒▒▒▒▒");
		System.out.println("▒▒▒ "+(page-1)+"페이지에서 ");
		System.out.println("▒▒▒ "+count+"건 평점을 수집 완료 ▒▒▒▒▒▒▒▒▒▒");
		System.out.println("▒▒▒ 평균평점은 "+result+"점 ▒▒▒▒▒▒▒▒▒▒");
		System.out.println("▒▒▒▒▒▒▒▒▒▒ Naver 수집한 영상리뷰 총 "+count+"건 수집 ▒▒▒▒▒▒▒▒▒▒");

	}
}
