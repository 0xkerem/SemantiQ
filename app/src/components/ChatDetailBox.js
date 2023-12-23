import React, { useEffect, useState } from 'react';
import axios from 'axios';

export default function ChatDetailBox({ chatBotId, onChatClick }) {
  const [chats, setChats] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/api/chats/bots/${chatBotId}`);
        if (response.status === 200) {
          const formattedChats = response.data.map(chat => ({
            id: chat.id,
            date: new Date(chat.datetime).toLocaleString(), // Format date as needed
            vote: chat.vote
          }));
          setChats(formattedChats);
        }
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    fetchData();
  }, [chatBotId]);

  const handleChatClick = (chatId) => {
    onChatClick(chatId);
  };

  return (
    <div style={{ maxHeight: '300px' }}>
      {chats.map((chat) => (
        <div
          className='cdb-container'
          key={chat.id}
          onClick={() => handleChatClick(chat.id)}
          style={{
            cursor: 'pointer',
          }}
        >
          <div className='cdb-1'>#{chat.id}</div>
          <div className='cdb-2'>{chat.date}</div>
          <div className='cdb-3' 
          style={{
            color: chat.vote === "\"1\"" ? 'green' : chat.vote ===  "\"-1\"" ? 'red' : 'inherit'
          }}
          >{chat.vote === "\"1\"" ? <span className="material-symbols-outlined">
          sentiment_satisfied
          </span> : chat.vote === "\"-1\"" ? <span className="material-symbols-outlined">
          sentiment_dissatisfied
          </span> : ''}
          </div>
        </div>
      ))}
    </div>
  );
};
