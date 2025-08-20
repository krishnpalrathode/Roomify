package com.hotelmanagement.roomify.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hotelmanagement.roomify.dto.Response;
import com.hotelmanagement.roomify.dto.RoomDTO;
import com.hotelmanagement.roomify.entity.Room;
import com.hotelmanagement.roomify.exception.OurException;
import com.hotelmanagement.roomify.repo.BookingRepository;
import com.hotelmanagement.roomify.repo.RoomRepository;
import com.hotelmanagement.roomify.service.FileStorageService;
import com.hotelmanagement.roomify.service.interfac.IRoomService;
import com.hotelmanagement.roomify.utils.Utils;

@Service
public class RoomService implements IRoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Override
    public Response addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice, String description) {
        Response response = new Response();
        try {
            // IMAGE String imageUrl = awsS3Service.saveImageToS3(photo) but we dont have a
            // aws so it will be different
            String imageUrl = fileStorageService.saveFileLocally(photo);
            Room room = new Room();
            room.setRoomPhotoUrl(imageUrl);
            room.setRoomType(roomType);
            room.setRoomPrice(roomPrice);
            room.setRoomDescription(description);
            Room savedRoom = roomRepository.save(room);
            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(savedRoom);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setRoom(roomDTO);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error saving a room" + e.getMessage());
        }
        return response;
    }

    @Override
    public List<String> getAllRoomTypes() {
        return roomRepository.findDistinctRoomTypes();
    }

    @Override
    public Response getAllRooms() {
        Response response = new Response();
        try {
            List<Room> roomList = roomRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
            List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomListDTO(roomList);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setRoomList(roomDTOList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error saving a room" + e.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteRoom(Long roomId) {
        Response response = new Response();

        try {
            roomRepository.findById(roomId).orElseThrow(() -> new OurException("Room Not Found"));
            roomRepository.deleteById(roomId);
            response.setStatusCode(200);
            response.setMessage("successful");
        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
            ;
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(("Error saving a room" + e.getMessage()));
        }
        return response;
    }

    @Override
    public Response updateRoom(Long roomId, String description, String roomType, BigDecimal roomPrice,
            MultipartFile photo) {
        Response response = new Response();

        try {
            String imageUrl = null;
            if (photo != null && !photo.isEmpty()) {
                imageUrl = fileStorageService.saveFileLocally(photo);
            }
            Room room = roomRepository.findById(roomId).orElseThrow(() -> new OurException("Room not found"));
            if (roomType != null)
                room.setRoomType(roomType);
            if (roomPrice != null)
                room.setRoomPrice(roomPrice);
            if (description != null)
                room.setRoomDescription(description);
            if (imageUrl != null)
                room.setRoomPhotoUrl(imageUrl);

            Room updatedRoom = roomRepository.save(room);
            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(updatedRoom);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setRoom(roomDTO);
        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error saving a room" + e.getMessage());
        }
        return response;

    }

    @Override
    public Response getRoomById(Long roomId) {
        Response response = new Response();

        try {
            Room room = roomRepository.findById(roomId).orElseThrow(() -> new OurException("Room Not Found"));
            RoomDTO roomDTO = Utils.mapRoomrEntityToRoomDTOPlusBooking(room);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setRoom(roomDTO);
        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
            ;
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(("Error saving a room" + e.getMessage()));
        }
        return response;
    }

    @Override
    public Response getAvailableRoomsByDataAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {
        Response response = new Response();

        try {
            List<Room> availableRooms = roomRepository.findAvailableRoomsByDateAndTypes(checkInDate, checkOutDate,
                    roomType);
            List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomListDTO(availableRooms);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setRoomList(roomDTOList);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(("Error saving a room" + e.getMessage()));
        }
        return response;
    }

    @Override
    public Response getAllAvailableRooms() {
        Response response = new Response();

        try {
            List<Room> roomList = roomRepository.getAllAvailableRooms();
            List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomListDTO(roomList);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setRoomList(roomDTOList);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(("Error saving a room" + e.getMessage()));
        }
        return response;
    }

}
