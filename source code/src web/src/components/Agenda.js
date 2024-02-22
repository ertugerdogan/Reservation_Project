import React, { useState } from 'react';
import './Agenda.css';

const TimeSlots = ({ day }) => {
  const generateTimeSlots = () => {
    const timeSlots = [];
    for (let i = 8; i <= 18; i += 2) {
      timeSlots.push({
        slot: `${i}:00 - ${i + 2}:00`,
        isReserved: false,
      });
    }
    return timeSlots;
  };

  const [selectedSlot, setSelectedSlot] = useState(null);
  const [timeSlots, setTimeSlots] = useState(generateTimeSlots());

  const handleReserveClick = () => {
    if (selectedSlot) {
      const updatedTimeSlots = timeSlots.map((timeSlot) =>
        timeSlot.slot === selectedSlot ? { ...timeSlot, isReserved: true } : timeSlot
      );
      setTimeSlots(updatedTimeSlots);
    }
  };

  return (
    <div className="time-slots-card">
      <h4>Time Slots for {day}:</h4>
      <div className="time-slots-container">
        {timeSlots.map((timeSlot, index) => (
          <div
            key={index}
            onClick={() => setSelectedSlot(timeSlot.slot)}
            className={`time-slot-card ${timeSlot.isReserved ? 'reserved' : ''} ${selectedSlot === timeSlot.slot ? 'selected' : ''}`}
          >
            {timeSlot.isReserved && <span className="full-tag">(full)</span>}
            {timeSlot.slot}
          </div>
        ))}
      </div>
      {selectedSlot && !timeSlots.find((timeSlot) => timeSlot.slot === selectedSlot).isReserved && (
        <button className="reserve-button" onClick={handleReserveClick}>
          Reserve {selectedSlot}
        </button>
      )}
    </div>
  );
};

export default TimeSlots;
