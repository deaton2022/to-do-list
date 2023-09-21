import { Link } from "react-router-dom"
import AuthContext from "../contexts/AuthContext"
import { useContext } from "react"
import "./Nav.css";


const Nav = () => {  
  const auth = useContext(AuthContext)
  const user = auth.auth.user
    return (
      <nav>
        {/* always */}
        <Link to="/landing">Home</Link>
        {" "}

        {user && (
        <div>
            <Link to="/tasklist">Task</Link>
            
            <Link to="/add">Add Task</Link>

            {/* <button><Link to="/logout">Log Out</Link></button> */}

        </div>
        )}



        {!user && (
        <div>
              <button><Link to="/login">Login</Link></button>

              <button><Link to="/signup">Sign Up</Link></button>
        </div>
        )}
      </nav>
    )
}

export default Nav