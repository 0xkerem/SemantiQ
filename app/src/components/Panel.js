import React, { useState, useEffect } from 'react';
import Dashboard from './Dashboard';
import CreateUpdate from './CreateUpdate';

export default function Panel({ email }) {
  // Set new document title
  useEffect(() => {
    document.title = "SemantiQ - Dashboard";
  }, []);

  const [hasBot, setHasBot] = useState(false);

  return (
    <div className='Panel-body'>
      {hasBot ? (
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
          <CreateUpdate />
        </div>
      )}
    </div>
  );
}
