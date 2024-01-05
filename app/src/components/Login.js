import React, { useState, useEffect } from 'react';
import Signup from './Signup'; // Import your Signup component here

export default function Login({ onLoginSuccess }) {
  const [showSignup, setShowSignup] = useState(false);
  const [rememberMe, setRememberMe] = useState(false);

  useEffect(() => {
    const rememberMeValue = localStorage.getItem('rememberMe');
    if (rememberMeValue && rememberMeValue === 'true') {
      setRememberMe(true);
    } else {
      setRememberMe(false)
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
    localStorage.setItem('rememberMe', checked ? 'true' : 'false');
    setRememberMe(checked);
  };

  const verify = (code, email) => {
    const xhr = new XMLHttpRequest();
    const endpoint = `http://localhost:8080/api/users/${email}/verify`;
  
    xhr.open('POST', endpoint, true);
    xhr.setRequestHeader('Content-Type', 'application/json');
  
    // Define what happens on successful response
    xhr.onload = function () {
      if (xhr.status === 200) {
        // Successful verification
        alert('Email address has been successfully verified.');
      } else if (xhr.status === 400) {
        // Verification failed due to wrong code
        alert('Verification code did not match! Try again.');
        // Re-invoke verify function to try again
        verify(code);
      }
    };
  
    // Handle network errors
    xhr.onerror = function () {
      alert('There was a network error.');
      // Re-invoke verify function to try again
      verify(code);
    };
  
    // Create a data object to send in the request body
    const data = JSON.stringify({
      code: code
    });
  
    // Send the request
    xhr.send(data);
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
        let verificationCode = prompt("Enter the verification code sent to your e-mail address.");
        verify(verificationCode, email)
        return;
      } else if (response.status === 404) {
        alert("User not found!")
        return;
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
        <Signup onLoginSuccess={onLoginSuccess} />
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
                  <input autocomplete="off" type="text" className="losign" name="email" placeholder="Email Address" />
                </div>
                <div className="form-group">
                  <input autocomplete="off" type="password" className="losign" name="password" placeholder="Password" />
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