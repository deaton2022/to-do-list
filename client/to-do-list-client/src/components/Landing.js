import { useEffect, useState } from "react";
import "./Landing.css";
import { Link } from "react-router-dom";

function Landing() {
    const [questions, setQuestions] = useState([]); 
    const [topics, setTopics] = useState([]);
    
    const loadQuestions = () => {
        fetch("http://localhost:8080/api/mini-stack/questions")
        .then(response => response.json())
        .then(payload => setQuestions(payload))
      }

    const [topicImages, setTopicImages] = useState([
        {
            topic: ["funny"],
            imgUrl: "https://images.unsplash.com/photo-1516810714657-e654b97f1d80?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxjb2xsZWN0aW9uLXBhZ2V8MXwxOTA2MzMyfHxlbnwwfHx8fHw%3D&auto=format&fit=crop&w=600&q=60"
        },
        {
            topic: ["party", "birthday"],
            imgUrl: "https://images.unsplash.com/photo-1560173045-beaf11c65dce?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Nnx8YmlydGhkYXl8ZW58MHx8MHx8fDA%3D&auto=format&fit=crop&w=600&q=60"
        },
        {
            topic: ["dinner", "late-night"],
            imgUrl: "https://images.unsplash.com/photo-1491944799262-a5be522e2300?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxjb2xsZWN0aW9uLXBhZ2V8OHwxMDU1NTg4MHx8ZW58MHx8fHx8&auto=format&fit=crop&w=600&q=60"
        },
        {
            topic: ["books", "reading"],
            imgUrl: "https://images.unsplash.com/photo-1488190211105-8b0e65b80b4e?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxjb2xsZWN0aW9uLXBhZ2V8NnwxMDU1NTg4MHx8ZW58MHx8fHx8&auto=format&fit=crop&w=600&q=60"
        },
        {
            topic: ["journal", "writing"],
            imgUrl: "https://images.unsplash.com/photo-1550327149-f5aef60d38f9?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxjb2xsZWN0aW9uLXBhZ2V8MnwxMDU1NTg4MHx8ZW58MHx8fHx8&auto=format&fit=crop&w=600&q=60"
        },
        {
            topic: ["art", "drawing"],
            imgUrl: "https://images.unsplash.com/photo-1505744386214-51dba16a26fc?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxjb2xsZWN0aW9uLXBhZ2V8MXwxMDU1NTg4MHx8ZW58MHx8fHx8&auto=format&fit=crop&w=600&q=60"
        },
    ]);

    useEffect(() => {
        loadQuestions();
    }, []);

    useEffect(() => {
        const topicSet = new Set(questions.map(q => q.topic));
        setTopics(Array.from(topicSet));
      }, [questions]);

    const getTopicImageUrl = (topicName) => {
        topicName = topicName.toLowerCase();
        let search = topicImages.filter(topic => topic.topic.includes(topicName)).map(topic => topic.imgUrl);
        return search;
    }
    
    return (
        <div className="container-fluid-landing">
           
            <section id="topics">
                <section>
                <div className="my-4" id="landing-img-container">
                    <img id="landing-img" className="img-fluid border border-secondary rounded-5"
                        src="https://images.unsplash.com/photo-1508413039422-5463a997c8f3?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=3300&q=80" alt="chat bubble" />
                </div>
                {/* <h2 id="topics-title">Topics</h2> */}
            </section>
                <div className="topics-flex-container">
                {topics ? 
                    topics.map(topic => (
                        <div key={topic} >
                            <h3 className="topic-card-title">{topic}</h3>
                            <img className="topic-img" src={getTopicImageUrl(topic)}/>
                        </div>
                    ))
                    : null
                }
                </div>
            </section>
            <footer id="footer-landing">
                <nav>
                    <a href="mailto:ministack@company.com">Email :  ministack@company.com</a>
                    <div id="landing-links">
                        <Link to="/questionslist">Questions</Link>
                        <Link to="/add">Add Question</Link>
                    </div>
                </nav>
            </footer>
        </div>
    );
}

export default Landing;