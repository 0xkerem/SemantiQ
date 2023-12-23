import React, { useState, useEffect } from 'react';
import axios from 'axios';

export default function Chat({ botId }) {
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
    // Generate code to add chatbot to user's webpage
    // Copy code to clipboard (use document.execCommand('copy'))
    // Alert the user that the code has been copied
    const generatedCode = `<Your chatbot code>`;
    // Copy code to clipboard (implementation required)
    // Alert message (implementation required)
  };

  const sendMessage = (e) => {
    e.preventDefault(); // Prevents the default form submission behavior
  
    if (inputText.trim() === '') return;
  
    // Update messages state with the user message immediately
    const newUserMessage = { text: inputText, sender: 'user' };
    setMessages([...messages, newUserMessage]);
    setInputText('');
  
    // Send user message to the server via Axios POST request
    const chatId = messages.length === 0 ? -1 : messages[messages.length - 1].chatId;
    axios.post(`http://localhost:8080/api/bots/${botId}/chat/${chatId}`, {
        question: inputText,
        // Include any necessary data for the message
      })
      .then(response => {
        let newAssistantMessage = response.data.answer.content;
        const newChatId = response.data.chatId;
  
        // Remove unwanted content if it exists in the assistant's message
        if (newAssistantMessage.startsWith('{"content":"') && newAssistantMessage.endsWith('"}')) {
          newAssistantMessage = newAssistantMessage.substring(12, newAssistantMessage.length - 2);
        }
  
        // Update messages state with the new assistant message, preserving previous messages
        setMessages(prevMessages => [
          ...prevMessages,
          { text: newAssistantMessage, sender: 'assistant', chatId: newChatId },
        ]);
      })
      .catch(error => {
        console.error('Error sending message:', error);
      });
  };
  

  return (
    <div className='message-body'>
        <div className="chat-header">
          <span style={{ margin: '15px' }}>{botName}</span>
          <button style={{ margin: '15px' }} onClick={generateCode}>{'</>'}</button>
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
              <span style={{ color: 'white', display: 'flex', alignItems: 'center' }} class="material-symbols-outlined">send</span>
            </button>
          </form>
        </div>
    </div>
  );
}
