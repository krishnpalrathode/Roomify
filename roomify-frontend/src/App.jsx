import { BrowserRouter, Routes, Route } from 'react-router-dom'
import './App.css'
import React from 'react'
import Navbar from './component/common/NavBar'
import HomePage from "./component/home/HomePage";
import { ProtectedRoute, AdminRoute } from './service/guard.jsx';
import FooterComponent from './component/common/Footer'
import AllRoomsPage from './component/booking_room/AllRoomPages';
import FindBookingPage from './component/booking_room/FindBookingPage';
import LoginPage from './component/auth/LoginPage';
import RegisterPage from './component/auth/RegisterPage';
import RoomDetailsPage from './component/booking_room/RoomDetailsPage';
import ProfilePage from './component/profile/ProfilePage';
import EditProfilePage from './component/profile/EditProfilePage';
import { Navigate } from "react-router-dom";
import AdminPage from './component/admin/AdminPage.jsx';
import ManageRoomPage from './component/admin/ManageRoomPage.jsx';
import EditRoomPage from './component/admin/EditRoomPage.jsx';
import AddRoomPage from './component/admin/AddRoomPage.jsx';
import ManageBookingsPage from './component/admin/ManageBookingPage.jsx';
import EditBookingPage from './component/admin/EditBookingPage.jsx';



function App() {
  

  return (
    <>
      <BrowserRouter>
      <div className="App">
        <Navbar/>
        <div className='content'> 
          <Routes>
            <Route exact path='/home' element={<HomePage/>}></Route>
            <Route exact path='/rooms' element={<AllRoomsPage/>}></Route>
            <Route exact path='/find-booking' element={<FindBookingPage/>}></Route>
            <Route exact path='/login' element={<LoginPage/>}></Route>
            <Route path="/register" element={<RegisterPage/>} />

            <Route path="/room-details-book/:roomId"
              element={<ProtectedRoute element={<RoomDetailsPage/>} />}
            />
            <Route path="/profile"
              element={<ProtectedRoute element={<ProfilePage />} />}
            />
            <Route path="/edit-profile"
              element={<ProtectedRoute element={<EditProfilePage />} />}
            />
            
            
          <Route path='*' element={<Navigate to ="/home"/>}/>


          {/* Admin Routes */}
            <Route path="/admin"
              element={<AdminRoute element={<AdminPage/>} />}
            />
            <Route path="/admin/manage-rooms"
              element={<AdminRoute element={<ManageRoomPage/>} />}
            />
            <Route path="/admin/edit-room/:roomId"
              element={<AdminRoute element={<EditRoomPage/>} />}
            />
            <Route path="/admin/add-room"
              element={<AdminRoute element={<AddRoomPage/>} />}
            />
            <Route path="/admin/manage-bookings"
              element={<AdminRoute element={<ManageBookingsPage/>} />}
            />
            <Route path="/admin/edit-booking/:bookingCode"
              element={<AdminRoute element={<EditBookingPage/>} />}
            />


           <Route path="*" element={<Navigate to="/login" />} />

          </Routes>
        </div>
        <FooterComponent/>
        </div>
        </BrowserRouter>
    </>
  )
}

export default App
