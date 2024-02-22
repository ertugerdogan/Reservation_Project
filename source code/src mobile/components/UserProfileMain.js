import React from 'react';
import { Text, Button, View, StyleSheet } from 'react-native';

const UserProfileMain = ({ navigation }) => {

    return (
        <View style={styles.container}>
            <Text style={styles.title}>Kullanıcı Profili</Text>

            <View style={styles.buttonContainer}>
                <Button title="User Info" onPress={() => navigation.push("UserInfo")} />
            </View>
            <View style={styles.separator} />

            <View style={styles.buttonContainer}>
                <Button title="Academic Program" onPress={() => navigation.push("AcademicProgram")} />
            </View>
            <View style={styles.separator} />

            <View style={styles.buttonContainer}>
                <Button title="Exercise Suggestion" onPress={() => navigation.push("ExerciseSuggestion")} />
            </View>
            <View style={styles.separator} />

            <View style={styles.buttonContainer}>
                <Button title="Change Password" onPress={() => navigation.push("ChangePassword")} />
            </View>
            <View style={styles.separator} />

            <View style={styles.buttonContainer}>
                <Button title="Logout" onPress={() => navigation.push("Logout")} />
            </View>
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        padding: 16,
        backgroundColor: 'white',
    },
    title: {
        fontSize: 24,
        marginBottom: 16,
        color: 'white', 
    },
    buttonContainer: {
        width: '80%',
    },
    separator: {
        height: 16,
    },
});

export default UserProfileMain;
