package cn.person.mq.enums;

public enum RoutingKey{

	EXCEPTION("spider.exception.v1","spider.exception.*","spider.exception","异常消息处理"),
	SPIDER_SINGER_CREATE("spider.singer.create.v1","spider.singer.create.*","spider.singer.create","歌手信息创建"),
	SPIDER_SINGER_UPDATE("spider.singer.update.v1","spider.singer.update.*","spider.singer.update","歌手信息更新"),
	SPIDER_SONG_CREATE("spider.song.create.v1","spider.song.create.*","spider.song.create","歌曲信息创建"),
	SPIDER_SONG_UPDATE("spider.song.update.v1","spider.song.update.*","spider.song.update","歌曲信息更新"),
	SPIDER_COMMENT_CREATE("spider.comment.create.v1","spider.comment.create.*","spider.comment.create","添加评论");

	private String sendKey;
	private String routingKey;
	private String queueName;
	private String node;
	RoutingKey(String sendKey,String routingKey,String queueName,String node){
		this.sendKey = sendKey;
		this.routingKey = routingKey;
		this.queueName = queueName;
		this.node = node;
	}
	public String getSendKey() {
		return sendKey;
	}
	public void setSendKey(String sendKey) {
		this.sendKey = sendKey;
	}
	public String getRoutingKey() {
		return routingKey;
	}
	public void setRoutingKey(String routingKey) {
		this.routingKey = routingKey;
	}
	public String getQueueName() {
		return queueName;
	}
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	public String getNode() {
		return node;
	}
	public void setNode(String node) {
		this.node = node;
	}
	
}
