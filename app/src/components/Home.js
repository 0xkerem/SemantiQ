import React from 'react'
import Header from './Header'

export default function Home({ onLoginSuccess }) {
  return (
    <div className='Home'>
      <div className='overlay'></div>
      <header className="Home-header">
        <Header onLoginSuccess={onLoginSuccess} />
      </header>
      <div className='body'>
        <p className='homeText'>„Transform your customer experience <br></br> with state-of-the-art chatbots“</p>
      </div>
    </div>
  )
}
