import React, { useEffect, useState } from 'react';
import { Form, Button, Alert, Row, Col, ListGroup } from 'react-bootstrap';
import api from '../../api';

const FunctionOperations = () => {
    const [functions, setFunctions] = useState([]);
    const [func1, setFunc1] = useState('');
    const [func2, setFunc2] = useState('');
    const [operation, setOperation] = useState('add');
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(null);

    const [searchFunc1, setSearchFunc1] = useState('');
    const [searchFunc2, setSearchFunc2] = useState('');

    const [showSuggestions1, setShowSuggestions1] = useState(false);
    const [showSuggestions2, setShowSuggestions2] = useState(false);

    useEffect(() => {
        const fetchFunctions = async () => {
            const response = await api.get('/functions/list');
            if (response && response.data) {
                setFunctions(response.data);
            } else {
                setFunctions([]);
            }
        };
        fetchFunctions();
    }, []);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(null);
        setSuccess(null);

        if (!func1 || !func2 || func1 === func2) {
            setError('Выберите две разные функции');
            return;
        }

        try {
            const response = await api.post(`/functions/operate?funcId1=${func1}&funcId2=${func2}&operation=${operation}`);
            if (response.data) {
                setSuccess(`Новая функция создана: ${response.data.name} (ID: ${response.data.id})`);
            }
        } catch (err) {
            setError(err.response?.data || err.message);
        }
    };

    const filteredFunctionsForFunc1 = functions.filter(f => 
        f.name.toLowerCase().includes(searchFunc1.toLowerCase())
    );
    const filteredFunctionsForFunc2 = functions.filter(f => 
        f.name.toLowerCase().includes(searchFunc2.toLowerCase())
    );

    const handleSelectFunc1 = (func) => {
        setSearchFunc1(func.name);
        setFunc1(func.id); 
        setShowSuggestions1(false);
    };

    const handleSelectFunc2 = (func) => {
        setSearchFunc2(func.name);
        setFunc2(func.id);
        setShowSuggestions2(false);
    };

    return (
        <div>
            <h2>Операции над функциями</h2>
            {error && <Alert variant="danger">{error}</Alert>}
            {success && <Alert variant="success">{success}</Alert>}

            <Form onSubmit={handleSubmit}>
                <Row>
                    <Col md={6}>
                        <Form.Group className="mb-3" controlId="searchFunc1">
                            <Form.Label>Функция 1</Form.Label>
                            <Form.Control
                                type="text"
                                placeholder="Введите название функции"
                                value={searchFunc1}
                                onChange={(e) => {
                                    setSearchFunc1(e.target.value);
                                    setShowSuggestions1(true);
                                }}
                                onFocus={() => setShowSuggestions1(true)}
                                onBlur={(e) => {
                                    setTimeout(() => setShowSuggestions1(false), 200);
                                }}
                            />
                            {showSuggestions1 && filteredFunctionsForFunc1.length > 0 && (
                                <ListGroup style={{ position: 'absolute', zIndex: 1000, width: '100%' }}>
                                    {filteredFunctionsForFunc1.map(func => (
                                        <ListGroup.Item 
                                            key={func.id} 
                                            action 
                                            onMouseDown={() => handleSelectFunc1(func)}
                                        >
                                            {func.name}
                                        </ListGroup.Item>
                                    ))}
                                </ListGroup>
                            )}
                        </Form.Group>
                    </Col>

                    <Col md={6}>
                        <Form.Group className="mb-3" controlId="searchFunc2">
                            <Form.Label>Функция 2</Form.Label>
                            <Form.Control
                                type="text"
                                placeholder="Введите название функции"
                                value={searchFunc2}
                                onChange={(e) => {
                                    setSearchFunc2(e.target.value);
                                    setShowSuggestions2(true);
                                }}
                                onFocus={() => setShowSuggestions2(true)}
                                onBlur={(e) => {
                                    setTimeout(() => setShowSuggestions2(false), 200);
                                }}
                            />
                            {showSuggestions2 && filteredFunctionsForFunc2.length > 0 && (
                                <ListGroup style={{ position: 'absolute', zIndex: 1000, width: '100%' }}>
                                    {filteredFunctionsForFunc2.map(func => (
                                        <ListGroup.Item
                                            key={func.id}
                                            action
                                            onMouseDown={() => handleSelectFunc2(func)}
                                        >
                                            {func.name}
                                        </ListGroup.Item>
                                    ))}
                                </ListGroup>
                            )}
                        </Form.Group>
                    </Col>
                </Row>

                <Form.Group className="mb-3">
                    <Form.Label>Операция</Form.Label>
                    <Form.Control as="select" value={operation} onChange={e=>setOperation(e.target.value)}>
                        <option value="add">Сложение</option>
                        <option value="subtract">Вычитание</option>
                        <option value="multiply">Умножение</option>
                        <option value="divide">Деление</option>
                    </Form.Control>
                </Form.Group>

                <Button variant="primary" type="submit" className="mt-3">Выполнить</Button>
            </Form>
        </div>
    );
};

export default FunctionOperations;
