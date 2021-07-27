package personal.blog.service.logic;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import personal.blog.dto.NewsDto;
import personal.blog.dto.SingleNewsDto;
import personal.blog.entity.News;
import personal.blog.entity.paging.Pagination;
import personal.blog.repository.NewsRepository;
import personal.blog.service.NewsService;
import personal.blog.utility.Scraper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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
    public Pagination getNews(int page, String website) {
        Pageable pageable = PageRequest.of(page - 1, ARTICLES_PER_PAGE, Sort.by("id").descending());
        Page<News> news = website == null ? newsRepository.findAll(pageable) : newsRepository.findNewsByWebsite(website, pageable);
        Page<NewsDto> newsDTOS = news.map(n -> mapper.map(n, NewsDto.class));
        return Pagination.of(newsDTOS.getTotalPages(), newsDTOS.getNumber(), newsDTOS.getContent());
    }

    @Override
    public void getNewsFromKHTV(String url) {
        List<NewsDto> newsDtos = scraper.getKhoaHocTv(url, newsRepository);
        List<News> news = convert(newsDtos);
        newsRepository.saveAll(news);
    }

    @Override
    public void getNewsFromTV(String url) {
        List<NewsDto> newsDtos = scraper.getTheVerge(url, newsRepository);
        List<News> news = convert(newsDtos);
        newsRepository.saveAll(news);
    }

    @Override
    public void getNewsFromVE(String url) {
        List<NewsDto> newsDtos = scraper.getVnExpress(url, newsRepository);
        List<News> news = convert(newsDtos);
        newsRepository.saveAll(news);
    }

    @Override
    public SingleNewsDto getNewsById(long id) {
        Optional<News> news = newsRepository.findById(id);
        news.orElseThrow(() -> new RuntimeException("can not find news with id =  " + id));
        return mapper.map(news.get(), SingleNewsDto.class);
    }

    @Override
    public void deleteByTime() {
        LocalDate expTime = LocalDate.now().minusDays(3);
        newsRepository.deleteByNowLessThan(expTime);
    }

    private List<News> convert(List<NewsDto> dtoList) {
        return dtoList
                .stream()
                .map(n -> mapper.map(n, News.class))
                .collect(Collectors.toList());
    }


}
