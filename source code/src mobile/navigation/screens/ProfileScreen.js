import * as React from 'react';
import {createNativeStackNavigator} from '@react-navigation/native-stack';

import UserProfileMain from '../../components/UserProfileMain';
import ChangePassword from '../../components/userprofilepages/ChangePassword';
import AcademicProgram from '../../components/userprofilepages/AcademicProgram';
import ExerciseSuggestion from '../../components/userprofilepages/ExerciseSuggestion';
import UserInfo from '../../components/userprofilepages/UserInfo';
import Logout from '../../components/userprofilepages/Logout';

export default function ProfileScreen() {
    const Stack = createNativeStackNavigator();
    return (
            <Stack.Navigator
            initialRouteName ='UserProfileMain'>
                <Stack.Screen name="UserProfileMain" component={UserProfileMain} />
                <Stack.Screen name="UserInfo" component={UserInfo} />
                <Stack.Screen name="AcademicProgram" component={AcademicProgram} />
                <Stack.Screen name="ExerciseSuggestion" component={ExerciseSuggestion} />
                <Stack.Screen name="ChangePassword" component={ChangePassword} />
                <Stack.Screen name="Logout" component={Logout}/>
            </Stack.Navigator>
    );
}