import React, { useState } from 'react';
import Login from './Login'; // Assuming Login component is in a separate file
import Signup from './Signup';

export default function Header() {
  const [showLogin, setShowLogin] = useState(false);
  const [showSignup, setShowSignup] = useState(false);

  const handleLoginClick = () => {
    setShowLogin(true);
    setShowSignup(false); // Ensure sign up form is hidden when logging in
  };

  const handleSignupClick = () => {
    setShowSignup(true);
    setShowLogin(false); // Ensure login form is hidden when signing up
  };

  return (
    <div className='header'>
      <div className='Header-logo-container'>
        <a href='/' className='Header-logo'>SemantiQ</a>
      </div>
      <div className='Header-button-container'>
        <button className='Header-login-button' onClick={handleLoginClick}>Log in</button>
        <button className='Header-signup-button' onClick={handleSignupClick}>Sign up</button>
      </div>
      {showLogin && <Login />}
      {showSignup && <Signup />}
    </div>
  );
}
