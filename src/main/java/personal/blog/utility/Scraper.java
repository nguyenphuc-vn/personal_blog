package personal.blog.utility;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import personal.blog.dto.NewsDTO;
import personal.blog.entity.News;
import personal.blog.repository.NewsRepository;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Component
public class Scraper {
    private static final Logger logger = Logger.getLogger(Scraper.class.getName());


    public List<NewsDTO> getKhoaHocTv(String url, NewsRepository repository) {
        List<NewsDTO> newsDTOList = new ArrayList<>();
        try {
            Elements items = getElements(url, "listitem");
            for (Element item : items) {
                Element titleEle = item.getElementsByClass("title").get(0);
                String href = url + titleEle.attr("href");
                if (exists(href, repository)) {
                    continue;
                }
                Element imgEle = item.getElementsByTag("img").get(0);
                Element divEle = item.getElementsByClass("desc").get(0);
                String title = titleEle.html();
                String image = imgEle.attr("data-src");
                String desc = divEle.html();
                //logger.info("title : " + title + " href : " + href + " image : " + image + " desc : " + desc);
                String content = getContent(0, href, 1);
                newsDTOList.add(NewsDTO.builder(title, desc, content, image, href, "KH"));
            }
        } catch (IOException ex) {
            logger.info("URL : " + url + "  ERROR");
        }
        return newsDTOList;
    }

    public List<NewsDTO> getTheVerge(String url, NewsRepository repository) {
        List<NewsDTO> newsDTOList = new ArrayList<>();
        try {
            Elements items = getElements(url, "c-entry-box--compact c-entry-box--compact--article");
            for (Element e : items) {
                Element anchorEle = e.getElementsByClass("c-entry-box--compact__image-wrapper").get(0);
                String href = anchorEle.attr("href");
                if (exists(href, repository)) {
                    continue;
                }
                Element titleEle = e.getElementsByClass("c-entry-box--compact__title").get(0)
                        .getElementsByTag("a").get(0);
                String image = anchorEle.select("img").attr("abs:src");
                String title = titleEle.html();
                String content = getContent(1, href, 2);
                String desc = getDesc(content);
                //logger.info("title : " + title + " desc : "+desc + " content : "+content+" href : "+href );
                newsDTOList.add(NewsDTO.builder(title, desc, content, image, href, "TV"));
            }
        } catch (IOException ex) {
            logger.info("URL : " + url + "  ERROR");
        }
        return newsDTOList;
    }

    public List<NewsDTO> getVnExpress(String url, NewsRepository repository) {
        List<NewsDTO> newsDTOList = new ArrayList<>();
        try {
            Elements items = getElementsFromRss(url, "item");
            for (Element e : items) {
                String descTag = e.select("description").text();
                String href = getFromDesc(0, descTag, "\"");
                if (exists(href, repository)) {
                    continue;
                }
                String title = e.select("title").text();
                String image = getFromDesc(1, descTag, "\"");
                String desc = getFromDesc(4, descTag, "");
                String content = getContent(0, href, 1);
                //logger.info("title : " + title + " desc : " + desc + " href : " + href + " image : " + image);
                newsDTOList.add(NewsDTO.builder(title, desc, content, image, href, "VE"));
            }
        } catch (IOException ex) {
            logger.info("URL : " + url + "  ERROR");
        }
        return newsDTOList;
    }

    private String getFromDesc(int num, String source, String sPattern) {
        String[] des = source.split(">");
        String subStr = des[num].substring(des[num].indexOf(sPattern));
        return subStr.replaceAll("\"", "");
    }

    private Elements getElements(String url, String cssClass) throws IOException {
        Document doc = Jsoup.connect(url).get();
        return doc.getElementsByClass(cssClass);
    }

    private Elements getElementsFromRss(String url, String tag) throws IOException {
        Document doc = Jsoup.parse(new URL(url).openStream(), "UTF-8", "", Parser.xmlParser());
        return doc.select(tag);
    }

    private String getDesc(String content) {
        return content.split("[,\n.]")[0];
    }

    private boolean exists(String href, NewsRepository repository) {
        Optional<News> news = repository.findNewsByHref(href);
        return news.isPresent();
    }

    private String getContent(int numStart, String href, int numMinus) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Document doc = Jsoup.connect(href).get();
            Elements items = doc.getElementsByTag("p");
            for (int i = numStart; i < items.size() - numMinus; i++) {
                String content = items.get(i).html();
                stringBuilder.append(content);
                stringBuilder.append("\n");
            }
        } catch (IOException ex) {
            logger.info("URL : " + href + "  ERROR");
        }
        return stringBuilder.toString();
    }
}