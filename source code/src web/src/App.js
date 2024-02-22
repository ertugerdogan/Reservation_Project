// src/App.js
import React, { useState } from 'react';
import MainContent from './components/MainContent';
import Login from './components/Login';
import './App.css';

function App() {
  const [isLoggedIn, setLoggedIn] = useState(false);

  const handleLogin = () => {
    setLoggedIn(true);
  };

  return (
    <div className="App">
      <div className="container">
        {isLoggedIn ? (
          <MainContent />
        ) : (
          <Login onLogin={handleLogin} />
        )}
      </div>
    </div>
  );
}

export default App;
