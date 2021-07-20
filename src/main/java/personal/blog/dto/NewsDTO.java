package personal.blog.dto;

//@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewsDTO {
    private Long id;

    private String title;

    private String description;

    private String content;

    private String image;

    private String website;

    private String href;

    public NewsDTO() {
    }

    public static NewsDTO builder(String title, String desc, String content, String image, String href,String website) {
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setTitle(title);
        newsDTO.setDescription(desc);
        newsDTO.setContent(content);
        newsDTO.setImage(image);
        newsDTO.setWebsite(website);
        newsDTO.setHref(href);
        return newsDTO;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }


}
