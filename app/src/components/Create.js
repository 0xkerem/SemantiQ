import React, { useState } from 'react';

export default function Create({ userId }) {
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

  const handleAddSection = () => {
    setSections([...sections, { sectionName: '', sectionInfo: '', webAddress: '' }]);
  };

  const handleSectionChange = (index, event) => {
    const { name, value } = event.target;
    const updatedSections = [...sections];
    updatedSections[index][name] = value;
    setSections(updatedSections);
  };

  const handleCreateBot = () => {
    const formData = {
      botName,
      companyName,
      companyWebAddress,
      sections
    };

    console.log(formData);

    // Sending the form data as JSON in the request body
    fetch(`http://localhost:8080/api/bots/${botName}/users/${userId}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(formData)
    })
      .then(response => {
        // Handle the response
        console.log('Response:', response);
        // You can add further logic here for success/error handling
      })
      .catch(error => {
        // Handle errors
        console.error('Error:', error);
      });
  };

  return (
    <div className='CreateUpdate-body'>
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
      <button className='botformButton' onClick={handleCreateBot}>+ Create Bot</button>
    </div>
  );
};
