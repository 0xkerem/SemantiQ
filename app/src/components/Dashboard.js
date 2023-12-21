import React, { useEffect } from 'react'
import PieChart from './PieChart'

export default function Dashboard() {
  // Set document title
  useEffect(() => {
    document.title = "SemantiQ - Dashboard"
  }, [])

  return (
    <div className='Dashboard-body'>
      <div className='db-top'>
        <div className='db-t1'>
          <button className='db-update'>Update Bot</button>
        </div>
        <div className='db-t2'></div>
        <div className='db-t3'>
          <div className='db-user-block'>
            <div><p className='db-user-text'>User</p></div>
            <div className='db-user-circle'></div>
          </div>
        </div>
      </div>
      <div className='db-main'>
        <div className='db-chathistory'>
          <div className='db-chathistorybox db'>
            <div>
              <h2 className='db-h2'>View Chat</h2>
            </div>
          </div>
        </div>
        <div className='db-stats'>
          <div className='db-usage db'>
            <div>
              <h2 className='db-h2'>Chatbot Usage Stats</h2>
            </div>
          </div>
          <div className='db-main-bottom'>
            <div className='db-chatlist db'>
              <div>
                <h2 className='db-h2'>Chat History</h2>
              </div>
            </div>
            <div className='db-happy db'>
              <div>
                <h2 className='db-h2'>Customer Happiness</h2>
                <div className='pie-container'>
                  <PieChart positive={80} negative={20} />
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
        </div>
      </div>
    </div>
  )
}
