import React, { useState } from 'react';
import { Form, Button, Card } from 'react-bootstrap';

const FunctionForm = ({ onSubmit }) => {
    const [numPoints, setNumPoints] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        const number = parseInt(numPoints, 10);
        if (isNaN(number) || number <= 0) {
            alert("Количество точек должно быть положительным числом.");
            return;
        }
        onSubmit(number);
    };

    return (
        <Card className="mb-3">
            <Card.Body>
                <Form onSubmit={handleSubmit}>
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
