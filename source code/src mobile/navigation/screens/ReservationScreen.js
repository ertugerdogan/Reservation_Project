import React, { useState, useEffect } from 'react';
import { View, Text, TextInput, Button, StyleSheet } from 'react-native';
import { FlatList } from 'react-native'; 
import axios from 'axios'; 
import FacilityCard from '../../components/FacilityCard';
import { Calendar, LocaleConfig } from 'react-native-calendars';
import { TouchableOpacity } from 'react-native';
import TimeSlots from '../../components/TimeSlots';
import { useFocusEffect } from '@react-navigation/native';
import AsyncStorage from '@react-native-async-storage/async-storage';


LocaleConfig.locales['tr'] = {
  monthNames: ['Ocak', 'Şubat', 'Mart', 'Nisan', 'Mayıs', 'Haziran', 'Temmuz', 'Ağustos', 'Eylül', 'Ekim', 'Kasım', 'Aralık'],
  monthNamesShort: ['Oca.', 'Şub.', 'Mar.', 'Nis.', 'May.', 'Haz.', 'Tem.', 'Ağu.', 'Eyl.', 'Eki.', 'Kas.', 'Ara.'],
  dayNames: ['Pazar', 'Pazartesi', 'Salı', 'Çarşamba', 'Perşembe', 'Cuma', 'Cumartesi'],
  dayNamesShort: ['Paz.', 'Pzt.', 'Sal.', 'Çar.', 'Per.', 'Cum.', 'Cmt.'],
};

LocaleConfig.defaultLocale = 'tr';
const ReservationScreen = () => {
  const [selectedFacility, setSelectedFacility] = useState(null);
  const [selectedTimeSlot, setSelectedTimeSlot] = useState(null);
  const [selectedDate, setSelectedDate] = useState(null);
  const [selectedDay, setSelectedDay] = useState(null);
  const [reservationTime, setReservationTime] = useState('');
  const [showCalendar, setShowCalendar] = useState(false);
  const [showTimeSlots, setShowTimeSlots] = useState(false); 
  const [showFacilities, setShowFacilities] = useState(true);
  const [showCreateButton, setShowCreateButton] = useState(false);

  

  const getUserId = async () => {
    const userId = await AsyncStorage.getItem('userId');
    console.log("Fonk userId: ", userId);
    return userId;
  };
  
  const [facilities, setFacilities] = useState([]);
  useFocusEffect(
    React.useCallback(() => {
      fetchData();
    }, [])
);
const fetchData = async () => {
   
    const fetchFacilities = async () => {
      try {
        const response = await axios.post('http://13.50.243.223:8080/facilities/listFacility');
        setFacilities(response.data.data); 
        
        console.log(response.data);
      } catch (error) {
        console.error('Error fetching facilities:', error);
      }
    };

    fetchFacilities();
  }; 
  console.log("facilities: ", facilities);
  const handleCreateReservation = async () => {
   
    const userId = await AsyncStorage.getItem('userId');
    console.log("userId: ", userId);
    const reservationData = {
      facilityId: selectedFacility.facilityId,
      userId: userId,
      startTime: selectedTimeSlot,
      date: selectedDay,
    };
    console.log("reservationdata: ",reservationData);
    

    const backendApiEndpoint = 'http://13.50.243.223:8080/reservations/makeReservation';

    axios.post(backendApiEndpoint, reservationData)
      .then(response => {
        console.log('Reservation created successfully:', response.data);
        
      })
      .catch(error => {
        console.error('Error creating reservation:', error);
     
      });


    setShowFacilities(true);
    setShowCreateButton(false);
    setSelectedFacility(null);
    setSelectedTimeSlot(null);
    setSelectedDate(null);
    setSelectedDay(null);
    console.log("hello")
  };


  const handleFacilityPress = (facility) => {
    console.log('Tesis Seçildi: ', facility);
    console.log("bu seçilmiş facilitidir: ",facility)
    setSelectedFacility(facility);
    setShowFacilities(false);
    setShowCalendar(true); 
   
  };

  const handleTimeSlotPress = (startTime, endTime) => {
    console.log('Seçilen Zaman Aralığı: ', startTime, endTime);
    setSelectedTimeSlot(`${startTime}`);
    setShowCreateButton(true);
    setShowTimeSlots(false);
    setShowCalendar(false);
   
  };

  const handleCalendarClose = () => {
    setShowCalendar(false); 
    setShowFacilities(true);
    setShowCreateButton(false);
    setShowTimeSlots(false);
    console.log(facilities);
  };

  const handleDayPress = (day) => {
    console.log('Seçilen Gün: ', day.dateString);
    setSelectedDay(day.dateString);
    setSelectedDate(day.dateString); 
    setShowTimeSlots(true);
    setShowCalendar(false);
  };



  return (
    <View style={styles.container}>
      {showFacilities && (
        <FlatList
          data={facilities}
          keyExtractor={(item) => item.facilityId}
          renderItem={({ item }) => (
            <FacilityCard facility={item} onPress={() => handleFacilityPress(item)} />
          )}
        />
      )}

      {showCalendar && (
        <View style={styles.calendarContainer}>
          
          <Calendar
            onDayPress={handleDayPress}
            monthFormat={'MMMM yyyy'}
            markedDates={{ [selectedDate]: { selected: true, disableTouchEvent: true } }}
            theme={{
              selectedDayBackgroundColor: '#3498db',
              todayTextColor: '#3498db',
              arrowColor: '#3498db',
            }}
          />
          <TouchableOpacity onPress={handleCalendarClose} style={styles.closeCalendarButton}>
            <Text style={styles.closeCalendarText}>Kapat</Text>
          </TouchableOpacity>
        </View>
      )}

      {showTimeSlots && (
        <TimeSlots
          
          onClose={() => setShowTimeSlots(false)}
          onTimeSlotPress={handleTimeSlotPress}
        />
      )}

      {showCreateButton && (
        <View>
          {/* <Text>{`Tesis Seçildi: ${selectedFacility.name} (${selectedFacility.type})`}</Text> */}
          <Text>{`Seçilen Zaman Aralığı: ${selectedTimeSlot}`}</Text>
          <Text>{`Seçilen Gün: ${selectedDay}`}</Text>
          <Button title="Rezervasyon Oluştur" onPress={handleCreateReservation} />
        </View>
        )}
    </View>
  );
};
     

const styles = StyleSheet.create({

  calendarContainer: {
    backgroundColor: '#fff',
    borderRadius: 8,
    padding: 16,
    marginTop: 16,
    marginBottom: 16,
  },
  closeCalendarButton: {
    backgroundColor: '#3498db',
    padding: 8,
    borderRadius: 8,
    marginTop: 8,
  },
  closeCalendarText: {
    color: '#fff',
    textAlign: 'center',
  },
});

export default ReservationScreen;
