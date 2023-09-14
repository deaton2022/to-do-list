import { Link } from "react-router-dom"
// import AuthContext from "./contexts/AuthContext"
import { useContext } from "react"


const Nav = () => {  
//   const auth = useContext(AuthContext)
//   const user = auth.user
    return (
      <nav>
        {/* always */}
        <Link to="/landing">Home</Link>
        {" "}
      

        {/* only logged out */}
        {/* { !user && (
            <>
          <Link to="/login">Log In</Link>
          {" "}
          <Link to='/signup'>Sign Up</Link>
          </>
        )} */}
      </nav>
    )
}

export default Nav