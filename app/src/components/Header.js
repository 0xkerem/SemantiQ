import React from 'react'

export default function Header() {
  return (
    <div className='header'>
      <div className='Header-logo-container'>
        <p className='Header-logo'>SemantiQ</p>
      </div>
      <div className='Header-button-container'>
        <button className='Header-login-button'>Log in</button>
        <button className='Header-signup-button'>Sign up</button>
      </div>
    </div>
  )
}
