import React, { useState } from 'react';
import Login from './Login';
import Signup from './Signup';

export default function Header({ onLoginSuccess }) {
  const [showLogin, setShowLogin] = useState(false);
  const [showSignup, setShowSignup] = useState(false);
  const [overlayVisible, setOverlayVisible] = useState(false);

  const handleLoginClick = () => {
    setShowLogin(true);
    setShowSignup(false); // Ensure sign up form is hidden when logging in
    setOverlayVisible(true); // Set the overlay to be visible
  };

  const handleSignupClick = () => {
    setShowSignup(true);
    setShowLogin(false); // Ensure login form is hidden when signing up
    setOverlayVisible(true); // Set the overlay to be visible
  };

  return (
    <div>
      <div className='header'>
        <div className='Header-logo-container'>
          <a href='/' className='Header-logo'>SemantiQ</a>
        </div>
        <div className='Header-button-container'>
          <button className='Header-login-button' onClick={handleLoginClick}>Log in</button>
          <button className='Header-signup-button' onClick={handleSignupClick}>Sign up</button>
        </div>
        {showLogin && <Login onLoginSuccess={onLoginSuccess} />}
        {showSignup && <Signup onLoginSuccess={onLoginSuccess} />}
      </div>
      {overlayVisible && <div className='back-blur' /> /* Render the overlay based on visibility */}
    </div>
  );
}
