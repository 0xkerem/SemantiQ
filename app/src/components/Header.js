import React, { useState } from 'react';
import Login from './Login'; // Assuming Login component is in a separate file

export default function Header() {
  const [showLogin, setShowLogin] = useState(false);

  const handleLoginClick = () => {
    setShowLogin(true);
  };

  return (
    <div className='header'>
      <div className='Header-logo-container'>
        <a href='/' className='Header-logo'>SemantiQ</a>
      </div>
      <div className='Header-button-container'>
        <button className='Header-login-button' onClick={handleLoginClick}>Log in</button>
        <button className='Header-signup-button'>Sign up</button>
      </div>
      {showLogin && <Login />}
    </div>
  );
}
