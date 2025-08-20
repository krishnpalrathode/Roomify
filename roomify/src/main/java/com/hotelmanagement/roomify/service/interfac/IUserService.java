package com.hotelmanagement.roomify.service.interfac;

import com.hotelmanagement.roomify.dto.LoginRequest;
import com.hotelmanagement.roomify.dto.Response;
import com.hotelmanagement.roomify.entity.User;

public interface IUserService {

    Response register(User user);

    Response login(LoginRequest loginRequest);

    Response getAllUsers();

    Response getUserBookingHistory(String userId);

    Response deleteUser(String userId);

    Response getUserById(String userId);

    Response getMyInfo(String email);

}
