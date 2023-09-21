import { useEffect, useState } from "react";
import "./Landing.css";
import { Link } from "react-router-dom";

function Landing() {
    const [tasks, setTasks] = useState([]); 
    
    // const loadTasks = () => {
    //     fetch("http://localhost:8080/api/task")
    //     .then(response => response.json())
    //     .then(payload => setTasks(payload))
    //   }
    //   useEffect(() => {
    //     loadTasks();
    // }, []);

    
    return (
        <>
        <div className="landing-container">
           
            <section id="tasks">
                <section>
                          <div class="welcome-text">
                                <h2>Get It Done</h2>

                                <p style={{ textDecoration: 'none', color: "white"}}>Organize and Prioritize your tasks. </p>
                                    <button class="btn btn-login-container" >
                                        <Link to="/tasklist">Let's Do This</Link>
                                    </button>
                         </div>
                
                {/* <h2 id="tasks-title">tasks</h2> */}
            </section>
                <div className="tasks-flex-container">
                {tasks ? 
                    tasks.map(task => (
                        <div key={task} >
                            <h3 className="task-card-title">{task}</h3>
                        </div>
                    ))
                    : null
                }
                </div>
            </section>
         
        </div>
        <div>
               <footer id="footer-landing">
                <nav>
                    <a href="mailto:ministack@company.com">Email :  generic@company.com</a>
                    <div id="landing-links">
                        <Link to="/about">About</Link>
                        <Link to="/contact">Contact</Link>
                    </div>
                </nav>
            </footer>
        </div>
        </>
    );
}

export default Landing;