//package com.userservice;
//
//import com.userservice.entities.Rating;
//import com.userservice.external.services.RatingService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.stereotype.Service;
//
//@SpringBootTest
//class UserSeviceApplicationTests {
//
//	@Test
//	void contextLoads() {
//	}
//
//	@Autowired
//	private RatingService ratingService;
//
//	@Test
//	void createRating(){
//		Rating rating = Rating.builder().ratings(10).userId("").hotelId("").
//				feedback("this is created through feign client help").build();
//
//		Rating rating1 = ratingService.createRating(rating);
//		System.out.println("new Rating Created");
//	}
//
//}
