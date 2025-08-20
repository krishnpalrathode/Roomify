import React, { useState, useEffect } from 'react';
import DatePicker from 'react-datepicker';
import ApiService from '../../service/ApiService';
import 'react-datepicker/dist/react-datepicker.css';

const RoomSearch = ({handleSearchResult}) => {const [startDate, setStartDate] = useState(null);
const [endDate, setEndDate] = useState(null);
const [roomType, setRoomType] = useState('');
const [roomTypeList, setRoomTypeList] = useState([]);
const [error, setError] = useState('');

 useEffect(()=> {
    const fetchRoomTypes = async () => {
        try {
            const types = await ApiService.getRoomTypes();
            setRoomTypeList(types);
        } catch (error){
            console.error('Error fetching room types:', error.message);
        }
    };
    fetchRoomTypes();
 }, []);


    const showError = (message, timeout=5000) =>{
        setError(message);
        setTimeout(()=> {
            setError('');
        }, timeout);
    }

    const handleInternalSearch = async () =>{
        if(!startDate || !endDate || !roomType){
            showError('Please select all feilds');
            return false;
        }
        try {
            const formattedStartDate = startDate ? startDate.toISOString().split('T')[0] : null ;
            const formattedEndDate = endDate ? endDate.toISOString().split('T')[0]: null;
            const response = await ApiService.getAvailableRoomsByDateAndType(formattedStartDate, formattedEndDate, roomType);

            if(response.statusCode === 200){
                if(response.roomList.length === 0){
                    showError("Room not currently available for the room type and selected data range")
                    return ;
                }
                handleSearchResult(response.roomList);
                setError('');
            }
            
        }catch(err){
            const message = err.response?.data?.message || err.message || "An unexpected error occurred";
            showError(message)
        }
    }

      return (
    <section>
      <div className="search-container">
        <div className="search-field">
          <label>Check-in Date</label>
          <DatePicker
            selected={startDate}
            onChange={(date) => setStartDate(date)}
            dateFormat="dd/MM/yyyy"
            placeholderText="Select Check-in Date"
          />
        </div>
        <div className="search-field">
          <label>Check-out Date</label>
          <DatePicker
            selected={endDate}
            onChange={(date) => setEndDate(date)}
            dateFormat="dd/MM/yyyy"
            placeholderText="Select Check-out Date"
          />
        </div>

        <div className="search-field">
          <label>Room Type</label>
          <select value={roomType} onChange={(e) => setRoomType(e.target.value)}>
            <option disabled value="">
              Select Room Type
            </option>
            {roomTypeList.map((type) => (
              <option key={type} value={type}>
                {type}
              </option>
            ))}
          </select>
        </div>
        <button className="home-search-button" onClick={handleInternalSearch}>
          Search Rooms
        </button>
      </div>
      {error && <p className="error-message">{error}</p>}
    </section>
  );
};


export default RoomSearch;