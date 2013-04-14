package pl.wsiadamy.webapp.controller.util;

public class Paginator {
	private int itemsCount;
	
	private int limit;
	
	private int page;
	
	public Paginator(int limit, int itemsCount, int page) {
		setLimit(limit);
		setItemsCount(itemsCount);
		setPage(page);
	}
	
	public int getOffset() {
		return getLimit() * (getPage() - 1);
	}
	
	public int getItemsCount() {
		return itemsCount;
	}

	private void setItemsCount(int itemsCount) {
		this.itemsCount = itemsCount;
	}

	public int getLimit() {
		return limit;
	}

	private void setLimit(int limit) {
		this.limit = limit;
	}

	public int getPage() {
		return page;
	}
	
	private void setPage(int page) {
		if(getItemsCount() / getLimit() < page)
			page = 1;
		
		this.page = page;
	}
}
