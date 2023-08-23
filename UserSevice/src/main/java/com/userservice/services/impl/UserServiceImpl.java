package com.userservice.services.impl;

import com.userservice.entities.Hotel;
import com.userservice.entities.Rating;
import com.userservice.entities.User;
import com.userservice.exceptions.ResourceNotFoudException;
import com.userservice.external.services.HotelService;
import com.userservice.repository.UserRepository;
import com.userservice.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.file.ReadOnlyFileSystemException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        //generating unique userId
        String randonUserId = UUID.randomUUID().toString();
        user.setUserId(randonUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers()
    {
        //todo
        //implement rest temp for all users
        return userRepository.findAll();
    }

    @Override
    public User getUserById(String userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoudException("User with given id is not found on server !! : " + userId));

        //fetch ratings from rating microservice
        //http://localhost:8083/ratings/users/8dfec018-87d7-499f-a09e-038dc25317e3
        Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(), Rating[].class);
        logger.info("{} ",ratingsOfUser);

        //used Rating[] because of stream because stream thinks it asa linkedhashset
        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();

        List<Rating> ratingList = ratings.stream().map(rating -> {
            //api call to hotel service to get the hotel
            //http://localhost:8082/hotels/getHotelById/bcc88538-a2d7-4dbb-a2f9-5c5068b95c1a

           //rest Template way
            //ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/getHotelById/"+rating.getHotelId(), Hotel.class);
           //logger.info("response status code",forEntity.getStatusCode());


           //feign client way
            Hotel hotel = hotelService.getHotel(rating.getHotelId());

            //set the hotel to rating
            rating.setHotel(hotel);
            //return rating
            return rating;
        }).collect(Collectors.toList());

        user.setRatings(ratingList);
        return user;
    }
}
