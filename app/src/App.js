import logo from './logo.svg';
import './App.css';
import Header from './components/Header';

function App() {
  return (
    <div className="App">
      <div className='overlay'></div>
      <header className="App-header">
        <Header/>
      </header>
      <body>
        <p className='homeText'>„Transform your customer experience <br></br> with state-of-the-art chatbots“</p>
      </body>
    </div>
  );
}

export default App;
