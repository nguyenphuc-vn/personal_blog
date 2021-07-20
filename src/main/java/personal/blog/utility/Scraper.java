package personal.blog.utility;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import personal.blog.dto.NewsDTO;
import personal.blog.entity.News;
import personal.blog.repository.NewsRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
public class Scraper {
    private static final Logger logger = Logger.getLogger(Scraper.class.getName());

    public List<NewsDTO> getKhoaHocTv(String url, NewsRepository repository) {
        List<NewsDTO> newsDTOList = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url).get();
            Elements items = doc.getElementsByClass("listitem");
            for (int i = 0; i < items.size(); i++) {
                Element anchorItem1 = items.get(i).getElementsByClass("title").get(0);
                Element anchorItem2 = items.get(i).getElementsByTag("img").get(0);
                Element divItem = items.get(i).getElementsByClass("desc").get(0);
                String href = url + anchorItem1.attr("href");
                //logger.info(href);
                if (exists(href, repository)) {
                    continue;
                }
                String title = anchorItem1.html();

                String image = anchorItem2.attr("data-src");
                String desc = divItem.html();
                logger.info("title : " + title + " href : " + href + " image : " + image + " desc : " + desc);
                String content = getContent(href);
                // logger.info(content);
                newsDTOList.add(NewsDTO.builder(title, desc, content, image, href));

            }
        } catch (IOException ex) {
            logger.info("URL : " + url + "  ERROR");
        }
        //newsDTOList.forEach(n -> System.out.println(n.getDesc()));
        //logger.info(newsDTOList+"");
        return newsDTOList;
    }


    private boolean exists(String href, NewsRepository repository) {
        News news = repository.findNewsByLink(href);
        return news != null;
    }

    private String getContent(String href) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Document doc = Jsoup.connect(href).get();
            Elements items = doc.getElementsByTag("p");
            for (int i = 0; i < items.size() - 1; i++) {
                String content = items.get(i).html();
                stringBuilder.append(content);
                stringBuilder.append("\n");
                // logger.info(content+"");
            }
        } catch (IOException ex) {
            logger.info("URL : " + href + "  ERROR");
        }
        return stringBuilder.toString();
    }
}