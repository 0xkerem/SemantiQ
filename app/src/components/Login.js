import React from 'react';

export default function Login() {
  return (
    <div className='Login'>
      <div className='Login-box'>
        <div className='Login-pattern'></div>
        <div className='Login-base'>
          <div className="login-container">
            <h2 className='Login-heading'>Welcome!</h2>
            <p className='Login-text'>
              Don't have an account yet? <span className='Signup-now'>Sign up now</span>
            </p>
            <form>
              <div className="form-group">
                <input type="text" id="email" name="email" placeholder="Email Address" />
              </div>
              <div className="form-group">
                <input type="password" id="password" name="password" placeholder="Password" />
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
    </div>
  );
}
