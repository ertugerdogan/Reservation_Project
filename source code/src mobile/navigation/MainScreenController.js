import React,{Fragment, useState} from "react";
import BottomNavigator from "./BottomNavigator";
import LoginScreen from "./screens/LoginScreen";
import { AuthContext } from "./context";

const MainScreenController = () => {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const authContext = React.useMemo(() => ({
        signIn: () => {
            setIsLoggedIn(true);
        },
        signOut: () => {
            setIsLoggedIn(false);
        },
    }));

    return (
        <Fragment>
            <AuthContext.Provider value={authContext}>
                {(function(){
                if(isLoggedIn){
                    return <BottomNavigator/>
                }else{
                    return <LoginScreen/>
                }
            })()}
            </AuthContext.Provider>
        </Fragment>
    )
}

export default MainScreenController;