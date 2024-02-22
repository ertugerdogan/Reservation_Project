import React, { useState } from 'react';

const FacilityCard = ({ facility, onCardClick }) => {
  const { facilityId, facilityType } = facility;
  const [isClicked, setIsClicked] = useState(false);
  const [buttonText, setButtonText] = useState('Select');

  const handleButtonClick = () => {
    setIsClicked(true);
    onCardClick(facilityId);
    setButtonText(isClicked ? 'Select' : 'Deselect');
  };
  return (
    <div className={`facility-card ${isClicked ? 'clicked' : ''}`} onClick={() => setIsClicked(!isClicked)}>
      <div className="title">{facilityType}</div>
      <div className="details">
        <p>Facility Id: {facilityId}</p>
        <button onClick={handleButtonClick}>{buttonText}</button>
      </div>
    </div>
  );
};

export default FacilityCard;
