import React, { useState } from 'react';
import Login from './Login';

export default function Signup() {
  const [showLogin, setShowLogin] = useState(false);

  const handleLoginClick = () => {
    setShowLogin(true);
  };

  const handleBacktoSignUp = () => {
    setShowLogin(false);
  };

  const handlePasswordChange = (event) => {
    const pass = event.target.value;

     // Check if the password meets the length requirement
    const hasValidLength = pass.length >= 8;

     // Check if the password contains at least three uppercase letters
    const hasUpperCase = pass.match(/[A-Z]/g);

    // Check if the password contains at least three lowercase letters
    const hasLowerCase = pass.match(/[a-z]/g);

    // Check if the password contains at least three digits
    const hasNumber = pass.match(/[0-9]/g);

    if (hasValidLength) {
      document.getElementById('sb1').style.backgroundColor = 'green';
      document.getElementById('sl1').style.color = 'green';
    } else {
      document.getElementById('sb1').style.backgroundColor = 'tomato';
      document.getElementById('sl1').style.color = 'tomato';
    }
    
    if (hasUpperCase) {
      document.getElementById('sb2').style.backgroundColor = 'green';
      document.getElementById('sl2').style.color = 'green';
    } else {
      document.getElementById('sb2').style.backgroundColor = 'tomato';
      document.getElementById('sl2').style.color = 'tomato';
    }
    
    if (hasLowerCase) {
      document.getElementById('sb3').style.backgroundColor = 'green';
      document.getElementById('sl3').style.color = 'green';
    } else {
      document.getElementById('sb3').style.backgroundColor = 'tomato';
      document.getElementById('sl3').style.color = 'tomato';
    }
    
    if (hasNumber) {
      document.getElementById('sb4').style.backgroundColor = 'green';
      document.getElementById('sl4').style.color = 'green';
    } else {
      document.getElementById('sb4').style.backgroundColor = 'tomato';
      document.getElementById('sl4').style.color = 'tomato';
    }

    if (hasValidLength && hasUpperCase && hasLowerCase && hasNumber) {
      // Enable the submit button
      document.getElementById('Signup-submit').disabled = false;
    } else {
      // Disable the submit button
      document.getElementById('Signup-submit').disabled = true;
    }
  };

  const handleSignup = async (event) => {
    event.preventDefault();
  
    const nameInput = document.getElementById('name');
    const surnameInput = document.getElementById('surname');
    const companyInput = document.getElementById('company');
    const emailInput = document.getElementById('email');
    const passwordInput = document.getElementById('password');
  
    const name = nameInput.value;
    const surname = surnameInput.value;
    const company = companyInput.value;
    const email = emailInput.value;
    const password = passwordInput.value;
  
    // Check if any of the input fields are empty
    if (!name || !surname || !company || !email || !password) {
      // Change the border color of empty input fields to tomato
      if (!name) nameInput.style.borderColor = 'tomato';
      if (!surname) surnameInput.style.borderColor = 'tomato';
      if (!company) companyInput.style.borderColor = 'tomato';
      if (!email) emailInput.style.borderColor = 'tomato';
      if (!password) passwordInput.style.borderColor = 'tomato';
      
      console.error('Please fill in all fields');
      return; // Prevent further execution
    }
  
    // If all fields are filled, reset border colors (if previously set)
    nameInput.style.borderColor = '';
    surnameInput.style.borderColor = '';
    companyInput.style.borderColor = '';
    emailInput.style.borderColor = '';
    passwordInput.style.borderColor = '';
  
    const requestBody = JSON.stringify({
      name,
      surname,
      company,
      email,
      password,
    });
  
    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'http://localhost:8080/api/users/signup', true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.onload = function () {
      if (xhr.status === 200) {
        // Handle the response data
        console.log(xhr.responseText);
      } else {
        // Handle errors
        console.error(xhr.statusText);
      }
    };
    xhr.send(requestBody);
  };
  

  return (
    <div className='Signup'>
      {showLogin ? (
        <Login onBackToSignUp={handleBacktoSignUp} />
      ) : (
        <div className='Signup-box'>
          <div className='Signup-pattern'></div>
          <div className='Signup-base'>
            <div className="Signup-container">
              <h2 className='Signup-heading'>Welcome!</h2>
              <p className='Signup-text'>
                Already have an account?{' '}
                <span className='Login-now' onClick={handleLoginClick}>Log in</span>
              </p>
              <form onSubmit={handleSignup}>
                <div className="form-group">
                  <input type="text" className="losign" name="name" id="name" placeholder="Name" />
                </div>
                <div className="form-group">
                  <input type="text" className="losign" name="surname" id="surname" placeholder="Surname" />
                </div>
                <div className="form-group">
                  <input type="text" className="losign" name="company" id="company" placeholder="Company" />
                </div>
                <div className="form-group">
                  <input type="email" className="losign" name="email" id="email" placeholder="Email Address" />
                </div>
                <div className="form-group">
                  <input type="text" className="losign" name="password" id="password" placeholder="Password" onChange={handlePasswordChange}/>
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
      )}
    </div>
  );
}