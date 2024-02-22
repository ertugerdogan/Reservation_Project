// TimeSlots.js
import React from 'react';
import { View, Text, FlatList, TouchableOpacity, StyleSheet } from 'react-native';

const TimeSlots = ({ selectedDate, onClose, onTimeSlotPress }) => {

    const timeSlots = [
        { id: '1', startTime: '08:00', endTime: '9:00' },
        { id: '2', startTime: '9:00', endTime: '10:00' },
        { id: '3', startTime: '10:00', endTime: '11:00' },
        { id: '4', startTime: '11:00', endTime: '12:00' },
        { id: '5', startTime: '12:00', endTime: '13:00' },
        { id: '6', startTime: '13:00', endTime: '14:00' },
        { id: '7', startTime: '14:00', endTime: '15:00' },
        { id: '8', startTime: '15:00', endTime: '16:00' },
        { id: '9', startTime: '16:00', endTime: '17:00' },
        { id: '10', startTime:'17:00', endTime: '18:00' },
        { id: '11', startTime:'18:00', endTime: '19:00' },
       
    ];

    return (
        <View style={styles.container}>
            <Text style={styles.title}>{`Zaman Aralıkları`}</Text>
            <FlatList
                data={timeSlots}
                keyExtractor={(item) => item.id}
                renderItem={({ item }) => (
                    <TouchableOpacity
                        style={styles.timeSlotItem}
                        onPress={() => onTimeSlotPress(item.startTime, item.endTime)}
                    >
                        <Text>{`${item.startTime} - ${item.endTime}`}</Text>
                    </TouchableOpacity>
                )}
            />
            <TouchableOpacity onPress={onClose} style={styles.closeButton}>
                <Text style={styles.closeButtonText}>Kapat</Text>
            </TouchableOpacity>
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        backgroundColor: '#fff',
        borderRadius: 8,
        padding: 16,
        marginTop: 16,
        marginBottom: 16,
    },
    title: {
        fontSize: 18,
        marginBottom: 16,
        textAlign: 'center',
    },
    timeSlotItem: {
        borderWidth: 1,
        borderColor: '#3498db',
        borderRadius: 8,
        padding: 12,
        marginBottom: 8,
        alignItems: 'center',
    },
    closeButton: {
        backgroundColor: '#3498db',
        padding: 8,
        borderRadius: 8,
        marginTop: 8,
        alignItems: 'center',
    },
    closeButtonText: {
        color: '#fff',
        textAlign: 'center',
    },
});

export default TimeSlots;
