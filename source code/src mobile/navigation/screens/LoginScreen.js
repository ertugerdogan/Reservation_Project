import React, { useState } from 'react';
import { View, Text, TextInput, Button, Alert } from 'react-native';
import axios from 'axios';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { AuthContext } from '../context';
import styles from '../../styles';

export function LoginScreen() {
  const [isLoginScreen, setIsLoginScreen] = useState(true);

  const RegisterScreen = ({ setIsLoginScreen }) => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');

    const handleRegister = async () => {
      try {
        const requestData = {
          firstName: firstName,
          lastName: lastName,
          email: email,
          password: password,
        };

        const response = await axios.post('http://13.50.243.223:8080/students/register', requestData);

        if (response.data.success) {

          Alert.alert('Success', 'Registration successful. You can now login with your credentials.');

          setIsLoginScreen(true);
        } else {
          Alert.alert('Error', response.data.message || 'Registration failed. Please check your credentials.');
        }
      } catch (error) {
        console.error('Error during registration:', error);
        Alert.alert('Error', 'An error occurred during registration. Please try again.');
      }
    };

    return (
      <View style={styles.container}>
        <Text style={styles.title}>Register</Text>
        <TextInput
          style={styles.input}
          placeholder="Ad"
          onChangeText={(text) => setFirstName(text)}
          value={firstName}
        />
        <TextInput
          style={styles.input}
          placeholder="Soyad"
          onChangeText={(text) => setLastName(text)}
          value={lastName}
        />
        <TextInput
          style={styles.input}
          placeholder="E-posta"
          onChangeText={(text) => setEmail(text)}
          value={email}
        />
        <TextInput
          style={styles.input}
          placeholder="Şifre"
          secureTextEntry
          onChangeText={(text) => setPassword(text)}
          value={password}
        />
        <Button title="Kayıt Ol" onPress={handleRegister} />
        <Button title="Giriş Ekranına Dön" onPress={() => setIsLoginScreen(true)} />
      </View>
    );
  };

  const LoginPage = ({ setIsLoginScreen }) => {
    const { signIn } = React.useContext(AuthContext);
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const handleLogin = async () => {
      try {
        const requestData = {
          email: email,
          password: password,
        };

        const response = await axios.post('http://13.50.243.223:8080/students/login', requestData);

        if (response.data.success) {
          const { jwtToken, userId } = response.data.data;
          await AsyncStorage.setItem('userId', userId.toString());

          signIn();
          
        } else {
          Alert.alert('Error', response.data.message || 'Login failed. Please check your credentials.');
        }
      } catch (error) {
        console.error('Error during login:', error);
        Alert.alert('Error', 'An error occurred during login. Please try again.');
      }
    };

    return (
      <View style={styles.container}>
        <Text style={styles.title}>Login</Text>
        <TextInput
          style={styles.input}
          placeholder="E-posta"
          onChangeText={(text) => setEmail(text)}
          value={email}
        />
        <TextInput
          style={styles.input}
          placeholder="Şifre"
          secureTextEntry
          onChangeText={(text) => setPassword(text)}
          value={password}
        />
        <Button title="Giriş Yap" onPress={handleLogin} />
        <Button title="Kayıt Ol" onPress={() => setIsLoginScreen(false)} />
      </View>
    );
  };

  return isLoginScreen ? (
    <LoginPage setIsLoginScreen={setIsLoginScreen} />
  ) : (
    <RegisterScreen setIsLoginScreen={setIsLoginScreen} />
  );
}

export default LoginScreen;
