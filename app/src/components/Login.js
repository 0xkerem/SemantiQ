import React, { useState, useEffect } from 'react';
import Signup from './Signup'; // Import your Signup component here

export default function Login({ onLoginSuccess }) {
  const [showSignup, setShowSignup] = useState(false);
  const [rememberMe, setRememberMe] = useState(false);

  useEffect(() => {
    const rememberMeValue = localStorage.getItem('rememberMe');
    if (rememberMeValue && rememberMeValue === 'true') {
      setRememberMe(true);
    }
  }, []);

  const handleSignUpClick = () => {
    setShowSignup(true);
  };

  const handleBackToLogin = () => {
    setShowSignup(false);
  };

  const handleRememberMeChange = (event) => {
    const { checked } = event.target;
    setRememberMe(checked);
    localStorage.setItem('rememberMe', checked ? 'true' : 'false');
  };

  const handleLogin = async (event) => {
    event.preventDefault();
    const email = event.target.email.value;
    const password = event.target.password.value;
    event.target.email.value = '';
    event.target.password.value = '';

    try {
      const response = await fetch('http://localhost:8080/api/users/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          email,
          password,
        }),
      });

      if (response.status === 401) {
        // Handle unauthorized access error
        alert('Invalid email or password');
        return;
      } else if (response.status === 403) {
        alert('User not verified! Please verify your email address.');
      } else if (response.status === 404) {
        alert("User not found!")        
      }

      const data = await response.text();
      // Handle successful login
      console.log(data);
      onLoginSuccess(email);
    } catch (error) {
      // Handle network errors or other issues
      console.error('Error:', error);
    }
  };

  return (
    <div className='Login'>
      {showSignup ? (
        <Signup onBackToLogin={handleBackToLogin} />
      ) : (
        <div className='Login-box'>
          <div className='Login-pattern'></div>
          <div className='Login-base'>
            <div className="login-container">
              <h2 className='Login-heading'>Welcome!</h2>
              <p className='Login-text'>
                Don't have an account yet?{' '}
                <span className='Signup-now' onClick={handleSignUpClick}>Sign up now</span>
              </p>
              <form onSubmit={handleLogin}>
                <div className="form-group">
                  <input type="text" className="losign" name="email" placeholder="Email Address" />
                </div>
                <div className="form-group">
                  <input type="password" className="losign" name="password" placeholder="Password" />
                </div>
                <div className="form-group">
                  <div className="checkbox-group">
                    <input
                      type="checkbox"
                      id="rememberMe"
                      name="rememberMe"
                      checked={rememberMe}
                      onChange={handleRememberMeChange}
                    />
                    <label className='Login-label' htmlFor="rememberMe">Remember Me</label>
                  </div>
                </div>
                <div className="form-group">
                  <input id="Login-submit" type="submit" value="Sign in" />
                </div>
              </form>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}