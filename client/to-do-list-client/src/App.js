import { useEffect, useState } from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import './App.css';
import Nav from './components/Nav';
import Landing from './components/Landing';
import TaskList from './components/Task/TaskList';
import Login from './components/Login Components/Login';
import SignUpForm from './components/Login Components/SignUpForm';
import TaskForm from './components/Task/TaskForm';
import AuthContext from './contexts/AuthContext';
import jwtDecode from 'jwt-decode'
function App() {

  const [user, setUser] = useState(null)

  const login = (token) => {
    // Decode the token
    const { sub: username, authorities: authoritiesString } = jwtDecode(token);
  
    // Split the authorities string into an array of roles
    const roles = authoritiesString.split(',');
  
    // Create the "user" object
    const user = {
      username,
      roles,
      token,
      hasRole(role) {
        return this.roles.includes(role);
      }
    };
    localStorage.setItem("auth-token", token)
    setUser(user);
    return user;
  };

  const logout = () => {
    setUser(null)
    localStorage.removeItem("auth-token")
  }

  const auth = { login, logout, user }


  useEffect(() => {
    const token = localStorage.getItem("auth-token")
    if (token) {
      login(token)
    }else{
      <Navigate to="/login"/>
    } 
  }, [])



  return (
    <AuthContext.Provider value={{auth}}>
        <BrowserRouter>
            <Nav/>
              <Routes>
              <Route path="/landing" element={<Landing />} />
              <Route path="/tasklist" element={user ? <TaskList /> : <Navigate to="/login"/>} />
              <Route path="/add" element={user ? <TaskForm /> : <Navigate to="/login"/>} />
              <Route path="/edit/:taskId" element={user ? <TaskForm/> : <Navigate to="/login"/>} />
              <Route path="/login" element={user ? <Navigate to="/landing" /> :<Login />} />
              <Route path="/signup" element={user ? <Navigate to="/landing" /> :<SignUpForm /> } />
              {/* <Route path="/logout" element={{logout} <Navigate to="/landing"}/> */}
              <Route path="*" element={<p>Page not found.</p>} />

              </Routes>
        </BrowserRouter>
</AuthContext.Provider>
  );
}

export default App;
