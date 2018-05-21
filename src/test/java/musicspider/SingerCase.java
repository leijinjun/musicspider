package musicspider;


import cn.person.musicspider.enums.AuthType;
import cn.person.musicspider.enums.Gender;
import cn.person.musicspider.enums.RelationType;
import cn.person.musicspider.pojo.Comment;
import cn.person.musicspider.pojo.User;
import cn.person.musicspider.service.CommentService;
import cn.person.musicspider.service.SingerService;
import cn.person.musicspider.service.UserService;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/applicationContext*.xml")
public class SingerCase {

	@Autowired
	private UserService userService;
	@Autowired
	private SingerService singerService;
	@Autowired
	private CommentService commentService;

	@Test
	public void test1(){
		Comment comment = new Comment();
		comment.setCommentId(-1L);
		comment.setUpdateTime(new Date());
		commentService.insertComment(comment);
	}
	@Test
	public void test2(){
		Comment comment = new Comment();
		comment.setCommentId(-1L);
		comment.setUpdateTime(new Date());
		comment.setBeReplied("");
		comment.setUpdateTime(new Date());
		comment.setCommentTime(new Date());
		comment.setContent("hahah");
		comment.setPraiseCount(21);
		comment.setRelationId(23255L);
		comment.setRelationType(RelationType.Song);
		comment.setUserId(35232L);
		commentService.insertComment(comment);
	}

	@Test
	public void test3(){
		Comment comment = commentService.getCommentById(-1L);
		System.out.println(JSONObject.toJSONString(comment));
	}

	@Test
	public void test4(){
		User user = new User();
		user.setUserId(-1L);
		user.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		userService.addUser(user);
	}

	@Test
	public void test5(){
		User user = new User();
		user.setUserId(-1L);
		user.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		user.setAuthType(AuthType.AUTH_V);
		user.setBirthDay(new Date());
		user.setArea("上海");
		user.setTitle("title");
		user.setPhotoUrl("http://img");
		user.setFansCount(23);
		user.setAttentionCount(1);
		user.setGender(Gender.MALE);
		user.setLevel(0);
		user.setVipType(1);
		user.setUserType(2);
		user.setNickname("冰与火");
		user.setTag("");
		userService.addUser(user);
	}

	@Test
	public void test6(){
		User user = userService.getUserById(-1L);
		System.out.println(JSONObject.toJSONString(user));
	}

	@Test
	public void test7(){
		User user = new User();
		user.setUserId(-1L);
		user.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		user.setAuthType(AuthType.AUTH_NO);
		user.setBirthDay(new Date());
		user.setArea("上海1");
		user.setTitle("title1");
		user.setPhotoUrl("http://img1");
		user.setFansCount(231);
		user.setAttentionCount(11);
		user.setGender(Gender.UNKNOWN);
		user.setLevel(01);
		user.setVipType(11);
		user.setUserType(21);
		user.setNickname("冰与火1");
		user.setTag("1");
		userService.updateUser(user);
	}

}
