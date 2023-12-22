import React, { useEffect, useState, useCallback } from 'react';
import PieChart from './PieChart';
import LineChart from './LineChart';
import Update from './Update';
import ChatDetailBox from './ChatDetailBox';
import axios from 'axios';

export default function Dashboard({ userData }) {
  const [voteData, setVoteData] = useState({ countPos: 0, countNeg: 0 });
  const [showUpdate, setShowUpdate] = useState(false);
  const [chatData, setChatData] = useState([]);
  const [chatVote, setChatVote] = useState([]);
  const [chatID, setChatId] = useState([]);
  const [chatHistory, setchatHistory] = useState([]);

  const fetchVoteData = useCallback(async () => {
    try {
      const chatBotId = userData.botId;
      const response = await axios.get(`http://localhost:8080/api/botdata/${chatBotId}/votes`);
      setVoteData(response.data);
    } catch (error) {
      console.error('Error fetching vote data:', error);
    }
  }, [userData.botId]);

  useEffect(() => {
    // Fetch vote data when component mounts
    fetchVoteData();
  }, [fetchVoteData]);

  const fetchData = useCallback(async () => {
    try {
      const chatBotId = userData.botId;
      const response = await axios.get(`http://localhost:8080/api/botdata/bots/${chatBotId}/chats-count`);
      setChatData(response.data);
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  }, [userData.botId]);

  useEffect(() => {
    // Fetch data when component mounts
    fetchData();
  }, [fetchData]);

  const fetchChatData = useCallback(async (chatId) => {
    try {
      const response = await axios.get(`http://localhost:8080/api/chats/${chatId}/data/${userData.email}`);
      const data = response.data;
      setChatId(data.id);
      setChatVote(data.vote);

    } catch (error) {
      alert(`The Chat with ${chatId} is not belong to your chatbot!`)
      console.error('Error fetching chat data:', error)
    }
  }, [userData.email]);

  const combineMessages = (data) => {
    let combinedString = '';
  
    data.forEach((item) => {
      const content = JSON.parse(item.content);
  
      if (item.role === 'user') {
        combinedString += `[User] : ${content.question}\n`;
      } else if (item.role === 'assistant') {
        combinedString += `[Assistant] : ${content.content}\n`;
      }
    });
  
    return combinedString;
  };

  const fetchChatHistory = useCallback(async (chatId) => {
    try {
      const response = await axios.get(`http://localhost:8080/api/chats/${chatId}`);
      console.log(response.data)
      const data = response.data;
      setchatHistory(combineMessages(data))

    } catch (error) {
      console.error('Error fetching chat data:', error)
    }
  }, []);

  const handleUpdateButtonClick = () => {
    setShowUpdate(true);
  };

  const handleInputChange = useCallback((event) => {
    const id = event.target.value;
    if (id !== "") {
      fetchChatData(id);
      fetchChatHistory(id);
    } else {
      setchatHistory("");
      setChatId("");
      setChatVote("");
    }
  }, [fetchChatData]);

  // Set document title
  useEffect(() => {
    document.title = "SemantiQ - Dashboard";
  }, []);

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
                    <input
                      className='db-ch-input'
                      type='text'
                      placeholder='Enter Chat #ID'
                      onChange={handleInputChange}></input>
                  </center>
                  <center>
                    <div className='db-ch-ln2container'>
                      <div className='db-ch-chatid'>#{chatID}</div>
                      <div className='db-ch-chatvote'>{chatVote}</div>
                    </div>
                  </center>
                  <div></div>
                <center>
                  <div className='db-ch-basecontainer'>
                  <div className='db-ch-detailbox'>{chatHistory}</div>
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