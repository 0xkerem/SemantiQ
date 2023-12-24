import React, { useState, useEffect } from 'react';
import axios from 'axios';

export default function Chat({ botId }) {
  const [chatId, setChatId] = useState(-1);
  const [botName, setBotName] = useState('');
  const [messages, setMessages] = useState([]);
  const [inputText, setInputText] = useState('');

  useEffect(() => {
    // Fetch botName using Axios GET request
    axios.get(`http://localhost:8080/api/bots/${botId}`)
      .then(response => {
        setBotName(response.data);
      })
      .catch(error => {
        console.error('Error fetching botName:', error);
      });
  }, [botId]);

  const generateCode = () => {
    const generatedCode = `
      <div id="semantiq-chatbot"></div>
      <script data-bot-id="${botId}" defer="defer" src="http://localhost:8080/static/main.57a7bb31.js"></script>
      <script>
        var link = document.createElement("link");
        link.href = "http://localhost:8080/static/main.ba187d75.css";
        link.rel = "stylesheet";
        document.head.appendChild(link);
      </script>
    `;

    copyToClipboard(generatedCode);
  
    // Alert message to indicate code has been copied
    alert('Code successfully copied to clipboard! To integrate the chatbox, insert the script elements just before the closing </body> tag, and place the div with the ID "seamntiq-chatbot" wherever you want the chatbox to appear.');
  };

  function copyToClipboard(text) {
    const el = document.createElement('textarea');
    el.value = text;
    el.setAttribute('readonly', '');
    el.style.position = 'absolute';
    el.style.left = '-9999px';
    document.body.appendChild(el);
    el.select();
    document.execCommand('copy');
    document.body.removeChild(el);
  }
  
  const votePositive = () => {
    sendVote(1);
  };

const voteNegative = async () => {
  sendVote(-1);
  const userEmail = prompt('If you want to contact a real person, leave your email address, otherwise leave it blank.');
  
  if (userEmail) {
      const data = {
          chatId: chatId,
          userEmail: userEmail,
          chatBotId: botId
      };

      try {
          const response = await axios.post('http://localhost:8080/api/email/report', data);
          
          if (response.status === 200) {
              // Request successful, do something
              console.log('Email reported successfully');
          } else {
              console.error('Failed to report email');
          }
      } catch (error) {
          // Handle errors
          console.error('Error:', error);
      }
  }
};


  const sendVote = (vote) => {
    // Determine the chatId for the vote
    const chatId = messages.length > 0 ? messages[messages.length - 1].chatId : -1;

    // Sending a POST request to vote
    axios.post(`http://localhost:8080/api/botdata/${botId}/chats/${chatId}/${vote}`)
      .then(response => {
        console.log(`Voted ${vote === 1 ? 'positive' : 'negative'}`);
      })
      .catch(error => {
        console.error('Error sending vote:', error);
      });
  };

  const sendMessage = (e) => {
    e.preventDefault();

    if (inputText.trim() === '') return;

    // Update messages state with the user message immediately
    const newUserMessage = { text: inputText, sender: 'user' };
    setMessages([...messages, newUserMessage]);
    setInputText('');

    // Send user message to the server via Axios POST request
    const currentChatId = messages.length === 0 ? -1 : messages[messages.length - 1].chatId;
    axios
      .post(`http://localhost:8080/api/bots/${botId}/chat/${currentChatId}`, {
        question: inputText,
      })
      .then((response) => {
        let newAssistantMessage = response.data.answer.content;
        const newChatId = response.data.chatId;

        // Remove unwanted content if it exists in the assistant's message
        if (newAssistantMessage.startsWith('{"content":"') && newAssistantMessage.endsWith('"}')) {
          newAssistantMessage = newAssistantMessage.substring(12, newAssistantMessage.length - 2);
        }

        // Update chatId state with the new chatId
        setChatId(newChatId);

        // Update messages state with the new assistant message, preserving previous messages
        setMessages((prevMessages) => [
          ...prevMessages,
          { text: newAssistantMessage, sender: 'assistant', chatId: newChatId },
        ]);
      })
      .catch((error) => {
        console.error('Error sending message:', error);
      });
  };
  
  return (
    <div className='message-body'>
        <div className="chat-header">
          <span style={{ margin: '15px' }}>{botName}</span>
          <div className='chat-buttons'>
            <button style={{ color: 'white' }} onClick={generateCode}>{'</>'}</button>
            <button onClick={votePositive}>
              <span style={{ color: 'white', display: 'flex', alignItems: 'center' }}
                className="material-symbols-outlined">thumb_up</span>
            </button>
            <button onClick={voteNegative}>
              <span style={{ color: 'white', display: 'flex', alignItems: 'center' }} 
                className="material-symbols-outlined">thumb_down
              </span>
            </button>
          </div>
        </div>
        <div className='chat-main'>
          <div className="chat-messages">
          {messages.map((msg, index) => (
              <div
                key={index}
                className={`message-container ${msg.sender === 'user' ? 'user' : 'assistant'}`}
              >
                <div className="message">
                  {msg.text}
                </div>
              </div>
            ))}
          </div>
          
          <form onSubmit={sendMessage} className="message-input">
            <input
              className='Chat-input'
              type="text"
              value={inputText}
              placeholder='Enter your message'
              onChange={(e) => setInputText(e.target.value)}
            />
            <button className="message-send-btn" type="submit">
              <span style={{ color: 'white', display: 'flex', alignItems: 'center' }} className="material-symbols-outlined">send</span>
            </button>
          </form>
        </div>
    </div>
  );
}
