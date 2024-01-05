import React, { useEffect, useState, useCallback } from 'react';
import PieChart from './PieChart';
import LineChart from './LineChart';
import Chat from './Chat';
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
      fetchChatHistory(chatId);

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
      const data = response.data;
      setchatHistory(combineMessages(data))

    } catch (error) {
      console.error('Error fetching chat data:', error)
    }
  }, []);

  const handleUpdateButtonClick = () => {
    setShowUpdate(true);
  };

  const handleDeleteButtonClick = async () => {
    try {
      const response = await axios.delete(`http://localhost:8080/api/users/${userData.email}/bots`);
  
      // If the response status is OK (200 range), refresh the page
      if (response.status >= 200 && response.status < 300) {
        window.location.reload(true);
      } else {
        // If the response status is not in the 200 range, alert 'Bad Request'
        alert('Bad Request');
      }
    } catch (error) {
      // If an error occurs during the request, alert the error message
      alert('An error occurred while processing the request.');
      console.error(error);
    }
  };

  const handleInputChange = useCallback((event) => {
    const id = event.target.value;
    if (id !== "") {
      fetchChatData(id);
    } else {
      setchatHistory("");
      setChatId("");
      setChatVote("");
    }
  }, [fetchChatData]);

  const onChatClick = useCallback((id) => {
    if (id !== "") {
      fetchChatData(id);
    } else {
      setchatHistory("");
      setChatId("");
      setChatVote("");
    }
  }, []);

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
    return <Update userId={userData.id}/>;
  } else {
    return (
      <div className='Dashboard-body'>
        <div className='db-top'>
          <div className='db-t1'>
            <button className='db-update' onClick={handleUpdateButtonClick}>Update Bot</button>
            <button className='db-delete' onClick={handleDeleteButtonClick}>Delete Bot</button>
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
                  <div className='db-ch-chatvote'
                  style={{
                    color: chatVote === "1" ? 'green' : chatVote ===  "-1" ? 'tomato' : 'inherit'
                  }}
                  >{
                    chatVote === "1" ? <span className="material-symbols-outlined">sentiment_satisfied</span>
                      : chatVote === "-1" ? <span className="material-symbols-outlined">sentiment_dissatisfied</span> : ''}</div>
                </div>
              </center>
              <center>
                <div className='db-ch-dbcontainer'>
                  <div className='db-ch-detailbox'>{chatHistory}</div>
                </div>
              </center>
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
                <div className='detailbox-container'>
                  <ChatDetailBox chatBotId={userData.botId} onChatClick={onChatClick}/>
                </div>
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
            <div className='db-chat-c1'></div>
            <div className='db-chat-c2'>
              <Chat botId={userData.botId}/>
            </div>
          </div>
        </div>
      </div>
  )}
}