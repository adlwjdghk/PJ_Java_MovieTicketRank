package daum;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class OneMovie {
	public static void main(String[] args) throws IOException {
		String url = "https://movie.daum.net/moviedb/grade?movieId=134091&type=netizen&page=1";
		int count = 0;
	
		Document doc = Jsoup.connect(url).get();
		
		Elements total = doc.select("div.review_info");
		System.out.println(total.text());
		for(Element element : total) {
		    count++;
			Elements star = element.select("em.emph_grade");
			Elements writer = element.select("em.link_profile");
			Elements content = element.select("p.desc_review");
			Elements reg = element.select("span.info_append");
			
			System.out.println(count);
			System.out.println("평점: "+star.text());
			System.out.println("작성자: "+writer.text());
			System.out.println("내용: "+content.text());
			System.out.println("작성일자: "+reg.text());
		}
		System.out.println("▒▒▒▒▒▒▒▒▒▒ Daum 수집한 영상리뷰 총 "+count+"건 수집 ▒▒▒▒▒▒▒▒▒▒");
		
		// 쌤
		String url1 = "https://movie.daum.net/moviedb/grade?movieId=134091&type=netizen&page=1";
		Document doc1 = Jsoup.connect(url1).get();
		Elements reply = doc1.select("ul.list_netizen div.review_info"); // 리뷰수집
		
		Elements movie = doc1.select("h2.tit_rel"); // 영화제목수집
		
		int score, regdate, count1 = 0;
		String writer1, content, basedate, subdate = "";
		for(Element one: reply) {
			count1++;
			writer1 = one.select("em.link_profile").text();
			score = Integer.parseInt(one.select("em.emph_grade").text());
			content = one.select("p.desc_review").text();
			basedate = one.select("span.info_append").text();
			subdate = basedate.substring(0, 10);
			regdate = Integer.parseInt(subdate.replace(".",""));
			
			System.out.println(count1);
			System.out.println("영화: "+movie.text());
			System.out.println("평점: "+score);
			System.out.println("작성자: "+writer1);
			System.out.println("내용: "+content);
			System.out.println("작성일자: "+regdate);
		}
		System.out.println("▒▒▒▒▒▒▒▒▒▒ Daum 수집한 영상리뷰 총 "+count1+"건 수집 ▒▒▒▒▒▒▒▒▒▒");
	}
}
