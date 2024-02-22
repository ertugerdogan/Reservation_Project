import * as React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import Ionicons from 'react-native-vector-icons/Ionicons';

// Screens
import ReservationManagementScreen from './screens/ReservationManagementScreen';
import ReservationScreen from './screens/ReservationScreen';
import CommentsScreen from './screens/CommentsScreen';
import RankingsScreen from './screens/RankingsScreen';
import ProfileScreen from './screens/ProfileScreen';

//Screen names
const reservationName = "Reservation";
const reservationManagementName = "Management";
const commentsName = "Comments";
const rankingsName = "Rankings";
const profileName = "Profile";

const Tab = createBottomTabNavigator();

function BottomNavigator() {

    return (

        <NavigationContainer>
            <Tab.Navigator
                initialRouteName={reservationName}
                screenOptions={({ route }) => ({
                    tabBaractiveTintColor: 'tomato',
                    tabBarinactiveTintColor: 'grey',
                    tabBarlabelStyle: { paddingBottom: 5, fontSize: 10 },
                    tabBarstyle: { dsplay: 'flex' },
                    tabBarIcon: ({ focused, color, size }) => {
                        let iconName;
                        let rn = route.name;

                        if (rn === reservationName) {
                            iconName = focused ? 'home' : 'home-outline';
                        } else if (rn === reservationManagementName) {
                            iconName = focused ? 'list' : 'list-outline';
                        } else if (rn === commentsName) {
                            iconName = focused ? 'chatbubbles' : 'chatbubbles-outline';
                        } else if (rn === rankingsName) {
                            iconName = focused ? 'trophy' : 'trophy-outline';
                        } else if (rn === profileName) {
                            iconName = focused ? 'settings' : 'settings-outline';
                        }

                
                        return <Ionicons name={iconName} size={size} color={color} />;
                    },
                })}>
                
                <Tab.Screen name={reservationName} component={ReservationScreen} />
                <Tab.Screen name={reservationManagementName} component={ReservationManagementScreen} />
                <Tab.Screen name={commentsName} component={CommentsScreen} />
                <Tab.Screen name={rankingsName} component={RankingsScreen} />
                <Tab.Screen name={profileName} component={ProfileScreen} />

            </Tab.Navigator>
        </NavigationContainer>
    );
}

export default BottomNavigator;