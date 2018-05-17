package cn.person.musicspider.result;

import java.util.List;
import java.util.Map;

public class Pagination<T> {

	private static Integer MAX_LIMIT = 100;
	private Integer pageNum=1;
	private Long total;
	private Integer offset=0;
	private Integer limit=30;
	private Integer startPage;//当前页面的第一页
	private Integer endPage;//当前页面的最后一页
	private Integer totalPage;
	private Integer pageSize=10;//导航条显示多少页
	private List<T> items;
	private Map<String, Object> params;

	public Pagination() {
	}

	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		if(pageNum==null||pageNum<1){
			return;
		}
		this.pageNum = pageNum;
		this.offset = (pageNum-1)*limit;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		if(total!=null&&total>0){
			totalPage = (int) ((total+limit-1)/limit);
		}
		if(pageNum>totalPage){
			pageNum=totalPage;
		}
		this.total = total;
		int i=(pageSize-1)/2;
		startPage = pageNum-(i+1);
		if(startPage<1){//修复起始页码
			startPage=1;
		}
		endPage = startPage+pageSize-1;
		if(endPage>totalPage){
			endPage=totalPage;
		}
		startPage = endPage-(pageSize-1);
	}
	public Integer getOffset() {
		return offset;
	}
	public Integer getStartPage() {
		return startPage;
	}
	public void setStartPage(Integer startPage) {
		this.startPage = startPage;
	}
	public Integer getEndPage() {
		return endPage;
	}
	public void setEndPage(Integer endPage) {
		this.endPage = endPage;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	
	public List<T> getItems() {
		return items;
	}
	public void setItems(List<T> items) {
		this.items = items;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		if(limit==null||limit>MAX_LIMIT)return;
		this.limit = limit;
	}
	public Map<String, Object> getParams() {
		return params;
	}
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Pagination [pageNum=");
		builder.append(pageNum);
		builder.append(", total=");
		builder.append(total);
		builder.append(", offset=");
		builder.append(offset);
		builder.append(", limit=");
		builder.append(limit);
		builder.append(", startPage=");
		builder.append(startPage);
		builder.append(", endPage=");
		builder.append(endPage);
		builder.append(", totalPage=");
		builder.append(totalPage);
		builder.append(", pageSize=");
		builder.append(pageSize);
		builder.append(", items=");
		builder.append(items);
		builder.append("]");
		return builder.toString();
	}
	
}
