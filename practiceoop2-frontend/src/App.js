import React, { useState, useEffect } from 'react';
import { Container } from 'react-bootstrap';
import NavigationBar from './components/Navbar';
import LoginForm from './components/Auth/LoginForm';
import RegisterForm from './components/Auth/RegisterForm';
import CreateFunction from './components/CreateFunction/CreateFunction';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';

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
            <Container className="mt-5">
                <Routes>
                    <Route 
                        path="/login" 
                        element={ isAuthenticated ? <Navigate to="/create-function" /> : <LoginForm onLogin={handleLogin} /> } 
                    />
                    <Route 
                        path="/register" 
                        element={ isAuthenticated ? <Navigate to="/create-function" /> : <RegisterForm /> } 
                    />
                    <Route 
                        path="/create-function" 
                        element={ isAuthenticated ? <CreateFunction /> : <Navigate to="/login" /> } 
                    />
                    <Route 
                        path="/" 
                        element={ isAuthenticated ? <Navigate to="/create-function" /> : <Navigate to="/login" /> } 
                    />
                    <Route path="*" element={<h2>404 - Страница не найдена</h2>} />
                </Routes>
            </Container>
        </Router>
    );
}

export default App;
