import {  useContext, useEffect, useState } from 'react';
import AuthContext from '../../contexts/AuthContext';
import { Link } from 'react-router-dom';

const TaskList = () =>{
const[task,setTask] = useState([])
const auth = useContext(AuthContext)

const loadTasks = () => {
    fetch(`http://localhost:8080/api/task`,{method: "GET",
headers: {
    "Content-Type": "application/json",
    Accept:"application/json",
    Authorization: "Bearer " 

}})
    .then(response => response.json())
    .then(payload => setTask(payload))
}

useEffect(loadTasks,[])

    return (
        <header>
            <div>
            <div className="flex-container task-list">
                {task.length !== 0 ? 
                
                <div className="row">
              
                    {task.map(tasks => (
                        <div className="col-4" key={tasks.taskId}>
                            
                            <div className="task-card">
                            <h3 >{tasks.name}</h3>
                                <div className="task-details">
                                <p>Name: {tasks.name}</p>
                                <p>Due Date: {tasks.dueDate}</p>
                                </div>
                             <div className="">   
                               {/* <div className="button-container">
                                    <button className="card-button" >
                                    {auth.user ? 
                                    <Link style={{ textDecoration: 'none', color: "white"}} to={`/dashboard/${tasks.taskId}/${tasks.dueDate}`}>View Task</Link>
                                    : <Link to="/login">Login to View</Link>
                                    } 
                                    </button>
                                </div>   */}
                                <div className="button-container">  
                                    <button className="card-button-edit">
                                        { auth.auth.user ? 
                                        <Link style={{ textDecoration: 'none', color: "white"}} to={`/edit/${tasks.taskId}`}>Edit</Link>
                                        : <Link to="/login">Login to edit</Link>
                                            }
                                    </button>
                                </div> 
                                <div className="button-container"> 
                                    <button className="card-button-delete"> { auth.auth.user ? 
                                        <Link style={{ textDecoration: 'none', color: "white"}} to={`/delete/${tasks.taskId}`} >Delete</Link>
                                      : <Link to="/login">Login to delete</Link>
                                    }</button>
                                </div>
                            </div>    
                              
                            </div>
                       
                       </div>

                    ))}               
                   </div> 
           : <p className="para"> No Tasks to display.</p>}  
            </div>
            </div>
        </header>
    )
}
export default TaskList