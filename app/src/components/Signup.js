import React from 'react';

export default function Signup() {
  return (
    <div className='Signup'>
      <div className='Signup-box'>
        <div className='Signup-pattern'></div>
        <div className='Signup-base'>
          <div className="Signup-container">
            <h2 className='Signup-heading'>Welcome!</h2>
            <p className='Signup-text'>
              Already have an account? <span className='Login-now'>Log in</span>
            </p>
            <form>
            <div className="form-group">
                <input type="text" className="losign" name="name" placeholder="Name" />
              </div>
              <div className="form-group">
                <input type="text" className="losign" name="surname" placeholder="Surname" />
              </div>
              <div className="form-group">
                <input type="text" className="losign" name="company" placeholder="Company" />
              </div>
              <div className="form-group">
                <input type="email" className="losign" name="email" placeholder="Email Address" />
              </div>
              <div className="form-group">
                <input type="text" className="losign" name="password" placeholder="Password" />
              </div>
              <div className="form-group p-container">
                <div className="password-c">
                  <div className='signal-container'>
                    <div id='sb1' className='signal-box'></div>
                    <li id='sl1'>At least 8 characters</li>
                  </div>
                  <div className='signal-container'>
                    <div id='sb2' className='signal-box'></div>
                    <li id='sl2'>Uppercase letter</li>
                  </div>
                </div>
                <div className="password-c">
                  <div className='signal-container'>
                    <div id='sb3' className='signal-box'></div>
                    <li id='sl3'>Lowercase character</li>
                  </div>
                  <div className='signal-container'>
                    <div id='sb4' className='signal-box'></div>
                    <li id='sl4'>Number</li>
                  </div>
                </div>
              </div>
              <div className="form-group">
                <input id="Signup-submit" type="submit" value="Sign in" />
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}
