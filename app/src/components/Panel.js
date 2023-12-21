import React, { useState, useEffect } from 'react';
import Dashboard from './Dashboard';
import Create from './Create';

export default function Panel({ email }) {
  const [userId, setUserId] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // Send a request to fetch user data
    fetch(`http://localhost:8080/api/users/${email}/load`)
      .then(response => response.json())
      .then(data => {
        if (data && data.botId !== -1) {
          setUserId(data.id); // Save the user ID from the response
        }
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
      {userId !== null && userId !== -1 ? (
        <Dashboard />
      ) : (
        <div>
          <center>
            <div className='new-user-alert'>
              <p>
                You don't have a Chatbot yet! To access your control panel, you first need to create a Chatbot.
              </p>
            </div>
          </center>
          <Create userId={userId} />
        </div>
      )}
    </div>
  );
}
