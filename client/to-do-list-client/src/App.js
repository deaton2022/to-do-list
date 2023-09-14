import { useEffect, useState } from 'react';
import { BrowserRouter, Routes, Route, useNavigate } from 'react-router-dom';
import './App.css';
import Nav from './components/Nav';
import Landing from './components/Landing';

function App() {
  return (
<BrowserRouter>
    <Nav/>
      <Routes>
      <Route path="/landing" element={<Landing />} />

      </Routes>
</BrowserRouter>
  );
}

export default App;
