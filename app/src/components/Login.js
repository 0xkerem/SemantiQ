import React, { useState } from 'react';
import Signup from './Signup'; // Import your Signup component here

export default function Login() {
  const [showSignup, setShowSignup] = useState(false);

  const handleSignUpClick = () => {
    setShowSignup(true);
  };

  const handleBackToLogin = () => {
    setShowSignup(false);
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
              <form>
                <div className="form-group">
                  <input type="text" className="losign" name="email" placeholder="Email Address" />
                </div>
                <div className="form-group">
                  <input type="password" className="losign" name="password" placeholder="Password" />
                </div>
                <div className="form-group">
                  <div className="checkbox-group">
                    <input type="checkbox" id="rememberMe" name="rememberMe" />
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
