import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import NavigationBar from './components/NavigationBar';
import LoginForm from './components/Auth/LoginForm';
import RegisterForm from './components/Auth/RegisterForm';
import CreateFunction from './components/CreateFunction/CreateFunction';
import FunctionList from './components/OperateFunction/FunctionList';
import ViewFunction from './components/OperateFunction/ViewFunction';
import FunctionOperations from './components/OperateFunction/FunctionOperations';
import HomePage from './components/HomePage';

function App() {
    const [isAuthenticated, setIsAuthenticated] = useState(false);

    useEffect(() => {
        const token = localStorage.getItem('jwtToken');
        if (token) {
            setIsAuthenticated(true);
        }
    }, []);

    const handleLogin = (token) => {
        setIsAuthenticated(true);
    };

    const handleLogout = () => {
        localStorage.removeItem('jwtToken');
        setIsAuthenticated(false);
    };

    return (
        <Router>
            <NavigationBar isAuthenticated={isAuthenticated} onLogout={handleLogout} />
            <div className="container mt-3">
                <Routes>
                    <Route path="/login" element={<LoginForm onLogin={handleLogin} />} />
                    <Route path="/register" element={<RegisterForm />} />
                    <Route path="/" element={<HomePage />} />
                    <Route path="/create-function" element={
                        isAuthenticated ? <CreateFunction /> : <LoginForm onLogin={handleLogin} />
                    } />
                    <Route path="/functions" element={
                        isAuthenticated ? <FunctionList /> : <LoginForm onLogin={handleLogin} />
                    } />
                    <Route path="/view-function/:id" element={
                        isAuthenticated ? <ViewFunction /> : <LoginForm onLogin={handleLogin} />
                    } />
                    <Route path="/operate-functions" element={
                        isAuthenticated ? <FunctionOperations /> : <LoginForm onLogin={handleLogin} />
                    } />
                </Routes>
            </div>
        </Router>
    );
}

export default App;
