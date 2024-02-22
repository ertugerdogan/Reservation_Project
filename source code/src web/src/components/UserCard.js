import React, { useState } from 'react';
import './UserCard.css';
import axios from 'axios';

const UserCard = ({ user }) => {
  const [isRestricted, setIsRestricted] = useState(user.isRestricted);

  const handleButtonClick = async () => {
    const requestData = {
      id: user.id
    };

    try {
      const response = await axios.post('http://13.50.243.223:8080/students/restrict', requestData);
      if (response.data) {
        console.log("Kullanıcının yasak durumu değiştirildi.");
      } else {
        console.log("Bir hata oldu.");
      }
    } catch (error) {
      console.error('Error restricting user:', error);
    }
    setIsRestricted(!isRestricted);
  };

  return (
    <div>
      {user && (
        <div className="user-card">
          <p className="user-id">ID: {user.id}</p>
          <p className="user-info">Name: {`${user.firstName} ${user.lastName}`}</p>
          <p className="user-restrict">{isRestricted ? 'Not Restricted' : 'Restricted'}</p>
          <button onClick={handleButtonClick}>
            {isRestricted ? 'Allow' : 'Restrict'}
          </button>
        </div>
      )}
    </div>
  );
};

export default UserCard;
