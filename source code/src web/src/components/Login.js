// src/components/Login.js
import React, { useState } from 'react';
import  './Login.css'; 
import axios from 'axios';

const Login = ({ onLogin }) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const handleLogin = async () => {
    try {
      const requestData = {
        email: username,
        password: password,
      };

      const response = await axios.post('http://13.50.243.223:8080/students/login', requestData);

      if (response.data.success) {
        onLogin();
      } else {
        setError('Invalid username or password');
      }
    } catch (error) {
      setError('Connection error');
    }
  };

  return (
    <div className="login-container">
      <h2>Login</h2>
      <div>
        <label>Username:</label>
        <input
          type="text"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
      </div>
      <div>
        <label>Password:</label>
        <input
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
      </div>
      {error && <div className="error-message">{error}</div>}
      <button onClick={handleLogin}>Login</button>
    </div>
  );
};

export default Login;
