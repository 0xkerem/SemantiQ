import React, { useEffect, useState } from 'react'
import PieChart from './PieChart'
import LineChart from './LineChart'
import Update from './Update'
import ChatDetailBox from './ChatDetailBox';
import axios from 'axios';

export default function Dashboard({ userData }) {
  const [voteData, setVoteData] = useState({ countPos: 0, countNeg: 0 });
  const [showUpdate, setShowUpdate] = useState(false);
  const [chatData, setChatData] = useState([]);

  const fetchVoteData = async () => {
    try {
      const chatBotId = userData.botId;
      const response = await axios.get(`http://localhost:8080/api/botdata/${chatBotId}/votes`);
      setVoteData(response.data);
    } catch (error) {
      console.error('Error fetching vote data:', error);
    }
  };

  useEffect(() => {
    // Fetch vote data when component mounts
    fetchVoteData();
  }, [userData.botId]); // Ensure to update when userData.botId changes
  
 
  const handleUpdateButtonClick = () => {
    setShowUpdate(true);
  };
  
  const fetchData = async () => {
    try {
      const chatBotId = userData.botId;
      
      const response = await axios.get(`http://localhost:8080/api/botdata/bots/${chatBotId}/chats-count`);
      setChatData(response.data);
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  };
  
  // Set document title
  useEffect(() => {
    document.title = "SemantiQ - Dashboard";

    // Fetch data when component mounts
    fetchData();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []); // Empty dependency array to run only on mount

  // Function to handle logout and clear local storage
  const handleLogout = () => {
    localStorage.setItem('rememberMe', 'false');
    localStorage.removeItem('emailAddress');
    // Refresh the page to apply changes after logout
    window.location.reload();
  };

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
                  <LineChart data={chatData} />
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
                <ChatDetailBox chatBotId={userData.botId}/>
              </div>
              <div className='db-happy db'>
                <div>
                  <h2 className='db-h2'>Customer Happiness</h2>
                  <div className='pie-container'>
                    <PieChart positive={voteData.countPos} negative={voteData.countNeg} />
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
