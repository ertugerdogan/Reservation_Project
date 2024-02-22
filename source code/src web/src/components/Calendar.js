import React, { useState, useEffect } from 'react';
import './Calendar.css';
import Agenda from './Agenda';

const Calendar = () => {
  const [currentMonth, setCurrentMonth] = useState('');
  const [days, setDays] = useState([]);
  const [selectedDay, setSelectedDay] = useState(null);
  const [originalColor, setOriginalColor] = useState(null);
  const [allowChangeColor, setAllowChangeColor] = useState(false);
  const [showTimeSlots, setShowTimeSlots] = useState(false);

  useEffect(() => {
    const currentDate = new Date();
    const currentMonth = currentDate.toLocaleDateString('en-US', { month: 'long', year: 'numeric' });
    setCurrentMonth(currentMonth);
    const firstDayOfMonth = new Date(currentDate.getFullYear(), currentDate.getMonth(), 1);
    const startingDay = firstDayOfMonth.getDay();
    const lastDayOfPrevMonth = new Date(currentDate.getFullYear(), currentDate.getMonth(), 0).getDate();
    const daysInMonth = new Date(currentDate.getFullYear(), currentDate.getMonth() + 1, 0).getDate();
    const daysArray = [];
    for (let i = lastDayOfPrevMonth - startingDay + 2; i <= lastDayOfPrevMonth; i++) {
      daysArray.push(i);
    }
    for (let i = 1; i <= daysInMonth; i++) {
      daysArray.push(i);
    }
    setDays(daysArray);
  }, []);

  const handleDayClick = (day) => {
    console.log('Selected Day:', day);
    setSelectedDay(day);
    setAllowChangeColor(true);
    const dayElement = document.getElementById(`day-${day}`);
    if (dayElement) {
      setOriginalColor(dayElement.style.backgroundColor || '');
    }
  };
  const handleSlotClick = (day, timeSlot) => {
    console.log(`Selected Slot on ${day}: ${timeSlot}`);
  };
  const handleChangeBackgroundColor = () => {
    if (selectedDay !== null) {
      const dayElement = document.getElementById(`day-${selectedDay}`);
      if (dayElement) {
        const currentColor = dayElement.style.backgroundColor;
        if (currentColor === 'red') {
          dayElement.style.backgroundColor = originalColor;
          setAllowChangeColor(!allowChangeColor)
        } else {
          dayElement.style.backgroundColor = 'red';
          setAllowChangeColor(!allowChangeColor)
        }
      }
    }
  };
  const handleShowTimeSlots = () => {
    setShowTimeSlots(!showTimeSlots);
  };
  return (
    <div className="calendar">
      <h2>{currentMonth}</h2>
      <div className="weekdays">
        <div className="weekday">Mon</div>
        <div className="weekday">Tue</div>
        <div className="weekday">Wed</div>
        <div className="weekday">Thu</div>
        <div className="weekday">Fri</div>
        <div className="weekday">Sat</div>
        <div className="weekday">Sun</div>
      </div>
      <div className="days">
        {days.map((day, index) => (
          <div
            key={index}
            id={`day-${day}`}
            className={`day ${selectedDay === day ? 'active' : ''}`}
            onClick={() => handleDayClick(day)}
          >
            {day}
          </div>
        ))}
      </div>
      <p>Selected Day: {selectedDay}</p>
      <button onClick={handleChangeBackgroundColor}>
        {allowChangeColor || originalColor === 'green' ? 'Restrict' : 'Allow'}
      </button>
      <button onClick={handleShowTimeSlots}>
        Show Time Slots
      </button>
      {showTimeSlots && <Agenda day={selectedDay} handleSlotClick={handleSlotClick} />}
    </div>

  );
};

export default Calendar;
