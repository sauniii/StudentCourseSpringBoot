import React from 'react';
import { Link, Route, Routes } from 'react-router-dom';
import "bootstrap/dist/css/bootstrap.min.css"; 

//import './App.css';
  
import Home from './components/Home';
import Student from './components/Student';
import Course from './components/Course';

function App() {
  return (
    <div className="App">
      <header className="App-header">
      <nav className="navbar navbar-expand navbar-dark bg-dark">
        <div className="navbar-nav ml-auto pr-10">
          <li className="nav-item mr-4">
            <Link to={"/students"} className="nav-link">
              Students
            </Link>
          </li>
          <li className="nav-item">
            <Link to={"/courses"} className="nav-link">
              Courses
            </Link>
          </li>
        </div>
      </nav> 
      </header>
 
      <div className="container mt-3">

        <Routes> 
          <Route path="/" element={<Home/>} />
          <Route path="/students" element={<Student/>} />  
          <Route path="/courses" element={<Course/>} />
{/*           <Route path="/add" element={<AddTutorial/>} />
          <Route path="/tutorials/:id" element={<Tutorial/>} /> */}
        </Routes>
      </div> 
    </div> 
  );
}

export default App;
