import React from 'react';
import { View, Text, Button, Alert } from 'react-native';
import styles from '../../styles';
import { AuthContext } from '../../navigation/context';

function LogoutScreen() {
  const { signOut } = React.useContext(AuthContext);
  const handleLogout = () => {
    try {
      
      signOut();

      console.log('Çıkış Başarılı');

    } catch (error) {
      console.error('Error during logout:', error);
      Alert.alert('Error', 'An error occurred during logout. Please try again.');
    }
  };

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Logout</Text>
      {/* İstediğiniz çıkış yapma işlemlerini gerçekleştiren bir buton ekleyebilirsiniz */}
      <Button title="Çıkış Yap" onPress={handleLogout} />
    </View>
  );
}

export default LogoutScreen;