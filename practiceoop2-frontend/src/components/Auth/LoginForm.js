import React, { useState } from 'react';
import { Form, Button, Alert, Card } from 'react-bootstrap';
import api from '../../api';
import { useNavigate } from 'react-router-dom';

const LoginForm = ({ onLogin }) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await api.post('/auth/login', { username, password });
            const token = response.data.token;
            localStorage.setItem('jwtToken', token);
            onLogin(token);
            navigate('/create-function');
        } catch (err) {
            setError(err.response?.data || 'Ошибка при входе');
        }
    };

    return (
        <Card className="mx-auto" style={{ maxWidth: '400px' }}>
            <Card.Body>
                <Card.Title>Вход</Card.Title>
                {error && <Alert variant="danger">{error}</Alert>}
                <Form onSubmit={handleSubmit}>
                    <Form.Group controlId="formUsername">
                        <Form.Label>Логин</Form.Label>
                        <Form.Control 
                            type="text" 
                            placeholder="Введите логин" 
                            value={username} 
                            onChange={(e) => setUsername(e.target.value)} 
                            required 
                        />
                    </Form.Group>

                    <Form.Group controlId="formPassword" className="mt-3">
                        <Form.Label>Пароль</Form.Label>
                        <Form.Control 
                            type="password" 
                            placeholder="Пароль" 
                            value={password} 
                            onChange={(e) => setPassword(e.target.value)} 
                            required 
                        />
                    </Form.Group>

                    <Button variant="primary" type="submit" className="mt-3" block>
                        Войти
                    </Button>
                </Form>
            </Card.Body>
        </Card>
    );
};

export default LoginForm;