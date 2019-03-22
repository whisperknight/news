package model;

import java.util.Date;

public class News {
	private int newsId;
	private String title;
	private String content;
	private Date publishTime;
	private String author;
	private int typeId = -1;
	private String typeName;
	private int click;// 浏览次数
	private int isHead;// 是否是头条新闻
	private int isImage;// 是否是轮播图片
	private String imageName;// 轮播图片
	private int isHot;// 是否是热点新闻

	public News() {
		super();
	}

	public News(int newsId, String title) {
		super();
		this.newsId = newsId;
		this.title = title;
	}

	public int getNewsId() {
		return newsId;
	}

	public void setNewsId(int newsId) {
		this.newsId = newsId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getClick() {
		return click;
	}

	public void setClick(int click) {
		this.click = click;
	}

	public int getIsHead() {
		return isHead;
	}

	public void setIsHead(int isHead) {
		this.isHead = isHead;
	}

	public int getIsImage() {
		return isImage;
	}

	public void setIsImage(int isImage) {
		this.isImage = isImage;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public int getIsHot() {
		return isHot;
	}

	public void setIsHot(int isHot) {
		this.isHot = isHot;
	}
}
