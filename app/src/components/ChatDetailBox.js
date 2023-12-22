import React, { useEffect, useState } from 'react';
import axios from 'axios';

export default function ChatDetailBox({ chatBotId, onChatClick }) {
  const [chats, setChats] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/api/chats/bots/${chatBotId}`);
        if (response.status === 200) {
          setChats(response.data);
        }
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    fetchData();
  }, [chatBotId]);

  const handleChatClick = (chatId) => {
    onChatClick(chatId); // Sends the signal to the parent component with the clicked chat ID
  };

  return (
    <div style={{ maxHeight: '300px', overflowY: 'scroll' }}>
      {chats.map((chat) => (
        <div
          key={chat.Id}
          onClick={() => handleChatClick(chat.Id)}
          style={{ cursor: 'pointer', color: chat.vote === 1 ? 'green' : chat.vote === -1 ? 'red' : 'inherit' }}
        >
          {chat.Id} - {chat.date} - {chat.vote === 1 ? '+' : chat.vote === -1 ? '-' : ''}
        </div>
      ))}
    </div>
  );
};