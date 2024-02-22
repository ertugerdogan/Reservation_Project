
import React, { useState } from 'react';
import { View, Text, TextInput, Button, StyleSheet, Alert } from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import axios from 'axios';

const ChangePasswordScreen = () => {
  const [currentPassword, setCurrentPassword] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [confirmNewPassword, setConfirmNewPassword] = useState('');



  
  const handleChangePassword = async () => {
    
    const backendApiEndpoint = 'http://13.50.243.223:8080/students/changepassword';
    const userId = await AsyncStorage.getItem('userId');
    const requestData = {
      userId: userId,
      oldPassword: currentPassword,
      newPassword: newPassword,
      newPasswordConfirmation: confirmNewPassword,   
    };
    console.log(requestData);
    axios.post(backendApiEndpoint, requestData)
        .then(response => {
          
            Alert.alert('Başarılı', 'Şifre değiştirildi!');
        })
        .catch(error => {
            console.error('Şifre Değiştirilemedi:', error);
          
        });
  };

  

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Change Password</Text>
      <Text style={styles.label}>Mevcut Şifre</Text>
      <TextInput
        secureTextEntry
        style={styles.input}
        value={currentPassword}
        onChangeText={setCurrentPassword}
      />

      <Text style={styles.label}>Yeni Şifre</Text>
      <TextInput
        secureTextEntry
        style={styles.input}
        value={newPassword}
        onChangeText={setNewPassword}
      />

      <Text style={styles.label}>Yeni Şifre Tekrar</Text>
      <TextInput
        secureTextEntry
        style={styles.input}
        value={confirmNewPassword}
        onChangeText={setConfirmNewPassword}
      />

      <Button title="Şifreyi Değiştir" onPress={handleChangePassword} />
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
    fontSize: 24,
    marginBottom: 16,
  },
  label: {
    fontSize: 16,
    marginBottom: 8,
  },
  input: {
    height: 40,
    width: '100%',
    borderColor: 'gray',
    borderWidth: 2, 
    borderRadius: 10, 
    marginBottom: 16,
    paddingHorizontal: 8,
  },
});

export default ChangePasswordScreen;
