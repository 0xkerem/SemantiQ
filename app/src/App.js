import './App.css';
import Home from './components/Home';
import { useState } from 'react';
import Panel from './components/Panel';

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  const handleLoginSuccess = () => {
    setIsLoggedIn(true);
  }

  return (
    <div className="App">
      {isLoggedIn ? (
        <Panel />
      ) : (
        <Home onLoginSuccess={handleLoginSuccess} />
      )}
    </div>
  );
}

export default App;
