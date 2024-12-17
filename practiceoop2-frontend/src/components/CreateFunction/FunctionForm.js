import React, { useState } from 'react';
import { Form, Button, Card } from 'react-bootstrap';

const FunctionForm = ({ onSubmit }) => {
    const [numPoints, setNumPoints] = useState('');
    const [functionName, setFunctionName] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        const number = parseInt(numPoints, 10);
        if (isNaN(number) || number <= 0) {
            alert("Количество точек должно быть положительным числом.");
            return;
        }
        if (!functionName.trim()) {
            alert("Название функции не может быть пустым.");
            return;
        }
        onSubmit({ functionName, number });
    };

    return (
        <Card className="mb-3">
            <Card.Body>
                <Form onSubmit={handleSubmit}>
                    {/* Поле для имени функции */}
                    <Form.Group controlId="functionName">
                        <Form.Label>Название функции</Form.Label>
                        <Form.Control
                            type="text"
                            value={functionName}
                            onChange={(e) => setFunctionName(e.target.value)}
                            placeholder="Введите название функции"
                            required
                        />
                    </Form.Group>

                    {/* Поле для количества точек */}
                    <Form.Group controlId="numPoints">
                        <Form.Label>Количество точек</Form.Label>
                        <Form.Control
                            type="number"
                            value={numPoints}
                            onChange={(e) => setNumPoints(e.target.value)}
                            placeholder="Введите количество точек"
                            required
                            min="1"
                        />
                    </Form.Group>
                    <Button variant="success" type="submit" className="mt-3">
                        Создать Таблицу
                    </Button>
                </Form>
            </Card.Body>
        </Card>
    );
};

export default FunctionForm;
