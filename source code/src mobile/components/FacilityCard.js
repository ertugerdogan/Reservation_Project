// FacilityCard.js
import React from 'react';
import { View, Text, TouchableOpacity, StyleSheet } from 'react-native';

const FacilityCard = ({ facility, onPress }) => {
    return (
        <TouchableOpacity style={styles.card} onPress={onPress}>
            <Text style={styles.facilityName}>{facility.facilityType}</Text>
            <Text style={styles.facilityType}>{facility.facilityId}</Text>
        </TouchableOpacity>
    );
};

const styles = StyleSheet.create({
    card: {
        borderWidth: 1,
        borderColor: '#ddd',
        borderRadius: 8,
        padding: 16,
        marginVertical: 8,
        backgroundColor: '#fff',
    },
    facilityName: {
        fontSize: 18,
        fontWeight: 'bold',
    },
    facilityType: {
        fontSize: 16,
        color: '#666',
    },
});

export default FacilityCard;
