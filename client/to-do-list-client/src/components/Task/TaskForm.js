import { useContext, useEffect, useState } from "react"
import { useNavigate, useParams } from "react-router-dom"
import AuthContext from "../../contexts/AuthContext"
import TaskList from "./TaskList"

const TaskForm = () => {

    const params = useParams()
    const navigate = useNavigate()
    const auth = useContext(AuthContext)


const[importance_id,setImportance_id] = useState("")
const[name,setName] = useState("")
const[description,setDescription] = useState("")
const[dueDate,setDueDate] = useState(Date.now)
const[appUserId,setAppUserId] = useState()
const[completed,setCompleted] = useState(false)

const[errors,setErrors] = useState([]) 

const resetState = () => {
    setImportance_id("")
    setName("")
    setDescription("")
    setDueDate("")
    setAppUserId("")
    setCompleted(false)
}

useEffect(() => {
    if(params.taskId !== undefined){
        const target = find(task => task.taskId === parseInt(params.taskId));
        if(target !== undefined){
            setImportance_id(target.importance_id)
            setName(target.name)
            setDescription(target.description)
            setDueDate(target.dueDate)
            setCompleted(target.completed)
        }
    }
})

//ADD AND UPDATE FUNCTION

const handleSubmit = (evt) => {
    evt.preventDefault()

    const newTask = {
        importance_id:importance_id,
        name:name,
        description:description,
        dueDate:dueDate,
        appUserId:auth.appUserId,// ADD AUTH ONCE AUTH IS COMPLETE
        completed:completed
    }
let url = null;
let method = null;

if(params.taskId !== undefined){
     newTask.taskId = params.taskId;
    url = `http://localhost:8080/api/task/${params.taskId}`
    method = `PUT`
}else {
    url = `http://localhost:8080/api/task`
    method = `POST`
}

fetch(url,{method,
    headers: {
        "Content-Type": "application/json",
        Accept:"application/json",
        Authorization: "Bearer " + auth.auth.user.token//ADD AUTH HERE
    }, body: JSON.stringify(newTask)
})
.then(response => {
    if(response.ok){
        navigate("/tasklist")
        resetState()
    } else {
        response.json()
        .then(errors => {
            if(Array.isArray(errors)){
                setErrors(errors)
            }else {
                setErrors([errors])
            }
        })
    }
})


}

    return(
        <>
       <div>
        <h1>New Task</h1>
        <form onSubmit={handleSubmit}>
            <ul>
            {errors.map(error => <li key={error}>{error}</li>)}
            </ul>

            <div>
                <label>Importance</label>
               <select>
                <option>Essential</option>
                <option>Moderate</option>
                <option>Low</option>
               </select>
            </div>
            
            <div>
                <label>Name</label>
                <input type="text"></input>
            </div>
            
            <div>
                <label>Description</label>
               <textarea></textarea>
            </div>
            
            <div>
                <label>Due Date</label>
                <input type="date"></input>
            </div>
            <div>
                <label>Complete?</label>
                <input type="checkbox"></input>
            </div>
            
            
            <div>   
                <button>Submit</button>
                <button>Cancel</button>
            </div>


        </form>

        </div> 
        </>
    )
}
export default TaskForm