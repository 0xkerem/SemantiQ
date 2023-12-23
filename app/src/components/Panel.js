import React, { useState, useEffect } from 'react';
import Dashboard from './Dashboard';
import Create from './Create';

export default function Panel({ email }) {
  const [userData, setUserData] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // Send a request to fetch user data
    fetch(`http://localhost:8080/api/users/${email}/load`)
      .then(response => response.json())
      .then(data => {
        setUserData(data); // Save the entire user data from the response
        setLoading(false);
      })
      .catch(error => {
        console.error('Error fetching user data:', error);
        setLoading(false);
      });
  }, [email]);

  if (loading) {
    return <p>Loading...</p>; // Render a loading indicator while fetching data
  }

  return (
    <div className='Panel-body'>
      {userData && userData.botId !== -1 ? (
        <Dashboard userData={userData} />
      ) : (
        <div>
          <center>
            <div className='new-user-alert'>
              <p>
                You don't have a Chatbot yet! To access your control panel, you first need to create a Chatbot.
              </p>
            </div>
          </center>
          <Create userData={userData.id} />
        </div>
      )}
    </div>
  );
}
