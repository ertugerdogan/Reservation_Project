// RankingsScreen.js
import React, { useState, useEffect } from 'react';
import { View, Text, FlatList, StyleSheet } from 'react-native';
import axios from 'axios';
import { useFocusEffect } from '@react-navigation/native';

const RankingsScreen = () => {
    const [users, setUsers] = useState([]);

    
    const fetchData = () => {
       
        const fetchUsersEndpoint = 'http://13.50.243.223:8080/students/getRankings';

        axios.post(fetchUsersEndpoint)
            .then(response => {
                // Kullanıcıları ranklarına göre sırala
                const sortedUsers = response.data.data;
                console.log(sortedUsers);
                setUsers(sortedUsers);
            })
            .catch(error => {
                console.error('Error fetching users:', error);
            });
    };

    useFocusEffect(
        React.useCallback(() => {
          fetchData();
        }, []) 
    );
    

    return (
        <View style={styles.container}>
            <Text style={styles.title}>Liderlik Tablosu</Text>
            {users.length > 0 ? (
                <FlatList
                    data={users}
                    keyExtractor={(item) => item.id}
                    renderItem={({ item, index }) => (
                        <View style={index % 2 === 0 ? styles.evenItem : styles.oddItem}>
                            <View style={styles.userInfoContainer}>
                                <Text style={styles.userName}>{`${index + 1}. ${item.name}`}</Text>
                                <Text style={styles.userRank}>{`  Rank: ${item.score}`}</Text>
                            </View>
                        </View>
                    )}
                />
            ) : (
                <Text style={styles.noUserText}>Kullanıcı bulunamadı.</Text>
            )}
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        padding: 16,
        backgroundColor: '#f5f5f5',
    },
    title: {
        fontSize: 45,
        marginBottom: 16,
        color: '#333',
        borderBottomWidth: 2,
        borderBottomColor: 'black', 
        alignSelf: 'center',
        marginTop: 5,
    },
    evenItem: {
        borderWidth: 3, 
        borderColor: 'black', 
        padding: 16,
        marginVertical: 8,
        backgroundColor: '#f9f9f9', 
    },
    oddItem: {
        borderWidth: 3, 
        borderColor: 'red', 
        padding: 16,
        marginVertical: 8,
        backgroundColor: '#fff', 
    },
    userInfoContainer: {
        flexDirection: 'row', 
        justifyContent: 'space-between', 
        alignItems: 'center', 
    },
    userName: {
        fontSize: 18,
        fontWeight: 'bold',
        color: '#333', 
    },
    userRank: {
        fontSize: 16,
        color: '#666', 
    },
    noUserText: {
        fontSize: 16,
        color: '#666',
    },
});

export default RankingsScreen;
