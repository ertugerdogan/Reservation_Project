import React, { useState, useEffect } from 'react';
import initialFacilitiesData from '../data/facilities';
import FacilityCard from './FacilityCard';
import Calendar from './Calendar';
import './MainContent.css';
import UserCard from './UserCard';
import { Route, Routes, BrowserRouter as Router } from 'react-router-dom';
import NavigationBar from './NavigationBar';
import axios from 'axios';

function MainContent() {
  const [searchTerm, setSearchTerm] = useState('');
  const [searchResult, setSearchResult] = useState(null);
  const [newFacilityModal, setNewFacilityModal] = useState(false);
  const [facilitiesData, setFacilitiesData] = useState(initialFacilitiesData);
  const [newFacilityType, setNewFacilityType] = useState(null);
  const [newFacilityCapacity, setNewFacilityCapacity] = useState(null);
  const [newFacilityDescription, setNewFacilityDescription] = useState(null);
  const [selectedCardName, setSelectedCardName] = useState(null);
  const [restrictedUsers, setRestrictedUsers] = useState([]);
  const [showCalendar, setShowCalendar] = useState(false);
  const [showAllFacilities, setShowAllFacilities] = useState(false);
  const [selectedFacility, setSelectedFacility] = useState(null);
  const [searchFacilityTerm, setSearchFacilityTerm] = useState('');

  useEffect(() => {
    fetchFacilitiesData();
  }, []);

  const fetchFacilitiesData = async () => {
    try {
      const response = await axios.post('http://13.50.243.223:8080/facilities/listFacility');
      setFacilitiesData(response.data.data);
      console.log(response.data);
    } catch (error) {
      console.error('Error fetching facilities:', error);
    }
  };

  const handleSearch = async () => {
    try {
      const requestData = {
        name: searchTerm
      }
      console.log("searchTerm: ", searchTerm);
      const response = await axios.post('http://13.50.243.223:8080/students/search', requestData);
      const foundUser = response.data.data;
      if (response.data) {
        setSearchResult(foundUser);
      } else {
        setSearchResult(null);
      }
      console.log(response.data);
    } catch (error) {
      console.error('Error searching users:', error);
    }
  };

  const handleSearchFacility = async () => {
    try {
      const requestData = {
        facilityType: searchFacilityTerm
      }
      const response = await axios.post('http://13.50.243.223:8080/facilities/searchFacility', requestData);
      const foundFacility = response.data.data;
      if (foundFacility) {
        setSelectedFacility(foundFacility);
      } else {
        setSelectedFacility(null);
      }
      console.log(response.data);
    } catch (error) {
      console.error('Error searching facilities:', error);
    }
  };

  const handleNewFacilityChange = async () => {
    try {
      const requestData = {
        facilityType: newFacilityType,
        capacity: newFacilityCapacity,
        description: newFacilityDescription,
        location: "ITU",
      }
      console.log(requestData);
      const response = await axios.post('http://13.50.243.223:8080/facilities/addFacility', requestData);
      if (response.data) {
        fetchFacilitiesData();
      } else {
      }
    } catch (error) {
      console.error('Connection error:', error);
    }
  };

  const handleNewFacilityCancel = () => {
    setNewFacilityModal(false);
  };

  const handleCardClick = (cardName) => {
    if (!showCalendar) {
      setSelectedCardName(cardName);
    } else {
      setSelectedCardName(null);
    }
    setShowCalendar(!showCalendar);
  };

  const showRestrictedUsersList = async () => {
    try {
      const response = await axios.post('http://13.50.243.223:8080/students/listRestricted');
      const restrictedUsers = response.data.data;
      if (restrictedUsers.length > 0) {
        setRestrictedUsers(restrictedUsers);
      }
    } catch (error) {
      console.error('Error fetching users:', error);
    }
  };

  return (

    <Router>
      <NavigationBar />
      <div>
        <main>
          <Routes>
            <Route path="/restricted-user" element={
              <React.Fragment>
                <div className="search-container">
                  <h2>Please enter student name for arrange restriction state!</h2>
                  <br />
                  <input
                    type="text"
                    placeholder="Enter a name..."
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                  />
                  <button onClick={handleSearch}>Search</button>
                </div>
                {searchResult && (
                  <div className="search-result">
                    <h3>Search Result:</h3>
                    {searchResult.map((searchResult, index) => (
                      <UserCard key={index} user={{
                        id: searchResult.id,
                        firstName: searchResult.firstName,
                        lastName: searchResult.lastName
                      }} />
                    ))}
                  </div>
                )}
                <button onClick={showRestrictedUsersList}>Show Restricted Users</button>

                {restrictedUsers.length > 0 && (
                  <div className="restricted-users-list">
                    <h3>Restricted Users:</h3>
                    {restrictedUsers.map((restrictedUser, index) => (
                      <UserCard key={index} user={{
                        id: restrictedUser.id,
                        firstName: restrictedUser.firstName,
                        lastName: restrictedUser.lastName
                      }} />
                    ))}
                  </div>
                )}
              </React.Fragment>
            } />

            <Route path="/" element={
              <div>
                <h2>Welcome to the Admin Panel!</h2>

                <div className="search-container">
                  <input
                    type="text"
                    placeholder="Enter facility name..."
                    value={searchFacilityTerm}
                    onChange={(e) => setSearchFacilityTerm(e.target.value)}
                  />
                  <button onClick={handleSearchFacility}>Search Facility</button>
                </div>

                {selectedFacility && (
                  <div className="selected-facility-info">
                    <h3>Selected Facility:</h3>
                    <FacilityCard
                      facility={selectedFacility}
                      onCardClick={handleCardClick} />
                  </div>
                )}

                <button onClick={() => {
                  setShowAllFacilities(!showAllFacilities);
                  fetchFacilitiesData();
                }}>
                  {showAllFacilities ? 'Hide Facilities' : 'Show All Facilities'}
                </button>
                {showAllFacilities && (
                  <>
                    <h3>Facilities:</h3>
                    <div className="facility-list">
                      {facilitiesData.map((facility, index) => (
                        <FacilityCard
                          key={index}
                          facility={facility}
                          onCardClick={handleCardClick}
                        />
                      ))}
                    </div>
                  </>
                )}
                <button onClick={() => setNewFacilityModal(!newFacilityModal)}>{newFacilityModal ? 'Return' : 'Add New Facility'}</button>
                {newFacilityModal && (
                  <div className="modal">
                    <h3>Add New Facility</h3>
                    <form>
                      <label>Type:</label>
                      <select
                        name="type"
                        onChange={(e) => setNewFacilityType(e.target.value)}
                      >
                        <option value="GYM">GYM</option>
                        <option value="POOL">POOL</option>
                        <option value="TENNIS_COURT">TENNIS COURT</option>
                        <option value="TABLE_TENNIS">TABLE TENNIS</option>
                        <option value="BASKETBALL">BASKETBALL</option>
                        <option value="FOOTBALL">FOOTBALL</option>
                        <option value="VOLLEYBALL">VOLLEYBALL</option>
                      </select>
                      <label>Capacity:</label>
                      <input
                        type="text"
                        name="capacity"
                        onChange={(e) => setNewFacilityCapacity(e.target.value)}
                      />
                      <label>Description:</label>
                      <input
                        type="text"
                        name="description"
                        onChange={(e) => setNewFacilityDescription(e.target.value)}
                      />
                      <button type="button" onClick={handleNewFacilityChange}>
                        Save
                      </button>
                      <button type="button" onClick={handleNewFacilityCancel}>
                        Cancel
                      </button>
                    </form>
                  </div>
                )}
                {selectedCardName && (
                  <div className="selected-card-info">
                    <p>Selected Card Name: {selectedCardName}</p>
                  </div>
                )}
                {showCalendar && <Calendar />}
              </div>
            } />
          </Routes>
        </main>
      </div>
    </Router>
  );
}

export default MainContent;