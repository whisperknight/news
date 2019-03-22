package model;

public class PageBean {
	private int currentPage;// 当前页
	private int pageSize;// 页面总日记数

	public PageBean(int currentPage, int pageSize) {
		super();
		this.currentPage = currentPage;
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	//获取查询记录的起始位置
	public int getStart() {
		return (currentPage - 1) * pageSize;
	}
}
