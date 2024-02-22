// CommentsScreen.js

import React, { useState, useEffect } from 'react';
import { View, Text, FlatList, TextInput, Button, StyleSheet, TouchableOpacity } from 'react-native';
import axios from 'axios'
import AsyncStorage from '@react-native-async-storage/async-storage';

const CommentsScreen = () => {
    const [selectedFacility, setSelectedFacility] = useState(null);
       const [facilities, setFacilities] = useState([]);
    const [comments, setComments] = useState([]);
    const [newComment, setNewComment] = useState('');
    const [userRating, setUserRating] = useState(0);

    // const facilities = [
    //     { id: '1', name: 'Futbol Sahası' },
    //     { id: '2', name: 'Basketbol Sahası' },
    //     // ... diğer tesisler
    // ];
    
    
    useEffect(() => {
        // Fetch facilities from the backend API when the component mounts
        const fetchFacilities = async () => {
          try {
            const response = await axios.post('http://13.50.243.223:8080/facilities/listFacility');
            setFacilities(response.data.data); // Assuming the response contains an arr of facilities
            
            console.log(response.data);
          } catch (error) {
            console.error('Error fetching facilities:', error);
          }
        };
    
        fetchFacilities();
      }, []); 
    
    const handleCloseComments = () => {
        setSelectedFacility(null);
        setComments([]);
    };

    const handleFacilitySelection = (facility) => {
        setSelectedFacility(facility);
        // Belirli bir tesis seçildiğinde, o tesise ait yorumları getirebilirsiniz.
        // Örnek: fetchCommentsForFacility(facility.id);
        // Tesis seçildiğinde, o tesise ait yorumları çekmek için yeni bir fonksiyon kullanabilirsiniz.
        console.log("facility: ",facility);
        console.log("facilityId: ",facility.facilityId);
        fetchCommentsForFacility(facility.facilityId);
        
    };


    const fetchCommentsForFacility = (facilityId) => {
        // Backend'ten tesis için yorumlar çekmek için enpoint
        console.log("facilityID(comments): ", facilityId)
        const fetchCommentsEndpoint = `http://13.50.243.223:8080/comments/listComment`;
        const requestData = {
            facilityId: facilityId,
        };
        console.log("requestData: ", requestData)
        
        axios.post(fetchCommentsEndpoint, requestData)
            .then(response => {
                setComments(response.data.data);
                console.log(response.data)
            })
            .catch(error => {
                console.error('Error fetching comments:', error);
            });
    };

    /*const handleAddComment = () => {
        // Yeni yorumu eklemek için
        if (selectedFacility && newComment.trim() !== '') {
            setComments([
                ...comments,
                {
                    id: comments.length + 1,
                    text: newComment,
                    user: 'John Doe',
                    rating: userRating,
                },
            ]);
            setNewComment('');
            setUserRating(0);

            // Yeni yorumu sunucuya göndermek için bir fonksiyonu çağırabilirsiniz.
            // Örnek: sendCommentToServer(selectedFacility.id, newComment);
        }
    };*/


    
    const handleAddComment = () => {
        // Yeni yorumu eklemek için
        if (selectedFacility && newComment.trim() !== '') {
            // Yeni yorumu sunucuya göndermek için bir fonksiyonu çağırabilirsiniz.
            sendCommentToServer(selectedFacility.facilityId, newComment);
        }
    };

    const sendCommentToServer = async (facilityId, commentText) => {
        // Backend'e yeni yorumu göndermek için endpoint
        const sendCommentEndpoint = 'http://13.50.243.223:8080/comments/addComment';
        const userId = await AsyncStorage.getItem('userId');
        const requestData = {
            facilityId: facilityId,
            comment: commentText,
            userId: userId,
            // userRating: userRating,
        };

        axios.post(sendCommentEndpoint, requestData)
            .then(response => {
                console.log('Comment sent successfully:', response.data);
                fetchCommentsForFacility(facilityId);
                setNewComment('');
                setUserRating(0);
            })
            .catch(error => {
                console.error('Error sending comment:', error);
                // Hata durumunda uygun şekilde işleyin
            });
    };


    

    const renderStar = (index) => {
        return (
            <TouchableOpacity
                key={index}
                onPress={() => setUserRating(index + 1)}
                style={[styles.star, index < userRating ? styles.filledStar : null]}
            />
        );
    };

    return (
        <View style={styles.container}>
            <Text style={styles.title}>Facilities</Text>
    
            {!selectedFacility ? (
                <FlatList
                    data={facilities}
                    keyExtractor={(item) => item.facilityId}
                    renderItem={({ item }) => (
                        <TouchableOpacity
                            style={styles.facilityButton}
                            onPress={() => handleFacilitySelection(item)}
                        >
                            <Text>{item.facilityType}</Text>
                        </TouchableOpacity>
                    )}
                />
            ) : (
                <View style={styles.commentsContainer}>
    
                    <Text>Yorumlar:</Text>
                    <FlatList
                        data={comments}
                        keyExtractor={(item) => item.id}
                        renderItem={({ item }) => (
                            <View style={styles.commentItem}>
                                <Text style={styles.commentUser}>{item.userName}</Text>
                                <Text>{item.comment}</Text>
                                {/* <View style={styles.commentRating}>
                                    <View style={styles.starsContainer}>
                                        {[0, 1, 2, 3, 4].map((index) => (
                                            <View
                                                key={index}
                                                style={[
                                                    styles.star,
                                                    index < item.rating ? styles.filledStar : null,
                                                ]}
                                            />
                                        ))}
                                    </View>
                                    <Text style={styles.commentRatingText}>({item.rating}/5)</Text>
                                </View> */}
                            </View>
                        )}
                    />
    
                    {/* <View style={styles.ratingContainer}>
                        <Text>Değerlendir:</Text>
                        <View style={styles.starsContainer}>
                            {[0, 1, 2, 3, 4].map((index) => renderStar(index))}
                        </View>
                    </View> */}
    
                    <TextInput
                        style={styles.input}
                        placeholder="Yorumunuzu buraya ekleyin"
                        onChangeText={(text) => setNewComment(text)}
                        value={newComment}
                    />
                    <View style={styles.buttonContainer}>
    <TouchableOpacity onPress={handleAddComment} style={styles.button}>
        <Text style={styles.buttonText}>Yorum Ekle</Text>
    </TouchableOpacity>
    <View style={{ marginHorizontal: 10 }} /> 
    <TouchableOpacity onPress={handleCloseComments} style={styles.button}>
        <Text style={styles.buttonText}>Geri Dön</Text>
    </TouchableOpacity>
</View>

               
                </View>
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
    },
    title: {
        fontSize: 45,
        marginBottom: 16,
        color: '#333', // Başlık rengi
        borderBottomWidth: 2,
        borderBottomColor: 'black', // Change to the desired underline color
        alignSelf: 'center',
        marginTop: 5,
    },
    facilityButton: {
        borderWidth: 1,
        borderColor: 'gray',
        padding: 8,
        marginVertical: 8,
    },
    commentsContainer: {
        marginTop: 16,
    },
    commentItem: {
        borderWidth: 1,
        borderColor: 'gray',
        padding: 8,
        marginVertical: 8,
        flexDirection: 'column',
        alignItems: 'flex-start',
    },
    commentUser: {
        fontSize: 18,
        fontWeight: 'bold',

        padding: 8,
        marginBottom: 8,
    },
    commentRating: {
        flexDirection: 'row',
        alignItems: 'center',
        marginTop: 8,
    },
    commentRatingText: {
        marginLeft: 8,
    },
    input: {
        height: 40,
        width: '100%',
        borderColor: 'gray',
        borderWidth: 1,
        marginBottom: 12,
        padding: 8,
    },
    ratingContainer: {
        flexDirection: 'row',
        alignItems: 'center',
        marginTop: 8,
    },
    starsContainer: {
        flexDirection: 'row',
        marginLeft: 8,
    },
    star: {
        width: 20,
        height: 20,
        margin: 2,
        borderWidth: 1,
        borderColor: 'gray',
    },
    filledStar: {
        backgroundColor: '#f8d64e',
    },
    closeButton: {
        position: 'absolute',
        top: 0,
        left: 0,
        backgroundColor: 'black',
        padding: 15,
        borderRadius: 20,
        zIndex: 1,
    },
    closeButtonText: {
        fontSize: 18,
        color: 'white',
        fontWeight: 'bold',
    },
    buttonContainer: {
        flexDirection: 'row',
        justifyContent: 'center',
        marginTop: 12,
    },
    
    button: {
        backgroundColor: '#007BFF', 
        paddingVertical: 10,
        paddingHorizontal: 20,
        borderRadius: 5,
    },
    
    buttonText: {
        color: 'white',
        fontSize: 16,
        textAlign: 'center',
    },
    title: {
        fontSize: 32,
        fontWeight: 'bold',
        color: 'black', 
        textAlign: 'center',
        marginTop: 20,  
        borderBottomWidth: 2,
        borderBottomColor: 'black',  
        width: '50%', 
        alignSelf: 'center',
        marginTop: 5,
    },
    
});

export default CommentsScreen;