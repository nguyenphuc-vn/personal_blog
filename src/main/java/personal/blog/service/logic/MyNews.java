package personal.blog.service.logic;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import personal.blog.dto.NewsDTO;
import personal.blog.entity.News;
import personal.blog.entity.paging.Pagination;
import personal.blog.repository.NewsRepository;
import personal.blog.service.NewsService;
import personal.blog.utility.Scraper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyNews implements NewsService {

    private static final int ARTICLES_PER_PAGE = 20;
    private final NewsRepository newsRepository;
    private final Scraper scraper;
    private final ModelMapper mapper;

    public MyNews(NewsRepository newsRepository, Scraper scraper, ModelMapper mapper) {
        this.newsRepository = newsRepository;
        this.scraper = scraper;
        this.mapper = mapper;
    }

    @Override
    public Pagination getNews(Integer page) {
        Pageable pageable = PageRequest.of(page - 1, ARTICLES_PER_PAGE, Sort.by("id").descending());
        Page<News> news = newsRepository.findAll(pageable);
        Page<NewsDTO> newsDTOS = news.map(n -> mapper.map(n, NewsDTO.class));
        return Pagination.of(newsDTOS.getTotalPages(), newsDTOS.getNumber(), newsDTOS.getContent());
    }

    @Override
    public void getNewsFromKHTV(String url) {
        List<NewsDTO> newsDTOS = scraper.getKhoaHocTv(url, newsRepository);
        List<News> news = convert(newsDTOS);
        newsRepository.saveAll(news);
    }

    @Override
    public void getNewsFromTV(String url) {
        List<NewsDTO> newsDTOS = scraper.getTheVerge(url, newsRepository);
        List<News> news = convert(newsDTOS);
        newsRepository.saveAll(news);
    }

    @Override
    public void getNewsFromVE(String url) {
        List<NewsDTO> newsDTOS = scraper.getVnExpress(url, newsRepository);
        List<News> news = convert(newsDTOS);
        newsRepository.saveAll(news);
    }

    private List<News> convert(List<NewsDTO> dtoList) {
        return dtoList
                .stream()
                .map(n -> mapper.map(n, News.class))
                .collect(Collectors.toList());
    }


}
