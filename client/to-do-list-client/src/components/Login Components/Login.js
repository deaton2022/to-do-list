import React, { useContext, useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import AuthContext from "../../contexts/AuthContext";


export default function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [errors, setErrors] = useState([]);

  const navigate = useNavigate();

  const auth = useContext(AuthContext)

  const handleSubmit = async (event) => {
    event.preventDefault();

    const response = await fetch("http://localhost:8080/authenticate", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        username,
        password,
      }),
    });
  
    // This code executes if the request is successful
    if (response.status === 200) {
      const { jwt_token } = await response.json();
      auth.auth.login(jwt_token);
      navigate("/tasklist");
    } else if (response.status === 403) {
      setErrors(["Login failed."]);
    } else {
      setErrors(["Unknown error."]);
    }
  };

  return (
    <div className="login-signup-form-container">
      {errors.map((error, i) => (
        <div className="login-error" key={i}>{error}</div>
      ))}
      <form onSubmit={handleSubmit} className="login-signup-form">
      <h2 className="login-signup-header">Login</h2>
        <div className="form-group">
          <label className="login-label mx-2" htmlFor="username">Username</label>
          <input
            type="text"
            onChange={(event) => setUsername(event.target.value)}
            value={username}
            id="username"
            class="login-input"
          />
        </div>
        <div>
          <label className="login-label my-2 mx-2 form-group" htmlFor="password">Password</label>
          <input
            type="password"
            onChange={(event) => setPassword(event.target.value)}
            value={password}
            id="password"
            class="login-input"
          />
        </div>
        <Link to="/signup" className="login-link">
          <p>Don't have an Account?</p>
        </Link>
        <div>
          <button type="submit" className="login-button btn btn-secondary">Login</button>
        </div>
      </form>
  </div>  
  );
}