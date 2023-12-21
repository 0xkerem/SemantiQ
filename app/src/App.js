import './App.css';
import Home from './components/Home';
import { useState } from 'react';
import Panel from './components/Panel';

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [emailAddress, setEmailAddress] = useState('');

  const handleLoginSuccess = (email) => {
    setEmailAddress(email); // Set the email address received from login
    setIsLoggedIn(true);
  }

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
