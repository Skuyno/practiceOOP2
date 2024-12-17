import React, { useState } from 'react';
import { Button, Modal } from 'react-bootstrap';
import FunctionForm from './FunctionForm';
import FunctionTable from './FunctionTable';
import api from '../../api';

const CreateFunction = () => {
    const [numPoints, setNumPoints] = useState(0);
    const [points, setPoints] = useState([]);
    const [functionName, setFunctionName] = useState('');
    const [x_from, setXFrom] = useState('');
    const [x_to, setXTo] = useState('');
    const [showTable, setShowTable] = useState(false);
    const [error, setError] = useState(null);
    const [showModal, setShowModal] = useState(false);

    const handleSetFunctionData = ({ functionName, number }) => {
        setFunctionName(functionName);
        setNumPoints(number);
        setPoints(Array.from({ length: number }, () => ({ x: '', y: '' })));
        setShowTable(true);
    };

    const handleChange = (index, field, value) => {
        const updatedPoints = [...points];
        updatedPoints[index][field] = value;
        setPoints(updatedPoints);
    };

    const handleSubmit = async () => {
        try {
            const xValues = points.map(p => parseFloat(p.x));
            const yValues = points.map(p => parseFloat(p.y));

            if (xValues.some(isNaN) || yValues.some(isNaN)) {
                throw new Error("Все значения x и y должны быть числами.");
            }

            const x_from = Math.min(...xValues);
            const x_to = Math.max(...xValues);

            const mathFunctionData = {
                name: functionName,
                count: numPoints,
                x_from: parseFloat(x_from),
                x_to: parseFloat(x_to),
            };

            const response = await api.post('/functions', mathFunctionData);
            const functionId = response.data.id;

            for (let i = 0; i < points.length; i++) {
                const pointData = {
                    functionId,
                    x: parseFloat(points[i].x),
                    y: parseFloat(points[i].y),
                };
                await api.post('/points', pointData);
            }

            alert('Табулированная функция успешно создана!');
            setShowTable(false);
            setNumPoints(0);
            setPoints([]);
            setFunctionName('');
            setXFrom('');
            setXTo('');
        } catch (err) {
            setError(err.response?.data || err.message);
            setShowModal(true);
        }
    };

    const handleCloseModal = () => {
        setShowModal(false);
    };

    return (
        <>
            {showTable && (
                <>
                    <FunctionTable points={points} onChange={handleChange} />
                    <Button variant="primary" onClick={handleSubmit} className="mt-3">
                        Создать
                    </Button>
                </>
            )}
            <Modal show={showModal} onHide={handleCloseModal} centered>
                <Modal.Header closeButton>
                    <Modal.Title>Ошибка</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    {error}
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="danger" onClick={handleCloseModal}>
                        Закрыть
                    </Button>
                </Modal.Footer>
            </Modal>
        </>
    );
};

export default CreateFunction;
