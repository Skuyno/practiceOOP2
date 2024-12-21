import React, { useState } from 'react';
import { Form, Button, Alert, Card } from 'react-bootstrap';
import api from '../../api';
import { useNavigate } from 'react-router-dom';

const RegisterForm = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(null);
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (password !== confirmPassword) {
            setError("Пароли не совпадают");
            return;
        }
        try {
            await api.post('/auth/register', { username, password });
            setSuccess("Регистрация прошла успешно");
            setError(null);
            setTimeout(() => {
                navigate('/login');
            }, 2000);
        } catch (err) {
            setError(err.response?.data || 'Ошибка при регистрации');
            setSuccess(null);
        }
    };

    return (
        <Card className="mx-auto" style={{ maxWidth: '400px' }}>
            <Card.Body>
                <Card.Title>Регистрация</Card.Title>
                {error && <Alert variant="danger">{error}</Alert>}
                {success && <Alert variant="success">{success}</Alert>}
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

                    <Form.Group controlId="formConfirmPassword" className="mt-3">
                        <Form.Label>Подтвердите пароль</Form.Label>
                        <Form.Control 
                            type="password" 
                            placeholder="Подтвердите пароль" 
                            value={confirmPassword} 
                            onChange={(e) => setConfirmPassword(e.target.value)} 
                            required 
                        />
                    </Form.Group>

                    <Button variant="success" type="submit" className="mt-3" block>
                        Зарегистрироваться
                    </Button>
                </Form>
            </Card.Body>
        </Card>
    );
};

export default RegisterForm;