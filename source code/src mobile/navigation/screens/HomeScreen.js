import React, { useEffect } from 'react';
import { useNavigation } from '@react-navigation/native';

const Home = () => {
  const navigation = useNavigation();

  useEffect(() => {
    navigation.reset({
      index: 0,
      routes: [{ name: 'Reservation' }],
    });
  }, [navigation]);

  

  return null;
};

export default Home;
