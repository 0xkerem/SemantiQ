import React, { useState, useEffect } from 'react';
import './App.css';
import Home from './components/Home';
import Panel from './components/Panel';

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [emailAddress, setEmailAddress] = useState('');

  useEffect(() => {
    // Check if rememberMe is true in local storage
    const rememberMe = localStorage.getItem('rememberMe');
    if (rememberMe === 'true') {
      // If rememberMe is true, set isLoggedIn to true
      setIsLoggedIn(true);

      // Retrieve and set the stored email address
      const storedEmail = localStorage.getItem('emailAddress');
      if (storedEmail) {
        setEmailAddress(storedEmail);
      }
    }
  }, []); // Empty dependency array to run this effect only once on initial render

  const handleLoginSuccess = (email) => {
    setEmailAddress(email); // Set the email address received from login
    setIsLoggedIn(true);

    // If remember me is checked, store the email and rememberMe value in local storage
    localStorage.setItem('emailAddress', email);
  };

  return (
    <div className="App">
      {isLoggedIn ? (
        <Panel email={emailAddress} />
      ) : (
        <Home onLoginSuccess={handleLoginSuccess} />
      )}
    </div>
  );
}

export default App;
