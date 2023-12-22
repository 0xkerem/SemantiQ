import React, { useEffect, useState } from 'react'
import PieChart from './PieChart'
import LineChart from './LineChart'
import Update from './Update'
import ChatDetailBox from './ChatDetailBox';

export default function Dashboard({ userData }) {
  const [showUpdate, setShowUpdate] = useState(false);

  // Set document title
  useEffect(() => {
    document.title = "SemantiQ - Dashboard"
  }, [])

  // Function to handle logout and clear local storage
  const handleLogout = () => {
    localStorage.setItem('rememberMe', 'false');
    localStorage.removeItem('emailAddress');
    // Refresh the page to apply changes after logout
    window.location.reload();
  };

  const handleUpdateButtonClick = () => {
    setShowUpdate(true);
  };

  const exampleData = [
    { date: '2023-12-01', totalUsage: 5 },
    { date: '2023-12-02', totalUsage: 6 },
    { date: '2023-12-03', totalUsage: 17 },
    { date: '2023-12-04', totalUsage: 18 },
    { date: '2023-12-05', totalUsage: 40 },
    { date: '2023-12-06', totalUsage: 33 },
    { date: '2023-12-07', totalUsage: 38 },
    { date: '2023-12-08', totalUsage: 30 },
    { date: '2023-12-09', totalUsage: 15 },
    { date: '2023-12-10', totalUsage: 16 },
    { date: '2023-12-11', totalUsage: 7 },
    { date: '2023-12-12', totalUsage: 18 },
    { date: '2023-12-13', totalUsage: 40 },
    { date: '2023-12-14', totalUsage: 23 },
    { date: '2023-12-15', totalUsage: 38 },
  ];

  if (showUpdate) {
    return <Update />;
  } else {
    return (
      <div className='Dashboard-body'>
        <div className='db-top'>
          <div className='db-t1'>
            <button className='db-update' onClick={handleUpdateButtonClick}>Update Bot</button>
          </div>
          <div className='db-t2'></div>
          <div className='db-t3'>
            <div className='db-user-block'>
              <div className='db-user-circle'></div>
              <div>
                <p className='db-user-text'>{userData.name} {userData.surname}</p>
              </div>
              <span className='material-symbols-outlined' onClick={handleLogout}>
                Logout
              </span>
            </div>
          </div>
        </div>
        <div className='db-main'>
          <div className='db-chathistory'>
            <div className='db-chathistorybox db'>
              <div className='db-ch-basecontainer'>
                <h2 className='db-h2'>View Chat</h2>
                  <center>
                    <input className='db-ch-input' type='text' placeholder='Enter Chat #ID'></input>
                  </center>
                  <center>
                    <div className='db-ch-ln2container'>
                      <div className='db-ch-chatid'>#3532</div>
                      <div className='db-ch-chatvote'>+</div>
                    </div>
                  </center>
                  <div></div>
                <center>
                  <div className='db-ch-detailbox'>
                  </div>
                </center>
              </div>
            </div>
          </div>
          <div className='db-stats'>
            <div className='db-usage db'>
              <div>
                <h2 className='db-h2'>Chatbot Usage Stats</h2>
                <div className='db-lc'>
                  <LineChart data={exampleData} />
                </div>
              </div>
            </div>
            <div className='db-main-bottom'>
              <div className='db-chatlist db'>
                <div>
                  <h2 className='db-h2'>Chat History</h2>
                </div>
                <div className='ch-container'>
                  <div className='db-cl-head'>
                    <div className='db-cl-head-element'>
                      <p>ID</p>
                    </div>
                    <div className='db-cl-head-element'>
                      <p>Date</p>
                    </div>
                    <div className='db-cl-head-element'>
                      <p>Vote</p>
                    </div>
                  </div>
                </div>
                <ChatDetailBox />
              </div>
              <div className='db-happy db'>
                <div>
                  <h2 className='db-h2'>Customer Happiness</h2>
                  <div className='pie-container'>
                    <PieChart positive={80} negative={20} />
                  </div>
                  <div className="pie-legend">
                    <div className="legend-item">
                      <div className="color-block" style={{ backgroundColor: '#686EBD' }}></div>
                      <span className="legend-text">Positive</span>
                    </div>
                    <div className="legend-item">
                      <div className="color-block" style={{ backgroundColor: '#939ADF' }}></div>
                      <span className="legend-text">Negative</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div className='db-chat'>
          </div>
        </div>
      </div>
  )}
}
