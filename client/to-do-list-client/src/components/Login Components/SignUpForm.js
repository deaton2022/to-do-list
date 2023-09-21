import { useState } from "react"
import { useNavigate,Link } from "react-router-dom"


const SignUpForm = () => {
    const navigate = useNavigate()
    const [errors,setErrors] = useState([]);

    const[username,setUsername] = useState("");
    const [password,setPassword] = useState("");

    const resetState = () => {
        setUsername("")
        setPassword("")
    }

    const handleSubmit = (evt) => {
        evt.preventDefault()

        const newUser = {
            username:username,
            password:password
        }
   
        fetch("http://localhost:8080/api/dashboard", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
           Accept: "application/json",
        },
        body: JSON.stringify(newUser)
      })
      .then(response => {
        if (response.ok) {
        resetState()
        navigate("/login")
        } else {
            response.json()
              .then(error => {
                if (Array.isArray(error)) {
                    setErrors(error)
                  } else {
                    setErrors([error])
                  }
        })
    }
})
    }
    
    return(
        <div className="login-signup-form-container" >
        {errors.length > 0 && (
          <ul className="signup-errors">
            {errors.map(error => <li key={error}>{error}</li>)}
          </ul>
        )}
        <form onSubmit={handleSubmit} className="login-signup-form">
          <h2 className="login-signup-header ml-5">Create an Account</h2> 
          <div className="form-group">
            <label className="mx-2" htmlFor="username-input">Username</label>
            <input id="username-input" className="signup-input" value={username} onChange={(evt) => setUsername(evt.target.value)}/>
          </div>
      
          <div className="form-group">
            <label className="mx-2" htmlFor="password-input">Password</label>{" "}
            <input type="password" id="password-input" className="signup-input" value={password} onChange={(evt) => setPassword(evt.target.value)}/>
          </div>
        
          <div>
            <button type="submit" className="btn btn-secondary" >Submit</button>
            <Link to="/landing" style={{ margin: 0, textDecoration: 'none' }}>
              <button id="btnAdd" className="signup-cancel-button btn btn-warning">Cancel</button>
            </Link>
          </div>
          
        </form>
      </div>      
    )
}
export default SignUpForm