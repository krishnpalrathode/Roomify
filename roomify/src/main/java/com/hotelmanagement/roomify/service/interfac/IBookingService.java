package com.hotelmanagement.roomify.service.interfac;

import com.hotelmanagement.roomify.dto.Response;
import com.hotelmanagement.roomify.entity.Booking;

public interface IBookingService {
    Response saveBooking(Long roomId, Long userId, Booking bookingRequest);

    Response findBookingByConfirmationCode(String confirmationCode);

    Response getAllBookings();

    Response cancelBooking(Long bookingId);
}
