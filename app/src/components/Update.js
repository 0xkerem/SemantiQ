import React, { useState, useEffect } from 'react';

export default function Update({ userId }) {
  const [botName, setBotName] = useState('');
  const [companyName, setCompanyName] = useState('');
  const [companyWebAddress, setCompanyWebAddress] = useState('');
  const [sections, setSections] = useState([
    {
      sectionName: '',
      sectionInfo: '',
      webAddress: ''
    }
  ]);

    // Set document title
    useEffect(() => {
      document.title = "SemantiQ - Update Chatbot"
    }, [])

  const handleAddSection = () => {
    setSections([...sections, { sectionName: '', sectionInfo: '', webAddress: '' }]);
  };

  const handleSectionChange = (index, event) => {
    const { name, value } = event.target;
    const updatedSections = [...sections];
    updatedSections[index][name] = value;
    setSections(updatedSections);
  };

  const handleUpdateBot = () => {
    const formData = {
      botName,
      companyName,
      companyWebAddress,
      sections
    };
  
    console.log(formData);
  
    // Sending the form data as JSON in the request body
    fetch(`http://localhost:8080/api/bots/users/${userId}/update`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(formData)
    })
      .then(response => {
        // Check if the response is OK (status code 200)
        if (response.ok) {
          // Page refresh upon successful response
          window.location.reload();
        } else if (response.status === 400) { // Check for 400
          alert('You have reached the daily update limit. Please try again tomorrow.');
        } else {
          console.log('Response:', response);
        }
      })
      .catch(error => {
        // Handle errors
        console.error('Error:', error);
      });
  };

  const handleBackButton = () => {
    window.location.reload();
  }

  return (
    <div className='CreateUpdate-body'>
      <div className='Update-back-button'>
        <span className="material-symbols-outlined" onClick={handleBackButton}>arrow_back</span>
      </div>
      <div>
        <div className='botform-item'>
          <label htmlFor="botName">Bot Name:</label>
          <input
            type="text"
            id="botName"
            name="botName"
            value={botName}
            className='botform'
            onChange={(e) => setBotName(e.target.value)}
          />
        </div>
        <div className='botform-item'>
          <label htmlFor="companyName">Company Name:</label>
          <input
            type="text"
            id="companyName"
            name="companyName"
            value={companyName}
            className='botform'
            onChange={(e) => setCompanyName(e.target.value)}
          />
        </div>
        <div className='botform-item'>
          <label htmlFor="companyWebAddress">Company Web Address:</label>
          <input
            type="text"
            id="companyWebAddress"
            name="companyWebAddress"
            value={companyWebAddress}
            className='botform'
            onChange={(e) => setCompanyWebAddress(e.target.value)}
          />
        </div>

        {sections.map((section, index) => (
          <div className='botformSection' key={index}>
            <hr />
            <div className='botform-item'>
              <label htmlFor={`sectionName-${index}`}>Section Name:</label>
              <input
                type="text"
                id={`sectionName-${index}`}
                name="sectionName"
                value={section.sectionName}
                className='botform'
                onChange={(event) => handleSectionChange(index, event)}
              />
            </div>
            <div className='botform-item'>
              <label htmlFor={`sectionInfo-${index}`}>Section Info:</label>
              <textarea
                id={`sectionInfo-${index}`}
                name="sectionInfo"
                value={section.sectionInfo}
                className='botform-ta'
                onChange={(event) => handleSectionChange(index, event)}
              />
            </div>
            <div className='botform-item'>
              <label htmlFor={`webAddress-${index}`}>Web Address:</label>
              <input
                type="text"
                id={`webAddress-${index}`}
                name="webAddress"
                value={section.webAddress}
                className='botform'
                onChange={(event) => handleSectionChange(index, event)}
              />
            </div>
          </div>
        ))}
        <button className='botformButton' onClick={handleAddSection}>+ Add Section</button>
      </div>
      <button className='botformButton' onClick={handleUpdateBot}>Update Bot</button>
    </div>
  );
};
