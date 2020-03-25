package daum;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import domain.MovieDTO;
import persistence.MovieDAO;

public class PageMovie {
	public static void main(String[] args) throws IOException {
//		String base = "https://movie.daum.net/moviedb/grade?movieId=134091&type=netizen&page=";
//		int page = 1;
//		String url = base + page;
//		int count = 0; 
//		
//		while(true) {		
//			Document doc = Jsoup.connect(url).get();
//			
//			Elements total = doc.select("div.review_info");
//			
//			if(total.isEmpty()) {
//				break;
//			}
//			for(Element element : total) {
//			    count++;
//				Elements star = element.select("em.emph_grade");
//				Elements writer = element.select("em.link_profile");
//				Elements content = element.select("p.desc_review");
//				Elements reg = element.select("span.info_append");
//				
//				System.out.println(count);
//				System.out.println("평점: "+star.text());
//				System.out.println("작성자: "+writer.text());
//				System.out.println("내용: "+content.text());
//				System.out.println("작성일자: "+reg.text());
//			}
//			
//			page++;
//			url = base + page;
//		}
//		System.out.println("▒▒▒▒▒▒▒▒▒▒ Daum 수집한 영상리뷰 총 "+count+"건 수집 ▒▒▒▒▒▒▒▒▒▒");
		
		// 쌤
		String base1 = "https://movie.daum.net/moviedb/grade?movieId=134091&type=netizen&page=";
		int page1 = 1;
		String url1 = base1 + page1;
		
		
		int score, regdate, count = 0;
		String writer, content, basedate, subdate, title = "";
		int total = 0; // 평점을 모두 더하는 변수
		double scoreAvg = 0.0; // 평균평점
		
		MovieDAO mDao = new MovieDAO();
		
		// 페이지를 돌면서 댓글을 수집!
		while(true) {
			Document doc1 = Jsoup.connect(url1).get();
			Elements reply = doc1.select("ul.list_netizen div.review_info"); // 리뷰수집
			
			Elements movieName = doc1.select("h2.tit_rel"); // 영화제목수집
			title = movieName.text();
			// 마지막페이지면 수집종료
			if(reply.isEmpty()) {
				break;
			}
			for(Element one: reply) {
				count++;
				// Element형인 one.select()를 .text()로 String 문자열로 변환해서 변수에 넣어라
				writer = one.select("em.link_profile").text();
				score = Integer.parseInt(one.select("em.emph_grade").text());
				content = one.select("p.desc_review").text();
				basedate = one.select("span.info_append").text();
				subdate = basedate.substring(0, 10);
				regdate = Integer.parseInt(subdate.replace(".",""));
				
				// 누적 평점을 계산
				total += score; // total = total + score;
				
				MovieDTO mDto = new MovieDTO(title, content, writer, score, "daum", regdate);
				
				// DB에 저장
				mDao.addMovie(mDto);
				System.out.println(count);
				System.out.println("영화: "+title);
				System.out.println("평점: "+score);
				System.out.println("작성자: "+writer);
				System.out.println("내용: "+content);
				System.out.println("작성일자: "+regdate);
			}
			// 다음 페이지로 이동하기위해 page+1씩증가
			page1++;
			// 다음 페이지로 이동할 url
			url1 = base1 + page1;
		}	
		// 평균평점 계산
		scoreAvg = (double)total / count;
		
		// 소수점 첫번째 자리까지 출력(나머지 버림)
		double result = Math.floor(scoreAvg);
		
		System.out.println("▒▒▒ 영화 "+title+" Daum영화 평점 수집 결과 ▒▒▒▒▒▒▒▒▒▒");
		System.out.println("▒▒▒ "+(page1-1)+"페이지에서 ");
		System.out.println("▒▒▒ "+count+"건 평점을 수집 완료 ▒▒▒▒▒▒▒▒▒▒");
		System.out.println("▒▒▒ 평균평점은 "+result+"점 ▒▒▒▒▒▒▒▒▒▒");
		
	}
}
